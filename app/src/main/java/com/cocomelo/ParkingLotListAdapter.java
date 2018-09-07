package com.cocomelo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ParkingLotListAdapter extends ArrayAdapter<ParkingEntity> {

    protected Context context;
    private ArrayList<ParkingEntity> lots;

    public ParkingLotListAdapter(Context context, ArrayList<ParkingEntity> values) {
        super(context, R.layout.list_group, values);
        this.context = context;
        this.lots = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_group, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.parkingLotName = convertView.findViewById(R.id.lblListHeader);
            viewHolder.parkingLotAvailability = convertView.findViewById(R.id.available_view);
            viewHolder.parkingListItem = convertView.findViewById(R.id.list_row);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ParkingEntity entity = lots.get(position);
        int parkingLotAvailability = entity.getParkingSpots();

        viewHolder.parkingLotName.setText(entity.getName());
        viewHolder.parkingLotAvailability.setText(String.valueOf(entity.getParkingSpots()));

        int parkingLotMaxSpaces = entity.getMaxSpots();
        mapColorAvailability(viewHolder, (double) parkingLotAvailability, (double )parkingLotMaxSpaces);

        return convertView;
    }

    public void mapColorAvailability(ViewHolder holder, double availability, double max) {
        double remainingAvailability = availability / max;
        Log.d("AVAILBILITY", String.valueOf(remainingAvailability));

        if (remainingAvailability >= 0.0 && remainingAvailability < 0.33) {
            holder.parkingListItem.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
        else if (remainingAvailability >= 0.33 && remainingAvailability < 0.66) {
            holder.parkingListItem.setBackgroundColor(context.getResources().getColor(R.color.yellow));

        }
        else if (remainingAvailability >=0.66 && remainingAvailability <=1.00){
            holder.parkingListItem.setBackgroundColor(context.getResources().getColor(R.color.green));
        }

    }

    private static class ViewHolder {
        TextView parkingLotName;
        TextView parkingLotAvailability;
        RelativeLayout parkingListItem;
    }
}