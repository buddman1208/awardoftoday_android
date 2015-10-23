package kr.edcan.awardoftoday.utils;

import java.util.List;

import kr.edcan.awardoftoday.data.Article;
import kr.edcan.awardoftoday.data.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Junseok on 2015-10-11.
 */
public interface NetworkService {

    // GCM Interface
    @FormUrlEncoded
    @POST("/alert/sendAlarm")
    void sendGCMRequest(@Field("token") String token, Callback<String> callback);

    // Authentication Interface
    @FormUrlEncoded
    @POST("/auth/login")
    void userLogin(@Field("id") String id, @Field("password") String password, @Field("token") String token, Callback<User> callback);

    @FormUrlEncoded
    @POST("/auth/loginValidate")
    void loginValidate(@Field("apikey") String apikey, @Field("token") String token, Callback<String> callback);

    @FormUrlEncoded
    @POST("/auth/register")
    void userRegister(@Field("id") String id, @Field("password") String password,
                      @Field("name") String name, @Field("isParent") boolean isParent,
                      Callback<String> callback);

    // Parent Interface
    @FormUrlEncoded
    @POST("/parent/findChild")
    void findChild(@Field("id") String id, Callback<User> callback);

    @FormUrlEncoded
    @POST("/parent/registerChild")
    void registerChild(@Field("name") String name, @Field("apikey") String apikey, @Field("targetName") String targetName, @Field("targetApikey") String targetApikey,
                       Callback<User> callback);

    @FormUrlEncoded
    @POST("/parent/configureArticle")
    void configureArticle(@Field("targetApikey") String targetApikey, @Field("articleKey") String articleKey, @Field("confirm") boolean isConfirm, Callback<String> callback);

    @FormUrlEncoded
    @POST("/parent/postArticle")
    void postArticle(@Field("apikey") String apikey, @Field("title") String title,
                     @Field("sticker") int sticker, @Field("date") String date, @Field("content") String content,
                     Callback<Article> callback);

    // Child Interface
    @FormUrlEncoded
    @POST("/child/checkMyParent")
    void checkMyParent(@Field("apikey") String apikey, Callback<User> callback);

    @FormUrlEncoded
    @POST("/child/getMyStatus")
    void getMyStatus(@Field("apikey") String apikey, Callback<User> callback);

    @FormUrlEncoded
    @POST("/child/finishArticle")
    void finishArticle(@Field("token") String token, @Field("targetApikey") String targetApikey, @Field("articleKey") String articleKey, Callback<String> callback);

    // Article Interface
    @FormUrlEncoded
    @POST("/article/listArticle")
    void listArticle(@Field("apikey") String apikey, @Field("status") String status, Callback<List<Article>> callback);

}
