package com.smoothswitch.helper;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;

import androidx.annotation.NonNull;

public class RingerModeManager {
    private AudioManager audioManager;

    public RingerModeManager(@NonNull Activity activity) {
        audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);

    }

    RingerMode getCurrentRingerMode() {
        int ringerMode = audioManager.getRingerMode();

        if (ringerMode == AudioManager.RINGER_MODE_SILENT)
            return RingerMode.SILENT;
        if (ringerMode == AudioManager.RINGER_MODE_VIBRATE)
            return RingerMode.VIBRATE;

        //we assume it normal ringer mode
        return RingerMode.NORMAL;
    }

    public void setRingerMode(RingerMode mode) {
        switch (mode) {
            case SILENT:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                break;
            case NORMAL:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                break;
            case VIBRATE:
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                break;
        }
    }

}

