package com.jk.simplememo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditActivity extends AppCompatActivity {

    EditText etMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        etMsg= findViewById(R.id.et_msg);
    }



    public void clickComplete(View view) {

        String msg= etMsg.getText().toString();

        //레트로핏 작업 5단계
        Retrofit retrofit= RetrofitHelper.getRetrofitInstanceScalars();
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);

        //이미지파일을 MultipartBody.Part로 포장 : @Part
        MultipartBody.Part filePart= null;


        //나머지 String 데이터들은 Map Collection에 저장 : @PartMap
        Map<String, String> dataPart= new HashMap<>();
        dataPart.put("msg", msg);

        Call<String> call= retrofitService.postDataToServer(dataPart, filePart);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s= response.body();
                Toast.makeText(EditActivity.this, ""+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EditActivity.this, "error : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //업로드가 완료되면 액티비티 종료
        finish();
    }
}