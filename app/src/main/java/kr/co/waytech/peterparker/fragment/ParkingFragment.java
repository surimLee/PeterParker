package kr.co.waytech.peterparker.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import kr.co.waytech.peterparker.activity.AddParkinglotActivity;
import kr.co.waytech.peterparker.activity.PostClass;
import kr.co.waytech.peterparker.adapter.ParkingAdapter;
import kr.co.waytech.peterparker.model.BookingList;
import kr.co.waytech.peterparker.model.ParkingList;

import static kr.co.waytech.peterparker.activity.PostClass.my_parking_lot;

public class ParkingFragment extends Fragment {

    View fragmentView;
    RecyclerView parkingListRecyclerView;
    ParkingAdapter parkingListAdapter;
    public static List<ParkingList> parkingList;
    final PostClass Postc = new PostClass();
    public static int adding_flag = 1;

    public ParkingFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Add dummy data in Booking class here
        if(parkingList == null) {
            parkingList = new ArrayList<>();
        }
        if(adding_flag == 1) {
            Postc.get_my_parking_lot("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9ibGF6aW5nY29kZS5hc3VzY29tbS5jb21cL2FwaVwvbG9naW4iLCJpYXQiOjE1OTgzNDU4MzIsImV4cCI6MTU5ODM0OTQzMiwibmJmIjoxNTk4MzQ1ODMyLCJqdGkiOiI2M0VWUlBLa1dpeDhYRlJ3Iiwic3ViIjoxNSwicHJ2IjoiODdlMGFmMWVmOWZkMTU4MTJmZGVjOTcxNTNhMTRlMGIwNDc1NDZhYSJ9.LNH9ovgm0E5p7XQkznDWSKG5Sx6XzdJdx2LoPmuKO1A");
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    Postc.count_my_parking_lot = Integer.parseInt(my_parking_lot.split(",")[0].split(":")[1]);
                    Postc.my_parking_lot_id = my_parking_lot.split("\\{")[2].split(",")[0].split("\"")[3];
                    Postc.my_parking_lot_name = my_parking_lot.split("\\{")[2].split(",")[2].split("\"")[3];
                    Postc.my_parking_lot_address = my_parking_lot.split("\\{")[2].split(",")[3].split("\"")[3];
                    Postc.my_parking_lot_price = my_parking_lot.split("\\{")[2].split(",")[4].split(":")[1];
                    Postc.my_parking_lot_imageurl = my_parking_lot.split("\\{")[2].split(",")[5].split("\"")[3].replace("\\", "");
                    System.out.println("splited result : " + Postc.count_my_parking_lot + " 개, " + Postc.my_parking_lot_id + ", " + Postc.my_parking_lot_address + ", " + "30분당 " + Postc.my_parking_lot_price + ", " + Postc.my_parking_lot_imageurl + ", ");
                    parkingList.add(new ParkingList(Postc.my_parking_lot_name, Postc.my_parking_lot_address, "30분당 " + Postc.my_parking_lot_price, Postc.my_parking_lot_imageurl, Postc.my_parking_lot_id));
                }
            }, 1000);
            adding_flag = 0;
        }
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
                Intent intent_addParkinglot = new Intent(getActivity(), AddParkinglotActivity.class);
                startActivity(intent_addParkinglot);
            }
        });

        return fragmentView;
    }
    public static void addlist(String name, String address, String price, String img, String ID){
        if(parkingList == null) {
            parkingList = new ArrayList<>();
        }
        parkingList.add(new ParkingList(name, address, price,  img, ID));
    }

}
