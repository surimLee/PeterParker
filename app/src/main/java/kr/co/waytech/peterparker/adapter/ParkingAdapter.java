package kr.co.waytech.peterparker.adapter;

import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;

import java.util.Arrays;
import java.util.List;

import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.model.BookingList;
import kr.co.waytech.peterparker.model.ParkingList;

public class ParkingAdapter extends RecyclerView.Adapter<ParkingAdapter.MyViewHolder> {

    Context mContext;
    List<ParkingList> parkingList;

    public ParkingAdapter(Context mContext, List<ParkingList> parkingList) {
        this.mContext = mContext;
        this.parkingList = parkingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_parkinglot_item, parent, false);

        final MyViewHolder vHolder = new MyViewHolder(view);


        //Dialog ini
        vHolder.set_time_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,"Click Item"+ vHolder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext,"Click Timesetting",Toast.LENGTH_SHORT).show();
                //Click Event here
            }
        });

        vHolder.edit_info_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,"Click Item"+ vHolder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext,"Click EditInfo",Toast.LENGTH_SHORT).show();
                //Click Event here
            }
        });
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.parkinglotName.setText(parkingList.get(position).getPLName());
        holder.parkinglotAddress.setText(parkingList.get(position).getPLAddress());
        holder.parkinglotPrice.setText(parkingList.get(position).getPLPrice());
        holder.parkinglotImage.setImageResource(parkingList.get(position).getPLImageUrl());

    }

    @Override
    public int getItemCount() { return parkingList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button set_time_btn, edit_info_btn;
        ImageView parkinglotImage;
        TextView parkinglotName, parkinglotAddress, parkinglotPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            set_time_btn = itemView.findViewById(R.id.btn_setting_sharetime);
            edit_info_btn = itemView.findViewById(R.id.btn_edit_info);
            parkinglotImage = itemView.findViewById(R.id.Parking_parkinglotImage);
            parkinglotName = itemView.findViewById(R.id.Parking_parkinglotName);
            parkinglotAddress = itemView.findViewById(R.id.Parking_parkinglotAddress);
            parkinglotPrice = itemView.findViewById(R.id.Parking_parkinglotPrice);
        }
    }
}