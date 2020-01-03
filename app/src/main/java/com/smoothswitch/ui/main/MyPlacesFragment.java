package com.smoothswitch.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.smoothswitch.R;
import com.smoothswitch.helper.DbHelper;
import com.smoothswitch.model.Place;
import com.smoothswitch.model.PlacesAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A placeholder fragment containing a simple view.
 */
public class MyPlacesFragment extends Fragment {

    private final int index = 1;
    private PageViewModel pageViewModel;
    private ListView mPlacesListView;
    private PlacesAdapter mPlaceAdapter;
    private DbHelper mDbHelper;

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
        View root = inflater.inflate(R.layout.fragment_my_places, container, false);
        mPlacesListView = root.findViewById(R.id.myPlacesListView);
        mDbHelper = new DbHelper(getContext());
       /* List<Place> myPlaces = new ArrayList<>();
        myPlaces.add(new Place(1, "ENSA", 35.5622, 5.3645, 10, true));
        myPlaces.add(new Place(1, "MAROC", 35.5622, 5.3645, 20, true));
        myPlaces.add(new Place(1, "YAOUNDE", 35.5622, 5.3645, 30, true));
        myPlaces.add(new Place(1, "BOBO-DSS0", 35.5622, 5.3645, 40, true));
        mDbHelper.addPlaceAll(myPlaces);*/
        mPlaceAdapter = new PlacesAdapter(getContext(), mDbHelper.getAllPlaces()); //crazy just to test dbHelper
        mPlacesListView.setAdapter(mPlaceAdapter);
        mPlaceAdapter.notifyDataSetChanged();
        return root;
    }
}