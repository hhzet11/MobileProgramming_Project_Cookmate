package com.example.tts;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.widget.TextView;

import java.util.ArrayList;
public class Recipe extends AppCompatActivity implements TextPlayer, View.OnClickListener {

    private final String dbName = "cook";
    static int step=0;
    int id=0;
    String name;
    String[] todo =new String[41];
    int[] step_int =new int[41];
    private int standbyIndex = 0;
    private int lastPlayIndex = 0;
    int check=0;

    private final Bundle params = new Bundle();
    private TextToSpeech tts;
    private Button playBtn;
    private Button pauseBtn;
    private Button stopBtn;
    private PlayState playState = PlayState.STOP;
    TimerFragment timerFragment;


    //stt 변수
    private static final int REQUEST_CODE = 1234;
    Dialog match_text_dialog;
    ArrayList<String> matches_text;
    String result;
    TextView Ditext;
    String passedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent passedIntent = getIntent();
        //final String passedName= passedIntent.getStringExtra("name");
        passedName= passedIntent.getStringExtra("name");


        timerFragment = new TimerFragment();
        ImageView nextB = findViewById(R.id.nextButton);
        ImageButton stt = findViewById(R.id.stt);
        initTTS();
        initView();

        SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        Cursor c = ReadDB.rawQuery("SELECT * FROM recipe WHERE name='"+passedName+"'", null);
        c.moveToFirst();
        step=0;
        id = c.getInt(c.getColumnIndex("id"));
        name = c.getString(c.getColumnIndex("name"));
        step_int[0]=c.getInt(c.getColumnIndex("step"));
        todo[0] = c.getString(c.getColumnIndex("todo"));
        step=0;
        step++;

        EditText nnnn = (EditText)findViewById(R.id.nnn);
        EditText rrrr = (EditText)findViewById(R.id.rrr);

        nnnn.setText("맛있는 "+name+"를 만들어 봅시다!");
        rrrr.setText("[STEP "+ step_int[0]+"] \n\n"+ todo[0]);

        //STT코드입니다
        stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){

                    //인터넷에 연결이 돼있으면 Speech Recognizer Intent가 열려서 사용자의 음성을 듣기 시작한다.
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    //결과 받아옴
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });


        nextB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check==1) {
                    getSupportFragmentManager().beginTransaction().remove(timerFragment).commit();
                    check=0;
                }
                readRecipe(timerFragment,passedName);
            }
        });


    }

    public void readRecipe(TimerFragment timerFragment, String passedName){
        try {
            SQLiteDatabase ReadDB = this.openOrCreateDatabase(dbName, MODE_PRIVATE, null);

            Cursor c = ReadDB.rawQuery("SELECT * FROM recipe WHERE name='"+passedName+"'", null);

            c.moveToPosition(step-1);
            if (c.moveToNext()){


                if(c.getInt(c.getColumnIndex("timer"))>0)
                {
                    getSupportFragmentManager().beginTransaction().add(R.id.container,timerFragment).commit();
                    Bundle bundle = new Bundle();
                    bundle.putLong("time",c.getInt(c.getColumnIndex("timer")));
                    timerFragment.setArguments(bundle);
                }

                step_int[step-1] = c.getInt(c.getColumnIndex("step"));
                todo[step-1] = c.getString(c.getColumnIndex("todo"));
                EditText rrrr = (EditText)findViewById(R.id.rrr);
                rrrr.setText("[STEP "+ step_int[step-1]+"] \n\n"+ todo[step-1]);
                step++;
            }
            else{
                Intent intent1 = new Intent(getApplicationContext(),MainActivity.class); //보내주는 인텐트 생성
                intent1.putExtra("name",passedName);
                startActivity(intent1); //번들을 인텐트에 실어서 보내주기
            }

            ReadDB.close();
        } catch (SQLiteException se) {

            Toast.makeText(getApplicationContext(),  se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("",  se.getMessage());
        }
        startPlay();
        check=1;
    }

    private void initView() {
        playBtn = findViewById(R.id.btn_play);
        pauseBtn = findViewById(R.id.btn_pause);
        stopBtn = findViewById(R.id.btn_stop);
        //inputEditText = findViewById(R.id.et_input);
        //contentTextView = findViewById(R.id.tv_content);

        playBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);
        stopBtn.setOnClickListener(this);
    }

    private void initTTS() {
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int state) {
                if (state == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.KOREAN);
                } else {
                    showState("TTS 객체 초기화 중 문제가 발생했습니다.");
                }
            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {

            }

            @Override
            public void onDone(String s) {

                clearAll();
            }

            @Override
            public void onError(String s) {
                showState("재생 중 에러가 발생했습니다.");
            }

            @Override
            public void onRangeStart(String utteranceId, int start, int end, int frame) {
                //changeHighlight(standbyIndex + start, standbyIndex + end);
                lastPlayIndex = start;
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                startPlay();
                break;

            case R.id.btn_pause:
                pausePlay();
                break;

            case R.id.btn_stop:
                stopPlay();
                break;
        }
        showState(playState.getState());
    }

    @Override
    public void startPlay() {
        ////////////////////////////////////////////////////////////////////////////////////

        //String content = inputEditText.getText().toString();
        EditText rrrr1 = (EditText)findViewById(R.id.rrr);

        String content = rrrr1.getText().toString();
        if (playState.isStopping() && !tts.isSpeaking()) {
            //setContentFromEditText(content);
            startSpeak(content);
        } else if (playState.isWaiting()) {
            standbyIndex += lastPlayIndex;
            startSpeak(content.substring(standbyIndex));
        }
        playState = PlayState.PLAY;

    }

    @Override
    public void pausePlay() {
        if (playState.isPlaying()) {
            playState = PlayState.WAIT;
            tts.stop();
        }
    }

    @Override
    public void stopPlay() {
        tts.stop();
        clearAll();
    }

    /*
    private void changeHighlight(final int start, final int end) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spannable.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        });
    }


    private void setContentFromEditText(String content) {
        contentTextView.setText(content, TextView.BufferType.SPANNABLE);
        spannable = (SpannableString) contentTextView.getText();
    }
    */

    private void startSpeak(final String text) {
        tts.speak(text, TextToSpeech.QUEUE_ADD, params, text);
    }

    private void clearAll() {
        playState = PlayState.STOP;
        standbyIndex = 0;
        lastPlayIndex = 0;

        //if (spannable != null) {
        //changeHighlight(0, 0); // remove highlight
        //}
    }


    private void showState(final String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        if (playState.isPlaying()) {
            pausePlay();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (playState.isWaiting()) {
            startPlay();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        tts.stop();
        tts.shutdown();
        super.onDestroy();
    }
    //구글 음성인식이 인터넷에 연결이 돼있어야 작동하기 때문에 isConnected() 메소드로 인터넷에 연결됐는지 확인하낟
    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    //이 메소드로 음성인식 결과를 받아온다!!!

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //사용자의 음성이 인식됐으면 RESULT_OK로 결과값을 반환하고, 새 다이얼로그를 열어 리스트로 반환되는 음성인식 결과들을 출력한다.
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            //다이얼로그 연다.
            match_text_dialog = new Dialog(Recipe.this);
            //다이얼로그 xml 인플레이션
            //걍 크기가 정해져있는, 가운데에 버튼이 있는 애인가봄
            match_text_dialog.setContentView(R.layout.dialog_matches_frag);
            match_text_dialog.setTitle("Select Matching Text");
            //textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            Ditext = (TextView)match_text_dialog.findViewById(R.id.Ditext);

            //결과들을  matches _ text 에 넣는다
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            //match_text_dialog.show();

            //이 결과들 중에서 사용자가 선택한  결과를 메인 Activity에 출력을 해준다.

            //리스트에넣고 클릭한것 메인으로 보내기
            //이 과정이 없었으면 좋겠음
            result = matches_text.get(0);
            if(result.equals("next")){
                Toast.makeText(getApplicationContext(), "Next", Toast.LENGTH_LONG).show();
                if(check==1) {
                    getSupportFragmentManager().beginTransaction().remove(timerFragment).commit();
                    check=0;
                }
                readRecipe(timerFragment,passedName);
            }
            else if(result.equals("start")){
             timerFragment.startpause();
            }
            else if(result.equals("stop")){
              timerFragment.pauseTimer();
            }
            else if(result.equals("reset")){
                timerFragment.reset();
            }

        }



//            Ditext.setText(result);
        //result 에 잘 들어감 !!

        //Speech.setText(result);

    }
}