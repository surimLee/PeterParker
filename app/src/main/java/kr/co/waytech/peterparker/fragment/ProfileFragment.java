package kr.co.waytech.peterparker.fragment;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Network;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.SharedPreferences;
import android.widget.ViewFlipper;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.BreakIterator;

import kr.co.waytech.peterparker.DownloadImageTask;
import kr.co.waytech.peterparker.activity.LoginActivity;
import kr.co.waytech.peterparker.activity.MainActivity;
import kr.co.waytech.peterparker.activity.PointActivity;
import kr.co.waytech.peterparker.activity.PostClass;
import kr.co.waytech.peterparker.R;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Thread.sleep;

public class ProfileFragment extends android.app.Fragment {

    static String str_Token;
    static final PostClass Postc = new PostClass();
    public static String user_id, nick_name, user_name, uuid, point, email, phone_number, car_number, profile_image, viewpoint;
    static ImageView iv_icon;
    public static TextView tv_nickname, tv_email, tv_point;

    public ProfileFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    Button LoginBtn, UploadBtn, btn_getToken, DeleteAccountBtn, btn_logout, test_btn, btn_goMypoint;
    static View view_beforeLogin;
    static View view_afterLogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

        tv_nickname = view.findViewById(R.id.tv_nickname);
        tv_email = view.findViewById(R.id.tv_email);
        tv_point = view.findViewById(R.id.tv_point);
        iv_icon = view.findViewById(R.id.img_profile);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        str_Token = sharedPreferences.getString("token", "");

//        SharedPreferences.Editor editor = sharedPreferences.edit(); //SP를 제어할 editor를 선언
//        editor.clear();
//        editor.commit();
//        Toast.makeText(getContext(), "토큰을 삭제하였습니다", Toast.LENGTH_SHORT).show();



        view_beforeLogin = (View) view.findViewById(R.id.view_beforeLogin);
        view_afterLogin = (View) view.findViewById(R.id.view_afterLogin);

        if (str_Token.length() > 20){
            Toast.makeText(getContext(), "토큰이 존재", Toast.LENGTH_SHORT).show();
            try {
                set_afterLoginView();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(getContext(), "토큰이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            view_beforeLogin.setVisibility(View.VISIBLE);
            view_afterLogin.setVisibility(View.GONE);
        }


        LoginBtn = (Button)view.findViewById(R.id.LoginButton);
        LoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intentLoadNewActivity = new Intent(getActivity(), LoginActivity.class);
                startActivity(intentLoadNewActivity);
            }
        });


        btn_goMypoint = view.findViewById(R.id.btn_goMypoint);
        btn_goMypoint.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                PointActivity.point = point;
                Intent intentPointActivity = new Intent(getActivity(), PointActivity.class);
                System.out.println("인텐트 OK");
                startActivity(intentPointActivity);
            }
        });

        UploadBtn = (Button)view.findViewById(R.id.UploadButton);
        UploadBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Postc.send_Img();
            }
        });

        btn_getToken = (Button)view.findViewById(R.id.btn_getToken);
        btn_getToken.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                str_Token = sharedPreferences.getString("token", "");
                Toast.makeText(getContext(), "불러오기 하였습니다."+str_Token, Toast.LENGTH_SHORT).show();
            }
        });

        test_btn = (Button)view.findViewById(R.id.test_btn);
        test_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                try {
                    Postc.Get_profile(str_Token);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        btn_logout = (Button)view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit(); //SP를 제어할 editor를 선언
                                editor.clear();
                                editor.commit();
                                Toast.makeText(getContext(), "토큰을 삭제하였습니다", Toast.LENGTH_SHORT).show();
                                Success_logout();
                                set_afterLogoutView();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //select No
                            }
                        });
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();

            }
        });


        DeleteAccountBtn = (Button)view.findViewById(R.id.delete_account_Button);
        DeleteAccountBtn.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("회원탈퇴")
                        .setMessage("정말로 탈퇴 하시겠습니까.")
                        .setCancelable(false)
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"Selected Option: YES",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"Selected Option: No",Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog dialog  = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    //화면 로그인 이후 화면으로 셋팅
    public static void set_afterLoginView() throws IOException, InterruptedException {

        Postc.Get_profile(str_Token);
        while(profile_image.length() < 5 ){sleep(1);}
        if (profile_image.length() > 5 ){
            tv_nickname.setText(nick_name);
            System.out.println("tv_nickname 변경 완료");
            profile_image = profile_image.replace("\\/", "/");
            System.out.println("profile_image 은 " + profile_image);

            try {
                URL url = new URL(profile_image);
                URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(bis);
                bis.close();
                iv_icon.setImageBitmap(bm);
                System.out.println("이미지 변경 완료");
            } catch (Exception e) {
            }

            int origin_point = Integer.parseInt(point);
            viewpoint = String.format("%,d", origin_point);

            tv_email.setText(email);
            tv_point.setText(viewpoint);
            view_beforeLogin.setVisibility(View.GONE);
            view_afterLogin.setVisibility(View.VISIBLE);
        }

    }


    //화면 로그아웃 이후 화면으로 셋팅
    public static void set_afterLogoutView() {
        view_beforeLogin.setVisibility(View.VISIBLE);
        view_afterLogin.setVisibility(View.GONE);
    }

    public void Success_logout(){
        profile_image = null;
    }

    private void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


}

