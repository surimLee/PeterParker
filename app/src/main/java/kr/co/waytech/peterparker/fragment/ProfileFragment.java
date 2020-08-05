package kr.co.waytech.peterparker.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import kr.co.waytech.peterparker.activity.LoginActivity;
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

    Button LoginBtn, UploadBtn;
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
        return view;
    }
}

