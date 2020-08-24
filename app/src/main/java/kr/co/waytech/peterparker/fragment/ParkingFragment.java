package kr.co.waytech.peterparker.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.adapter.ParkingAdapter;
import kr.co.waytech.peterparker.model.ParkingList;

public class ParkingFragment extends Fragment {

    View fragmentView;
    RecyclerView parkingListRecyclerView;
    ParkingAdapter parkingListAdapter;
    List<ParkingList> parkingList;

    public ParkingFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Add dummy data in Booking class here
        parkingList = new ArrayList<>();
        parkingList.add(new ParkingList( "한국산업기술대학교 종합관...", "경기도 시흥시 정왕동 2121-1\n한국산업기술대학교 종합관 뒤", "시간당 1,000원", R.drawable.parkinglot4));
       parkingList.add(new ParkingList( "한국산업기술대학교 시흥비즈...", "경기도 시흥시 정왕동 2121-1\n한국산업기술대학교 시흥비즈니스센터", "시간당 2,000원", R.drawable.parkinglot3));

    }

    ImageButton btn_addParking;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_parking, container,false);
        parkingListRecyclerView = fragmentView.findViewById(R.id.parking_recycler);
        parkingListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        parkingListRecyclerView.setHasFixedSize(true);

        parkingListAdapter = new ParkingAdapter(getActivity(), parkingList);
        parkingListRecyclerView.setAdapter(parkingListAdapter);

        btn_addParking = (ImageButton)fragmentView.findViewById(R.id.btn_Add_Parkinglot);
        btn_addParking.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //Intent intent_addParkinglot = new Intent(getActivity(), AddParkinglotActivity.class);
                //startActivity(intent_addParkinglot);
            }
        });

        return fragmentView;
    }

}
