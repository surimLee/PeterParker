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
import kr.co.waytech.peterparker.model.BookingList;
import java.util.List;

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

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.parkinglotName.setText(bookingList.get(position).getParkinglotName());
        holder.status.setText(bookingList.get(position).getStatus());
        holder.parkinglotAddress.setText(bookingList.get(position).getParkinglotAddress());
        holder.parkinglotPrice.setText(bookingList.get(position).getParkinglotPrice());
        holder.parkinglotSchedule.setText(bookingList.get(position).getParkinglotSchedule());
        holder.parkinglotImage.setImageResource(bookingList.get(position).getImageUrl());

    }

    @Override
    public int getItemCount() { return bookingList.size(); }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView parkinglotImage;
        TextView parkinglotName, status, parkinglotAddress, parkinglotPrice, parkinglotSchedule;

        public MyViewHolder(@NonNull View itemView) {
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
