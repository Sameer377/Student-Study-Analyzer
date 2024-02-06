package com.page_1.app.student;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pub.devrel.easypermissions.EasyPermissions;

import static com.page_1.app.student.DisplaySubjectsS.SelectedSubject;
import static com.page_1.app.student.student_menu.Year;
import static com.page_1.app.student.student_menu.fullName;
//   databaseReference= FirebaseDatabase.getInstance().getReference("Database2").child(Year).child(SubjectName).child("Assignments").child(getAssignment__No()).child(fullName);
//   storageReference = FirebaseStorage.getInstance().getReference("CO/"+Year+"/"+SubjectName+"/Assignments/"+Assignment__No+"/StudentResponse/"+fileName);
public class StudentUpload extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
    private ImageButton btn_teacher_upload_back,clear;
    private Button btn_teacher_upload_browse;
    private Button btn_teacher_upload_upload;
    private ImageButton AssignmentList;
    private TextView tv_filename;
    RelativeLayout subdet;
    private TextView stud_up_sub,textView3ass;
    private TextView student_up_year;
    private Button spinner;
    private String SubjectName;
    private DatabaseReference databaseReference;
    private String key;
    private DatabaseReference databaseReference1;

    public String getAssignment__No() {
        return Assignment__No;
    }

    public void setAssignment__No(String assignment__No) {
        Assignment__No = assignment__No;
    }

    private String Assignment__No;
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
    ProgressDialog progress1;
    public StorageReference getStorageReference() {
        return storageReference;
    }

    public void setStorageReference(StorageReference storageReference) {
        this.storageReference = storageReference;
    }
    public void openProgressDialog() {
        progress1 = new ProgressDialog(StudentUpload.this );
        progress1.setMessage("Please wait.......");
        progress1.setCancelable(false);
        progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress1.setIndeterminate(true);
        progress1.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_upload);
        getSupportActionBar().hide();

        initUI();
        initListener();
//        setSub();
        setDetails();
    }
    private ArrayList<String> arrayList1;

    public void initUI() {
        subdet=findViewById(R.id.subdet);
        AssignmentList=findViewById(R.id.AssignmentList);
        textView3ass=findViewById(R.id.textView3ass);
        btn_teacher_upload_back = findViewById(R.id.btn_teacher_upload_back);
        btn_teacher_upload_browse = findViewById(R.id.btn_teacher_upload_browse);
        btn_teacher_upload_upload=findViewById(R.id.btn_teacher_upload_upload);
        tv_filename=findViewById(R.id.tv_filename1);
        stud_up_sub=findViewById(R.id.stud_up_sub);
        student_up_year=findViewById(R.id.student_up_year);
         spinner = findViewById(R.id.student_spinner);
         //firebase variable
        storageReference= FirebaseStorage.getInstance().getReference();
        clear=findViewById(R.id.clear);
        AssignmentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFolderList();
            }
        });
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSub2();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayListFileObjects!=null){
                    arrayListFileObjects.clear();
                    tv_filename.setText("Select File");
                }

            }
        });
    }
    public void initListener() {
        btn_teacher_upload_back.setOnClickListener(this);
        btn_teacher_upload_browse.setOnClickListener(this);
        btn_teacher_upload_upload.setOnClickListener(this);
    }
    public void setDetails(){
        student_up_year.setText(Year);
    }
    public void setSub2(){
        AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(StudentUpload.this);

        builderSingle1.setTitle("Select Subject");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentUpload.this, android.R.layout.select_dialog_item);
        databaseReference = FirebaseDatabase.getInstance().getReference("Database").child(Year);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();

                    arrayAdapter.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        databaseReference.addListenerForSingleValueEvent(eventListener);


        builderSingle1.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spinner.setText("select Subject");
                SubjectName=null;
                dialog.dismiss();
            }
        });

        builderSingle1.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                spinner.setText(strName);
                SubjectName=strName;

            }
        });
        builderSingle1.show();
    }
//    public void setSub(){
//        String Subject="First year";
//        arrayList1 = new ArrayList<>();
//        switch(Year){
//            case "First year":
//
//                arrayList1.add("select");
//                arrayList1.add("22001 - Fundamentals of ICT ");
//                arrayList1.add("22003 - Engineering graphics ");
//                arrayList1.add("22005 - Workshop practice ");
//                arrayList1.add("22101 - English ");
//                arrayList1.add("22102 - Basic science ");
//                arrayList1.add("22103 - Basic mathematics ");
//                arrayList1.add("22215 - Elements of electrical engineering");
//                arrayList1.add("22224 - Applied mathematics");
//                arrayList1.add("22225 - Basic electronics");
//                arrayList1.add("22226 - Programming in C");
//                arrayList1.add("22009 - Business communication using computers");
//                arrayList1.add("22013 - Computer peripherals and hardware maintenance ");
//                arrayList1.add("22014 - Web page designing with HTML ");
//                ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(StudentUpload.this,android.R.layout.simple_spinner_item,arrayList1);
//                arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(arrayAdapter1);
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        SubjectName = parent.getItemAtPosition(position).toString();
//                        stud_up_sub.setText(SubjectName);
//                        addValueToList(SubjectName);
//                        keys2.clear();
//                        if(SubjectName.equals("select"))
//                        {
//                            tv_filename.setText("Plz Select Subject ");
//                            btn_teacher_upload_browse.setEnabled(false);
//                            btn_teacher_upload_upload.setEnabled(false);
//                        }
//                        else{
//                            tv_filename.setText("");
//                            btn_teacher_upload_browse.setEnabled(true);
//                            btn_teacher_upload_upload.setEnabled(true);
//
//                        }
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView <?> parent) {
//                    }
//                });
//                break;
//            case "Second year":
//                ArrayList<String> arrayList2 = new ArrayList<>();
//                arrayList2.add("select");
//                arrayList2.add("22316 - Object oriented programming");
//                arrayList2.add("22317 - Data structure using c");
//                arrayList2.add("22318 - Computer graphics");
//                arrayList2.add("22319 - Database management system");
//                arrayList2.add("22320 - Digital techniques");
//                arrayList2.add("22412 - Java Programming");
//                arrayList2.add("22413 - Software Engineer");
//                arrayList2.add("22414 - Data communication and computer network             ");
//                arrayList2.add("22415 - MICROPROCESSOR");
//                arrayList2.add("22034 - GUI Application development using VB.Net");
//                ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(StudentUpload.this,android.R.layout.simple_spinner_item,arrayList2);
//                arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(arrayAdapter2);
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        SubjectName = parent.getItemAtPosition(position).toString();
//                        stud_up_sub.setText(SubjectName);
//                        addValueToList(SubjectName);
//                        keys2.clear();
////                        setUploadAs();
//                        if(SubjectName.equals("select"))
//                        {
//                            tv_filename.setText("Plz Select Subject ");
//                            btn_teacher_upload_browse.setEnabled(false);
//                            btn_teacher_upload_upload.setEnabled(false);
//                        }
//                        else{
//                            tv_filename.setText("");
//                            btn_teacher_upload_browse.setEnabled(true);
//                            btn_teacher_upload_upload.setEnabled(true);
//
//                        }
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView <?> parent) {
//                    }
//                });
//                break;
//            case "Third year":
//                ArrayList<String> arrayList3 = new ArrayList<>();
//                arrayList3.add("select");
//                arrayList3.add("22447 - Environmental Studies");
//                arrayList3.add("22516 - Operating Systems");
//                arrayList3.add("22517 - Advanced Java Programming");
//                arrayList3.add("22518 - Software Testing");
//                arrayList3.add("22520 - Advance computer network");
//                arrayList3.add("22057 - Industrial Training");
//                arrayList3.add("22509 - Management");
//                arrayList3.add("22616 - Programming With Python");
//                arrayList3.add("22617 - Mobile Application Development");
//                arrayList3.add("22618 - Emerging Trends in Computer and Information Technology");
//                arrayList3.add("22032 - Entrepreneurship Development");
//                ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(StudentUpload.this,android.R.layout.simple_spinner_item,arrayList3);
//                arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner.setAdapter(arrayAdapter3);
//                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        SubjectName = parent.getItemAtPosition(position).toString();
//                        stud_up_sub.setText(SubjectName);
//                        addValueToList(SubjectName);
//                        keys2.clear();
////                        setUploadAs();
//                        if(SubjectName.equals("select"))
//                        {
//                            tv_filename.setText("Plz Select Subject ");
//                            btn_teacher_upload_browse.setEnabled(false);
//                             btn_teacher_upload_upload.setEnabled(false);
//                        }
//                        else{
//                            tv_filename.setText("");
//                            btn_teacher_upload_browse.setEnabled(true);
//                            btn_teacher_upload_upload.setEnabled(true);
//
//                        }
//                    }
//                    @Override
//                    public void onNothingSelected(AdapterView <?> parent) {
//                    }
//                });
//                break;
//
//        }
//    }
    private void showFolderList() {
        if(SubjectName==null||SubjectName=="")
        {
            Toast.makeText(StudentUpload.this,"Select Subject",Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder builderSingle1 = new AlertDialog.Builder(StudentUpload.this);

            builderSingle1.setTitle("Select Folder");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(StudentUpload.this, android.R.layout.select_dialog_item);
            databaseReference = FirebaseDatabase.getInstance().getReference("Database").child(Year).child(SubjectName).child("Assignments");


            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getChildren();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();

                        arrayAdapter.add(key);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
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
                    stud_up_sub.setText(strName);
                    setAssignment__No(strName);

                }
            });
            builderSingle1.show();
        }
    }
//    @Override
//    protected void onResume() {
//
//        super.onResume();
//        ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(StudentUpload.this,android.R.layout.simple_spinner_item,keys2);
//        arrayAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        SpinnerUPLOADAS.setAdapter(arrayAdapter4);
//        SpinnerUPLOADAS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Assignment__No = parent.getItemAtPosition(position).toString();
//                setAssignment__No(Assignment__No);
//                textView3ass.setText(Assignment__No);
//            }
//            @Override
//            public void onNothingSelected(AdapterView <?> parent) {
//            }
//        });
//        if(keys2.size()==0)
//        {
//            SpinnerUPLOADAS.setVisibility(View.GONE);
//            tv_filename.setText("Assignments not Found !!!");
//            btn_teacher_upload_upload.setEnabled(false);
//        }
//        else{
//        }
//    }
//Browsing variables
String tutorialsName;
    public String intentType;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_teacher_upload_back:
                finish();
                break;
            case R.id.btn_teacher_upload_browse:
//                    selectImage();
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent();
                                intent.setType("*/*");
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
            case R.id.btn_teacher_upload_upload:
//                if(imageUri==null)
//                {
//                    Toast.makeText(StudentUpload.this,"Files not selected !!",Toast.LENGTH_SHORT).show();
//                }
//                else {
////                    uploadImage();
                    _funcUploadFiles(getArrayListFileObjects());
//                }

                break;
        }
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

    public  ArrayList<String> keys2 = new ArrayList<String>();
    public void addValueToList(String SubjectName)
    {

        databaseReference1= FirebaseDatabase.getInstance().getReference("Database").child(Year).child(SubjectName).child("Assignments");


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();

                    keys2.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference1.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        keys2.clear();
    }
//firebase.............
public static String file_name;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private Uri imageUri;

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
                tv_filename.setText(fff1);
                tv_filename.setMovementMethod(new ScrollingMovementMethod());
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
                tv_filename.setText(fff1);
                tv_filename.setMovementMethod(new ScrollingMovementMethod());
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
    String fff="";
    public int fileno=0;
    double progress;
    public void _funcUploadFiles(ArrayList arrayList)
    {
        if(arrayListFileObjects==null || arrayListFileObjects.size()==0)
        {
            Toast.makeText(StudentUpload.this,"Select\nFiles",Toast.LENGTH_SHORT).show();
        }
        else if( SubjectName ==null){
            Toast.makeText(StudentUpload.this,"Select\nSubjects",Toast.LENGTH_SHORT).show();
        }
        else if(Assignment__No==null){
            Toast.makeText(StudentUpload.this,"Select\nAssignment",Toast.LENGTH_SHORT).show();
        }
        else {


            databaseReference = FirebaseDatabase.getInstance().getReference("Database2").child(Year).child(SubjectName).child("Assignments").child(getAssignment__No()).child(fullName);

            openProgressDialog();


            for (int i = 0; i < arrayList.size(); i++) {
                fileno = fileno + 1;
//    progressDialog = new ProgressDialog(this);
//
//    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//    progressDialog.setCancelable(false);
//    progressDialog.show();
                //  arrayListFileObjects.add(i,data.getClipData().getItemAt(i).getUri());

                Uri fileuri = (Uri) arrayList.get(i);
                String filename = getfilenamefromuri(fileuri);

                fff = fff + "\n" + filename;
                setRel_ffname(fff);
                progress1.setMessage(filename + " Uploading...");
                index = i;
                storageReference = FirebaseStorage.getInstance().getReference("CO/" + Year + "/" + SubjectName + "/Assignments/" + Assignment__No + "/StudentResponse/" + filename);
                setStorageReference(storageReference);
                StorageReference st = storageReference;
                storageReference.putFile(fileuri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                tv_filename.setText("Select File");

                                st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        arrayListFileObjects.clear();
                                        Log.i("TAG", "File path ..............:" + getStorageReference());
                                        model obj = new model(filename, uri.toString(), 0, 0, 0);
                                        databaseReference.child(databaseReference.push().getKey()).setValue(obj);
                                        Toast.makeText(StudentUpload.this, filename + " Uploaded", Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.i("TAG", "File path ..............:" + e);

                                        Toast.makeText(StudentUpload.this, "Db Node Not seted", Toast.LENGTH_LONG).show();

                                    }
                                });
                                if (index == arrayList.size() - 1) {
                                    file_name = filename;
//                            Toast.makeText(StudentUpload.this, filename+" Uploaded",          Toast.LENGTH_LONG).show();
                                    progress1.dismiss();
                                    arrayListFileObjects.clear();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progress1.isShowing()) {
                            progress1.dismiss();
                        }
                        Toast.makeText(StudentUpload.this, "Failed to Upload", Toast.LENGTH_SHORT).show();


                    }
                });


            }
        }

    }
// Old Code

//    private void selectImage() {
//
//        Intent intent = new Intent();
//        intent.setType("*/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent,100);
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 100 && data != null && data.getData() != null){
//
//            imageUri = data.getData();
//            Uri fileuri = imageUri;
//            String filename = getfilenamefromuri(fileuri);
//            tv_filename.setText(filename);
//        }
//    }
//    public String getfilenamefromuri(Uri filepath)
//    {
//        String result = null;
//        if (filepath.getScheme().equals("content")) {
//            Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            } finally {
//                cursor.close();
//            }
//        }
//        if (result == null) {
//            result = filepath.getPath();
//            int cut = result.lastIndexOf('/');
//            if (cut != -1) {
//                result = result.substring(cut + 1);
//            }
//        }
//        return result;
//    }
}
