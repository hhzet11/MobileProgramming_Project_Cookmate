package com.example.tts;


import android.content.DialogInterface;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Modify extends AppCompatActivity {
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

    //수정할 재료의 이름 담는곳
    String totalName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod);


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

        //수정하는 버튼
        Button modB = findViewById(R.id.modB);
        TextView name1 = (TextView) findViewById(R.id.reModName);


        ///////////////////////냉동고에 수정 ///////////////////////////////////////
        if(openH==1) {
            name1.setText("냉동고에서 수정하기 ");

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

            modB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    click = 1;

                    Toast.makeText(getApplicationContext(),deleteItem[0], Toast.LENGTH_LONG).show();
                    modHDB(deleteItem[0],listView,adapter);

                }
            });
        }
        ////////////////////////////냉장고에 수정 //////////////////////////////////////////////
        if(openL==1) {
            name1.setText("냉장고에서 수정하기 ");

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

            modB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click = 1;

                    Toast.makeText(getApplicationContext(),deleteItem[0], Toast.LENGTH_LONG).show();
                    modLDB(deleteItem[0],listView2,adapter2);
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



    /////////////////////////////////////////////////////////////냉동고/////////////////////////////////////////////////////
    private void modHDB(final String modItem, final ListView listView, final ArrayAdapter adapter) {
        //선택하고 수정버튼을 누르면 , 수정 창이 뜬다.
        //버튼을 누르면, 이름으로 데이터를 가져와서 양을 수정한다.
        //양을 어떻게 수정 ?
        //누른 데이터를 일단 가져와야 함.
        // 그 데이터의 양을 가져와서 수정해야함.
        if (click == 1) {
            String sheeep = "";
            final String[] newSheep = {""};
            try {
                sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                //Toast.makeText(getApplicationContext(), "디비열기성공쓰", Toast.LENGTH_LONG).show();
                //modItem이 지금 수정할 아이템 이름임. 이거 팝업창에 띄워야함
                Cursor c = sampleDB.rawQuery("SELECT amount from highRef WHERE ingred_name='" + modItem + "'", null);

                c.moveToFirst();
                while (!c.isAfterLast()) {
                    sheeep = c.getString(0);
                    c.moveToNext();
                }
                c.close();

                sampleDB.close();
            } catch (SQLiteException se) {
                Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            //양까지는 잘 뜸. ..
            Toast.makeText(getApplicationContext(),sheeep, Toast.LENGTH_LONG).show();

            //팝업창 띄움 .
            //modItem이 지금 수정할 아이템 이름임. 이거 팝업창에 띄워야함

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            //여기에 변수쓰면안될까?ㅠㅠㅠㅠ
            alert.setTitle(modItem+" 은(는)"+sheeep+" 남았습니다");
            alert.setMessage("수정할 양을 입력하세요");

            final EditText Sheep = new EditText(this);
            alert.setView(Sheep);

            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    newSheep[0] = Sheep.getText().toString();
                    //새로운 양 받기 성공
                    Toast.makeText(getApplicationContext(),modItem+newSheep[0], Toast.LENGTH_LONG).show();
                    //디비에서 수정하기
                    //여기서 디비접근불가;;
                    //따로뺴자

                    realModHDB(modItem, newSheep[0],listView,adapter);

                }
            });
            alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        }
        click = 0;
    }
    private void realModHDB(String itemName, String itemAmount,ListView listView,ArrayAdapter adapter) {

        try {
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            sampleDB.execSQL("Update highRef set amount = '"+itemAmount+"' WHERE ingred_name ='" + itemName + "'");

            ////////////dddd
            int checked = listView.getCheckedItemPosition();
            if (checked > -1) {
                // 아이템 수정
                list.set(checked, itemName+" 남은 양 : "+itemAmount);
                // listview 갱신
                adapter.notifyDataSetChanged();
            }
            sampleDB.close();
        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        Toast.makeText(getApplicationContext(),"수정완료", Toast.LENGTH_LONG).show();
    }


    ///////////////////////////////////////냉 장 고 ///////////////////////////////////////////////////////
    private void modLDB(final String modItem,final ListView listView2, final ArrayAdapter adapter2) {
        //선택하고 수정버튼을 누르면 , 수정 창이 뜬다.
        //버튼을 누르면, 이름으로 데이터를 가져와서 양을 수정한다.
        //양을 어떻게 수정 ?
        //누른 데이터를 일단 가져와야 함.
        // 그 데이터의 양을 가져와서 수정해야함.
        if (click == 1) {
            String sheeep = "";
            final String[] newSheep = {""};
            try {
                sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                //Toast.makeText(getApplicationContext(), "디비열기성공쓰", Toast.LENGTH_LONG).show();
                //modItem이 지금 수정할 아이템 이름임. 이거 팝업창에 띄워야함
                Cursor c = sampleDB.rawQuery("SELECT amount from lowRef WHERE ingred_name='" + modItem + "'", null);

                c.moveToFirst();
                while (!c.isAfterLast()) {
                    sheeep = c.getString(0);
                    c.moveToNext();
                }
                c.close();

                sampleDB.close();
            } catch (SQLiteException se) {
                Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("", se.getMessage());
            }
            //양까지는 잘 뜸. ..
            Toast.makeText(getApplicationContext(),sheeep, Toast.LENGTH_LONG).show();

            //팝업창 띄움 .
            //modItem이 지금 수정할 아이템 이름임. 이거 팝업창에 띄워야함

            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            //여기에 변수쓰면안될까?ㅠㅠㅠㅠ
            alert.setTitle(modItem+" 은(는)"+sheeep+" 남았습니다");
            alert.setMessage("수정할 양을 입력하세요");

            final EditText Sheep = new EditText(this);
            alert.setView(Sheep);

            alert.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    newSheep[0] = Sheep.getText().toString();
                    //새로운 양 받기 성공
                    Toast.makeText(getApplicationContext(),modItem+newSheep[0], Toast.LENGTH_LONG).show();
                    //디비에서 수정하기
                    //여기서 디비접근불가;;
                    //따로뺴자

                    realModLDB(modItem, newSheep[0],listView2,adapter2);
                }
            });
            alert.setNegativeButton("no",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        }
        click = 0;
    }
    private void realModLDB(String itemName, String itemAmount,final ListView listView2, final ArrayAdapter adapter2) {

        try {
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            sampleDB.execSQL("Update lowRef set amount = '"+itemAmount+"' WHERE ingred_name ='" + itemName + "'");
            ////////////dddd
            int checked = listView2.getCheckedItemPosition();
            if (checked > -1) {
                // 아이템 수정
                list2.set(checked, itemName+" 남은 양 : "+itemAmount);
                // listview 갱신
                adapter2.notifyDataSetChanged();
            }

            sampleDB.close();
        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        Toast.makeText(getApplicationContext(),"수정완료", Toast.LENGTH_LONG).show();
    }


    //수정할 리스트 보여주는 메소드
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
                        totalName = ingName+" 남은 양 : "+ingAm;
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
                        totalName = ingName+" 남은 양 : "+ingAm;
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