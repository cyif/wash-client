package com.zju.chen.wash_client.view.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zju.chen.wash_client.R;
import com.zju.chen.wash_client.model.Room;
import com.zju.chen.wash_client.model.WashMachine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by chen on 16/7/9.
 */
public class MachineAdapter extends ArrayAdapter<WashMachine> {

    private int mResourceId;

    public MachineAdapter(Context context, int resourceId, List<WashMachine> objects) {

        super(context, resourceId, objects);
        this.mResourceId = resourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WashMachine washMachine = getItem(position);
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);
        TextView machineText = (TextView) view.findViewById(R.id.machine);
        TextView timeText = (TextView) view.findViewById(R.id.time);

        machineText.setText("编号: " + washMachine.getId());

        long time;
        switch (washMachine.getStatus()) {
            case 0 :
                timeText.setText("可用");
                return view;
            case 1 :
                time = 60;
                break;
            case 2 :
                time = 60 * 20;
                break;
            case 3 :
                time = 60 * 30;
                break;
            default:
                time = 10;
                break;
        }

       // time *= 1000;
        /*if (washMachine.getBeginTime() != null && washMachine.getEndTime() != null)
            if (washMachine.getEndTime() == null) {
                time -= new Date().getTime() - washMachine.getBeginTime().getTime();
            }
            else*/
        /*if (washMachine.getBeginTime() != null && washMachine.getEndTime() != null)
                time = washMachine.getEndTime().getTime() - washMachine.getBeginTime().getTime();*/

        if (washMachine.getEndTime() != null) {
            time = washMachine.getEndTime().getTime() - new Date().getTime();
            Log.d("Time!!!!!    ", washMachine.getEndTime().toString() + "        " + new Date().toString());
        }

        Log.d("Time!!!!!", time + "");
        TimeCount timeCount = new TimeCount(time, 1000, timeText);
        timeCount.start();

        return view;
    }

    class TimeCount extends CountDownTimer {

        TextView statusView;

        public TimeCount(long millisInFuture, long countDownInterval, View view) {
            super(millisInFuture, countDownInterval);
            statusView = (TextView)view;
        }

        @Override
        public void onFinish() {
            statusView.setText("可用");
        }
        @Override
        public void onTick(long millisUntilFinished){

            statusView.setText("剩余时间: " + millisUntilFinished /1000 + "秒");
        }
    }
}
