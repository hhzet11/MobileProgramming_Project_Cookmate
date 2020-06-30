package com.example.tts;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class Setting_alarm extends AppCompatActivity {
    NotificationManager manager;
    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    Button home;
    Button refrigerator;
    Button recipe;
    Button setting;

    int alarmSpecial = 0;
    int alarmLimit = 0;
    int alarmRecommend = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alarm);

        this.InitializeView();
        this.SetListener();

        //체크박스 눌렸을때 알림 처리
        CheckBox special = (CheckBox) findViewById(R.id.alarmSpecial);
        special.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    alarmSpecial=1;
                    Toast.makeText(getApplicationContext(),"special 눌림",Toast.LENGTH_SHORT).show();
                }else{
                    alarmSpecial=0;
                    Toast.makeText(getApplicationContext(),"special 해제",Toast.LENGTH_SHORT).show();
                }
            }
        });
        CheckBox limit = findViewById(R.id.alarmLimit);
        limit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    alarmLimit=1;
                    Toast.makeText(getApplicationContext(),"limit 눌림",Toast.LENGTH_SHORT).show();
                }else{
                    alarmLimit=0;
                    Toast.makeText(getApplicationContext(),"limit 해제",Toast.LENGTH_SHORT).show();
                }
            }
        });
        CheckBox recom = findViewById(R.id.alarmRecommend);
        recom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    alarmRecommend=1;

                    Toast.makeText(getApplicationContext(),"recommendation 눌림",Toast.LENGTH_SHORT).show();
                }else{
                    alarmRecommend=0;
                    Toast.makeText(getApplicationContext(),"recommendation 해제",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //setting 화면으로 돌아가기
        Button button = findViewById(R.id.alarmButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alarmSpecial==1){
                    // showNoti1();
                    Toast.makeText(getApplicationContext(),"비오는 날 새우부추전 어떠세요??",Toast.LENGTH_SHORT).show();
                }if(alarmLimit==1){
                    // showNoti1();
                    Toast.makeText(getApplicationContext(),"유통기한 확인하러 가기",Toast.LENGTH_SHORT).show();
                }if(alarmRecommend==1){
                    // showNoti1();
                    Toast.makeText(getApplicationContext(),"홈의 쿠킹마마를 눌러보세요!",Toast.LENGTH_SHORT).show();
                }


                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showNoti1(){
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(manager.getNotificationChannel(CHANNEL_ID) == null){
                manager.createNotificationChannel(new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT));
                builder = new NotificationCompat.Builder(this,CHANNEL_ID);
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            }
        }else{
            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
            Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
        }

        //Intent intent = new Intent(this, RecipeActivity.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this,101,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("특별한 날 알림");
        builder.setContentText("비오는 날 새우부추전 어떠세요??");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        //builder.setAutoCancel(true);
        //builder.setContentIntent(pendingIntent);
        Notification noti = builder.build();

        manager.notify(1,noti);
    }


    private void InitializeView() {
        home = (Button) findViewById(R.id.home_button);
        refrigerator = (Button) findViewById(R.id.refrigerator_button);
        recipe = (Button) findViewById(R.id.recipe_button);
        setting = (Button) findViewById(R.id.setting_button);
    }

    private void SetListener() {
        home.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        refrigerator.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), Refrigerator.class);
                startActivity(intent);
            }
        });

        recipe.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SelectRecipe.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
