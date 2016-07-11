package com.zju.chen.wash_client.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Room;

import java.util.List;

/**
 * Created by chen on 16/7/9.
 */
public class  RoomAdapter extends ArrayAdapter<Room> {

    private int mResourceId;

    public RoomAdapter(Context context, int resourceId, List<Room> objects) {
        super(context, resourceId, objects);
        this.mResourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Room room = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView roomText = (TextView) view.findViewById(R.id.room);
        TextView numText = (TextView) view.findViewById(R.id.num);

        roomText.setText("房间号: " + room.getRoom());
        numText.setText("洗衣机数量: " + room.getNum());

        return view;
    }

}
