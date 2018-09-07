package com.cocomelo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivity extends Activity {

    Button parkingHereButton;
    Button findCarButton;
    Button leavingButton;
    String parkingLotName;
    int parkingLotAvailability;
    int parkingLotMaxSpots;
    FirebaseFirestore db;
    DocumentReference parkingLotRef;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        parkingLotName = getIntent().getExtras().getString("PARKING_LOT_ID");
        parkingLotAvailability = getIntent().getExtras().getInt("PARKING_LOT_AVAILABILITY");
        parkingLotMaxSpots = getIntent().getExtras().getInt("PARKING_LOT_MAX");

        parkingHereButton = findViewById(R.id.parking_here_button);
        findCarButton = findViewById(R.id.find_car_button);
        leavingButton = findViewById(R.id.leaving_button);
        toolbarTitle = findViewById(R.id.toolbar_title);

        toolbarTitle.setText(parkingLotName);

        db = FirebaseFirestore.getInstance();
        parkingLotRef = db.collection("ParkingSpots").document(parkingLotName);
        setupOnClickListeners();
    }

    public void setupOnClickListeners() {
        parkingHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updatedAvailability = parkingLotAvailability--;
                if (parkingLotAvailability > 0) {
                    parkingLotRef.update("num_spaces", updatedAvailability);
                }
                else {
                    parkingLotRef.update("num_spaces", 0);
                    parkingLotAvailability = 0;
                }
            }
        });

        leavingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updatedAvailability = parkingLotAvailability++;
                if (parkingLotAvailability < parkingLotMaxSpots) {
                    parkingLotRef.update("num_spaces", updatedAvailability);
                }
                else {
                    parkingLotRef.update("num_spaces", parkingLotMaxSpots);
                    parkingLotAvailability = parkingLotMaxSpots;
                }
            }
        });
    }
}