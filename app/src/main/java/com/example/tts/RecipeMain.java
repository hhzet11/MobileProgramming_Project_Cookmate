package com.example.tts;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class RecipeMain extends AppCompatActivity{
    String name;
    String calories;
    String level ;
    String[] ingred=new String[10];
    String[] amount=new String[10];
    String[] inam=new String[10];
    ArrayList<HashMap<String,String>> ingredList;

    ListView list;

    private final String dbName = "cook";
    private final String table1Name = "main"; //이름 , 칼로리, 난이도
    private final String table2Name = "recipe"; //이름, 레시피1줄, 시간, 단계
    private final String table3Name = "ingredients"; //이름, 재료, 양

    private final String tableName = "person";

    static SQLiteDatabase sampleDB = null;
    ListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recipe);
        Button button1 = findViewById(R.id.rrrrr);
        Intent passedIntent = getIntent();
        EditText calories1 = (EditText)findViewById(R.id.Calories1);

        final String passedName= passedIntent.getStringExtra("name");

        try {
            sampleDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
            //테이블이 존재하지 않으면 새로 생성합니다.
           // sampleDB.execSQL("DROP TABLE highRef");
          //  sampleDB.execSQL("DROP TABLE lowRef");
          //  sampleDB.execSQL("CREATE TABLE highRef (name VARCHAR(20) primary key, amount varchar(10), limitt varchar(10))");
          //  sampleDB.execSQL("CREATE TABLE lowRef (name VARCHAR(20) primary key, amount varchar(10), limitt varchar(10))");
            /*
            //테이블이 존재하는 경우 기존 데이터를 지우기 위해서 사용합니다.
            sampleDB.execSQL("DELETE FROM " + table1Name );
            sampleDB.execSQL("DELETE FROM highRef"  );
            sampleDB.execSQL("DELETE FROM lowRef" );
            sampleDB.execSQL("DELETE FROM recipe" );
            sampleDB.execSQL("DROP TABLE main");
            sampleDB.execSQL("DROP TABLE recipe");
            sampleDB.execSQL("DROP TABLE ingredients");
            sampleDB.execSQL("DROP TABLE highRef");
            sampleDB.execSQL("DROP TABLE lowRef");

            //CREATE TABLE
            sampleDB.execSQL("CREATE TABLE main (id int primary key, name VARCHAR(20), type VARCHAR(20), calories int, level int, heart int)");
            sampleDB.execSQL("CREATE TABLE recipe (id int, name VARCHAR(20) , todo VARCHAR(100), timer long, step int, FOREIGN KEY (id) REFERENCES main(id))");
            sampleDB.execSQL("CREATE TABLE ingredients (id int, name VARCHAR(20), ingred varchar(10), amount varchar(10), FOREIGN KEY (id) REFERENCES main(id))");
            sampleDB.execSQL("CREATE TABLE highRef (ingred_name VARCHAR(20) primary key, limitt varchar(10), amount varchar(10))");
            sampleDB.execSQL("CREATE TABLE lowRef (ingred_name VARCHAR(20) primary key, limitt varchar(10), amount varchar(10))");


            // 한식
            // 1. 김치찌개
            sampleDB.execSQL("INSERT INTO main values(1, '김치찌개', '한식', 128, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(1, '김치찌개', '묵은지와 돼지고기, 대파와 청양고추를 준비합니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(1,'김치찌개', '냄비에 물과 돼지고기를 넣어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(1, '김치찌개', '묵은지를 잘게 썰고 냄비에 넣어줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(1,'김치찌개', '고춧가루, 다진 마늘, 국간장, 새우젓을 넣어줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(1,'김치찌개', '마지막으로 청양고추와 대파도 넣어줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(1,'김치찌개', '맛있게 냠냠.', null, 6)");

            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '묵은지', '1/4포기')");
            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '돼지고기', '150g')");
            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '고춧가루', '2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '다진 마늘', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '국간장', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '새우젓', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '청양고추', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(1, '김치찌개', '대파', '적당량')");

            // 2. 된장찌개
            sampleDB.execSQL("INSERT INTO main values(2, '된장찌개', '한식', 176, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(2, '된장찌개', '양파와 애호박을 작은 큐브모양으로 썰어줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(2, '된장찌개', '냄비에 물과 양파, 애호박, 다시마를 넣어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(2, '된장찌개', '물이 끓으면 다시마는 건져줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(2, '된장찌개', '야채가 익으면 된장, 쌈장, 고춧가루를 넣어줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(2, '된장찌개', '두부를 넣어줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(2, '된장찌개', '맛있게 냠냠.', null, 6)");

            sampleDB.execSQL("INSERT INTO ingredients values(2, '된장찌개', '두부', '1/2모')");
            sampleDB.execSQL("INSERT INTO ingredients values(2, '된장찌개', '된장', '3큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(2, '된장찌개', '쌈장', '3큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(2, '된장찌개', '고춧가루', '2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(2, '된장찌개', '물', '400ml')");

            // 3. 달걀말이
            sampleDB.execSQL("INSERT INTO main values(3, '달걀말이', '한식', 238, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(3, '달걀말이', '계란과 물을 섞어줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(3, '달걀말이', '대파를 썰어 기름에 볶아줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(3, '달걀말이', '볶아준 대파에 계란물을 1/4만큼 붓고 말아줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(3, '달걀말이', '계란물을 붓고 말아주는 걸 반복해줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(3, '달걀말이', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(3, '달걀말이', '계란', '4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(3, '달걀말이', '물', '50ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(3, '달걀말이', '대파', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(3, '달걀말이', '소금', '적당량')");

            // 4. 고등어구이
            sampleDB.execSQL("INSERT INTO main values(4, '고등어구이', '한식', 379, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(4, '고등어구이', '흐르는 물에 고등어를 씻어줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(4, '고등어구이', '식초와 식용유를 섞어주고 고등어에 발라줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(4, '고등어구이', '팬을 달군 후 고등어 껍질 부분을 1분 구워줍니다.', 60000, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(4, '고등어구이', '고등어를 뒤집어 준 후 1분 30초 구워줍니다.', 90000, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(4, '고등어구이', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(4, '고등어구이', '고등어', '1마리')");
            sampleDB.execSQL("INSERT INTO ingredients values(4, '고등어구이', '식용유', '2스푼')");
            sampleDB.execSQL("INSERT INTO ingredients values(4, '고등어구이', '식초', '2스푼')");
            sampleDB.execSQL("INSERT INTO ingredients values(4, '고등어구이', '소금', '적당량')");

            // 5. 제육볶음
            sampleDB.execSQL("INSERT INTO main values(5, '제육볶음', '한식', 572, 2, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(5, '제육볶음', '돼지고기에 다진 마늘, 맛술, 후춧가루를 넣고 밑간을 해줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(5, '제육볶음', '고춧가루, 고추장, 올리고당을 넣고 양념장을 만들어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(5, '제육볶음', '달군 팬에 돼지고기를 볶아줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(5, '제육볶음', '돼지고기가 익으면 양념장, 대파를 넣고 볶아줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(5, '제육볶음', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '돼지고기', '200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '대파', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '다진 마늘', '2스푼')");
            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '맛술', '1스푼')");
            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '후춧가루', '적당량')");
            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '고춧가루', '2스푼')");
            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '고추장', '2스푼')");
            sampleDB.execSQL("INSERT INTO ingredients values(5, '제육볶음', '올리고당', '3스푼')");

            // 6. 떡볶이
            sampleDB.execSQL("INSERT INTO main values(6, '떡볶이', '한식', 280, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(6, '떡볶이', '물과 떡볶이 떡을 넣고 끓여줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(6, '떡볶이', '고추장, 고춧가루, 간장, 설탕을 넣고 양념을 만들어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(6, '떡볶이', '물이 끓으면 양념을 넣고 마저 끓여줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(6, '떡볶이', '대파를 넣어줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(6, '떡볶이', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(6, '떡볶이', '떡볶이떡', '400g')");
            sampleDB.execSQL("INSERT INTO ingredients values(6, '떡볶이', '물', '400ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(6, '떡볶이', '대파', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(6, '떡볶이', '고추장', '2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(6, '떡볶이', '고춧가루', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(6, '떡볶이', '간장', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(6, '떡볶이', '설탕', '3큰술')");

            // 7. 감자채볶음
            sampleDB.execSQL("INSERT INTO main values(7, '감자채볶음', '한식', 107, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(7, '감자채볶음', '감자를 채썰어 소금에 5분간 절여줍니다.', 300000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(7, '감자채볶음', '양파와 당근도 채썰어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(7, '감자채볶음', '팬에 기름을 두른 후 감자를 3분 볶아줍니다.', 180000, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(7, '감자채볶음', '양파와 당근을 넣고 볶아줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(7, '감자채볶음', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(7, '감자채볶음', '감자', '2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(7, '감자채볶음', '양파', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(7, '감자채볶음', '당근', '1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(7, '감자채볶음', '소금', '약간')");

            // 8. 새우부추전
            sampleDB.execSQL("INSERT INTO main values(8, '새우부추전', '한식', 262, 2, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(8, '새우부추전', '부추, 청양고추, 새우를 다져줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(8, '새우부추전', '다진 재료에 소금과 부침가루를 넣어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(8, '새우부추전', '물을 넣고 섞어줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(8, '새우부추전', '팬에 기름을 두른 후 반죽을 적당량 올려줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(8, '새우부추전', '한쪽 면이 익으면 반대로 뒤집어줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(8, '새우부추전', '맛있게 냠냠.', null, 6)");

            sampleDB.execSQL("INSERT INTO ingredients values(8, '새우부추전', '칵테일새우', '10마리')");
            sampleDB.execSQL("INSERT INTO ingredients values(8, '새우부추전', '부추', '1/5단')");
            sampleDB.execSQL("INSERT INTO ingredients values(8, '새우부추전', '부침가루', '5큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(8, '새우부추전', '청양고추', '2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(8, '새우부추전', '물', '100ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(8, '새우부추전', '소금', '적당량')");

            // 9. 김치볶음밥
            sampleDB.execSQL("INSERT INTO main values(9, '김치볶음밥', '한식', 446, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(9, '김치볶음밥', '기름을 두른 팬에 파를 넣고 볶아줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(9, '김치볶음밥', '파가 노릇해지면 김치를 넣고 함께 볶아줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(9, '김치볶음밥', '고춧가루와 간장을 넣고 함께 볶아줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(9, '김치볶음밥', '밥과 참기름을 넣고 함께 볶아줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(9, '김치볶음밥', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(9, '김치볶음밥', '밥', '1공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(9, '김치볶음밥', '김치', '1/2포기')");
            sampleDB.execSQL("INSERT INTO ingredients values(9, '김치볶음밥', '대파', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(9, '김치볶음밥', '고춧가루', '1/2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(9, '김치볶음밥', '간장', '2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(9, '김치볶음밥', '참기름', '1큰술')");

            // 10. 소고기미역국
            sampleDB.execSQL("INSERT INTO main values(10, '소고기미역국', '한식', 290, 2, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '미역을 물에 10분간 불려줍니다.', 600000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '미역을 먹기 좋은 크기로 잘라준 후 물을 제거해줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '냄비에 참기름, 다진 마늘, 소고기를 넣고 볶아줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '소고기가 익을 때쯤 국간장을 넣어줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '미역을 넣고 푸른색이 될 때까지 볶아줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '물을 400ml 붓고 강불에 끓여줍니다.', null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '물이 끓기 시작하면 1L를 더 넣어준 뒤 15분간 끓여줍니다.', 900000, 7)");
            sampleDB.execSQL("INSERT INTO recipe values(10, '소고기미역국', '맛있게 냠냠.', null, 8)");

            sampleDB.execSQL("INSERT INTO ingredients values(10, '소고기미역국', '소고기(국거리용)', '180g')");
            sampleDB.execSQL("INSERT INTO ingredients values(10, '소고기미역국', '미역', '20g')");
            sampleDB.execSQL("INSERT INTO ingredients values(10, '소고기미역국', '다진 마늘', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(10, '소고기미역국', '참기름', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(10, '소고기미역국', '국간장', '2큰술')");

            //중식
            // 11. 짜장면
            sampleDB.execSQL("INSERT INTO main values(11, '짜장면', '중식', 785, 3, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '팬에 식용유 2컵과 함께 춘장을 튀겨줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '오이, 양파, 파, 돼지고기를 썰어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '파를 볶아준 후 돼지고기를 넣고 함께 볶아줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '고기가 익으면 양파를 넣고 볶아줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '춘장을 넣고 볶아줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '물과 전분을 넣고 소스를 걸쭉하게 만들어줍니다.', null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '면을 10분 삶아줍니다.', 600000, 7)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '삶은 면 위에 소스를 붓고 오이를 올려줍니다.', null, 8)");
            sampleDB.execSQL("INSERT INTO recipe values(11, '짜장면', '맛있게 냠냠.', null, 9)");

            sampleDB.execSQL("INSERT INTO ingredients values(11, '짜장면', '양파', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(11, '짜장면', '파', '2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(11, '짜장면', '오이', '1/3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(11, '짜장면', '돼지고기', '200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(11, '짜장면', '면', '300g')");
            sampleDB.execSQL("INSERT INTO ingredients values(11, '짜장면', '춘장', '1/3컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(11, '짜장면', '전분물', '3큰술')");

            // 12. 새우볶음밥
            sampleDB.execSQL("INSERT INTO main values(12, '새우볶음밥', '중식', 365, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '끓는 물에 소금을 넣고 내장을 제거한 새우를 데쳐줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '데친 새우를 찬물에 헹궈줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '대파, 당근, 피망을 썰어줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '볼에 계란, 소금을 넣어 풀어준 후 체에 걸러줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '팬에 기름을 두른 후 계란 스크램블을 만들어줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '팬에 기름을 두른 후 대파, 피망, 당근 순으로 넣고 볶아줍니다.', null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '새우, 스크램블, 밥, 소금, 참기름 순으로 넣고 볶아줍니다.', null, 7)");
            sampleDB.execSQL("INSERT INTO recipe values(12, '새우볶음밥', '맛있게 냠냠.', null, 8)");

            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '밥', '1인분')");
            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '칵테일새우', '30g')");
            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '계란', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '대파', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '당근', '1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '피망', '1/3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '소금', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(12, '새우볶음밥', '참기름', '약간')");

            // 13. 부추잡채
            sampleDB.execSQL("INSERT INTO main values(13, '부추잡채', '중식', 240, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(13, '부추잡채', '부추와 돼지고기를 썰어줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(13, '부추잡채', '돼지고기에 소금과 청주를 넣고 밑간을 해줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(13, '부추잡채', '밑간한 돼지고기에 계란흰자, 녹말가루를 넣고 섞어줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(13, '부추잡채', '팬에 식용유를 두른 후 돼지고기를 살짝 익혀줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(13, '부추잡채', '부추를 넣고 함께 볶아줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(13, '부추잡채', '맛있게 냠냠.', null, 6)");

            sampleDB.execSQL("INSERT INTO ingredients values(13, '부추잡채', '부추', '120g')");
            sampleDB.execSQL("INSERT INTO ingredients values(13, '부추잡채', '돼지고기', '50g')");
            sampleDB.execSQL("INSERT INTO ingredients values(13, '부추잡채', '계란', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(13, '부추잡채', '청주', '2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(13, '부추잡채', '소금', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(13, '부추잡채', '녹말가루', '1/2큰술')");

            // 14. 마파두부
            sampleDB.execSQL("INSERT INTO main values(14, '마파두부', '중식', 290, 3, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '두부를 썰어 소금물에 데쳐준 후 물기를 제거해줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '팬에 기름을 두른 후 고춧가루를 넣어 30초 볶아줍니다.', 30000, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '홍고추, 대파, 마늘을 다져줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '팬에 고추기름, 마늘, 대파를 넣고 볶아줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '간장을 넣은 후 홍고추와 돼지고기를 넣고 볶아줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '두반장을 넣고 볶아준 후 물 300ml를 넣어줍니다.', null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '물전분을 넣고 끓여준 후 두부와 참기름을 넣어줍니다.', null, 7)");
            sampleDB.execSQL("INSERT INTO recipe values(14, '마파두부', '맛있게 냠냠.', null, 8)");

            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '두부', '150g')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '고춧가루', '15g')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '마늘', '2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '대파', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '홍고추', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '두반장', '10g')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '돼지등심', '50g')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '물전분', '15g')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '간장', '10ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(14, '마파두부', '참기름', '약간')");

            // 15. 오징어냉채
            sampleDB.execSQL("INSERT INTO main values(15, '오징어냉채', '중식', 59, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(15, '오징어냉채', '볼에 겨자와 물을 넣고 섞은 후 4분 숙성시켜줍니다.', 240000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(15, '오징어냉채', '오징어 껍질을 제거한 후 칼집을 내고 썰어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(15, '오징어냉채', '칼집 낸 오징어를 끓는 소금물에 데쳐줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(15, '오징어냉채', '오이를 어슷하게 썰어줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(15, '오징어냉채', '숙성시킨 겨자와 식초, 설탕, 소금, 참기름을 섞어 소스를 만들어줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(15, '오징어냉채', '오징어, 오이를 소스와 함께 섞어줍니다.', null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(15, '오징어냉채', '맛있게 냠냠.', null, 7)");

            sampleDB.execSQL("INSERT INTO ingredients values(15, '오징어냉채', '갑오징어살', '100g')");
            sampleDB.execSQL("INSERT INTO ingredients values(15, '오징어냉채', '오이', '1/3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(15, '오징어냉채', '식초', '30ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(15, '오징어냉채', '설탕', '15g')");
            sampleDB.execSQL("INSERT INTO ingredients values(15, '오징어냉채', '소금', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(15, '오징어냉채', '참기름', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(15, '오징어냉채', '겨자', '20g')");

            // 16. 빠스고구마
            sampleDB.execSQL("INSERT INTO main values(16, '빠스고구마', '중식', 377, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(16, '빠스고구마', '고구마는 껍질을 제거한 후 썰어줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(16, '빠스고구마', '썰은 고구마를 찬 물에 담근 후 물기를 제거해줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(16, '빠스고구마', '예열된 기름에 고구마를 튀겨줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(16, '빠스고구마', '팬에 설탕, 식용유를 넣어 약불에 녹여줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(16, '빠스고구마', '시럽이 완성되면 고구마를 넣어 섞어줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(16, '빠스고구마', '맛있게 냠냠.', null, 6)");

            sampleDB.execSQL("INSERT INTO ingredients values(16, '빠스고구마', '고구마', '300g')");
            sampleDB.execSQL("INSERT INTO ingredients values(16, '빠스고구마', '식용유', '1L')");
            sampleDB.execSQL("INSERT INTO ingredients values(16, '빠스고구마', '설탕', '100g')");

            // 17. 칠리새우
            sampleDB.execSQL("INSERT INTO main values(17, '칠리새우', '중식', 240, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(17, '칠리새우', '새우는 물주머니를 제거한 후 후추와 요리술에 10분간 재워줍니다.', 600000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(17, '칠리새우', '튀김가루와 물을 넣어 튀김반죽을 만들어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(17, '칠리새우', '새우에 튀김가루를 살짝 묻힌 후 반죽에 넣어줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(17, '칠리새우', '170도로 달군 튀김기름에 새우를 튀겨줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(17, '칠리새우', '칠리소스에 튀긴 새우를 넣고 섞어줍니다.', null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(17, '칠리새우', '맛있게 냠냠.', null, 6)");

            sampleDB.execSQL("INSERT INTO ingredients values(17, '칠리새우', '새우', '300g')");
            sampleDB.execSQL("INSERT INTO ingredients values(17, '칠리새우', '요리술', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(17, '칠리새우', '후추', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(17, '칠리새우', '튀김가루', '200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(17, '칠리새우', '물', '200ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(17, '칠리새우', '칠리소스', '6큰술')");

            // 18. 깐풍만두
            sampleDB.execSQL("INSERT INTO main values(18, '깐풍만두', '중식', 315, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(18, '깐풍만두', '홍고추와 대파를 썰어줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(18, '깐풍만두', '간장, 물엿, 식초를 넣고 양념을 만들어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(18, '깐풍만두', '만두를 구워준 후 홍고추, 대파를 넣고 섞어줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(18, '깐풍만두', '양념, 후추, 물을 넣고 졸여줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(18, '깐풍만두', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(18, '깐풍만두', '만두', '10개')");
            sampleDB.execSQL("INSERT INTO ingredients values(18, '깐풍만두', '대파', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(18, '깐풍만두', '홍고추', '적당량')");
            sampleDB.execSQL("INSERT INTO ingredients values(18, '깐풍만두', '간장', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(18, '깐풍만두', '물엿', '1/2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(18, '깐풍만두', '식초', '2큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(18, '깐풍만두', '후추', '약간')");

            // 19. 꿔바로우
            sampleDB.execSQL("INSERT INTO main values(19, '꿔바로우', '중식', 384, 2, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(19, '꿔바로우', '전분가루에 물을 붓고 16분 후 윗물을 버려줍니다.', 960000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(19, '꿔바로우', '대파와 돼지고기를 썰어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(19, '꿔바로우', '돼지고기에 소금과 후추를 넣고 밑간을 해줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(19, '꿔바로우', '밑간한 돼지고기에 전분을 입혀 튀김옷을 입혀줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(19, '꿔바로우', '돼지고기를 180도에 3분 튀겨줍니다.', 180000, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(19, '꿔바로우', '튀긴 고기에 소스를 뿌려줍니다.', null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(19, '꿔바로우', '맛있게 냠냠.', null, 7)");

            sampleDB.execSQL("INSERT INTO ingredients values(19, '꿔바로우', '돼지고기', '220g')");
            sampleDB.execSQL("INSERT INTO ingredients values(19, '꿔바로우', '물', '100ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(19, '꿔바로우', '후추', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(19, '꿔바로우', '소금', '약간')");
            sampleDB.execSQL("INSERT INTO ingredients values(19, '꿔바로우', '전분가루', '150g')");
            sampleDB.execSQL("INSERT INTO ingredients values(19, '꿔바로우', '탕수육소스', '2큰술')");

            // 20. 부추달걀볶음
            sampleDB.execSQL("INSERT INTO main values(20, '부추달걀볶음', '중식', 553, 1, 0)");

            sampleDB.execSQL("INSERT INTO recipe values(20, '부추달걀볶음', '부추를 썰어줍니다.', null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(20, '부추달걀볶음', '식용유를 넣어 달군 팬에 계란을 풀어 스크램블을 만들어줍니다.', null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(20, '부추달걀볶음', '부추를 넣고 스크램블과 볶아줍니다.', null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(20, '부추달걀볶음', '굴소스와 참기름을 넣고 볶아줍니다.', null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(20, '부추달걀볶음', '맛있게 냠냠.', null, 5)");

            sampleDB.execSQL("INSERT INTO ingredients values(20, '부추달걀볶음', '계란', '3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(20, '부추달걀볶음', '부추', '1줌')");
            sampleDB.execSQL("INSERT INTO ingredients values(20, '부추달걀볶음', '굴소스', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(20, '부추달걀볶음', '참기름', '1큰술')");
            sampleDB.execSQL("INSERT INTO ingredients values(20, '부추달걀볶음', '식용유', '적당량')");

            //양식!!!!!!!!!!!
            //**************토마토 파스타 - id : 21
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(21,'토마토파스타', '양식',350, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(21,'토마토파스타','끓는 물에 올리브유를 조금 넣고 파스타 면을 9분간 끓인후 면수를 2컵 남겨둔다',540000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(21,'토마토파스타','피망, 양파를 깍둑썰기한다.',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(21,'토마토파스타','냉동 새우는 찬물에 담궈 해동시킨다.',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(21,'토마토파스타','팬에 기름을 두르고 마늘을 볶다가 썰어 놓은 야채와 새우를 함께 볶는다.',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(21,'토마토파스타','토마토 소스와 면수를 넣고 같이 볶은 후 소금으로 간을 한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(21,'토마토파스타','삶은 면을 넣고 요리를 완성한다.',null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(21,'토마토파스타','맛있게 냠냠.',null, 7)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(21,'토마토파스타','파스타면', '1인분양')");
            sampleDB.execSQL("INSERT INTO ingredients values(21,'토마토파스타','피망', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(21,'토마토파스타','양파', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(21,'토마토파스타','새우', '7개')");
            sampleDB.execSQL("INSERT INTO ingredients values(21,'토마토파스타','토마토소스', '2컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(21,'토마토파스타','올리브유', '2T')");
            sampleDB.execSQL("INSERT INTO ingredients values(21,'토마토파스타','마늘', '1T')");

            //**************알리오 올리오 - id : 22
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(22,'알리오올리오', '양식',500, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(22,'알리오올리오','물을 끓여 소금 반숟갈과 올리브유, 면을 넣고 6분간 삶아준다',360000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(22,'알리오올리오','마늘은 슬라이스, 페페론치노는 잘게 썰어준다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(22,'알리오올리오','다른 후라이팬에 오일을 5스푼 두르고 약불에서 마늘과 페페론치노를 볶는다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(22,'알리오올리오','면을 건져 후라이팬으로 옮기고 면수를 2컵 넣어준다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(22,'알리오올리오','2분간 볶으며 소금과 후추, 치즈로 간을 한다',120000, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(22,'알리오올리오','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(22,'알리오올리오','파스타면', '1인분양')");
            sampleDB.execSQL("INSERT INTO ingredients values(22,'알리오올리오','페페론치노', '3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(22,'알리오올리오','파마산치즈가루', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(22,'알리오올리오','올리브유', '2T')");
            sampleDB.execSQL("INSERT INTO ingredients values(22,'알리오올리오','마늘', '5개')");

            //**************목살 스테이크 - id : 23
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(23,'목살스테이크', '양식',540, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(23,'목살스테이크','고기에 올리브유, 소금, 후추로 밑간을한다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(23,'목살스테이크','달군 팬에 목살을 3분간 구워준다',180000, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(23,'목살스테이크','돈까스 소스, 우스터 소스, 굴소스, 간장, 올리고당, 물을 넣어 소스를 만든다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(23,'목살스테이크','완성된 소스는 팬에 부어 고기 위에 뿌려 졸인다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(23,'목살스테이크','완성된 고기를 플레이팅한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(23,'목살스테이크','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(23,'목살스테이크','목살', '1인분양')");
            sampleDB.execSQL("INSERT INTO ingredients values(23,'목살스테이크','돈까스소스', '2T')");
            sampleDB.execSQL("INSERT INTO ingredients values(23,'목살스테이크','우스터소스', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(23,'목살스테이크','굴소스', '0.5T')");
            sampleDB.execSQL("INSERT INTO ingredients values(23,'목살스테이크','간장', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(23,'목살스테이크','올리고당', '2T')");
            sampleDB.execSQL("INSERT INTO ingredients values(23,'목살스테이크','물', '5T')");

            //**************크림리조또 - id : 24
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(24,'크림리조또', '양식',345, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(24,'크림리조또','베이컨과 마늘, 청양고추를 썬다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(24,'크림리조또','달군 팬에 베이컨, 마늘, 청양고추를 함께 볶는다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(24,'크림리조또','베이컨이 익으면 크림소스, 우유를 넣고 3분간 끓인다',180000, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(24,'크림리조또','밥을 넣고 볶듯이 섞은 후 치즈, 소금, 후추로 간을 한다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(24,'크림리조또','완성된 리조또에 파슬리를 뿌려 완성한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(24,'크림리조또','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','베이컨', '2줄')");
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','밥', '1인분양')");
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','마늘', '5개')");
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','청양고추', '2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','크림파스타소스', '1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','우유', '2컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','모짜렐라치즈', '2장')");
            sampleDB.execSQL("INSERT INTO ingredients values(24,'크림리조또','파슬리가루', '1T')");

            //**************양송이 스프 - id : 25
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(25,'양송이스프', '양식',65, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(25,'양송이스프','양송이버섯은 껍질을 벗긴 후 잘게 다진다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(25,'양송이스프','버터를 녹인 후 중력분을 넣어 볶아 루를 만든다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(25,'양송이스프','버섯을 넣어 같이 복은 후 우유를 넣어 루를 풀어준다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(25,'양송이스프','루가 다 풀리면 생크림고 물을 넣고 7분간 끓여준다',420000, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(25,'양송이스프','맛있게 냠냠.',null, 5)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(25,'양송이스프','양송이버섯', '200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(25,'양송이스프','중력분', '50g')");
            sampleDB.execSQL("INSERT INTO ingredients values(25,'양송이스프','버터', '20g')");
            sampleDB.execSQL("INSERT INTO ingredients values(25,'양송이스프','생크림', '120g')");
            sampleDB.execSQL("INSERT INTO ingredients values(25,'양송이스프','우유', '150g')");
            sampleDB.execSQL("INSERT INTO ingredients values(25,'양송이스프','물', '4컵')");

            //**************새우 필라프 - id : 26
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(26,'새우필라프', '양식',320, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(26,'새우필라프','대파, 양파, 애호박, 풋고추, 마늘, 표고버섯을 썰어준다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(26,'새우필라프','팬에 버터를 녹인후 대파, 양파, 마늘을 볶는다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(26,'새우필라프','양파와 마늘에 색깔이 올라오면 호박, 고추, 버섯, 새우를 볶는다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(26,'새우필라프','재료가 어느정도 볶아지면 밥을 넣고 볶는다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(26,'새우필라프','간장, 굴소스, 올리고당으로 간을 한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(26,'새우필라프','완성된 밥을 덜고 계란후라이와 마요네즈를 뿌려서 완성한다',null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(26,'새우필라프','맛있게 냠냠.',null, 7)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','대파', '1/2단')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','양파', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','애호박', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','고추', '3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','표고버섯', '3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','새우', '7개')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','마늘', '5개')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','버터', '1조각')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','간장', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','굴소스', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','올리고당', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','계란후라이', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(26,'새우필라프','마요네즈', '4T')");

            //**************마르게리따 피자 - id : 27
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(27,'마르게리따피자', '양식',850, 2,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(27,'마르게리따피자','방울토마토를 닦아 반으로 자르고 치즈와 또띠아를 해동한다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(27,'마르게리따피자','팬에 약불로 한 뒤 또디아를 올리고 토마토 소스를 뿌린다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(27,'마르게리따피자','그 위에 토마토, 피자치즈를 듬뿍 올린다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(27,'마르게리따피자','뚜껑을 덮고 4분간 기다린다',240000, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(27,'마르게리따피자','그 후 바질을 올려 마무리한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(27,'마르게리따피자','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(27,'마르게리따피자','또띠아', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(27,'마르게리따피자','방울토마토', '4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(27,'마르게리따피자','피자치즈', '1/2공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(27,'마르게리따피자','바질', '5개')");
            sampleDB.execSQL("INSERT INTO ingredients values(27,'마르게리따피자','스파게티소스', '1컵')");

            //**************고르곤졸라 피자 - id : 28
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(28,'고르곤졸라피자', '양식',700, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(28,'고르곤졸라피자','또띠아에 올리브오일을 골고루 바른다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(28,'고르곤졸라피자','그 위에 모짜렐라 치즈를 조금씩 뿌려준다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(28,'고르곤졸라피자','다진 마늘을 갈색이 될때 까지 볶은 후 피자 위에 골고루 펴준다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(28,'고르곤졸라피자','그 위에 모짜렐라 치즈를 올린다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(28,'고르곤졸라피자','뚜껑을 덮고 약한불에 치즈가 녹을때까지 5분간 기다린다',300000, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(28,'고르곤졸라피자','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(28,'고르곤졸라피자','또띠아', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(28,'고르곤졸라피자','모짜렐라치즈', '2/3공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(28,'고르곤졸라피자','마늘', '3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(28,'고르곤졸라피자','꿀', '3T')");
            sampleDB.execSQL("INSERT INTO ingredients values(28,'고르곤졸라피자','올리브오일', '1T')");

            //**************피시앤칩스 - id : 29
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(29,'피시앤칩스', '양식',500, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(29,'피시앤칩스','생선의 물기를 키친타올로 제거 후 소금과 후추로 밑간한다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(29,'피시앤칩스','밀가루와 베이킹 파우더를 섞은후 꿀과 맥주를 넣고 섞는다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(29,'피시앤칩스','생선을 밀가루에 얇게 묻힌 뒤 반죽을 입혀 5분간 튀긴다',300000, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(29,'피시앤칩스','감자 껍질을 깎은 후 1cm 두께로 썰어 물에 담가 전분기를 제거한다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(29,'피시앤칩스','감자를 5분간 삶은 후 물기를 제거해 식힌다',300000, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(29,'피시앤칩스','감자를 5분간 기름에 튀겨 마무리한다',300000, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(29,'피시앤칩스','맛있게 냠냠.',null, 7)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(29,'피시앤칩스','흰살생선', '2조각')");
            sampleDB.execSQL("INSERT INTO ingredients values(29,'피시앤칩스','밀가루', '250g')");
            sampleDB.execSQL("INSERT INTO ingredients values(29,'피시앤칩스','맥주', '300ml')");
            sampleDB.execSQL("INSERT INTO ingredients values(29,'피시앤칩스','베이킹파우더', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(29,'피시앤칩스','꿀', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(29,'피시앤칩스','감자', '4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(29,'피시앤칩스','식용유', '4컵')");

            //**************파니니 - id : 30
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(30,'파니니', '양식',500, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(30,'파니니','토마토를 얇게 슬라이스 한다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(30,'파니니','양파를 얇게 썰어서 버터들 두른팬에 5분간 볶는다',300000, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(30,'파니니','치아바타 빵의 가운데를 가른 후 바질페스토를 바른다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(30,'파니니','빵 위에 양파, 치즈, 토마토, 양상추를 넣는다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(30,'파니니','완성된 판을 치즈가 녹을 정도로 팬에 굽는다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(30,'파니니','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(30,'파니니','치아바타빵', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(30,'파니니','토마토', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(30,'파니니','양파', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(30,'파니니','양상추', '3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(30,'파니니','버터', '1조각')");
            sampleDB.execSQL("INSERT INTO ingredients values(30,'파니니','치즈', '4장')");
            sampleDB.execSQL("INSERT INTO ingredients values(30,'파니니','바질페스토', '2T')");


            //일식!!!!!!!!!!!!!!!!!!!
            //**************라멘 - id : 31
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(31,'라멘', '일식',500, 3,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(31,'라멘','돼지 등뼈, 다시마를 물에 넣고 오래동안 끓인다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(31,'라멘','간장,커피, 삼겹살을 넣고 끌인다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(31,'라멘','계란을 넣고 8분간 같이 삶는다',480000, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(31,'라멘','고기와 계란은 빼서 준비하고 국물에 간을한다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(31,'라멘','면을 넣어 끓인 후 고명을 얹어서 완성한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(31,'라멘','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','돼지등뼈', '200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','삼겹살', '100g')");
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','라멘면', '1인분양')");
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','계란', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','다시마', '1조각')");
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','인스턴트커피', '2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','간장', '3T')");
            sampleDB.execSQL("INSERT INTO ingredients values(31,'라멘','물', '7컵')");

            //**************돈부리 - id : 32
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(32,'돈부리', '일식',655, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(32,'돈부리','쯔유, 청주, 물을 부어 끓인다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(32,'돈부리','양파,대파를 썰어 넣어 함께 끓인다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(32,'돈부리','소스가 졸아들면 닭고기를 넣고 끓인다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(32,'돈부리','계란 2개를 풀어 넣어 뚜껑을 엎고 1분간 익힌다',60000, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(32,'돈부리','밥위에 얹어서 마무리한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(32,'돈부리','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','닭고기', '8개')");
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','계란', '2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','양파', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','대파', '1/2단')");
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','밥', '1공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','쯔유', '1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','청주', '1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(32,'돈부리','물', '1컵')");

            //**************차슈덮밥 - id : 33
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(33,'차슈덮밥', '일식',580, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(33,'차슈덮밥','팬에 물과 간장, 설탕, 마늘을 넣고 끓인다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(33,'차슈덮밥','양파와 수육을 먹기좋게 자른다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(33,'차슈덮밥','소스가 끓으면 양파와 수육을 넣는다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(33,'차슈덮밥','5분간 소스에 졸인다',300000, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(33,'차슈덮밥','밥위에 계란, 쪽파와 함께 얹어서 마무리한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(33,'차슈덮밥','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(33,'차슈덮밥','삼겹살', '200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(33,'차슈덮밥','양파', '1/2개')");
            sampleDB.execSQL("INSERT INTO ingredients values(33,'차슈덮밥','밥', '1공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(33,'차슈덮밥','계란', '1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(33,'차슈덮밥','쪽파', '1T')");

            //**************새우초밥 - id : 34
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(34,'새우초밥', '일식',50, 3,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(34,'새우초밥','새우를 소금물에 여러번 씻는다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(34,'새우초밥','새우의 머리와 내장, 꼬리를 제거한다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(34,'새우초밥','새우를 키친타올에 올려 물기를 제거하고 칼집을 넣어 펴준다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(34,'새우초밥','밥에 식초, 깨소금,참기름을 넣고 버무린 후 먹기 좋은 크기로 만든다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(34,'새우초밥','밥위에 손질한 새우를 올려 마무리한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(34,'새우초밥','맛있게 냠냠.',null, 6)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(34,'새우초밥','새우', '15마리')");
            sampleDB.execSQL("INSERT INTO ingredients values(34,'새우초밥','밥', '1공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(34,'새우초밥','깨소금', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(34,'새우초밥','참기름', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(34,'새우초밥','간장', '1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(34,'새우초밥','식초', '1T')");

            //**************우동 - id : 35
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(35,'우동', '일식',360, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(35,'우동','냉동 우동면을 뜨거운물에 2분 끓여 면을 푼다',120000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(35,'우동','냄비에 물과 쯔유를 넣은후 끓인다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(35,'우동','그릇에 면과 국물을 담는다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(35,'우동','고명으로 쑥갓이나 튀김을 올린다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(35,'우동','맛있게 냠냠.',null, 5)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(35,'우동','냉동우동면','1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(35,'우동','쯔유','1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(35,'우동','어묵','1장')");
            sampleDB.execSQL("INSERT INTO ingredients values(35,'우동','물','3컵')");

            //**************텐동 - id : 36
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(36,'텐동','일식',430, 2,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(36,'텐동','가지, 버섯, 연근, 오징어, 계란 등 튀김재료를 먹기 좋게 자른다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(36,'텐동','튀김옷을 입혀 3분간 튀긴다',180000, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(36,'텐동','쯔유, 간장, 미림, 설탕, 물을 넣고 끓여준다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(36,'텐동','밥위에 버터 한스푼을 올리고 튀김, 소스를 얹어 마무리한다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(36,'텐동','맛있게 냠냠.',null, 5)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','가지','1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','팽이버섯','1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','연근','1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','오징어','1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','계란','1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','쯔유','4T')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','간장','2T')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','미림','3T')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','설탕','1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','물','5T')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','밥','1공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(36,'텐동','버터','1조각')");

            //**************규카츠 - id : 37
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(37,'규카츠','일식',580, 2,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(37,'규카츠','소고기는 소금, 후추로 밑간한다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(37,'규카츠','밀가루-계란물-빵가루 순으로 옷을 입힌다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(37,'규카츠','기름에 2분간 튀긴다',120000, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(37,'규카츠','키친타올로 기름을 제거하고 한입크기로 썬다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(37,'규카츠','맛있게 냠냠.',null, 5)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(37,'규카츠','육우안심','200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(37,'규카츠','밀가루','2컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(37,'규카츠','계란','3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(37,'규카츠','빵가루','2컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(37,'규카츠','식용유','2컵')");

            //**************메밀소바 - id : 38
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(38,'메밀소바','일식',380, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(38,'메밀소바','무를 강판에 곱게 간다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(38,'메밀소바','물을 냄비에 넣고 멸치,다시마,표고버섯으로 육수를 낸다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(38,'메밀소바','가쓰오부시를 넣고 색깔이 진해지도록 5분간 끓인다',300000, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(38,'메밀소바','간장,멸치액젓,설탕으로 간을 한다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(38,'메밀소바','육수는 냉동실에서 얼린다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(38,'메밀소바','면을 삶은 뒤 육수를 얹고 고명으로 완성한다',null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(38,'메밀소바','맛있게 냠냠.',null, 7)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','무','1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','멸치','4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','다시마','1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','표고버섯','3개')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','가쓰오부시','1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','간장','3T')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','멸치액젓','1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','설탕','2T')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','물','5컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(38,'메밀소바','메밀면','1개')");

            //**************타코야끼 - id : 39
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(39,'타코야끼','일식',30, 3,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','문어를 깨끗이 씻어 한입크기로 자른 후 다진 마늘에 버무린다',null, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','타코야끼 가루와 물, 계란을 넣고 섞는다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','당근,양배추,쪽파를 다져서 반죽에 넣는다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','타코야끼팬에 기름을 골고루 바른다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','팬에 반죽을 붓고 문어를 칸마다 하나씩 넣어준다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','반죽이 익으면 나무젓가락으로 돌려준다',null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','완성된 타코야끼위에 가쓰오부시를 올려 마무리한다',null, 7)");
            sampleDB.execSQL("INSERT INTO recipe values(39,'타코야끼','맛있게 냠냠.',null, 8)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','양배추','1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','당근','1/4개')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','다진 마늘','1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','문어','1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','마요네즈','3T')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','가쓰오부시','1컵')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','타코야끼가루','200g')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','계란','1개')");
            sampleDB.execSQL("INSERT INTO ingredients values(39,'타코야끼','물','550g')");


            //**************오니기리 - id : 40
            //메인추가
            sampleDB.execSQL("INSERT INTO main values(40,'오니기리','일식',220, 1,0)");

            //레시피 추가
            sampleDB.execSQL("INSERT INTO recipe values(40,'오니기리','소고기 다짐육을 기름에 설탕, 간장과 함께 2분간 볶는다',120000, 1)");
            sampleDB.execSQL("INSERT INTO recipe values(40,'오니기리','참치 1캔에 기름을 따르고 양파는 다져서 물에 담가 매운맛을 빼준다',null, 2)");
            sampleDB.execSQL("INSERT INTO recipe values(40,'오니기리','소고기, 참치, 양파에 마요네즈, 후추를 넣어 버무린다',null, 3)");
            sampleDB.execSQL("INSERT INTO recipe values(40,'오니기리','밥에 소금과 참기름을 넣어 버무린 후 틀에 넣어준다',null, 4)");
            sampleDB.execSQL("INSERT INTO recipe values(40,'오니기리','그 위에 속재료를 넣고 다시 밥을 넣어 형태를 완성한다',null, 5)");
            sampleDB.execSQL("INSERT INTO recipe values(40,'오니기리','김으로 겉을 완성한다',null, 6)");
            sampleDB.execSQL("INSERT INTO recipe values(40,'오니기리','맛있게 냠냠.',null, 7)");

            //재료 추가
            sampleDB.execSQL("INSERT INTO ingredients values(40,'오니기리','밥','1공기')");
            sampleDB.execSQL("INSERT INTO ingredients values(40,'오니기리','참치','1캔')");
            sampleDB.execSQL("INSERT INTO ingredients values(40,'오니기리','소고기다짐육','100g')");
            sampleDB.execSQL("INSERT INTO ingredients values(40,'오니기리','김','1장')");
            sampleDB.execSQL("INSERT INTO ingredients values(40,'오니기리','식용유','1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(40,'오니기리','설탕','1T')");
            sampleDB.execSQL("INSERT INTO ingredients values(40,'오니기리','간장','1T')");
*/
            sampleDB.close();
        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }

        showList(passedName,calories1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),Recipe.class); //보내주는 인텐트 생성
                intent1.putExtra("name",passedName);
                startActivity(intent1); //번들을 인텐트에 실어서 보내주기
            }
        });
    }

    protected void showList(String passedName, TextView calories1){
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = ReadDB.rawQuery("SELECT * from ingredients WHERE name='"+passedName+"'",null);

            if (c != null) {
                int j=0;
                if (c.moveToFirst()) {
                    do {

                        name = c.getString(c.getColumnIndex("name"));

                        TextView name1 = (TextView) findViewById(R.id.Name1);
                        name1.setText(name);

                        ingred[j] = c.getString(c.getColumnIndex("ingred"));
                        amount[j] = c.getString(c.getColumnIndex("amount"));

                        j++;
                    } while (c.moveToNext());
                }
            }
            ReadDB.close();

            for (int k=0;k<ingred.length;k++)
            {
                if(ingred[k]!=null) {
                    calories1.append(ingred[k] + " - " + amount[k] + "\n");
                }
            }

        } catch (SQLiteException se) {
            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("",  se.getMessage());
        }
    }
}