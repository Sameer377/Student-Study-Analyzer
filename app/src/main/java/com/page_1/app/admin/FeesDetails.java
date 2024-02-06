package com.page_1.app.admin;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.page_1.app.R;
import com.page_1.app.admin.Fees.FeeAdapter;
import com.page_1.app.admin.Fees.FeeModel;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static com.page_1.app.admin.FeesConsolAdmin.Finalkey;
import static com.page_1.app.admin.FeesConsolAdmin.YEARR;

public class FeesDetails extends AppCompatActivity {
ListView listFees;
FloatingActionButton FloatingDownloadbutton;
TextInputEditText FeesET;
TextInputLayout FeesIL;
ImageButton SetFees;
    private RecyclerView courseRV;

    // Arraylist for storing data
    private ArrayList<FeeModel> FeeModelArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees_details);
        FeesET=findViewById(R.id.FeesET);
        FeesIL=findViewById(R.id.FeesIL);
        SetFees=findViewById(R.id.SetFees);
        checkFeesAmmount();
        courseRV = findViewById(R.id.idRVCourse);
        FeeModelArrayList = new ArrayList<>();
        int n =Finalkey.size();
        for(int i = 0;i<n;i++)
        {
            String details =Finalkey.get(i);
            Log.v("DetailsMain","Key :"+details);
            String ERP=details.substring(0,10);
            Log.v("DetailsMain","ERP :"+ERP);
            String NAME[]=details.split("Name");
            String finalname=NAME[0].substring(11,NAME[0].length()-1);
            Log.v("DetailsMain","Name :"+finalname);
            String DATE=details.substring(11+finalname.length()+7,11+finalname.length()+10+7);
            Log.v("DetailsMain","Date :"+DATE);
            String TIME=details.substring(11+finalname.length()+7+3+10,11+finalname.length()+7+12+10);
            Log.v("DetailsMain","Time :"+TIME);
            String paymentID=details.substring(11+finalname.length()+7+3+10+1+8,11+finalname.length()+7+3+10+19+8);
            Log.v("DetailsMain","Payment ID :"+paymentID);
            String status=details.substring(11+finalname.length()+7+3+10+1+8+19,11+finalname.length()+7+3+10+1+8+19+10);
            Log.v("DetailsMain","Status :"+status);
            String Ammount=details.substring(11+finalname.length()+7+3+10+1+8+19+11,details.length());
            Log.v("DetailsMain","Ammount :"+Ammount);
            Log.v("DetailsMain","======================================================================================================================");
            FeeModelArrayList.add(new FeeModel(ERP,finalname,Ammount,paymentID,"D : "+DATE,"UTC : "+TIME,i+1+"."));
        }

        FeeAdapter FeeAdapter = new FeeAdapter(this, FeeModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(FeeAdapter);


        FloatingDownloadbutton=findViewById(R.id.FloatingDownloadbutton);
        FloatingDownloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy hh mm aa");
                String currentDateandTime = sdf.format(new Date());
                Workbook wb=new HSSFWorkbook();
                Cell cell=null;

                CellStyle cellStyle=wb.createCellStyle();
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                //Now we are creating sheet
                Sheet sheet=null;
                sheet = wb.createSheet("Fees - "+YEARR+currentDateandTime);
                //Now column and row
                Row row =sheet.createRow(0);

                cell=row.createCell(0);
                cell.setCellValue("ERP No");
                cell.setCellStyle(cellStyle);
                cell = row.createCell(1);
                cell.setCellValue("Name of Students");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(2);
                cell.setCellValue("Date");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(3);
                cell.setCellValue("Time");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(4);
                cell.setCellValue("Payment ID");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(5);
                cell.setCellValue("Amount");
                cell.setCellStyle(cellStyle);

                cell = row.createCell(6);
                cell.setCellValue("Status");
                cell.setCellStyle(cellStyle);
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);

                sheet.setColumnWidth(0,(10*300));
                sheet.setColumnWidth(1,(10*800));
                sheet.setColumnWidth(2,(10*300));
                sheet.setColumnWidth(3,(10*300));
                sheet.setColumnWidth(4,(10*550));
                sheet.setColumnWidth(5,(10*250));
                sheet.setColumnWidth(6,(10*300));
                Log.v("TAG","Processss : TITLED ROW CREATED");

                int n =Finalkey.size();
                for(int i = 0;i<n;i++)
                {
                    Log.v("TAG","Processss : Entered in FORLOOP");

                    String details =Finalkey.get(i);
                    Log.v("DetailsMain","Key :"+details);
                    String ERP=details.substring(0,10);
                    Log.v("DetailsMain","ERP :"+ERP);
                    String NAME[]=details.split("Name");
                    String finalname=NAME[0].substring(11,NAME[0].length()-1);
                    Log.v("DetailsMain","Name :"+finalname);
                    String DATE=details.substring(11+finalname.length()+7,11+finalname.length()+10+7);
                    Log.v("DetailsMain","Date :"+DATE);
                    String TIME=details.substring(11+finalname.length()+7+3+10,11+finalname.length()+7+12+10);
                    Log.v("DetailsMain","Time :"+TIME);
                    String paymentID=details.substring(11+finalname.length()+7+3+10+1+8,11+finalname.length()+7+3+10+19+8);
                    Log.v("DetailsMain","Payment ID :"+paymentID);
                    String status=details.substring(11+finalname.length()+7+3+10+1+8+19,11+finalname.length()+7+3+10+1+8+19+10);
                    Log.v("DetailsMain","Status :"+status);
                    String Ammount=details.substring(11+finalname.length()+7+3+10+1+8+19+11,details.length());
                    Log.v("DetailsMain","Ammount :"+Ammount);
                    Log.v("DetailsMain","======================================================================================================================");
                    if(i==0)
                    {
                        Log.v("TAG","Processss : ROW 1");

                        Row  row1=sheet.createRow(1);

                        cell = row1.createCell(0);
                        cell.setCellValue(ERP);

                        cell = row1.createCell(1);
                        cell.setCellValue(finalname);

                        cell = row1.createCell(2);
                        cell.setCellValue(DATE);

                        cell = row1.createCell(3);
                        cell.setCellValue(TIME);

                        cell = row1.createCell(4);
                        cell.setCellValue(paymentID);

                        cell = row1.createCell(5);
                        cell.setCellValue(Ammount);

                        cell = row1.createCell(6);
                        cell.setCellValue(status);

                    }
                    else {

                        Log.v("TAG","Processss : ROW 2");

                        Row row1 = sheet.createRow(i+1);
                        cell = row1.createCell(0);
                        cell.setCellValue(ERP);

                        cell = row1.createCell(1);
                        cell.setCellValue(finalname);

                        cell = row1.createCell(2);
                        cell.setCellValue(DATE);

                        cell = row1.createCell(3);
                        cell.setCellValue(TIME);

                        cell = row1.createCell(4);
                        cell.setCellValue(paymentID);

                        cell = row1.createCell(5);
                        cell.setCellValue(Ammount);

                        cell = row1.createCell(6);
                        cell.setCellValue(status);


                        }
                    if (i == Finalkey.size() - 1) {
                        Log.v("TAG","Processss : Creating");

                        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Fees - "+YEARR+"_"+currentDateandTime+ ".xls");
                        FileOutputStream outputStream = null;

                        try {
                            Log.v("TAG","Processss : Created");

                            outputStream = new FileOutputStream(file);
                            wb.write(outputStream);
                            Toast.makeText(getApplicationContext(), "File Downloaded", Toast.LENGTH_LONG).show();
                        } catch (java.io.IOException e) {
                            e.printStackTrace();
                            Log.v("TAG","Processss : FAILED");

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

        });
        AddActionBar();
    }
    public void AddActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Fees "+YEARR);
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
    public String checkFeesAmmount(){
        FeesET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                FeesET.setCursorVisible(true);
                SetFees.setVisibility(View.VISIBLE);
                return false;
            }
        });

        SetFees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FeesET.getText().toString().trim().equals(null) || FeesET.getText().toString().trim()==null ||FeesET.getText().toString().trim().length()<2){
                    FeesET.setError("Enter valid Amount");

                }
                else {
                    if((Integer.parseInt(FeesET.getText().toString().trim()))>200&&(Integer.parseInt(FeesET.getText().toString().trim()))<100000000){
                        FeesET.setCursorVisible(false);
                        DatabaseReference FeesAmmount_DBR= FirebaseDatabase.getInstance().getReference("FeesAmmount");
                        FeesAmmount_DBR.child(YEARR).setValue(FeesET.getText().toString().trim());
                        Toast.makeText(FeesDetails.this,"Saved",Toast.LENGTH_SHORT).show();
                        SetFees.setVisibility(View.GONE);
                    }else {
                        FeesET.setError("Enter valid Amount");
                    }

                }

            }
        });
        DatabaseReference FeesAmmount_DBR= FirebaseDatabase.getInstance().getReference("FeesAmmount").child(YEARR);

        FeesAmmount_DBR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                FeesET.setText(String.valueOf(snapshot.getValue()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return null;
    }


}
