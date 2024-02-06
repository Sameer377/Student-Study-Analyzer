package com.page_1.app.teacher;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.Selection;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.page_1.app.DisplayFiles.model;
import com.page_1.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;

public class teacher_upload extends AppCompatActivity implements View.OnClickListener , EasyPermissions.PermissionCallbacks {
    private ImageButton existingF;
    private ScrollView scLinks;
    private ImageButton btn_teacher_upload_back;
    private Button btn_teacher_upload_browse, btn_teacher_upload_upload;
    private TextView tv_subjectName_teacher_upload;
    private TextView tv_year_teacher_upload;
    private TextView tv1,tv_filename1;
    private ProgressDialog progressDialog;
    private String user_url_key;
    private TextInputEditText tf_URLMAIN;
    public String getIntentType() {
        return intentType;
    }
    private DatabaseReference dbr;
    public void setIntentType(String intentType) {
        this.intentType = intentType;
    }

    public String intentType;
    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }

    private  StorageReference storageReference;
    private TextInputEditText tf_URLNAME;
    private Uri imageUri;
    //for URL
    private TextInputLayout url_name;
    private TextInputLayout url_main;
    private String URLNAME_str;
    private String URL_str;
    //for assignment
    private  TextView attachfile;
    private  TextView file_namesTV;
    private  ImageButton clear;
    private  ImageView pasteLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_upload);
        initUI();
        initListener();
        folderSpinner();
        setDetails();
    }

    private String assesment;
    String tutorialsName;
    ClipboardManager myClipboard = null;
    private void folderSpinner() {
        if (!EasyPermissions.hasPermissions(teacher_upload.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, 41, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        Spinner spinner = findViewById(R.id.student_spinner);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("select");
        arrayList.add("Assignments");
        arrayList.add("Notes");
        arrayList.add("Links");
        arrayList.add("Videos");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tutorialsName = parent.getItemAtPosition(position).toString();

//procedure
                if(tutorialsName.equals("select"))
                {
                    scLinks.setVisibility(View.GONE);
                    existingF.setVisibility(View.GONE);
                    btn_teacher_upload_browse.setVisibility(View.GONE);
                    btn_teacher_upload_upload.setVisibility(View.INVISIBLE);
                    file_namesTV.setText("Select File ");

                }
                else if(tutorialsName.equals("Links"))
                {
                    scLinks.setVisibility(View.VISIBLE);
                    existingF.setVisibility(View.GONE);
                    tv_filename1.setVisibility(View.GONE);
                    url_name.setVisibility(View.VISIBLE);
                    url_main.setVisibility(View.VISIBLE);
                    file_namesTV.setVisibility(View.GONE);
                    btn_teacher_upload_upload.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.GONE);
                    attachfile.setVisibility(View.GONE);
                    btn_teacher_upload_browse.setVisibility(View.GONE);
                    myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                    pasteLink.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ClipData abc = myClipboard.getPrimaryClip();
                            ClipData.Item item = abc.getItemAt(0);
                            if (myClipboard != null && myClipboard.getPrimaryClip() != null && myClipboard.getPrimaryClip().getItemCount() > 0) {
                                tf_URLMAIN.setText(item.getText().toString().trim());
                            }

                            int position1 = tf_URLMAIN.length();
                            Editable etext = tf_URLMAIN.getText();
                            Selection.setSelection(etext, position1);
                            tf_URLMAIN.setSelection(position1);
                        }
                    });
                    btn_teacher_upload_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //code
                            //Links

                            if(((tf_URLMAIN.getText().toString().trim()).equals("")) || ((tf_URLNAME.getText().toString().trim()).equals("")))
                            {
                                Toast.makeText(teacher_upload.this,"Fill details",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Map<String,Object> map=new HashMap<String, Object>();
                                user_url_key=dbr.push().getKey();
                                dbr.updateChildren(map);
                                DatabaseReference dbr2 = dbr.child(user_url_key);
                                String [] F_URL=(tf_URLMAIN.getText().toString().trim()).split("://");
                                Map<String, Object> map2 = new HashMap<String, Object>();
                               int uuuuu=F_URL.length;
                               if(uuuuu==1){
                                   map2.put("url", F_URL[0]);
                               }
                               else {
                                   map2.put("url", F_URL[1]);
                               }


                                map2.put("Name", tf_URLNAME.getText().toString().trim());
                                dbr2.updateChildren(map2);
                                Toast.makeText(teacher_upload.this, "Link Uploaded", Toast.LENGTH_LONG).show();
                                tf_URLMAIN.setText(null);
                                tf_URLNAME.setText(null);
                            }
//
                        }
                    });
                }
                else{
                    scLinks.setVisibility(View.GONE);
                    existingF.setVisibility(View.VISIBLE);
                    existingF.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showFolderList();
                        }
                    });
                    btn_teacher_upload_upload.setVisibility(View.INVISIBLE);
                    tv_filename1.setVisibility(View.VISIBLE);
                    file_namesTV.setText("");
                    url_name.setVisibility(View.GONE);
                    url_main.setVisibility(View.GONE);
                    file_namesTV.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.VISIBLE);
                    attachfile.setVisibility(View.VISIBLE);
                    btn_teacher_upload_browse.setVisibility(View.VISIBLE);

                    btn_teacher_upload_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //code
                            _funcUploadFiles(getArrayListFileObjects());

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    private void showFolderList() {
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(teacher_upload.this);

        builderSingle1.setTitle("Select Folder");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(teacher_upload.this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("+ Add New ");
            databaseReference= FirebaseDatabase.getInstance().getReference("Database").child(teachingYear).child(SubjectName).child(tutorialsName);


            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                       String key = ds.getKey();

                        arrayAdapter.add(key);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };

            databaseReference.addListenerForSingleValueEvent(eventListener);


        builderSingle1.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle1.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
            if(strName.equals("+ Add New ")){
                showdialogB();
            }else {
                Assignmentadd=strName;
            }
            }
        });
        builderSingle1.show();
    }


    DatabaseReference databaseReference;
    ProgressDialog progress1;
    public void openProgressDialog() {
        progress1 = new ProgressDialog(teacher_upload.this );
        progress1.setMessage("Please wait.......");
        progress1.setCancelable(false);
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress1.setIndeterminate(true);
        progress1.show();
    }
    public void initUI() {
        scLinks=findViewById(R.id.scLinks);
        pasteLink=findViewById(R.id.pasteLink);
        existingF=findViewById(R.id.existingF);
        btn_teacher_upload_upload = findViewById(R.id.btn_teacher_upload_upload);
        btn_teacher_upload_back = findViewById(R.id.btn_teacher_upload_back);
        btn_teacher_upload_browse = findViewById(R.id.btn_teacher_upload_browse);
        tv_subjectName_teacher_upload = findViewById(R.id.tv_subjectName_teacher_upload);
        tv_year_teacher_upload = findViewById(R.id.tv_year_teacher_upload);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        url_name=findViewById(R.id.layURlname);
        url_main=findViewById(R.id.layURl);
        tf_URLNAME=(TextInputEditText) findViewById(R.id.tf_URLNAME);
        tf_URLMAIN= ( TextInputEditText) findViewById(R.id.tf_URL_MAIN);
        file_namesTV=findViewById(R.id.tv_filename);
        attachfile=findViewById(R.id.tv_1);
        clear=findViewById(R.id.clear);
        URLNAME_str=tf_URLNAME.getText().toString().trim();
        URL_str=tf_URLMAIN.getText().toString().trim();
        tv1=findViewById(R.id.tv_1);
        tv_filename1=findViewById(R.id.tv_filename1);
        //firebase path
        dbr= FirebaseDatabase.getInstance().getReference("Database").child(teachingYear).child(SubjectName).child("Links");

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayListFileObjects!=null){
                    file_namesTV.setText("");
                    arrayListFileObjects.clear();
                    tv_filename1.setText("select Files");
                }

            }
        });

    }

    public void initListener() {
        btn_teacher_upload_back.setOnClickListener(this);
        btn_teacher_upload_browse.setOnClickListener(this);
        btn_teacher_upload_upload.setOnClickListener(this);
    }

    private void setDetails() {
        tv_subjectName_teacher_upload.setText(SubjectName);
        tv_year_teacher_upload.setText(teachingYear);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_teacher_upload_back:
                finish();

                break;
            case R.id.btn_teacher_upload_browse:
//                selectImage();
                btn_teacher_upload_upload.setVisibility(View.VISIBLE);
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                if(tutorialsName.equals("Videos"))
                                {
                                    intentType="video/*";
                                }
                                else
                                {
                                    intentType="*/*";
                                }
                                intent.setType(intentType);
                                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Please Select Multiple Files"), 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();

                break;

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    boolean isPermissionGranted = false;

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        isPermissionGranted = true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        isPermissionGranted = false;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout1, fragment);
        fragmentTransaction.commit();
    }


//    ProgressDialog progress;

//firebase.............
/*
    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        String tech="TeacherResponse";
        storageReference = FirebaseStorage.getInstance().getReference("CO/"+teachingYear+"/"+SubjectName+"/"+tutorialsName+"/TeacherResponse/"+fileName);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                model obj=new model("Hello",uri.toString(),0,0,0);
                                databaseReference.child(databaseReference.push().getKey()).setValue(obj);
                                if (progressDialog.isShowing())
                                    progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"File Uploaded",Toast.LENGTH_LONG).show();

                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(teacher_upload.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        //calculating progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        //displaying percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                });

    }
*/

//    private void selectImage() {
//
//        Intent intent = new Intent();
//        intent.setType("*/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//
//    startActivityForResult(intent,100);
//
//}

    String Filename;
    String FilePath;

    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 100 && data != null && data.getData() != null){

                imageUri = data.getData();
                FilePath=imageUri.getPath();
                File file = new File(FilePath);
                Filename=file.getName();
                file_name = file_name + Filename + "\n";

                Log.i("TAG","File Name :"+Filename);

            }
        }

    */
    String filest;
    ArrayList arrayListFileObjects=null;

    public ArrayList getArrayListFileObjects() {
        return arrayListFileObjects;
    }

    public void setArrayListFileObjects(ArrayList arrayListFileObjects) {
        this.arrayListFileObjects = arrayListFileObjects;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //for selecting single file data.getClipData() method returns null as the action mentioned
        //for launching the image picker intent is EXTRA_ALLOW_MULTIPLE=true.
        //so for single image selection data.getData() method returns path of the single image
        //so condition should be first if(data.getClipData()!=null) then in else if we should check for
        //else if(data.getData()!=null)


        arrayListFileObjects=new ArrayList();
        if(requestCode==101 && resultCode==RESULT_OK)
        {
            if(data.getClipData()!=null) //condition for multiple images selected
            {
                for(int i=0; i<data.getClipData().getItemCount();i++)
                {
                    arrayListFileObjects.add(i,data.getClipData().getItemAt(i).getUri());

                }
                String fff1="",filename="l";
                setArrayListFileObjects(arrayListFileObjects);
                for(int i=0; i<arrayListFileObjects.size();i++) {
                    Uri fileuri = (Uri) arrayListFileObjects.get(i);
                    filename = getfilenamefromuri(fileuri);
                    fff1=fff1+filename+"\n";
                }
                tv_filename1.setVisibility(View.VISIBLE);
                file_namesTV.setVisibility(View.GONE);
                tv_filename1.setText(fff1);
                tv_filename1.setMovementMethod(new ScrollingMovementMethod());
            }
            else if(data.getData()!=null){ //condition for single image selected
                Uri uri=(Uri)data.getData();
                arrayListFileObjects.add(0,uri);
                String fff1="",filename="l";
                setArrayListFileObjects(arrayListFileObjects);
                for(int i=0; i<arrayListFileObjects.size();i++) {
                    Uri fileuri = (Uri) arrayListFileObjects.get(i);
                    filename = getfilenamefromuri(fileuri);
                    fff1=fff1+filename+"\n";
                }
                tv_filename1.setVisibility(View.VISIBLE);
                file_namesTV.setVisibility(View.GONE);
                tv_filename1.setText(fff1);
                tv_filename1.setMovementMethod(new ScrollingMovementMethod());
            }

        }


    }
    int index=0;

    public String getRel_ffname() {
        return rel_ffname;
    }

    public void setRel_ffname(String rel_ffname) {
        this.rel_ffname = rel_ffname;
    }

    String rel_ffname;
    public String file_name;
    String fff="";
    public int fileno=0;
    double progress;
    public void _funcUploadFiles(ArrayList arrayList)
    {


//        if(Assignmentadd.equals(null))
//        {
//            showdialogB();
//        }
//       else
      if(arrayList.size()==0)
        {
            Toast.makeText(teacher_upload.this,"Select Files",Toast.LENGTH_SHORT).show();
        }
      else if(Assignmentadd==null||Assignmentadd.equals("")||Assignmentadd==""){
          showFolderList();
      }
        else
        {
            openProgressDialog();
            databaseReference= FirebaseDatabase.getInstance().getReference("Database").child(teachingYear).child(SubjectName).child(tutorialsName).child(Assignmentadd);

            for(int i=0; i<arrayList.size();i++)
            {

                fileno=fileno+1;

                Uri fileuri=(Uri)arrayList.get(i);
                String filename=getfilenamefromuri(fileuri);

                fff=fff+"\n"+filename;
                setRel_ffname(fff);

                index=i;
                storageReference = FirebaseStorage.getInstance().getReference("CO/"+teachingYear+"/"+SubjectName+"/"+tutorialsName+"/TeacherResponse/"+filename);
                Log.v("TAG","SetMSG :"+filename);
                progress1.setMessage(filename+" Uploading...");
                setStorageReference(storageReference);
                StorageReference st=storageReference;
                storageReference.putFile(fileuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                tv_filename1.setText("");
                                tv_filename1.setVisibility(View.GONE);
                                file_namesTV.setText("Select File ");
                                file_namesTV.setVisibility(View.VISIBLE);
                                st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        arrayListFileObjects.clear();
                                        Log.i("TAG","File path ..............:"+getStorageReference());
                                        model obj=new model(filename,uri.toString(),0,0,0);
                                        databaseReference.child(databaseReference.push().getKey()).setValue(obj);
                                        Toast.makeText(teacher_upload.this, filename+" Uploaded",          Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("TAG","File path ..............:"+e);

                                        Toast.makeText(teacher_upload.this,"Db Node Not seted",Toast.LENGTH_LONG).show();

                                    }
                                });
                                if(index==arrayList.size()-1)
                                {
                                    file_name=filename;
                                    progress1.dismiss();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progress1.isShowing()){
                            progress1.dismiss();}
                        Toast.makeText(teacher_upload.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }

                });


            }
        }


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        file_namesTV.setText(getRel_ffname());
    }

    public String getMyData() {
        return file_name;
    }
    public String getfilenamefromuri(Uri filepath)
    {
        String result = null;
        if (filepath.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = filepath.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
    String Assignmentadd;
    private void showdialogB() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(teacher_upload.this);
        alertDialog.setTitle("Folder ");
        alertDialog.setMessage("Upload as ");

        final EditText input = new EditText(teacher_upload.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Assignmentadd=input.getText().toString().trim();
                    }
                });

        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }



}
