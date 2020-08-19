package kr.co.waytech.peterparker.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PostClass {
    public static int imgcount = 1;
    protected static String realtoken, bookingbody;
    String status = "none";
    int Thread_Status = 3 ;
    int Login_Status = 3;
    public static int count;
    public static Integer Count_Parkinglot;
    public static int[] ParkingPrice;
    public static double[] ParkingLat, ParkingLng;
    public static String[] Parking_img, Parking_phone;
    public static String[][] All_Parkinglot;
    public static int b = 7;
    static File tempSelectFile;
    public static Marker ParkingMark;
    public static String body_parkinglot = "abc";
    LoginActivity2 LogA = new LoginActivity2();
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String serverHtml = bun.getString("Server_HTML");
        }
    };

    public void send_login(final String SID, final String SPassword) throws IOException {
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

    public void send_token(){
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

    public void Post_alllocation(){
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    getalllot();
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
    public void send_Location(final String id){
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    Get_Location(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void send_booking(final String syear, final String smonth, final String sday, final String shour, final String smin, final String eyear, final String emonth, final String eday
            , final String ehour, final String emin, final String ID, final String token){
        new Thread() {
            public void run() {
                Bundle bun = new Bundle();
                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
                try {
                    post_booking(syear, smonth, sday, shour, smin, eyear, emonth, eday, ehour, emin, ID, token);
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
    public void Get_Location(String id) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/api/parking-lot/"+ id)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        body_parkinglot = response.body().string();
        System.out.println("Body is" + body_parkinglot);
        String[] split_locationcount = body_parkinglot.split(",");
        String phone_number = split_locationcount[11].split("\"")[3];
        System.out.println("phone : " + phone_number);
        String Image_1 = split_locationcount[5].split("\"")[3].replace("\\", "");
        String Image_2 = split_locationcount[6].split("\"")[3].replace("\\", "");
        String Image_3 = split_locationcount[7].split("\"")[3].replace("\\", "");
        String Image_4 = split_locationcount[8].split("\"")[3].replace("\\", "");
        Parking_phone = new String[1];
        Parking_img = new String[4];
        Parking_img[0] = Image_1;
        Parking_img[1] = Image_2;
        Parking_img[2] = Image_3;
        Parking_img[3] = Image_4;
        Parking_phone[0] = phone_number;
        System.out.println(Parking_phone[0]);
        /*
        String[] split_onebody = body_parkinglot.split("\\{");
        System.out.println(split_onebody[0]);
        split_location = body_parkinglot.split("\"");
        String[] count_parkinglot = split_locationcount[0].split(":");
        Count_Parkinglot = new Integer(Integer.parseInt(count_parkinglot[1]));
        count = Count_Parkinglot.intValue();
        String[][] dataArray = new String[count][12];
        b = 7;
        for(int countbody = 2; countbody < count + 2; countbody++){
            String[] split_onedata = split_onebody[countbody].split(","); // split_onedata[0] = "parking_lot_id":"0"
            for(int amount = 0; amount < 11; amount++){
                String[] splitedData = split_onedata[amount].split(":"); // splitedData[1] = "0"
                dataArray[countbody-2][amount] = splitedData[1];
                System.out.println("dataArray : [" + (countbody - 2) + "]" + "[" + (amount) + "] = " + dataArray[countbody-2][amount]);
            }
            System.out.println(split_onedata[5].split(":")[1] + ":" + split_onedata[5].split(":")[2].replace("\\", ""));
            String[] splitedLng = dataArray[countbody-2][10].split("\\}");
            dataArray[countbody-2][10] = splitedLng[0];
            System.out.println(dataArray[countbody-2][10]);
            ParkingLat = new double[count];
            ParkingLat[countbody-2] = Double.parseDouble(dataArray[countbody-2][9]);
            ParkingLng = new double[count];
            ParkingLng[countbody-2] = Double.parseDouble(dataArray[countbody-2][10]);
            ParkingPrice = new int[count];
            ParkingPrice[countbody-2] = Integer.parseInt(dataArray[countbody-2][4]);
            Parking_phone = new String[count];
            Parking_phone[countbody-2] = dataArray[countbody-2][11];


      }

         */
        // ID : 7 , ownerID : 11, Name : 15, Address : 19, Price : 22, Image1 : 25, Image2 : 29, Image3 : 33, Image4 : 37, Lat : 40, Lng : 42
    }

    public void getalllot() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/api/parking-lot/show")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        String allbody = response.body().string();
        try {
            System.out.println("all body : " + allbody);
            String[] All_onePL = allbody.split("\\{");
            String[] count_parkinglot = All_onePL[1].split(",");
            Count_Parkinglot = new Integer(Integer.parseInt(count_parkinglot[0].substring(8)));
            count = Count_Parkinglot.intValue();
            System.out.println("count : " + count);

            All_Parkinglot = new String[count][4];
            for(int i = 0; i < count; i++) {
                String[] ALL_oneData = All_onePL[i+2].split(",");
                String[] ALL_oneIDraw = ALL_oneData[0].split(":");
                String[] ALL_oneID = ALL_oneIDraw[1].split("\"");
                String[] ALL_oneprice = ALL_oneData[1].split(":");
                String[] ALL_oneLat = ALL_oneData[2].split(":");
                String[] ALL_oneLngraw = ALL_oneData[3].split(":");
                String[] ALL_oneLng = ALL_oneLngraw[1].split("\\}");
                All_Parkinglot[i][0] = ALL_oneID[1];
                All_Parkinglot[i][1] = ALL_oneprice[1];
                All_Parkinglot[i][2] = ALL_oneLat[1];
                All_Parkinglot[i][3] = ALL_oneLng[0];
                System.out.println("Array Data is.. " + " " +  All_Parkinglot[i][0] + " " + All_Parkinglot[i][1] + " " + All_Parkinglot[i][2] + " " +All_Parkinglot[i][3]);
            }

        }

        catch (Exception e){
            System.out.println("전체 데이터 수신/스플릿 오류");
        }

    }
    public void post_booking(String syear, String smonth, String sday, String shour, String smin, String eyear, String emonth, String eday
    , String ehour, String emin, String ID, String token) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("start_year", syear)
                .addFormDataPart("start_month", smonth)
                .addFormDataPart("start_day", sday)
                .addFormDataPart("start_hour", shour)
                .addFormDataPart("start_min", smin)
                .addFormDataPart("end_year", eyear)
                .addFormDataPart("end_month", emonth)
                .addFormDataPart("end_day", eday)
                .addFormDataPart("end_hour", ehour)
                .addFormDataPart("end_min", emin)
                .addFormDataPart("parking_lot_id", ID)
                .build();
        Request request = new Request.Builder()
                .url("http://blazingcode.asuscomm.com/api/booking")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Response response = client.newCall(request).execute();
        bookingbody = response.toString();
        System.out.println(bookingbody);
    }
    public int getcountnumber(){
        return count;
    }
    /*
    public void AddMarker(int count, double x1, double x2, double y1, double y2){
        for(int i = 0; i < count; i++) {
            double pLat = Double.parseDouble(All_Parkinglot[i][2]);
            double pLng = Double.parseDouble(All_Parkinglot[i][3]);
            LatLng Parking = new LatLng(pLat, pLng);
            if (pLat < x1 && pLat > x2 && pLng > y1 && pLng < y2) {
                mMap.addMarker(new MarkerOptions().position(Parking).title(All_Parkinglot[i][0]));
            }
            else
                mMap.Marker.setVisible(boolean);
        }
    }

     */
     }
