package kr.co.waytech.peterparker.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import kr.co.waytech.peterparker.activity.LoginActivity;
import kr.co.waytech.peterparker.activity.MainActivity;
import kr.co.waytech.peterparker.activity.PostClass;
import kr.co.waytech.peterparker.R;

public class ProfileFragment extends android.app.Fragment {

    public ProfileFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button LoginBtn, UploadBtn, DeleteAccountBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final PostClass Postc = new PostClass();
        View view = inflater.inflate(R.layout.fragment_profile, container,false);



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


}

