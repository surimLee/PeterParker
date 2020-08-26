package kr.co.waytech.peterparker.fragment;


import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.SharedPreferences;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import kr.co.waytech.peterparker.activity.LoginActivity;
import kr.co.waytech.peterparker.activity.MainActivity;
import kr.co.waytech.peterparker.activity.PostClass;
import kr.co.waytech.peterparker.R;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends android.app.Fragment {


    public ProfileFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button LoginBtn, UploadBtn, btn_getToken, DeleteAccountBtn, btn_logout;
    String str_Token;
    static View view_beforeLogin;
    static View view_afterLogin;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final PostClass Postc = new PostClass();
        View view = inflater.inflate(R.layout.fragment_profile, container,false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", MODE_PRIVATE);
        str_Token = sharedPreferences.getString("token", "");

        view_beforeLogin = (View) view.findViewById(R.id.view_beforeLogin);
        view_afterLogin = (View) view.findViewById(R.id.view_afterLogin);

        if (str_Token.length() > 20){
            Toast.makeText(getContext(), "토큰이 존재", Toast.LENGTH_SHORT).show();
            view_beforeLogin.setVisibility(View.GONE);
            view_afterLogin.setVisibility(View.VISIBLE);

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
                                view_beforeLogin.setVisibility(View.VISIBLE);
                                view_afterLogin.setVisibility(View.GONE);
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

    public static void set_afterLoginView(){
        view_beforeLogin.setVisibility(View.GONE);
        view_afterLogin.setVisibility(View.VISIBLE);
        Log.d("로그인화면", "표시함");
    }

    private void refresh(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }


}

