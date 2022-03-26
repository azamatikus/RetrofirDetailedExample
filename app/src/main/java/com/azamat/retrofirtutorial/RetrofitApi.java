package com.azamat.retrofirtutorial;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitApi {

    //----------------------------------- READ -----------------------------------------------
    @GET("https://jsonplaceholder.typicode.com/posts")
    Call<List<PostModel>> getPosts2();

    @GET("posts")
    Call<List<PostModel>> getPosts();


    @GET("posts")
    Call<List<PostModel>> getPosts(
            @Query("userId") int postId
    );

    @GET("posts")
    Call<List<PostModel>> getPosts(
            @QueryMap Map<String, String> parametres
            );

    @GET("posts")
    Call<List<PostModel>> getPosts(
            @Query("userId") Integer [] postId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("posts/{id}/comments")
    Call<List<CommentModel>> getComments(@Path("id") int postId);

    @GET
    Call<List<CommentModel>> getComments(@Url String url);

    //----------------------------------- CREATE -----------------------------------------------

    @POST("posts")
    Call<PostModel> createPost(@Body PostModel post);

    @FormUrlEncoded
    @POST("posts")
    Call<PostModel> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );

    @FormUrlEncoded
    @POST("posts")
    Call<PostModel> createPost(
            @FieldMap Map<String, String> fields);

    //----------------------------------- UPDATE -----------------------------------------------

    @PUT("posts/{id}")
    Call<PostModel> putPost (@Path("id") int id, @Body PostModel post);

    @Headers({"Static-header: 123", "Static-header2: 456"})
    @PUT("posts/{id}")
    Call<PostModel> putPost (@Header ("Dynamic-Header") String header,
                             @Path("id") int id,
                             @Body PostModel post);

    @PATCH("posts/{id}")
    Call<PostModel> patchPost (@Path("id") int id, @Body PostModel post);

    @PATCH("posts/{id}")
    Call<PostModel> patchPost (@HeaderMap Map<String, String> header,
                               @Path("id") int id,
                               @Body PostModel post);

    //----------------------------------- DELETE -----------------------------------------------

    @DELETE("posts/{id}")
    Call<Void> deletePost (@Path("id") int id);

}
