package com.page_1.app.DisplayFiles;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;
import com.page_1.app.teacher.AssignmentStudentResponse;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import static com.page_1.app.DisplayFiles.DisplayAssignmentF.AssignmentNOO;
import static com.page_1.app.DisplayFiles.DisplayAssignmentF.keys4;
import static com.page_1.app.DisplayFiles.DisplayAssignmentF.keys5;
import static com.page_1.app.teacher.MainTeacherActivity.SubjectName;
import static com.page_1.app.teacher.MainTeacherActivity.teachingYear;

public class TeacherAssignment extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recview;
    ListView recview2;
    myadapter adapter;
    RelativeLayout StudentRes_rel_btn,view_eye_rel2;
    ListView l1;
    TextView AssignmentFilestv,SubjectName_Teacher_asses,Assignment_teacher_assesment,countanalysis;
    RelativeLayout view_eye_rel,view_eye_rel1;
    ImageView downexel;
    static ProgressDialog progress;
    private DatabaseReference databaseReference;
    private String key;
    private DatabaseReference databaseReference3;

    public void openProgressDialog() {
        progress = new ProgressDialog(TeacherAssignment.this );
        progress.setMessage("Please wait.......");
        progress.setCancelable(false);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_assignment);
        AddActionBar();
        Log.v("TAG","array1 ::"+keys4);
        Assignment_teacher_assesment=findViewById(R.id.Assignment_teacher_assesment);
        Assignment_teacher_assesment.setText(AssignmentNOO);
        recview = (RecyclerView) findViewById(R.id.recview1);

        recview.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Database").child(teachingYear).child(SubjectName).child("Assignments").child(AssignmentNOO), model.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);
        initUI();
        initListner();
        addValueToList();
    }

    private void initListner() {
        view_eye_rel.setOnClickListener(this);
        view_eye_rel1.setOnClickListener(this);
        view_eye_rel2.setOnClickListener(this);
        StudentRes_rel_btn.setOnClickListener(this);
        downexel.setOnClickListener(this);
    }

    private void initUI() {
        downexel=(ImageView) findViewById(R.id.downexel);
        l1 = findViewById(R.id.recview2);
        AssignmentFilestv=findViewById(R.id.AssignmentFilestv);
        view_eye_rel=findViewById(R.id.view_eye_rel);
        recview2=findViewById(R.id.recview2);
        countanalysis=findViewById(R.id.countanalysis);
        countanalysis.setVisibility(View.GONE);
        view_eye_rel1=findViewById(R.id.view_eye_rel1);
        StudentRes_rel_btn=findViewById(R.id.StudentRes_rel_btn);
        view_eye_rel2=findViewById(R.id.view_eye_rel2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.view_eye_rel:
                changeToReadBy();
                break;
            case R.id.view_eye_rel1:
                changeToAssignmentFiles();

                break;
            case R.id.view_eye_rel2:
                changetoPending();
                break;

        }
    }

    private void changeToReadBy() {

            recview.setVisibility(View.GONE);
            recview2.setVisibility(View.VISIBLE);

            ArrayAdapter<String> arr;
            arr
                    = new ArrayAdapter<String>(
                    this,
                    R.layout.item_list_for_dataanaylysis,
                    keys4);
            l1.setAdapter(arr);
            AssignmentFilestv.setText("Read By :");
            countanalysis.setText(String.valueOf(keys4.size()));
            countanalysis.setVisibility(View.VISIBLE);
            view_eye_rel2.setVisibility(View.VISIBLE);
            view_eye_rel.setVisibility(View.GONE);
            view_eye_rel1.setVisibility(View.GONE);
        Log.v("TAG","array1 ::"+keys4);

        downexel.setVisibility(View.VISIBLE);
        downexel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Workbook wb=new HSSFWorkbook();
                Cell cell=null;
                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                //Now we are creating sheet
                Sheet sheet=null;
                sheet = wb.createSheet("Viewed List");
                //Now column and row
                Row row =sheet.createRow(0);

                cell=row.createCell(0);
                cell.setCellValue("Name of students");
                cell.setCellStyle(cellStyle);
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
                sheet.setColumnWidth(0,(10*800));
                int n =keys4.size();
                for(int i = 0;i<n;i++)
                {
                    if(i==0)
                    {
                        Row  row1=sheet.createRow(1);
                        cell=row1.createCell(0);
                        cell.setCellValue(String.valueOf( keys4.get(i)));
                    }
                    else {


                        Row row1 = sheet.createRow(i+1);
                        cell = row1.createCell(0);
                        cell.setCellValue(String.valueOf(keys4.get(i)));
                        if (i == keys4.size() - 1) {
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), SubjectName + "_" + AssignmentNOO + "Viewed" + ".xls");
                            FileOutputStream outputStream = null;

                            try {
                                outputStream = new FileOutputStream(file);
                                wb.write(outputStream);
                                Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_LONG).show();
                            } catch (java.io.IOException e) {
                                e.printStackTrace();

                                Toast.makeText(getApplicationContext(), "NO OK", Toast.LENGTH_LONG).show();
                                try {
                                    outputStream.close();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }

                        }
                    }
                }




            }
        });


    }
 public  void changetoPending(){
       recview.setVisibility(View.GONE);
       ArrayList<String> PendingView = new ArrayList<String>(keys5);
       PendingView.removeAll(keys4);
       Log.v("TAG","Pending ::"+PendingView);
       ArrayAdapter<String> arr1;
       arr1
               = new ArrayAdapter<String>(
               this,
               R.layout.item_list_for_dataanaylysis,
               PendingView);
       l1.setAdapter(arr1);
     l1.setVisibility(View.VISIBLE);
       AssignmentFilestv.setText("Remaining :");
       countanalysis.setText(String.valueOf(PendingView.size()));
       countanalysis.setVisibility(View.VISIBLE);
       view_eye_rel1.setVisibility(View.VISIBLE);
       view_eye_rel2.setVisibility(View.GONE);
       view_eye_rel.setVisibility(View.GONE);
     downexel.setVisibility(View.VISIBLE);
     //Excel
downexel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Workbook wb=new HSSFWorkbook();
        Cell cell=null;
        CellStyle cellStyle=wb.createCellStyle();
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        // creating sheet
        Sheet sheet=null;
        sheet = wb.createSheet("Pending Student List");
        //creating column and row
        Row row =sheet.createRow(0);

        cell=row.createCell(0);
        cell.setCellValue("Name of Students");
        cell.setCellStyle(cellStyle);
        cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        sheet.setColumnWidth(0,(10*800));
        int n =PendingView.size();
        for(int i =0;i<n;i++)
        {
            if(i==0)
            {
                Row  row1=sheet.createRow(1);
                cell=row1.createCell(0);
                cell.setCellValue(String.valueOf( PendingView.get(i)));
            }
            else {


                Row row1 = sheet.createRow(i+1);
                cell = row1.createCell(0);
                cell.setCellValue(String.valueOf(PendingView.get(i)));
                if (i == PendingView.size() - 1) {
                    FileOutputStream outputStream = null;

                    try {
                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), SubjectName + "_" + AssignmentNOO + "Pending" + ".xls");

                        outputStream = new FileOutputStream(file);
                        wb.write(outputStream);
                        Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_LONG).show();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();

                        Toast.makeText(getApplicationContext(), "NO OK", Toast.LENGTH_LONG).show();
                        try {
                            outputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }
            }
        }
    }
});



 }

    public void changeToAssignmentFiles(){
        countanalysis.setVisibility(View.GONE);
        recview.setVisibility(View.VISIBLE);
        recview2.setVisibility(View.GONE);
        AssignmentFilestv.setText("Assignment Files :");
        view_eye_rel1.setVisibility(View.GONE);
        view_eye_rel.setVisibility(View.VISIBLE);
        view_eye_rel1.setVisibility(View.GONE);
        downexel.setVisibility(View.GONE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        keys4.clear();
        keys6.clear();

    }
    public static String tutorials2[]
            = {};
    public static ArrayList<String> keys6 = new ArrayList<String>();
    public void addValueToList()
    {

        databaseReference= FirebaseDatabase.getInstance().getReference("Database2").child(teachingYear).child(SubjectName).child("Assignments").child(AssignmentNOO);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();
                    int n=tutorials2.length;
                    String x=key;
                    keys6.add(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        databaseReference.addListenerForSingleValueEvent(eventListener);
        StudentRes_rel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TeacherAssignment.this, AssignmentStudentResponse.class);
                startActivity(intent);
            }
        });
    }

    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(" "+SubjectName);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1C3D4E")));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
