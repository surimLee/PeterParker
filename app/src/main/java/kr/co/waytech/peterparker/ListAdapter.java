package kr.co.waytech.peterparker;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.*;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;

import kr.co.waytech.peterparker.activity.BookingActivity;
import kr.co.waytech.peterparker.activity.LoginActivity;
import kr.co.waytech.peterparker.activity.PostClass;

import static kr.co.waytech.peterparker.activity.BookingActivity.booking_parking_lot_address;
import static kr.co.waytech.peterparker.activity.BookingActivity.booking_parking_lot_phone;

import static kr.co.waytech.peterparker.activity.BookingActivity.booking_parking_lot_price;

public class ListAdapter extends BaseAdapter {

    private Context mContext;
    public static String address, price, distance, phone, ID;

    private ArrayList<ListData> array_parking_lot = new ArrayList<ListData>();
    final PostClass Postc = new PostClass();


    public ListAdapter() {

    }

    // position 위치의 아이템 타입 리턴.

    @Override
    public int getCount() {
        return array_parking_lot.size();
    }

    public Object getItem(int position) {
        return array_parking_lot.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        int viewType = getItemViewType(position) ;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final ListData listViewItem = array_parking_lot.get(position);

        convertView = inflater.inflate(R.layout.map_list_item,
                parent, false);
        Postc.send_Location(listViewItem.getId());
        System.out.println("get id " + listViewItem.getId());
        TextView textView_address = (TextView)convertView.findViewById(R.id.list_text_address);
        TextView textView_price = (TextView) convertView.findViewById(R.id.list_text_price);

        TextView textView_Distance = (TextView) convertView.findViewById(R.id.text_distance);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_image);
        textView_address.setText(listViewItem.getAddress());
        textView_price.setText(listViewItem.getContent_Price());
        textView_Distance.setText(listViewItem.getDistance());
        Handler mHandler = new Handler();
        final View finalConvertView = convertView;
        mHandler.postDelayed(new Runnable()  {
            public void run() {
                new DownloadImageTask((ImageView) finalConvertView.findViewById(R.id.list_image)).execute(Postc.Parking_img[0]);
            }
        }, 200);
        final CardView cmdArea = (CardView)convertView.findViewById(R.id.List_cardview);
        cmdArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(getItemId(position));
                address = listViewItem.getAddress();
                price = listViewItem.getContent_Price();
                distance = listViewItem.getDistance();
                System.out.println(address);
                Postc.send_Location(listViewItem.getId());

                ID = listViewItem.getId();
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable()  {
                    public void run() {
                        phone = Postc.Parking_phone[0];
                        // 시간 지난 후 실행할 코딩
                        Intent intent = new Intent(context.getApplicationContext(), BookingActivity.class);
                        context.startActivity(intent);
                    }
                }, 1000); // 0.5초후

            }
        });
        return convertView;
    }
    public void addItem(String Address, String price, String Distance, String id) {
        ListData item = new ListData() ;
        item.setAddress(Address);
        item.setContent_Price(price);
        item.setDistance(Distance);
        item.setId(id);
        array_parking_lot.add(item);
    }

/*
    @Override
            public int getCount() {
                return array_parking_lot.size();
            }

            @Override
            public Object getItem(int position) {
                return array_parking_lot.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }



            @Override

            public View getView(int position, View convertView, ViewGroup parent) {
                // ViewHoldr 패턴
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.map_list_item, parent, false);
                    mViewHolder = new ViewHolder(convertView);
                    convertView.setTag(mViewHolder);
                } else {
                    mViewHolder = (ViewHolder) convertView.getTag();
                }

                // View에 Data 세팅
                for(int i = 0; i < 5; i ++) {
                    String[] Data = array_parking_lot.get(i).split("\\*");
                    System.out.println(Data[i]);
                    mViewHolder.textView_address.setText(Data[1]);
                    mViewHolder.textView_price.setText(Data[2] + " 원");
                }

                return convertView;

            }



            public class ViewHolder {
                private TextView textView_address;
                private TextView textView_price;
                private ImageView imageView;

                public ViewHolder(View convertView) {

            textView_address = (TextView) convertView.findViewById(R.id.list_text_address);
            textView_price = (TextView) convertView.findViewById(R.id.list_text_price);
            imageView = (ImageView) convertView.findViewById(R.id.list_image);

        }

    }

 */

}

