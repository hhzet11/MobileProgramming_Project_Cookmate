package com.example.tts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity {
    Button home;
    Button refrigerator;
    Button recipe;
    Button setting;

    ListView listView;
    ImageView heartView;
    private Spinner spinner;
    boolean isClicked = false;

    private final String dbName = "cook";
    int heart[] = new int[41];
    int level[] = new int[41];
    int calories[] = new int[41];
    String yori[] = new String[41];
    int id = 0;
    String name;

    ArrayList<String> arrayList = new ArrayList<>();

    int value = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe1);

        this.InitializeView();
        this.SetListener();

        listView = findViewById(R.id.listView);
        spinner = (Spinner)findViewById(R.id.spinner);

        final ArrayList<String> list = new ArrayList<>();
        list.add("기본");
        list.add("난이도 쉬운순");
        list.add("난이도 어려운순");
        list.add("칼로리순");
        list.add("하트순");

        //리스트 인텐드 가져오기
        Intent intent = getIntent();
        ArrayList<String> recipeName = new ArrayList<>();
        value = intent.getExtras().getInt("value");
        SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        if(value == 1){
            for(int a=1; a<=10; a++) {
                Cursor c1 = ReadDB.rawQuery("SELECT * FROM recipe WHERE id=" + a, null);
                c1.moveToFirst();
                name = c1.getString(c1.getColumnIndex("name"));
                recipeName.add(name);
            }
        }else if(value == 2){
            for(int a=11; a<=20; a++) {
                Cursor c1 = ReadDB.rawQuery("SELECT * FROM recipe WHERE id=" + a, null);
                c1.moveToFirst();
                name = c1.getString(c1.getColumnIndex("name"));
                recipeName.add(name);
            }
        }else if(value == 3){
            for(int a=21; a<=30; a++) {
                Cursor c1 = ReadDB.rawQuery("SELECT * FROM recipe WHERE id=" + a, null);
                c1.moveToFirst();
                name = c1.getString(c1.getColumnIndex("name"));
                recipeName.add(name);

            }
        }else if(value == 4){
            for(int a=31; a<=40; a++) {
                Cursor c1 = ReadDB.rawQuery("SELECT * FROM recipe WHERE id=" + a, null);
                c1.moveToFirst();
                name = c1.getString(c1.getColumnIndex("name"));
                recipeName.add(name);
            }
        }


        final CustomAdapter adapter = new CustomAdapter(this,0,recipeName);
        listView.setAdapter(adapter);

        ArrayAdapter spinnerAdapter;
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RecipeActivity.this, "정렬 방식 : " + spinner.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                //기본 (id) 순
                if (position == 0) {
                    int val = value;
                    if(val == 1) {
                        showlist(0);
                    }else if(val == 2) {
                        showlist(10);
                    }else if(val == 3) {
                        showlist(20);
                    }else if(val == 4) {
                        showlist(30);
                    }
                    adapter.notifyDataSetChanged();
                }
                //난이도 쉬운순
                else if (position == 1) {
                    int val = value;
                    if(val == 1) {
                        showlist1(0);
                    }else if(val == 2) {
                        showlist1(10);
                    }else if(val == 3) {
                        showlist1(20);
                    }else if(val == 4) {
                        showlist1(30);
                    }
                    adapter.notifyDataSetChanged();
                }
                //난이도 어려운순
                else if (position == 2) {
                    int val = value;
                    if (val == 1) {
                        showlist2(0);
                    } else if (val == 2) {
                        showlist2(10);
                    } else if (val == 3) {
                        showlist2(20);
                    } else if (val == 4) {
                        showlist2(30);
                    }
                    adapter.notifyDataSetChanged();
                }
                //칼로리순
                else if (position == 3) {
                    int val = value;
                    if(val == 1) {
                        showlist3(0);
                    }else if(val == 2) {
                        showlist3(10);
                    }else if(val == 3) {
                        showlist3(20);
                    }else if(val == 4) {
                        showlist3(30);
                    }
                    adapter.notifyDataSetChanged();
                }
                //하트순
                else if(position==4){
                    int val = value;
                    if(val == 1) {
                        showlist4(0);
                    }else if(val == 2) {
                        showlist4(10);
                    }else if(val == 3) {
                        showlist4(20);
                    }else if(val == 4) {
                        showlist4(30);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    protected void showlist(int n) {
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = ReadDB.rawQuery("SELECT * from main", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        id=c.getInt(c.getColumnIndex("id"));
                        yori[id]=c.getString(c.getColumnIndex("name"));
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

        } catch (SQLException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        String[] name = new String[10];
        for(int i=0; i<10; i++) {
            name[i] = yori[i+(n+1)];
        }
        //name[0]=yori[1]; name[1]=yori[2]; name[2]=yori[3];
        ArrayList<String> recipeName = new ArrayList<>(Arrays.asList(name));

        final CustomAdapter adapter = new CustomAdapter(this,0, recipeName);
        listView.setAdapter(adapter);
    }
    protected void showlist1(int n) {
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = ReadDB.rawQuery("SELECT * from main", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        id=c.getInt(c.getColumnIndex("id"));
                        yori[id]=c.getString(c.getColumnIndex("name"));
                        level[id] = c.getInt(c.getColumnIndex("level"));
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

        } catch (SQLException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        int temp1 = 0;
        String temp2="" ;
        for (int i = n+10; i > n+1; i--) {
            for (int j = n+1; j < i; j++) {
                if(level[j] > level[j+1]) {
                    temp1 = level[j];
                    temp2 = yori[j];

                    level[j] = level[j+1];
                    yori[j] = yori[j+1];

                    level[j+1] = temp1;
                    yori[j+1] = temp2;
                }
            }
        }

        String[] name = new String[10];
        for(int i=0; i<10; i++) {
            name[i] = yori[i+(n+1)];
        }
        //name[0]=yori[1]; name[1]=yori[2]; name[2]=yori[3];
        ArrayList<String> recipeName = new ArrayList<>(Arrays.asList(name));

        final CustomAdapter adapter = new CustomAdapter(this,0, recipeName);
        listView.setAdapter(adapter);
    }

    protected void showlist2(int n) {
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = ReadDB.rawQuery("SELECT * from main", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        id=c.getInt(c.getColumnIndex("id"));
                        yori[id]=c.getString(c.getColumnIndex("name"));
                        level[id] = c.getInt(c.getColumnIndex("level"));
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

        } catch (SQLException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        int temp1 = 0;
        String temp2="" ;
        for (int i = n+10; i > n+1; i--) {
            for (int j = n+1; j < i; j++) {
                if(level[j] < level[j+1]) {
                    temp1 = level[j];
                    temp2 = yori[j];

                    level[j] = level[j+1];
                    yori[j] = yori[j+1];

                    level[j+1] = temp1;
                    yori[j+1] = temp2;
                }
            }
        }

        String[] name = new String[10];
        for(int i=0; i<10; i++) {
            name[i] = yori[i+(n+1)];
        }
        //name[0]=yori[1]; name[1]=yori[2]; name[2]=yori[3];
        ArrayList<String> recipeName = new ArrayList<>(Arrays.asList(name));

        final CustomAdapter adapter = new CustomAdapter(this,0, recipeName);
        listView.setAdapter(adapter);
    }

    protected void showlist3(int n) {
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = ReadDB.rawQuery("SELECT * from main", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        id=c.getInt(c.getColumnIndex("id"));
                        yori[id]=c.getString(c.getColumnIndex("name"));
                        calories[id] = c.getInt(c.getColumnIndex("calories"));
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

        } catch (SQLException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        int temp1 = 0;
        String temp2="" ;
        for (int i = n+10; i > n+1; i--) {
            for (int j = n+1; j < i; j++) {
                if(calories[j] < calories[j+1]) {
                    temp1 = calories[j];
                    temp2 = yori[j];

                    calories[j] = calories[j+1];
                    yori[j] = yori[j+1];

                    calories[j+1] = temp1;
                    yori[j+1] = temp2;
                }
            }
        }

        String[] name = new String[10];
        for(int i=0; i<10; i++) {
            name[i] = yori[i+(n+1)];
        }
        //name[0]=yori[1]; name[1]=yori[2]; name[2]=yori[3];
        ArrayList<String> recipeName = new ArrayList<>(Arrays.asList(name));

        final CustomAdapter adapter = new CustomAdapter(this,0, recipeName);
        listView.setAdapter(adapter);
    }

    protected void showlist4(int n) {
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = ReadDB.rawQuery("SELECT * from main", null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        id=c.getInt(c.getColumnIndex("id"));
                        yori[id]=c.getString(c.getColumnIndex("name"));
                        heart[id] = c.getInt(c.getColumnIndex("heart"));
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

        } catch (SQLException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        int temp1 = 0;
        String temp2="" ;
        for (int i = n+10; i > n+1; i--) {
            for (int j = n+1; j < i; j++) {
                if(heart[j]!= heart[j+1]) {
                    if (heart[j] < heart[j + 1]) {
                        temp1 = heart[j];
                        temp2 = yori[j];

                        heart[j] = heart[j + 1];
                        yori[j] = yori[j + 1];

                        heart[j + 1] = temp1;
                        yori[j + 1] = temp2;
                    }
                }
            }
        }

        String[] name = new String[10];
        for(int i=0; i<10; i++) {
            name[i] = yori[i+(n+1)];
        }
        //name[0]=yori[1]; name[1]=yori[2]; name[2]=yori[3];
        ArrayList<String> recipeName = new ArrayList<>(Arrays.asList(name));

        final CustomAdapter adapter = new CustomAdapter(this,0, recipeName);
        listView.setAdapter(adapter);
    }


    private class CustomAdapter extends ArrayAdapter<String>{
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
                    Toast.makeText(RecipeActivity.this, text + "시작 눌림", Toast.LENGTH_SHORT).show();

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
