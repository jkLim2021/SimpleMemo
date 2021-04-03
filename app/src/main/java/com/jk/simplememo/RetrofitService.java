package com.jk.simplememo;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    //POST로 데이터를 보낼때는 @Field 사용 - @FormUrlEncoded와 함께 써야함
    //이미지 파일을 보낼때는 @Part 사용 - @Multipart와 함께 써야함
    //@FormUrlEncoded 와 @Multipart는 동시에 사용 불가
    //@Field처럼 php에서 $_POST로 받으려면 마치 GET방식의 @QueryMap처럼 @PartMap 사용
    @Multipart
    @POST("/memo/insertDB.php")
    Call<String> postDataToServer(@PartMap Map<String, String> dataPart,
                                  @Part MultipartBody.Part filePart);


    //서버에서 데이터를 json으로 파싱하여 가져오는 추상메소드
    @GET("/memo/loadDB.php")
    Call<ArrayList<MemoItem>> loadDataFromServer();

//    //선택한 메모장 item 보내기
//    @GET("/memo/loadDBitem.php")
//    Call<ArrayList<MemoItem>> loadmyData(@Query("nodb") int nodb);
//
//    @PUT("/memo/updateitem")
//    Call<MemoItem> updateitem(@Path("fileName") String nodb, @Body MemoItem item);

// @PATCH

    @GET("/memo/updateitem.php")
    Call<String> updateitem(@Query("nodb") int nodb,@Query("msgdb") String msgdb);




}
