package com.cocomelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends Activity {

    ListView parkingListView;
    List<String> listDataHeader;
    ParkingLotListAdapter listAdapter;
    HashMap<String, List<String>> listDataChild;
    FirebaseFirestore db;
    ArrayList<ParkingEntity> parkingEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        // get the listview
        parkingListView = (ListView) findViewById(R.id.list_view_expandable);
        readFirebaseData();
    }

    public void setOnClickListeners() {
        parkingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent detailIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailIntent.putExtra("PARKING_LOT_ID", parkingEntities.get(position).getName());
                detailIntent.putExtra("PARKING_LOT_AVAILABILITY", parkingEntities.get(position).getParkingSpots());
                detailIntent.putExtra("PARKING_LOT_MAX", parkingEntities.get(position).getMaxSpots());

                startActivity(detailIntent);
            }
        });
    }

    public void readFirebaseData() {
        parkingEntities = new ArrayList<>();

        db.collection("ParkingSpots")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int x = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.d("PARKING", document.getId() + " => " + document.getData().get("num_spaces"));

                                ParkingEntity parkEntity= new ParkingEntity();
                                parkEntity.setName(document.getId());
                                parkEntity.setParkingSpots(Integer.valueOf(document.getData().get("num_spaces").toString()));
                                parkEntity.setMaxSpots(Integer.valueOf(document.getData().get("max_spots").toString()));

                                parkingEntities.add(parkEntity);
                                Log.d("SIZE", String.valueOf(parkingEntities.size()));
                                Log.d("SPOTS", String.valueOf(parkingEntities.get(x).getParkingSpots()));
                                x++;
                            }
                        } else {
                            Log.w("PARKING", "Error getting documents.", task.getException());
                        }

                        populateListView();
                    }
                });
    }

    public void populateListView() {
        listAdapter = new ParkingLotListAdapter(this, parkingEntities);
        parkingListView.setAdapter(listAdapter);
        setOnClickListeners();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (listAdapter!=null) {
            readFirebaseData();
            listAdapter.notifyDataSetChanged();
        }
    }
    
    /*public ParkingEntity withinParkingLot(ArrayList<Lot> lots) {
        
        //parkingLot.location
        //currentLocation
        //parkingLot.radius
        
        for (int x = 0; x < lots.size; x++) {
            lot = lots.get(x);
            
            if (currentLocation isWithin (radius of parkingLot)) {
              return lot;
            }
            else {
              continue;
            }
        }
     
        return null;
    }*/
        
}
