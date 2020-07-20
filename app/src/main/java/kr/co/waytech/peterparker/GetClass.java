package kr.co.waytech.peterparker;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetClass {
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static void main(String[] args) throws IOException{
        GetClass getc = new GetClass();
        String response = getc.run("https://raw.github.com/square/okhttp/master/README.md");
        System.out.println(response);
    }
}
