package com.jk.simplememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    int nodb;
    String msg;
    String msgdb;

    EditText et_msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        et_msg= findViewById(R.id.et_msg);

        Intent intentget = getIntent();
        nodb= intentget.getIntExtra("no",0);
        msg= intentget.getStringExtra("msg");

        et_msg.setText(msg);


    }


    public void clickComplete(View view) {

        msgdb= et_msg.getText().toString();


//        //레트로핏 작업 5단계
        Retrofit retrofit= RetrofitHelper.getRetrofitInstanceScalars();
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);
        Call<String> call= retrofitService.updateitem(nodb,msgdb);
//        Call<ArrayList<MemoItem>> call= retrofitService.loadDataFromServer();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(DetailActivity.this, "서버 업데이트 완료", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "서버 요청 실패", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);

    }


}