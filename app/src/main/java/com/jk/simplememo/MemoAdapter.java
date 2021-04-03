package com.jk.simplememo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.VH> {

    Context context;
    ArrayList<MemoItem> items;

    public MemoAdapter(Context context, ArrayList<MemoItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View itemView= inflater.inflate(R.layout.recycler_item, parent, false);
        VH vh= new VH(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        MemoItem item= items.get(position);

        //텍스트들 지정
        holder.tvMsg.setText(item.msg);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    //뷰홀더 이너클래스
    class VH extends RecyclerView.ViewHolder{

        TextView tvMsg;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvMsg= itemView.findViewById(R.id.tv_msg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position= getAdapterPosition();

                    int no= items.get(position).no;
                    String msg= items.get(position).msg;

                    Intent intent= new Intent(context, DetailActivity.class);
                    intent.putExtra("no",no);
                    intent.putExtra("msg", msg);
                    context.startActivity(intent);



                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position= getAdapterPosition();

                    int no= items.get(position).no;






                    return false;
                }
            });

        }
    }
}
