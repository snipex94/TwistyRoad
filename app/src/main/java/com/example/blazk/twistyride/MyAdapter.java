package com.example.blazk.twistyride;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.blazk.twistyride.MyDatabaseHelper.COLUMN_DATATYPE_INDEX;
import static com.example.blazk.twistyride.MyDatabaseHelper.COLUMN_DATE_INDEX;
import static com.example.blazk.twistyride.MyDatabaseHelper.COLUMN_IMAGEPATH;
import static com.example.blazk.twistyride.MyDatabaseHelper.COLUMN_IMAGEPATH_INDEX;
import static com.example.blazk.twistyride.MyDatabaseHelper.COLUMN_SERVICEINFO_INDEX;
import static com.example.blazk.twistyride.MyDatabaseHelper.COLUMN_TIMESTAMP_INDEX;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<List<String>> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView shopName;
        TextView cost;
        ImageView photo;
        TextView date;
        TextView info;

        public ViewHolder(View v) {
            super(v);
            cv = (CardView)itemView.findViewById(R.id.cv);
            shopName = (TextView)itemView.findViewById(R.id.shop_name);
            cost = (TextView)itemView.findViewById(R.id.cost);
            photo = (ImageView)itemView.findViewById(R.id.photo);
            date = (TextView)itemView.findViewById(R.id.date);
            info = (TextView)itemView.findViewById(R.id.info);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<List<String>> myDataset) {
        mDataset = myDataset;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //System.out.println(Arrays.deepToString(mDataset.toArray()));

        position = mDataset.size() - position - 1;

        if(mDataset.get(position).get(COLUMN_DATATYPE_INDEX).contains("TEXT")) {
            String serviceInfo = mDataset.get(position).get(COLUMN_SERVICEINFO_INDEX).toString();
            String[] serviceInfo_nibbles = serviceInfo.split(";");
            Log.d("MyAdapter", serviceInfo);
            holder.shopName.setText(serviceInfo_nibbles[0]);
            holder.cost.setText("Cost: " + serviceInfo_nibbles[1] + " €");

            String serviceInfoString = serviceInfo_nibbles[2];

            for (int i = 3; i <= (serviceInfo_nibbles.length - 2); i++) {
                serviceInfoString = serviceInfoString + ", " + serviceInfo_nibbles[i];
            }

            holder.date.setText("Date:     " + mDataset.get(position).get(COLUMN_DATE_INDEX));
            holder.info.setText(serviceInfoString);
            holder.cv.getLayoutParams().height = 700;
            holder.photo.setVisibility(View.INVISIBLE);
        }else if(mDataset.get(position).get(COLUMN_DATATYPE_INDEX).contains("IMAGE")){
            Log.d("MyAdapter", mDataset.get(position).get(COLUMN_IMAGEPATH_INDEX));
            holder.photo.setImageURI(Uri.parse(mDataset.get(position).get(COLUMN_IMAGEPATH_INDEX)));
            holder.date.setText(mDataset.get(position).get(COLUMN_TIMESTAMP_INDEX));
        }
    }

    @Override
    public int getItemCount() {
        Log.d("MyAdapter", "Size of Database is : " + String.valueOf(mDataset.size()));
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
