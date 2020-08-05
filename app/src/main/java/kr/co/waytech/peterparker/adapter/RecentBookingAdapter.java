package kr.co.waytech.peterparker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.waytech.peterparker.R;
import kr.co.waytech.peterparker.model.RecentBooking;
import java.util.List;

public class RecentBookingAdapter extends RecyclerView.Adapter<RecentBookingAdapter.RecentViewHolder> {

    Context context;
    List<RecentBooking> recentBookingList;

    public RecentBookingAdapter(Context context, List<RecentBooking> recentBookingList) {
        this.context = context;
        this.recentBookingList = recentBookingList;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.booking_list_item, parent, false);

        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder holder, int position) {

        holder.parkinglotName.setText(recentBookingList.get(position).getParkinglotName());
        holder.status.setText(recentBookingList.get(position).getStatus());
        holder.parkinglotAddress.setText(recentBookingList.get(position).getParkinglotAddress());
        holder.parkinglotPrice.setText(recentBookingList.get(position).getParkinglotPrice());
        holder.parkinglotSchedule.setText(recentBookingList.get(position).getParkinglotSchedule());
        holder.parkinglotImage.setImageResource(recentBookingList.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() {
        return recentBookingList.size();
    }

    public static final class RecentViewHolder extends RecyclerView.ViewHolder {

        ImageView parkinglotImage;
        TextView parkinglotName, status, parkinglotAddress, parkinglotPrice, parkinglotSchedule;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);

            parkinglotImage = itemView.findViewById(R.id.parkinglotImage);
            parkinglotName = itemView.findViewById(R.id.parkinglotName);
            status = itemView.findViewById(R.id.status);
            parkinglotAddress = itemView.findViewById(R.id.parkinglotAddress);
            parkinglotPrice = itemView.findViewById(R.id.parkinglotPrice);
            parkinglotSchedule = itemView.findViewById(R.id.parkinglotSchedule);

        }
    }
}
