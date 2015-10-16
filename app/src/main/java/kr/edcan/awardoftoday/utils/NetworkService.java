package kr.edcan.awardoftoday.utils;

import kr.edcan.awardoftoday.data.User;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Junseok on 2015-10-11.
 */
public interface NetworkService {
    // Authentication Interface
    @FormUrlEncoded
    @POST("/auth/login")
    void userLogin(@Field("id") String id, @Field("password") String password, Callback<User> callback);

    @FormUrlEncoded
    @POST("/auth/login")
    void userLogout(@Field("apikey") String apikey, Callback<String> callback);

    @FormUrlEncoded
    @POST("/auth/loginValidate")
    void loginValidate(@Field("apikey") String apikey, Callback<String> callback);

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
    void registerChild(@Field("targetApikey") String id, @Field("apikey") String apikey,
                       Callback<User> callback);

    // Child Interface
    @FormUrlEncoded
    @POST("/child/checkMyParent")
    void checkMyParent(@Field("apikey") String apikey, Callback<User> callback);
}
