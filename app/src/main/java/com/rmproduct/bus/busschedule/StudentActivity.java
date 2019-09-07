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

public class StudentActivity extends AppCompatActivity {

    private ListView busList;
    private List<BusDataAdd> busDataAdds;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        busList = findViewById(R.id.busList);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Student Bus");

        busDataAdds = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                busDataAdds.clear();
                for (DataSnapshot busSnapshot : dataSnapshot.getChildren()) {
                    BusDataAdd busDataAdd = busSnapshot.getValue(BusDataAdd.class);

                    busDataAdds.add(busDataAdd);
                }
                BusData adapter = new BusData(StudentActivity.this, busDataAdds);
                busList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
