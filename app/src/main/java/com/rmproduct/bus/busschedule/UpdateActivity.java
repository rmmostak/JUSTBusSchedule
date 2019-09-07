package com.rmproduct.bus.busschedule;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private ListView busList;
    private List<BusDataAdd> busDataAdds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final Spinner spinner=findViewById(R.id.updateSpinner);
        final Button show=findViewById(R.id.showBus);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinner_name=spinner.getSelectedItem().toString().trim();
                if (spinner_name.equals("Student Bus")) {
                    BusList(spinner_name);
                    return;
                }
                if (spinner_name.equals("Teacher Bus")) {
                    BusList(spinner_name);
                    return;
                }
                if (spinner_name.equals("Stuff Bus")) {
                    BusList(spinner_name);
                    return;
                }
            }
        });

        busList=findViewById(R.id.busList);
        busDataAdds=new ArrayList<>();

        busList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long i) {

                BusDataAdd busDataAdd=busDataAdds.get(position);
                showUpdateDialog(busDataAdd.getId(), busDataAdd.getBusName(), busDataAdd.getDepartureTime(), busDataAdd.getArrivalTime());

                return false;
            }
        });
    }

    void BusList(String busType) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child(busType);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                busDataAdds.clear();
                for (DataSnapshot busSnapshot : dataSnapshot.getChildren()) {
                    BusDataAdd busDataAdd = busSnapshot.getValue(BusDataAdd.class);

                    busDataAdds.add(busDataAdd);
                }
                BusData adapter = new BusData(UpdateActivity.this, busDataAdds);
                busList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String id, String busName, String departureTime, String arrivalTime) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater=getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);

        dialogBuilder.setView(dialogView);

        final EditText bus_name=dialogView.findViewById(R.id.dialogBusName);
        final EditText departure_time=dialogView.findViewById(R.id.dialogDepartureTime);
        final EditText arrival_time=dialogView.findViewById(R.id.dialogArrivalTime);
        final Button update=dialogView.findViewById(R.id.dialogUpdate);

        bus_name.setText(busName);
        departure_time.setText(departureTime);
        arrival_time.setText(arrivalTime);

        dialogBuilder.setTitle("Bus Name: "+busName);
        final AlertDialog alertDialog=dialogBuilder.create();
        alertDialog.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busName=bus_name.getText().toString().trim();
                String departure=departure_time.getText().toString().trim();
                String arrival=arrival_time.getText().toString().trim();

                if (TextUtils.isEmpty(busName)) {
                    bus_name.setError("Bus Name Required!");
                    bus_name.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(departure)) {
                    departure_time.setError("Departure Time Required!");
                    departure_time.requestFocus();
                }
                if (TextUtils.isEmpty(arrival)) {
                    arrival_time.setError("Arrival Time Required!");
                    arrival_time.requestFocus();
                }

                updateData(id, busName, departure, arrival);
                alertDialog.dismiss();

            }
        });


    }

    private boolean updateData(String id, String Bus_Name, String Departure_Time, String Arrival_Time) {
        Spinner spinner=findViewById(R.id.updateSpinner);
        String spinner_name=spinner.getSelectedItem().toString().trim();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child(spinner_name).child(id);
        BusDataAdd busDataAdd=new BusDataAdd(id, Bus_Name, Departure_Time, Arrival_Time);
        databaseReference.setValue(busDataAdd);

        return true;
    }
}
