package com.smoothswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.smoothswitch.helper.DbHelper;
import com.smoothswitch.helper.RingerMode;
import com.smoothswitch.model.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class AddAlarm extends AppCompatActivity {

    TextView alarmPositionEditText;
    TextView alarmNameEditText;
    Spinner alarmModeSpinner;
    SeekBar alarmRadiusSeekBar;
    Button alarmCreateButton;

    private DbHelper mDbHelper;
    Place place;
    LatLng coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        mDbHelper= new DbHelper(this.getBaseContext());
        place = new Place();
        initViewItems();
        addItemsOnModeSpinner();
        addListenerOnCreateAlarmButton();
        addListenerOnSpinnerItemSelection();
        addListenerOnSeekerChange();


        Intent intent = getIntent();
        HashMap<String, LatLng> hashMap = (HashMap<String, LatLng>)intent.getSerializableExtra("localisation");
        coordinates = hashMap.get("latlong");

        alarmPositionEditText.setText(coordinates.latitude + " - " + coordinates.longitude);

        place.setLatitude(coordinates.latitude);
        place.setLongitude(coordinates.longitude);
    }


    private void initViewItems(){
        alarmModeSpinner = (Spinner) findViewById(R.id.alarmModeSpinner);
        alarmPositionEditText = findViewById(R.id.alarmPositionEditText);
        alarmNameEditText = findViewById(R.id.alarmNameEditText);
        alarmRadiusSeekBar = findViewById(R.id.alarmRadiusSeekBar);
        alarmCreateButton = findViewById(R.id.alarmCreateButton);
    }


    private void addListenerOnSeekerChange() {
        alarmRadiusSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }


    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            place.setRadius(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void addListenerOnSpinnerItemSelection() {
        List<String> list = new ArrayList<String>();
        list.add(RingerMode.VIBRATE.name());
        list.add(RingerMode.NORMAL.name());
        list.add(RingerMode.SILENT.name());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        alarmModeSpinner.setAdapter(dataAdapter);
    }

    private void addListenerOnCreateAlarmButton() {
        alarmCreateButton.setOnClickListener(alarmCreateOnClickListener);
    }

    private View.OnClickListener alarmCreateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Adding place alarm to database
            place.setEnabled(true);
            place.setName(alarmNameEditText.getText().toString());
            Logger.getAnonymousLogger().info("PLACE ADDED..........................");
            mDbHelper.addPlace(place);
            Logger.getAnonymousLogger().info("PLACE ADDED..........................");
        }
    };

    private void addItemsOnModeSpinner() {

    }
}
