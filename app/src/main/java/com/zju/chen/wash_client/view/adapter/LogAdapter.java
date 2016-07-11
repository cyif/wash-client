package com.zju.chen.wash_client.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.DealLog;
import com.zju.chen.wash_client.model.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16/7/10.
 */
public class LogAdapter extends ArrayAdapter<DealLog> {

    private int mResourceId;

    public LogAdapter(Context context, int resourceId, List<DealLog> objects) {
        super(context, resourceId, objects);
        this.mResourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DealLog dealLog = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView timeText = (TextView) view.findViewById(R.id.dealTime);
        TextView washMachineText = (TextView) view.findViewById(R.id.washId);
        TextView moneyText = (TextView)view.findViewById(R.id.money);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeText.setText("时间: " + fmt.format(dealLog.getDealTime()).toString());
        washMachineText.setText("机器编号: " + dealLog.getWashId());
        moneyText.setText("金额: " + dealLog.getMoney() + " 元");

        return view;
    }
}
