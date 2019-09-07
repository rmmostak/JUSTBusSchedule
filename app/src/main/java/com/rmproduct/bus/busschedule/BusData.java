package com.rmproduct.bus.busschedule;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class BusData extends ArrayAdapter<BusDataAdd> {

    private Activity context;
    private List<BusDataAdd> busesList;

    public BusData(Activity context, List<BusDataAdd> busesList) {
        super(context, R.layout.list_layout, busesList);
        this.context=context;
        this.busesList=busesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout, null, true);

        TextView busNameId= listViewItem.findViewById(R.id.busNameID);
        TextView departureTimeId= listViewItem.findViewById(R.id.departureTimeId);
        TextView arrivalTimeId= listViewItem.findViewById(R.id.arrivalTimeId);

        BusDataAdd busData=busesList.get(position);
        busNameId.setText(busData.getBusName());
        departureTimeId.setText("Departure Time: "+busData.getDepartureTime());
        arrivalTimeId.setText("Arrival Time: "+busData.getArrivalTime());

        return listViewItem;
    }
}
