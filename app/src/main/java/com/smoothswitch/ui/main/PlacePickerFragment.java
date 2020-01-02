package com.smoothswitch.ui.main;

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
import com.smoothswitch.R;
import com.smoothswitch.helper.GPSPoint;
import com.smoothswitch.helper.LocationHelper;
import com.smoothswitch.helper.Workable;

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
        return  rootView;

        /*
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);

        if (mapFragment == null){
            FragmentManager fragmentManager=getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment= SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.mapView, mapFragment).commit();

            Logger.getAnonymousLogger().info("MAP NULL ....................");
        }
        mapFragment.getMapAsync(this);
        return rootView;*/
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