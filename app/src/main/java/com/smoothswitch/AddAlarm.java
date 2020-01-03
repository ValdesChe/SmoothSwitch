package com.smoothswitch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView alarmeRadiusText;
    Button alarmCreateButton;
    private String selectedMode = "";

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
        alarmeRadiusText = findViewById(R.id.alarmeRadiusText);
        alarmCreateButton = findViewById(R.id.alarmCreateButton);
    }


    private void addListenerOnSeekerChange() {
        alarmRadiusSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        alarmModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMode = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedMode = RingerMode.NORMAL.name();
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            place.setRadius(i);
            alarmeRadiusText.setText(new StringBuilder().append(String.valueOf(i)).append(" meter around the marker !").toString());
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
            place.setRingerMode(selectedMode);
            place.setName(alarmNameEditText.getText().toString());
            mDbHelper.addPlace(place);
            Logger.getAnonymousLogger().info("PLACE ADDED..........................");
            alarmNameEditText.setText("");
            Toast.makeText(getApplicationContext(), "Alarm mode created", Toast.LENGTH_LONG);

            // Redirect to list View
            openMainActivity();
        }
    };


    // Lancement de la fenetre d'accueil
    private void openMainActivity() {
        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(intent);
    }
}
