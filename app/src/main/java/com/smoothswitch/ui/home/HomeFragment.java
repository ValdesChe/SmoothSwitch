package com.smoothswitch.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.smoothswitch.R;
import com.smoothswitch.RingerMode;
import com.smoothswitch.RingerModeManager;

public class HomeFragment extends Fragment {

    private RingerModeManager soundModeManager;

    private HomeViewModel homeViewModel;
    private Button silent, normal, vibrate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        silent = root.findViewById(R.id.silentModeButton);
        normal = root.findViewById(R.id.ringerModeButton);
        vibrate = root.findViewById(R.id.vibrateModeButton);
//        final TextView textView = root.findViewById(R.id.text_home);
        soundModeManager = new RingerModeManager(getActivity());

        silent.setOnClickListener(v -> soundModeManager.setRingerMode(RingerMode.SILENT));
        normal.setOnClickListener(v -> soundModeManager.setRingerMode(RingerMode.NORMAL));
        vibrate.setOnClickListener(v -> {
            soundModeManager.setRingerMode(RingerMode.VIBRATE);
            Toast.makeText(getContext(), "VIBRATE", Toast.LENGTH_SHORT).show();
        });

        homeViewModel.getText().observe(this, s -> {
//                textView.setText(s);
        });
        return root;
    }
}