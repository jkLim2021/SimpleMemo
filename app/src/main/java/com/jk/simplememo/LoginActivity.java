package com.jk.simplememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;


import javax.security.auth.login.LoginException;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;



public class LoginActivity extends AppCompatActivity {

    TextView tvNickname;
    TextView tvEmail;
    CircleImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String keyHash = Utility.INSTANCE.getKeyHash(this);
        Log.i("key", keyHash);

        tvNickname = findViewById(R.id.tv_nickname);
        tvEmail = findViewById(R.id.tv_email);
        ivProfile = findViewById(R.id.iv);


    }

    public void clickLogin(View view) {
        UserApiClient.getInstance().loginWithKakaoAccount(this, new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                if (oAuthToken !=null){
                    Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT).show();

                    UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                        @Override
                        public Unit invoke(User user, Throwable throwable) {

                            if (user != null){
                                long id= user.getId();

                                String nickname= user.getKakaoAccount().getProfile().getNickname();
                                String profileImageUrl= user.getKakaoAccount().getProfile().getThumbnailImageUrl();

                                String email= user.getKakaoAccount().getEmail();

                                tvNickname.setText(nickname);
                                tvEmail.setText(email);
                                Glide.with(LoginActivity.this).load(profileImageUrl).into(ivProfile);

                                Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            }else {
                                Toast.makeText(LoginActivity.this, "사용자 정보 요청 실패 : ", Toast.LENGTH_SHORT).show();

                            }
                            return null;
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "로그인 실패 : " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                }
                return null;
            }
        });

    }

    public void clickLogout(View view) {
        //로그아웃 요청
        UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {
                if(throwable!=null)
                    Toast.makeText(LoginActivity.this, "로그아웃 실패", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(LoginActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();

                    //로그인 회원정보 화면들 모두 초기화
                    tvNickname.setText("닉네임");
                    tvEmail.setText("이메일");
                    Glide.with(LoginActivity.this).load(R.mipmap.ic_launcher).into(ivProfile);
                }
                return null;
            }
        });
    }
}