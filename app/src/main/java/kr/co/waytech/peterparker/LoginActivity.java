package kr.co.waytech.peterparker;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {
    Button LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginBtn =(Button)findViewById(R.id.loginActivity_button_login);
        LoginBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                new Thread() {
                    public void run() {
                        Bundle bun = new Bundle();
                        Message msg = handler.obtainMessage();
                        msg.setData(bun);
                        handler.sendMessage(msg);
                        try {
                            send_select();
                            //send_login("eomky2005", "testtest");
                            /*
                            send_signup( "Email@naver.com", "eomky2005", "NameName", "testtest",
                                    "NICKNICK", "01048136286", "123허1234");

                             */
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String serverHtml = bun.getString("Server_HTML");
        }
    };

    public void send_login(String ID, String Password) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", ID)
                .addFormDataPart("password", Password)
                .build();
        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/api/login")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("Response Body is " + response.body().string());
    }

    public void send_signup(String Email, String ID, String Name, String Password, String Nick_Name, String Phone, String Car) throws IOException {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", Email)
                    .addFormDataPart("user_id", ID)
                    .addFormDataPart("name", Name)
                    .addFormDataPart("password", Password)
                    .addFormDataPart("nick_name", Nick_Name)
                    .addFormDataPart("phone_number", Phone)
                    .addFormDataPart("car_number", Car)
                    .build();
            Request request = new Request.Builder()
                    .url("http://blazingcode.asuscomm.com/api/register")
                    .post(requestBody)
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            System.out.println("Response Body is " + response.body().string());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send_select () throws IOException{
        String token = "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9ibGF6aW5nY29kZS5hc3VzY29tbS5jb21cL2F" +
                "waVwvbG9naW4iLCJpYXQiOjE1OTU0ODA2OTQsImV4cCI6MTU5NTQ4NDI5NCwibmJmIjoxNTk1NDgwNjk0LCJqdGkiOiJMMWhOeUl" +
                "1ZGtxWUdyb2dDIiwic3ViIjoxOCwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.LnlZnm587" +
                "6Oc4lEOsvZ8c2Sutn8nVbbKZs5cJSvrPLA";
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/")
                .method("GET", null)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("Response Body is " + response.body().string());
    }
}




