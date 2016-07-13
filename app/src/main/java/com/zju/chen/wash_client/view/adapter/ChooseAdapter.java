package com.zju.chen.wash_client.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Type;

import java.util.List;

/**
 * Created by ab on 2016/7/12.
 */
public class ChooseAdapter extends ArrayAdapter<Type>{
    private int mResourceId;
    private int clickTmp=-1;

    public void setSelection(int position){
        clickTmp=position;
    }

    public ChooseAdapter(Context context, int resourceId, List<Type> objects) {
        super(context, resourceId, objects);
        this.mResourceId = resourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Type choose = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView wash_type = (TextView) view.findViewById(R.id.wash_type);
        TextView wash_price = (TextView) view.findViewById(R.id.wash_price);

        wash_type.setText("type:\n"+choose.getName());
        Log.d("!!!!!",choose.getName());
        wash_price.setText("price:\n"+choose.getPrice() + "");
        Log.d("!!!!!",choose.getPrice()+"");

        if(clickTmp==position){
            view.setBackgroundResource(R.drawable.textview_border);
        }else{
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        return view;
    }

}
