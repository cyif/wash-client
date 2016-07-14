package com.zju.chen.wash_client.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.WashMachine;

import java.util.List;

/**
 * Created by ab on 2016/7/13.
 */
public class StatusAdapter extends ArrayAdapter<WashMachine> {
    private int mResourceId;
    public StatusAdapter(Context context, int resourceId, List<WashMachine> objects) {
        super(context, resourceId, objects);
        this.mResourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        WashMachine washMachine = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView status_id = (TextView) view.findViewById(R.id.status_id);
        TextView status_info = (TextView) view.findViewById(R.id.status_info);

        status_id.setText("machine id:  "+washMachine.getId());
        if(washMachine.getStatus()==1){
            status_info.setText("using");
            //status_image.setImageResource();
        }
        else{
            status_info.setText("end");
        }

        return view;
    }

}
