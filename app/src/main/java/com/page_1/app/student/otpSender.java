package com.page_1.app.student;

import android.widget.Toast;

import com.page_1.app.utils.OkHttpHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class otpSender {
    public static void otpSender(String message,String number)
    {
//		System.out.println(message);
//		System.out.println(number);
        try
        {

            String apiKey="2SV7FjT9HaUqXnvpLBgy3KY8GwImkNMZ5dWboxizctEerCfOAlWU0MjdOFNwKJhqSCAGZYyc8zR9mD7a";
            String sendId="FSTSMS";
            //important step...
            message= URLEncoder.encode(message, "UTF-8");
            String language="english";

            String route="p";


            String myUrl="https://www.fast2sms.com/dev/bulkV2?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;
            //sending get request using java..

            URL url=new URL(myUrl);

            HttpsURLConnection con=(HttpsURLConnection)url.openConnection();


            con.setRequestMethod("GET");

            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");
            System.out.println("Wait..............");

            int code=con.getResponseCode();

            System.out.println("Response code : "+code);

            StringBuffer response=new StringBuffer();

            BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));

            while(true)
            {
                String line=br.readLine();
                if(line==null)
                {
                    break;
                }
                response.append(line);
            }

            System.out.println(response);

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        System.out.println("Program started.....");
//        int length = 4;
//        String numbers = "1234567890";
//        Random random = new Random();
//        char[] otp = new char[length];
//        for (int i = 0; i < length; i++) {
//            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
//        }
//        int finalotp=Integer.parseInt(String.valueOf(otp));
//        otpSender.otpSender(" This is Your OTP " + finalotp , "8999596143");
//        System.out.println("otp sent successfully.......");
//    }
}
