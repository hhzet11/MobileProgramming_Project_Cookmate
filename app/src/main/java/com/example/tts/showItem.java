package com.example.tts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class showItem extends AppCompatActivity {
    String ingName;
    String ingAm;
    String ingU;

    final ArrayList<String> list = new ArrayList<>();
    final ArrayList<String> list2 = new ArrayList<>();

    private final String dbName = "cook";
    private final String high = "highRef";
    private final String low = "lowRef";

    static SQLiteDatabase sampleDB = null;
    ListAdapter adapter;
    EditText tx1;
    EditText tx2;
    EditText tx3;
    int click =0;
    //삭제할 재료의 이름 담는곳
    String totalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        final ListView listView= (ListView) findViewById(R.id.listview );
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);
        listView.setAdapter(adapter);

        final ListView listView2= (ListView) findViewById(R.id.listview2 );
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list2);
        listView2.setAdapter(adapter2);

        //어느 냉장고가 열렸는지 받아오기!
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();

        int openH = bundle.getInt("H_open");
        int openL = bundle.getInt("L_open");

        TextView name1 = (TextView) findViewById(R.id.reShowName);

        //냉장고 리스트 보여주기
        if(openH==1) {
            name1.setText("냉동고 리스트 보여주기");
            showListHigh(list,listView);
        }
        if(openL==1) {
            name1.setText("냉장고 리스트 보여주기");
            showListLow(list2,listView2);
        }

        //냉장고로 돌아가는 버튼 !!
        Button returnB = findViewById(R.id.returnB);
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //리스트 보여주는 메소드
    protected void showListHigh(final ArrayList list, final ListView listView){
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            Cursor c = ReadDB.rawQuery("SELECT * FROM " + high, null);

            if (c != null) {
                if (c.moveToFirst()) {

                    do {
                        ingName = c.getString(c.getColumnIndex("ingred_name"));
                        ingAm = c.getString(c.getColumnIndex("amount"));
                        ingU = c.getString(c.getColumnIndex("limitt"));
                        totalName = ingName+" 양: "+ingAm+" 유통기한: "+ingU;
                        list.add(totalName);


                    } while (c.moveToNext());
                }
                c.close();
            }
            ReadDB.close();
        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("",  se.getMessage());
        }
    }

    protected void showListLow(final ArrayList list2, final ListView listView2){
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            Cursor c = ReadDB.rawQuery("SELECT * FROM lowRef", null);

            if (c != null) {
                if (c.moveToFirst()) {

                    do {
                        ingName = c.getString(c.getColumnIndex("ingred_name"));
                        ingAm = c.getString(c.getColumnIndex("amount"));
                        ingU = c.getString(c.getColumnIndex("limitt"));
                        totalName = ingName+" 양: "+ingAm+" 유통기한: "+ingU;
                        list2.add(totalName);


                    } while (c.moveToNext());
                }
                c.close();
            }
            ReadDB.close();
        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("",  se.getMessage());
        }
    }
}
