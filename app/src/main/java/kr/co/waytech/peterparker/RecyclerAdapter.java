package kr.co.waytech.peterparker;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.*;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private ArrayList<Data> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_picked_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    public void addItem(Data data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textView_address;
        private TextView textView_price;
        private TextView textView_time;
        private ImageView imageView;
        private TextView textview_distance;

        ItemViewHolder(View itemView) {
            super(itemView);

            textView_address = itemView.findViewById(R.id.textView_Address);
            textView_price = itemView.findViewById(R.id.textView_price);
            textView_time = itemView.findViewById(R.id.textView_time);
            imageView = itemView.findViewById(R.id.imageView);
            textview_distance = itemView.findViewById(R.id.textview_distance);
        }

        void onBind(Data data) {
            textView_address.setText(data.getAddress());
            textView_price.setText(data.getContent_Price());
            textView_time.setText(data.getContent_time());
            textview_distance.setText(data.getDistance());
            imageView.setImageResource(data.getResId());
        }
    }
}