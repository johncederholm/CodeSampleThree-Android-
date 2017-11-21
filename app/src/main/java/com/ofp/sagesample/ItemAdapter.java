package com.ofp.sagesample;

/**
 * Created by johncederholm on 11/20/17.
 */

import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends BaseAdapter implements View.OnClickListener {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TrackedItem> mDataSource;


    public ItemAdapter(Context context, ArrayList<TrackedItem> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void changeValue(int position, int modification) {
        int currentAmount = mDataSource.get(position).amount;
        if (currentAmount + modification < 0) {
            return;
        }
        mDataSource.get(position).amount += modification;

        this.notifyDataSetChanged();
        saveObject(mDataSource.get(position));
        Log.v("LOG_D", String.valueOf(mDataSource.get(position).amount));
        return;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {

            convertView = mInflater.inflate(R.layout.list_item, parent, false);

            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.item_list_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.item_list_title);
            holder.subtitleTextView = (TextView) convertView.findViewById(R.id.item_list_subtitle);
            holder.detailTextView = (TextView) convertView.findViewById(R.id.item_list_detail);
            holder.buttonUp = (ImageButton) convertView.findViewById(R.id.upButton);
            holder.buttonDown = (ImageButton) convertView.findViewById(R.id.downButton);

            holder.buttonUp.setOnClickListener(this);
            holder.buttonDown.setOnClickListener(this);

            HashMap<String, Object> upObject = new HashMap<String, Object>();
            upObject.put("position", position);
            upObject.put("direction", "up");

            HashMap<String, Object> downObject = new HashMap<String, Object>();
            downObject.put("position", position);
            downObject.put("direction", "down");

            holder.buttonUp.setTag(R.string.buttonKey, upObject);
            holder.buttonDown.setTag(R.string.buttonKey, downObject);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        TextView titleTextView = holder.titleTextView;
        TextView subtitleTextView = holder.subtitleTextView;
        TextView detailTextView = holder.detailTextView;
        ImageView thumbnailImageView = holder.thumbnailImageView;
        TrackedItem item = (TrackedItem) getItem(position);

        titleTextView.setText(item.title);
        subtitleTextView.setText(item.description);
        detailTextView.setText(String.valueOf(item.amount));

        Picasso.with(mContext).load(item.imageUrl).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-Bold.ttf");
        titleTextView.setTypeface(titleTypeFace);

        Typeface subtitleTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-SemiBoldItalic.ttf");
        subtitleTextView.setTypeface(subtitleTypeFace);

        Typeface detailTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Bold.otf");
        detailTextView.setTypeface(detailTypeFace);

        return convertView;
    }

    @Override public void onClick(View v) {
        HashMap<String, Object> obj = (HashMap<String, Object>) v.getTag(R.string.buttonKey);
        int position = (int) obj.get("position");
        int change = 0;
        String direction = (String) obj.get("direction");
        if (direction == "up") {
            change = 1;
        } else if (direction == "down") {
            change = -1;
        }
        this.changeValue(position, change);
    }

    private static class ViewHolder {
        public TextView titleTextView;
        public TextView subtitleTextView;
        public TextView detailTextView;
        public ImageView thumbnailImageView;
        public ImageButton buttonUp;
        public ImageButton buttonDown;
    }

    private static void saveObject(final TrackedItem item) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SAGESample");
        query.whereEqualTo("type", item.type);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.v("OBJECT", String.valueOf(objects.get(0)));
                    ParseObject object = objects.get(0);
                    object.put("value", item.amount);
                    object.saveInBackground();
                } else {
                    Log.v("ERROR", e.getLocalizedMessage());
                }
            }
        });
    }
}

