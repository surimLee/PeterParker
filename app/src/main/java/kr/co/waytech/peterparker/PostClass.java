package kr.co.waytech.peterparker;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostClass {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String post(String url, String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    String LoginJson(){
        return "'email' : 'emailemail', "
                + "'name' : 'namename',"
                + "'password' : '1q2w3e4r'";
    }
    public static void main(String[] args) throws  IOException{
        PostClass postc = new PostClass();
        String json = postc.LoginJson();
        String response = postc.post("blazingcode.asuscomm.com/api/register", json);
        System.out.println(response);
    }
}
