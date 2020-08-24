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

import kr.co.waytech.peterparker.DownloadImageTask;
import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.model.BookingList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.MyViewHolder> {

    Context mContext;
    List<BookingList> bookingList;

    public BookingListAdapter(Context mContext, List<BookingList> bookingList) {
        this.mContext = mContext;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.row_bookinglist_item, parent, false);

        final MyViewHolder vHolder = new MyViewHolder(view);


        //Dialog ini
        vHolder.checkin_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
//                Toast.makeText(mContext,"Click Item"+ vHolder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext,"Click Item",Toast.LENGTH_SHORT).show();
                connectIBeacon();
            }
        });
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void connectIBeacon() {
        Beacon beacon = new Beacon.Builder()
                .setId1("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6") // UUID for beacon 변경 가능 => 사용자 uuid를 넣어줘야함
                .setId2("0206") // Major for beacon 고정
                .setId3("0406") // Minor for beacon 고정
                .setManufacturer(0x004C) // Radius Networks.0x0118  Change this for other beacon layouts//0x004C for iPhone
                .setTxPower(-56)// Power in dB
                .setDataFields(Arrays.asList(new Long[] {0l})) // Remove this for beacon layouts without d: fields
                .build();

        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24");

        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(mContext.getApplicationContext(), beaconParser);

        beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {
            @Override
            public void onStartFailure(int errorCode) {
                Toast.makeText(mContext, "Advertisement start failed with code:" +errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                Toast.makeText(mContext, "Advertisement start succeeded.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        View v = holder.itemView;
        holder.parkinglotName.setText(bookingList.get(position).getParkinglotName());
        holder.status.setText(bookingList.get(position).getStatus());
        holder.parkinglotAddress.setText(bookingList.get(position).getParkinglotAddress());
        holder.parkinglotPrice.setText(bookingList.get(position).getParkinglotPrice());
        holder.parkinglotSchedule.setText(bookingList.get(position).getParkinglotSchedule());
        new DownloadImageTask(holder.parkinglotImage).execute(bookingList.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() { return bookingList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button checkin_btn;
        ImageView parkinglotImage;
        TextView parkinglotName, status, parkinglotAddress, parkinglotPrice, parkinglotSchedule;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkin_btn = itemView.findViewById(R.id.btn_check_in);
            parkinglotImage = itemView.findViewById(R.id.parkinglotImage);
            parkinglotName = itemView.findViewById(R.id.parkinglotName);
            status = itemView.findViewById(R.id.status);
            parkinglotAddress = itemView.findViewById(R.id.parkinglotAddress);
            parkinglotPrice = itemView.findViewById(R.id.parkinglotPrice);
            parkinglotSchedule = itemView.findViewById(R.id.parkinglotSchedule);

        }
    }
}
