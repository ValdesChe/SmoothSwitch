package com.smoothswitch.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smoothswitch.AddAlarm;
import com.smoothswitch.R;
import com.smoothswitch.helper.GPSPoint;
import com.smoothswitch.helper.LocationHelper;
import com.smoothswitch.helper.Workable;

import java.util.HashMap;
import java.util.logging.Logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlacePickerFragment extends Fragment implements OnMapReadyCallback, Workable<GPSPoint> {

    private GoogleMap mMap;
    private final int index = 1;
    private PageViewModel pageViewModel;
    Marker markerName = null;
    Marker markerClicked = null;

    private Button mButtonAddAlarm;


    public void intiViewItems(View rootView){
        mButtonAddAlarm = rootView.findViewById(R.id.add_alarm_btn);
        // Setting click listener on add button
        mButtonAddAlarm.setOnClickListener(buttonAddClicked);
    }

    // When add button is clicked
    public View.OnClickListener buttonAddClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Ouverture de la nouvelle fenetre
           openAddActivity();
        }
    };

    // Lancement de la fenetre d'ajout d'alarme
    private void openAddActivity() {
        // Setting the params
        HashMap<String, LatLng> hashMap = new HashMap<String, LatLng>();
        // On recupere les parametres depuis le marker
        hashMap.put("latlong", markerClicked.getPosition());
        Intent intent = new Intent(this.getContext() , AddAlarm.class);
        intent.putExtra("localisation", hashMap);
        // On demarre la nouvelle activité
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_picker, container, false);
        rootView.setClickable(true);
        // Init components on view
        intiViewItems(rootView);
        // Hiding the add btn
        mButtonAddAlarm.setVisibility(View.INVISIBLE);

        return  rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }
        mapFragment.getMapAsync(this);
    }

    public GoogleMap.OnMapClickListener mapClickListener = new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            // Si un maker existe le retirer et en créer un nouveau
            if(markerClicked != null)
                markerClicked.remove();
            if(mButtonAddAlarm.getVisibility() != View.VISIBLE)
                mButtonAddAlarm.setVisibility(View.VISIBLE);
            markerClicked = mMap.addMarker(new MarkerOptions().position(latLng));
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(mapClickListener);
        LocationHelper.instance().onChange(this);
    }

    @Override
    public void work(GPSPoint gpsPoint) {
        LatLng userLocation = new LatLng(gpsPoint.getLatitude(), gpsPoint.getLongitude());
        if(markerName != null)
            markerName.remove();
        markerName = mMap.addMarker(new MarkerOptions().position(userLocation).title("Votre position"));
        mMap.getUiSettings().setZoomControlsEnabled(true); // Enable zoom
    }
}