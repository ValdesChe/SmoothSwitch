package com.smoothswitch.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.smoothswitch.R;
import com.smoothswitch.helper.GPSPoint;
import com.smoothswitch.helper.LocationHelper;
import com.smoothswitch.helper.Workable;

public class GalleryFragment extends Fragment implements Workable<GPSPoint> {

    private GalleryViewModel galleryViewModel;
    private TextView latLongView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        latLongView = root.findViewById(R.id.latLongView);
        LocationHelper.instance().onChange(this);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, textView::setText);
        return root;
    }

    @Override
    public void work(GPSPoint gpsPoint) {
        latLongView.setText(gpsPoint.toString());
    }
}