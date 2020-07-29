package kr.co.waytech.peterparker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PostClass {
    public static int imgcount;
    protected static String realtoken;
    String status = "none";
    int Thread_Status = 3 ;
    int Login_Status = 3;
    static File tempSelectFile;
    LoginActivity LogA = new LoginActivity();
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String serverHtml = bun.getString("Server_HTML");
        }
    };

    protected void send_login(final String SID, final String SPassword) throws IOException {
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    Post_Login(SID, SPassword);
                    Improve_Status();
                } catch (IOException e) {
                    e.printStackTrace();
                } Improve_Login();
            }
        }.start();
    }

    protected void send_signup(final String SEmail, final String SID, final String SName, final String SPassword,
                               final String SNick_Name, final String SPhone, final String SCar){
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    Post_Signup(SEmail, SID, SName, SPassword, SNick_Name, SPhone, SCar);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    protected void send_token(){
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    Post_token();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void send_Img(){
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    Post_Img();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void Post_Login(String ID, String Password) throws IOException{
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
            String body2 = response.body().string();
            String[] split_token = body2.split("\"");
            realtoken = split_token[7];
            status = split_token[3];
            System.out.println(status);
            System.out.println(realtoken);
    }
    private void Post_Signup(String Email, String ID, String Name, String Password, String Nick_Name, String Phone, String Car) throws IOException {
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
    }

    private void Post_token() throws IOException{
        String token = "Bearer " + realtoken;

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/")
                .method("GET", null)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("Response Body is " + response.body().string());
    }
    public synchronized void Improve_Status(){
        if(status.equals("error")) {
            Thread_Status = 0;
        }
        else if(status.equals("success")) {
            Thread_Status = 1;
        }
        else {
            //Toast
            Thread_Status = 2;
        }
    }
    public synchronized void Improve_Login(){
        if(Thread_Status == 1) {
           Login_Status = 1;
           LogA.Success_Login();
        }
        else {
            Login_Status = 0;
        }
    }
    private void Post_Img() throws IOException{
        String token = "Bearer " + realtoken;
        Bitmap orgImage = BitmapFactory.decodeFile("/sdcard/DCIM/Camera/20160528_115613.jpg");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        orgImage.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        byte[] ImgByte = stream.toByteArray();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = null;
        switch (imgcount){
            case 1:
                body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("profile_image1","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .build();
                break;
            case 2:
                body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("profile_image1","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .addFormDataPart("profile_image2","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .build();
                break;
            case 3:
                body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("profile_image1","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .addFormDataPart("profile_image2","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .addFormDataPart("profile_image3","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .build();
                break;
            case 4:
                body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("profile_image1","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .addFormDataPart("profile_image2","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .addFormDataPart("profile_image3","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .addFormDataPart("profile_image4","20160528_115613.jpeg",
                                RequestBody.create(MediaType.parse("image/*"), ImgByte))
                        .build();
                break;
            default:
        }


        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/api/profile/image_upload")
                .method("POST", body)
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("Response Body is " + response.body().string());

    }
}
