package com.example.tts;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Locale;

import static android.os.SystemClock.sleep;

public class TimerFragment extends Fragment {
    private static final long START_TIME_IN_MILLIS = 0;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button stopRingtone;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    Ringtone ringtone;

    long time=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_timer, container, false);


        Bundle bundle = getArguments();
        if(bundle!=null){
            time = bundle.getLong("time");
        }
        mTimeLeftInMillis = time;
        mTextViewCountDown = rootView.findViewById(R.id.text_view_countdown);
        mButtonStartPause = rootView.findViewById(R.id.button_start_pause);
        mButtonReset = rootView.findViewById(R.id.button_reset);
        stopRingtone=rootView.findViewById(R.id.stop_ringtone);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startpause();

            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        updateCountDownText();

        stopRingtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ringtone.stop();
            }
        });



        return rootView;
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {

                mTimerRunning = false;
                mTextViewCountDown.setText("Finish!");
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.INVISIBLE);

                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                ringtone=RingtoneManager.getRingtone(getActivity().getApplicationContext(),notification);
                ringtone.play();
                stopRingtone.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);
    }
    public void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);


    }
    private void resetTimer() {
        mTimeLeftInMillis = time; ////////////
        updateCountDownText();
        mTimerRunning = false;
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
    }
    public void startpause()
    {
        if (mTimerRunning) {
            pauseTimer();
        } else {
            startTimer();
        }
    }
    public void reset()
    {
        resetTimer();
    }
}