package kr.co.waytech.peterparker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import kr.co.waytech.peterparker.fragment.MapFragment;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static kr.co.waytech.peterparker.fragment.MapFragment.mMap;


public class PostClass {
    public static int imgcount = 1;
    protected static String realtoken;
    String status = "none";
    int Thread_Status = 3 ;
    int Login_Status = 3;
    public static Integer Count_Parkinglot;
    public static int[] ParkingPrice;
    public static double[] ParkingLat, ParkingLng;
    public static String[] split_location;
    public static int b = 7;
    static File tempSelectFile;
    String body_parkinglot = null;
    LoginActivity2 LogA = new LoginActivity2();
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
    public void send_Location(final double x1, final double y1, final double x2, final double y2){
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    Get_Location(x1, y1, x2, y2);
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
                        .addFormDataPart("profile_image","20160528_115613.jpeg",
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
    public void Get_Location(double x1, double y1, double x2, double y2) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/api/parking-lot/"+ x1 +"-"+ y1 +"-"+ x2 + "-" + y2)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        body_parkinglot = response.body().string();
        System.out.println("Body is" + body_parkinglot);
        String[] split_locationcount = body_parkinglot.split(",");
        System.out.println("splited Body is " + split_locationcount[0]);

        String[] split_onebody = body_parkinglot.split("\\{");

        System.out.println(split_onebody[0]);


        String[] data = new String[500];
        split_location = data;
        split_location = body_parkinglot.split("\"");
        String[] count_parkinglot = split_locationcount[0].split(":");
        Count_Parkinglot = new Integer(Integer.parseInt(count_parkinglot[1]));
        int count = Count_Parkinglot.intValue();
        int[] Price = new int[Count_Parkinglot+3];
        double[] Lat = new double[Count_Parkinglot+3];
        double[] Lng = new double[Count_Parkinglot+3];
        String[][] dataArray = new String[count][11];
        b = 7;
        for(int countbody = 2; countbody < count + 2; countbody++){
            String[] split_onedata = split_onebody[countbody].split(","); // split_onedata[0] = "parking_lot_id":"0"
            for(int amount = 0; amount < 11; amount++){
                String[] splitedData = split_onedata[amount].split(":"); // splitedData[1] = "0"
                dataArray[countbody-2][amount] = splitedData[1];
                System.out.println("dataArray : [" + (countbody - 2) + "]" + "[" + (amount) + "] = " + dataArray[countbody-2][amount]);
            }
            String[] splitedLng = dataArray[countbody-2][10].split("\\}");
            dataArray[countbody-2][10] = splitedLng[0];
            System.out.println(dataArray[countbody-2][10]);
      }
        /*
        for(int i = 0; i < count; i++){
            System.out.println("Data is.. " + split_location[b + 0] + "/" + split_location[b +  4] + "/" + split_location[b +  8] + "/" + split_location[b +  12]
                    + "/" + split_location[b +  15] + "/" + split_location[b +  18] + "/" + split_location[b +  22]  + "/" + split_location[b +  26]  + "/" + split_location[b +  30]
                    + "/" + split_location[b +  33]  + "/" + split_location[b +  35]);
            String replacePrice = split_location[b +  15].replace(",", ":");
            String[] parking_price = replacePrice.split(":");
            ParkingPrice = Price;
            ParkingPrice[i] = Integer.parseInt(parking_price[1]);
            System.out.println("Price is.. " + (i) + "번째 "+ ParkingPrice[i]);

            String replaceLat = split_location[b +  33].replace(",", ":");
            String[] parking_Lat = replaceLat.split(":");
            ParkingLat = Lat;
            ParkingLat[i] =  Double.parseDouble(parking_Lat[1]);
            System.out.println("Lat is.. " + ParkingLat[i]);

            String replaceLng = split_location[b +  35].replace("}", ":");
            String[] parking_Lng = replaceLng.split(":");
            ParkingLng = Lng;
            ParkingLng[i] =  Double.parseDouble(parking_Lng[1]);
            System.out.println("Lng is.. " + ParkingLng[i]);

            b = b + 38;
        }

         */
        // ID : 7 , ownerID : 11, Name : 15, Address : 19, Price : 22, Image1 : 25, Image2 : 29, Image3 : 33, Image4 : 37, Lat : 40, Lng : 42
    }
}
