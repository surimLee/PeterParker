package kr.co.waytech.peterparker.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.adapter.BookingListAdapter;
import kr.co.waytech.peterparker.model.BookingList;

import java.util.ArrayList;
import java.util.List;

public class BookingListFragment extends Fragment {

    View fragmentView;
    RecyclerView bookingListRecyclerView;
    BookingListAdapter bookingListAdapter;
    List<BookingList> bookingList;

    public BookingListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Add dummy data in Booking class here
        bookingList = new ArrayList<>();
        bookingList.add(new BookingList("산기대 종합관 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "3,000원", "2020년 07월 20일(월) 12:00\n~2020년 07월 20일(월) 14:00", R.drawable.parkinglot4));
        bookingList.add(new BookingList("산기대 E동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot5));
        bookingList.add(new BookingList("산기대 A동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "4,000원", "2020년 07월 15일(금) 14:00\n~2020년 07월 15일(금) 15:00", R.drawable.parkinglot1));
        bookingList.add(new BookingList("산기대 B동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "1,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot4));
        bookingList.add(new BookingList("산기대 C동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "1,400원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot2));
        bookingList.add(new BookingList("산기대 D동 뒤","사용완료","경기도 시흥시 정왕동 2121-1", "2,500원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot5));
        bookingList.add(new BookingList("시흥비즈니스센터","사용완료","경기도 시흥시 정왕동 2121-1", "2,000원", "2020년 07월 17일(금) 14:00\n~2020년 07월 17일(금) 15:00", R.drawable.parkinglot3));

    }



    Button DeleteBookingBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_bookinglist, container,false);
        bookingListRecyclerView = fragmentView.findViewById(R.id.recent_recycler);
        bookingListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookingListRecyclerView.setHasFixedSize(true);

        bookingListAdapter = new BookingListAdapter(getActivity(), bookingList);
        bookingListRecyclerView.setAdapter(bookingListAdapter);


        return fragmentView;

    }

}
