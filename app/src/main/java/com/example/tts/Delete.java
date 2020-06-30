package com.example.tts;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Delete extends AppCompatActivity {
    String ingName;
    String ingAm;
    String ingU;

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
        setContentView(R.layout.activity_del);

        //어느 냉장고가 열렸는지 받아오기!
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle = intent.getExtras();

        int openH = bundle.getInt("H_open");
        int openL = bundle.getInt("L_open");

        //삭제하는 버튼
        Button delB = findViewById(R.id.delB);
        TextView name1 = (TextView) findViewById(R.id.reDelName);

        final ArrayList<String> list = new ArrayList<>();
        final ListView listView= (ListView) findViewById(R.id.listview );
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);
        listView.setAdapter(adapter);

        final ArrayList<String> list2 = new ArrayList<>();
        final ListView listView2= (ListView) findViewById(R.id.listview2 );
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list2);
        listView2.setAdapter(adapter2);

        ///////////////////////냉동고에 삭제///////////////////////////////////////
        if(openH==1) {
            name1.setText("냉동고에서 삭제하기 ");

            showListHigh(list,listView);

            final String[] deleteItem = new String[1];

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemValue = (String) listView.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),itemValue, Toast.LENGTH_SHORT).show();
                    //리스트뷰에 이름 양 유통기한 다 있으니까 이름만 빼오기 !!!
                    int indexSpace = itemValue.indexOf(' ');
                    itemValue.substring(0,indexSpace);
                    deleteItem[0] =itemValue.substring(0,indexSpace);

                }
            });

            final int checked = listView.getCheckedItemPosition();

            delB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = 1;
                    int count, checked ;
                    count = adapter.getCount() ;

                    if (count > 0) {
                        checked = listView.getCheckedItemPosition();

                        if (checked > -1 && checked < count) {
                            list.remove(checked) ;
                            listView.clearChoices();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    delHDB(deleteItem[0]);
                }
            });
        }
        ////////////////////////////냉장고에 삭제 //////////////////////////////////////////////
        if(openL==1) {
            name1.setText("냉장고에서 삭제하기 ");

            showListLow(list2,listView2);

            final String[] deleteItem = new String[1];

            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemValue = (String) listView2.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),itemValue, Toast.LENGTH_SHORT).show();
                    //리스트뷰에 이름 양 유통기한 다 있으니까 이름만 빼오기 !!!
                    int indexSpace = itemValue.indexOf(' ');
                    itemValue.substring(0,indexSpace);
                    deleteItem[0] =itemValue.substring(0,indexSpace);
                }
            });

            delB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = 1;
                    int count, checked ;
                    count = adapter2.getCount() ;

                    if (count > 0) {
                        checked = listView2.getCheckedItemPosition();

                        if (checked > -1 && checked < count) {
                            list2.remove(checked) ;
                            listView2.clearChoices();
                            adapter2.notifyDataSetChanged();
                        }
                    }
                    delLDB(deleteItem[0]);
                }
            });

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

    private void delHDB(String deleteItem){
        if (click == 1) {
            try {
                sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

                sampleDB.execSQL("DELETE from highRef where ingred_name=\'"+deleteItem+"\'");

                sampleDB.close();
            } catch (SQLiteException se) {
                Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            Toast.makeText(getApplicationContext(), "냉동고에 삭제완료", Toast.LENGTH_LONG).show();

        }
        click =0;
    }
    private void delLDB(String deleteItem){
        if (click == 1) {
            try {
                sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

                sampleDB.execSQL("DELETE from lowRef where ingred_name=\'"+deleteItem+"\'");

                sampleDB.close();
            } catch (SQLiteException se) {
                Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            Toast.makeText(getApplicationContext(), "냉장고에 삭제완료", Toast.LENGTH_LONG).show();
        }
        click =0;
    }

    //삭제할 리스트 보여주는 메소드
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