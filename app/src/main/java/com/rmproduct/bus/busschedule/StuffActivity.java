package com.rmproduct.bus.busschedule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StuffActivity extends AppCompatActivity {

    private ListView busList;
    private List<BusDataAdd> busDataAdds;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff);

        busList = findViewById(R.id.stuffList);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Stuff Bus");

        busDataAdds = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                busDataAdds.clear();
                for (DataSnapshot busSnapshot : dataSnapshot.getChildren()) {
                    BusDataAdd busDataAdd = busSnapshot.getValue(BusDataAdd.class);

                    busDataAdds.add(busDataAdd);
                }
                BusData adapter = new BusData(StuffActivity.this, busDataAdds);
                busList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
