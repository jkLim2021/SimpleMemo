package com.jk.simplememo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;




public class MainActivity extends AppCompatActivity {

    ArrayList<MemoItem> items= new ArrayList<>();
    RecyclerView recyclerView;
    MemoAdapter adapter;

    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recycler);
        adapter= new MemoAdapter(this, items);
        recyclerView.setAdapter(adapter);

        refreshLayout = findViewById(R.id.layout_refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                refreshLayout.setRefreshing(false);
            }
        });


        //동적퍼미션
        String[] permissions= new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if( ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_DENIED ){
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }


    //이 액티비티가 화면에 보여질 때 리사이클러가 보여줄 아이템들을 서버DB에서 불러오기
    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    //서버에서 데이터를 불러오는 기능 메소드
    void loadData(){

        Retrofit retrofit= RetrofitHelper.getRetrofitInstanceGson();
        RetrofitService retrofitService= retrofit.create(RetrofitService.class);
        Call<ArrayList<MemoItem>> call= retrofitService.loadDataFromServer();
        call.enqueue(new Callback<ArrayList<MemoItem>>() {
            @Override
            public void onResponse(Call<ArrayList<MemoItem>> call, Response<ArrayList<MemoItem>> response) {

                //기존데이터들 모두 제거
                items.clear();
                adapter.notifyDataSetChanged();

                //결과로 받아온 ArrayList<MemoItem>을 items에 추가
                ArrayList<MemoItem> list= response.body();
                for(MemoItem item: list){
                    items.add(0, item);
                    adapter.notifyItemInserted(0);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<MemoItem>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }



    public void clickAdd(View view) {
        Intent intent= new Intent(this, EditActivity.class);
        startActivity(intent);
    }

}