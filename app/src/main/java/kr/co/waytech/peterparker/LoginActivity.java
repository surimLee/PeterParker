package kr.co.waytech.peterparker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    Button LoginBtn, SignupBtn, TokenBtn;
    EditText EdID, EdPW;
    String VID, VPW;
    public static Activity loginActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final PostClass Postc = new PostClass();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginActivity = LoginActivity.this;
        EdID = (EditText)findViewById(R.id.loginActivity_edittext_id);
        EdPW = (EditText)findViewById(R.id.loginActivity_edittext_password);
        LoginBtn =(Button)findViewById(R.id.loginActivity_button_login);
        LoginBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v) {
                VID = EdID.getText().toString();
                VPW = EdPW.getText().toString();
                try {
                    Postc.send_login(VID, VPW);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        SignupBtn =(Button)findViewById(R.id.loginActivity_button_signup);
        SignupBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v) {
                Postc.send_signup("Email@naver.com", "eomky2005", "NameName", "testtest",
                        "NICKNICK", "01048136286", "123허1234");
            }
        });
        TokenBtn =(Button)findViewById(R.id.loginActivity_button_token);
        TokenBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v) {
                Postc.send_token();
            }
        });
    }
    public void Success_Login(){
        loginActivity.finish();
    }
}




