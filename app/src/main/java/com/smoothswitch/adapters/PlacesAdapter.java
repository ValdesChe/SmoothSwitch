package com.smoothswitch.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.smoothswitch.R;
import com.smoothswitch.model.Place;

import java.util.List;

public class PlacesAdapter extends ArrayAdapter<Place> {

    public PlacesAdapter(Context context, List<Place> places) {
        super(context, R.layout.place_view, places);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.place_view, parent, false);
            Place place = getItem(position);
            if (place != null) {
                TextView placeName = convertView.findViewById(R.id.placeNameTextView);
                TextView placeLat = convertView.findViewById(R.id.placeLatTextView);
                TextView placeLong = convertView.findViewById(R.id.placeLongTextView);
                TextView placeRadius = convertView.findViewById(R.id.placeRadiusTextView);
                Switch placeIsEnabled = convertView.findViewById(R.id.placeIsEnabledSwitch);
                Resources res = convertView.getResources();
                placeName.setText(res.getString(R.string.place_name, place.getName()));
                placeRadius.setText(res.getString(R.string.place_radius, place.getRadius()));
                placeLat.setText(res.getString(R.string.place_lat, place.getLatitude()));
                placeLong.setText(res.getString(R.string.place_long, place.getLongitude()));
                placeIsEnabled.setChecked(place.isEnabled());
            }
        }
        return convertView;
    }
}
