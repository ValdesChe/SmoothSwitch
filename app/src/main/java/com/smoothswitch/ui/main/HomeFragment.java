package com.smoothswitch.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.smoothswitch.R;
import com.smoothswitch.helper.GPSPoint;
import com.smoothswitch.helper.LocationHelper;
import com.smoothswitch.helper.RingerModeManager;
import com.smoothswitch.helper.Workable;


/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment implements Workable<GPSPoint> {

    private final int index = 0;
    private PageViewModel pageViewModel;

    private TextView placeNameView;
    private TextView latitudeView;
    private TextView longitudeView;
    private ImageView imageView;

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
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //initialize your view here for use view.findViewById("your view id")
        imageView = root.findViewById(R.id.iconImage);
        placeNameView = root.findViewById(R.id.placeName);
        latitudeView = root.findViewById(R.id.latitude);
        longitudeView = root.findViewById(R.id.lognitude);

        return root;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        pageViewModel.setIndex(index);
        LocationHelper.instance().onChange(this);
    }

    public void work(GPSPoint gpsPoint) {
        RingerModeManager ringerModeManager= new RingerModeManager(this.getActivity());
        String mode= ringerModeManager.getCurrentRingerMode().name();
        switch (mode){
            case "SILENT":
                imageView.setImageResource( R.drawable.silent);
                break;
            case "VIBRATE":
                imageView.setImageResource( R.drawable.vibrate);
                break;
            case "NORMAL":
                imageView.setImageResource( R.drawable.normal);
                break;
            default:
                imageView.setImageResource(R.drawable.loading);
                break;

        }
        setTextViews(gpsPoint);
    }

    private void setTextViews(GPSPoint gpsPoint) {
        latitudeView.setText( String.valueOf(gpsPoint.getLatitude()) );
        longitudeView.setText( String.valueOf(gpsPoint.getLongitude()) );
        placeNameView.setText("DEFAULT");
        // placeNameView.setText(gpsPoint.getPlaceName());
    }

}