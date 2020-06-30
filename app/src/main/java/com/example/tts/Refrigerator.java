package com.example.tts;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.Toast;



public class Refrigerator extends AppCompatActivity implements FragmentLow.OnApplySelectedListener,FragmentHigh.OnApplySelectedListener {

    int openH=0;
    int openL=0;

    Button home;
    Button refrigerator;
    Button recipe;
    Button setting;


    @Override
    public void onCategoryApplySelectedH( int open) {


        openH=open;
        if(openH==1&&openL==0)
        {Toast.makeText(getApplicationContext(),  " 냉동고 열림 ", Toast.LENGTH_LONG).show();}
        if(openH==1&&openL==1)
        {Toast.makeText(getApplicationContext(),  " 문 두개 열지 마세요 전력낭비", Toast.LENGTH_LONG).show();}
    }

    @Override
    public void onCategoryApplySelectedL( int open) {

        openL=open;
        if(openH==0&&openL==1)
        {Toast.makeText(getApplicationContext(),  " 냉장고 열림 ", Toast.LENGTH_LONG).show();}
        if(openH==1&&openL==1)
        {Toast.makeText(getApplicationContext(),  " 문 두개 열지 마세요 전력낭비", Toast.LENGTH_LONG).show();}
    }

    FragmentHigh highFrag;
    FragmentLow lowFrag;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refrigerator);

        this.InitializeView();
        this.SetListener();

        highFrag = new FragmentHigh();
        getSupportFragmentManager().beginTransaction().add(R.id.highFragment,highFrag).commit();
        lowFrag = new FragmentLow();
        getSupportFragmentManager().beginTransaction().add(R.id.lowFragment,lowFrag).commit();

        //추가버튼 기능 구현 -> 추가 창 이동
        Button button1 = findViewById(R.id.plus);
        //ㅇ이거 안뜸


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((openH==1&&openL==0)||(openH==0&&openL==1)) {
                    Toast.makeText(getApplicationContext(), "추가버튼연결 성공 ", Toast.LENGTH_LONG).show();
                    //추가하는 액티비티로 넘어감
                    Intent intent3 = new Intent(getApplicationContext(), Addition.class);

                    Bundle bundle = new Bundle();

                    bundle.putInt("H_open", openH);
                    bundle.putInt("L_open", openL);

                    //냉장고, 냉동고의 열림 상태 같이 넘겨줌
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                }
                else
                    Toast.makeText(getApplicationContext(), "문을 하나만 열고 추가하세요 ", Toast.LENGTH_LONG).show();
            }
        });




        //삭제버튼 기능 구현 -> 삭제 창 이동
        Button button2 = findViewById(R.id.delete);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((openH==1&&openL==0)||(openH==0&&openL==1)) {
                    Toast.makeText(getApplicationContext(), "삭제버튼연결 성공 ", Toast.LENGTH_LONG).show();
                    //삭제하는 액티비티로 넘어감
                    Intent intent4 = new Intent(getApplicationContext(), Delete.class);

                    Bundle bundle = new Bundle();

                    bundle.putInt("H_open", openH);
                    bundle.putInt("L_open", openL);

                    //냉장고, 냉동고의 열림 상태 같이 넘겨줌
                    intent4.putExtras(bundle);
                    startActivity(intent4);
                }
                else
                    Toast.makeText(getApplicationContext(), "문을 하나만 열고 삭제하세요 ", Toast.LENGTH_LONG).show();
            }
        });



        //수정버튼 기능 구현 -> 삭제 창 이동후 선택 -> data 묶어서 추가창으로 전송
        Button button3 = findViewById(R.id.edit);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((openH==1&&openL==0)||(openH==0&&openL==1)) {
                    Toast.makeText(getApplicationContext(), "수정버튼연결 성공 ", Toast.LENGTH_LONG).show();
                    //삭제하는 액티비티로 넘어감
                    Intent intent5 = new Intent(getApplicationContext(), Modify.class);

                    Bundle bundle = new Bundle();

                    bundle.putInt("H_open", openH);
                    bundle.putInt("L_open", openL);

                    //냉장고, 냉동고의 열림 상태 같이 넘겨줌
                    intent5.putExtras(bundle);
                    startActivity(intent5);
                }
                else
                    Toast.makeText(getApplicationContext(), "문을 하나만 열고 수정하세요 ", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void InitializeView() {
        home = (Button) findViewById(R.id.home_button);
        refrigerator = (Button) findViewById(R.id.refrigerator_button);
        recipe = (Button) findViewById(R.id.recipe_button);
        setting = (Button) findViewById(R.id.setting_button);
    }

    private void SetListener() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        refrigerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Refrigerator.class);
                startActivity(intent);
            }
        });

        recipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectRecipe.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}