package com.rmproduct.bus.busschedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class BusDataAdd {
    String id, busName, departureTime, arrivalTime;

    public BusDataAdd() {

    }

    public BusDataAdd(String id, String busName, String departureTime, String arrivalTime) {
        this.id=id;
        this.busName = busName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public String getBusName() {
        return busName;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }
}


public class MainActivity extends AppCompatActivity {

    private EditText busName, departureTime, arrivalTime;
    private Button addData;
    private Spinner busCategory;
    //private ListView busList;
    //private List<BusDataAdd> busDataAdds;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busCategory = findViewById(R.id.busCategory);
        busName = findViewById(R.id.busName);
        departureTime = findViewById(R.id.departureTime);
        arrivalTime = findViewById(R.id.arrivalTime);
        addData = findViewById(R.id.addData);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBusData();
            }
        });

        Button update=findViewById(R.id.updateData);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UpdateActivity.class));
            }
        });

    }


    public void addBusData() {
        String bus_Category = busCategory.getSelectedItem().toString().trim();
        String bus_Name = busName.getText().toString().trim();
        String bus_departureTime = departureTime.getText().toString().trim();
        String bus_arrivalTime = arrivalTime.getText().toString().trim();

        if (!TextUtils.isEmpty(bus_Name)) {
            if (!TextUtils.isEmpty(bus_departureTime)) {
                if (!TextUtils.isEmpty(bus_arrivalTime)) {

                    String id = databaseReference.push().getKey();
                    BusDataAdd busDataAdd = new BusDataAdd(id, bus_Name, bus_departureTime, bus_arrivalTime);
                    databaseReference.child(bus_Category).child(id).setValue(busDataAdd);

                    Toast.makeText(getApplicationContext(), "Data Added Successful!", Toast.LENGTH_SHORT).show();

                } else {
                    arrivalTime.setError("Please set arrival time");
                    arrivalTime.requestFocus();
                    return;
                }

            } else {
                departureTime.setError("Please set departure time.");
                departureTime.requestFocus();
                return;
            }

        } else {
            busName.setError("Set a Bus Name");
            busName.requestFocus();
            return;
        }

    }
}
