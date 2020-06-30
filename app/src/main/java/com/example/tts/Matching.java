package com.example.tts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class Matching extends AppCompatActivity {
    Button home;
    Button refrigerator;
    Button recipe;
    Button setting;

    private final String dbName = "cook";
    String food[]=new String[100];
    String yori[]=new String[41];
    int count[]=new int[41];

    ListView listView;
    ImageView heartView;
    boolean isClicked = false;

    int heart=0;
    int level=0;
    int calories=0;
    String name;

    TextView txt1;
    TextView txt2;

    int a=0;//냉장고에 있는 음식 개수

    static SQLiteDatabase sampleDB = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);
        Intent passedIntent = getIntent();

        this.InitializeView();
        this.SetListener();

        txt1=(TextView)findViewById(R.id.txt1);
        txt2=(TextView)findViewById(R.id.txt2);

        listView = findViewById(R.id.listView);

        storeFood();

        try {
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
/*
            sampleDB.execSQL("CREATE TABLE test (ind int,name VARCHAR(20), ingred varchar(10))");

            sampleDB.execSQL("INSERT INTO test values(0,'김진아', '김')");
            sampleDB.execSQL("INSERT INTO test values(0,'김진아', '진')");
            sampleDB.execSQL("INSERT INTO test values(0,'김진아', '아')");

            sampleDB.execSQL("INSERT INTO test values(1,'유은석', '유')");
            sampleDB.execSQL("INSERT INTO test values(1,'유은석', '은')");
            sampleDB.execSQL("INSERT INTO test values(1,'유은석', '석')");

            sampleDB.execSQL("INSERT INTO test values(2,'김진석', '김')");
            sampleDB.execSQL("INSERT INTO test values(2,'김진석', '진')");
            sampleDB.execSQL("INSERT INTO test values(2,'김진석', '석')");
*/
            //차라리 냉장고에 있는 재료를 다 한 어레이에 넣을까

            sampleDB.close();
        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            int ind=0;

            Cursor c = ReadDB.rawQuery("SELECT * from ingredients",null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        ind= c.getInt(c.getColumnIndex("id"));
                        yori[ind]=c.getString(c.getColumnIndex("name"));

                        for(int k=0;k<a;k++)
                        {
                            if(food[k].equals(c.getString(c.getColumnIndex("ingred"))))
                            {
                                count[ind]++;
                            }
                        }

                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("",  se.getMessage());
        }

        int temp1=0;
        String temp2;
        for(int i=40; i>1; i--){
            // 0 ~ (i-1)까지 반복
            for(int j=1; j<i; j++){
                // j번째와 j+1번째의 요소가 크기 순이 아니면 교환
                if(count[j]<count[j+1]){
                    temp1 = count[j];
                    count[j] = count[j+1];
                    count[j+1] = temp1;
                    temp2 = yori[j];
                    yori[j] = yori[j+1];
                    yori[j+1] = temp2;
                }
            }
        }

        txt1.setText("냉장고에 있는 재료로\n만들어봐요!");
        //txt2.setText("food:"+food[0]+","+food[1]+","+food[2]);


        String[] name = new String[3];
        name[0]=yori[1]; name[1]=yori[2]; name[2]=yori[3];
        ArrayList<String> recipeName = new ArrayList<>(Arrays.asList(name));

        final CustomAdapter adapter = new CustomAdapter(this,0, recipeName);
        listView.setAdapter(adapter);
    }


    public void storeFood(){
        try {

            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c1 = ReadDB.rawQuery("SELECT * from highRef",null);
            if (c1 != null) {
                if (c1.moveToFirst()) {
                    do {

                        food[a] = c1.getString(c1.getColumnIndex("ingred_name"));
                        a++;

                    } while (c1.moveToNext());
                }
            }

            Cursor c2 = ReadDB.rawQuery("SELECT * from lowRef",null);
            if (c2 != null) {
                if (c2.moveToFirst()) {
                    do {

                        food[a] = c2.getString(c2.getColumnIndex("ingred_name"));
                        a++;

                    } while (c2.moveToNext());
                }
            }
            ReadDB.close();

        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("",  se.getMessage());
        }
    }

    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> recipeName;

        public CustomAdapter(Context context, int textView, ArrayList<String> objects){
            super(context, textView, objects);
            this.recipeName = objects;
        }

        public View getView(int position, View convertview, ViewGroup parent) {
            View v = convertview;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.activity_recipe_list, null);
            }
            //ImageView
            ImageView imageView = v.findViewById(R.id.recipePhoto);
            // 리스트뷰의 아이템에 이미지를 변경한다.
            if("김치찌개".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p1);
            else if("된장찌개".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p2);
            else if("달걀말이".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p3);
            else if("고등어구이".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p4);
            else if("제육볶음".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p5);
            else if("떡볶이".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p6);
            else if("감자채볶음".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p7);
            else if("새우부추전".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p8);
            else if("김치볶음밥".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p9);
            else if("소고기미역국".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p10);
            else if("짜장면".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p11);
            else if("새우볶음밥".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p12);
            else if("부추잡채".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p13);
            else if("마파두부".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p14);
            else if("오징어냉채".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p15);
            else if("빠스고구마".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p16);
            else if("칠리새우".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p17);
            else if("깐풍만두".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p18);
            else if("꿔바로우".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p19);
            else if("부추달걀볶음".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p20);
            else if("토마토파스타".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p21);
            else if("알리오올리오".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p22);
            else if("목살스테이크".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p23);
            else if("크림리조또".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p24);
            else if("양송이스프".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p25);
            else if("새우필라프".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p26);
            else if("마르게리따피자".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p27);
            else if("고르고졸라피자".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p28);
            else if("피시앤칩스".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p29);
            else if("파니니".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p30);
            else if("라멘".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p31);
            else if("돈부리".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p32);
            else if("차슈덮밥".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p33);
            else if("새우초밥".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p34);
            else if("우동".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p35);
            else if("텐동".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p36);
            else if("규카츠".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p37);
            else if("메밀소바".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p38);
            else if("타코야끼".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p39);
            else if("오니기리".equals(recipeName.get(position))) imageView.setImageResource(R.drawable.p40);

            TextView textView = v.findViewById(R.id.recipeName1);
            textView.setText(recipeName.get(position));

            // heart 오류 고치기!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            heartView = v.findViewById(R.id.heartButton);
            heartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vl) {
                    isClicked = ! isClicked;
                    if(isClicked) {
                        heartView.setImageResource(R.drawable.fillheart);
                    }
                    else {
                        heartView.setImageResource(R.drawable.heart);
                    }
                }
            });

            final String text = recipeName.get(position);
            TextView start = v.findViewById(R.id.recipeStart);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Matching.this, text + "시작 눌림", Toast.LENGTH_SHORT).show();

                    // 레시피 시작 창으로 이동하기
                    Intent intent = new Intent(getApplicationContext(), RecipeMain.class);
                    intent.putExtra("name", text);
                    startActivity(intent);
                }
            });
            return v;
        }
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