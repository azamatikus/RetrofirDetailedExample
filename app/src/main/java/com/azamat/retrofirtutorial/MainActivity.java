package com.azamat.retrofirtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.azamat.retrofirtutorial.databinding.ActivityMainBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RetrofitApi retrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //view binder. You can get anything from layout xml just using binding...
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        //using gson object to see nulls as is, instead of ignoring them. Then passing this to retrofit factory
        Gson gson = new GsonBuilder().serializeNulls().create();

        //creating logging interceptor to see all data in logcat
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        /*creating okhttp client and passing it to retrofit as .client
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

         */

        //passing header via okhttp interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Interseptor-header", "xyz")
                                .build();

                        return chain.proceed(newRequest);
                    }
                })
                .addInterceptor(httpLoggingInterceptor)
                .build();

        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        retrofitApi = retrofit.create(RetrofitApi.class);

        //getPosts();
        //getComments();
        //createPost();
        updatePost();
        //deletePost();

        setContentView(view);
    }

    private void deletePost() {
        //deleting whole post with some ID

        Call<Void> call = retrofitApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                binding.textViewResult.setText("code: " + response.code());
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                binding.textViewResult.setText(t.getMessage());
            }
        });
    }

    private void updatePost() {
        // updating whole post with PUT method, and some values with PATCH method

        //creating new model to send for replacing
        PostModel post = new PostModel(12, null, "new Text");

        //if we used Map in HEADERS
        Map<String, String> map = new HashMap<>();
        map.put("Map-Header1", "def");
        map.put("Map-Header2", "ghi");

        // sending PATCH with MAP of HEADERS
        Call<PostModel> call = retrofitApi.patchPost(map, 5, post);
//        Call<PostModel> call = retrofitApi.putPost( "abc", 5, post);
//        Call<PostModel> call = retrofitApi.putPost(5, post);
//        Call<PostModel> call = retrofitApi.patchPost(5, post);

        //calling a call.enqueue in separate thread. if we call.execute, it will be on main thread
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {
                if (!response.isSuccessful()) {
                    binding.textViewResult.setText("code: " + response.code());
                    return;
                }

                PostModel postResponse = response.body();
                String content = "";
                content += "code: " + response.code() + "\n";
                if (postResponse != null) {
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "User ID: " + postResponse.getUserId() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getBody() + "\n\n";
                    binding.textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable t) {
                binding.textViewResult.setText(t.getMessage());
            }
        });

    }

    private void createPost() {
        //creating new post object to send
        PostModel post = new PostModel(23, "new Title", "new Text");


        Map<String, String> fields = new HashMap<>();
        fields.put("userId", "25");
        fields.put("title", "25");

        Call<PostModel> call = retrofitApi.createPost(fields);
//        Call<PostModel> call = retrofitApi.createPost(23, "new Title", "new Text");
//        Call<PostModel> call = retrofitApi.createPost(post);

        //calling a call.enqueue in separate thread. if we call.execute, it will be on main thread
        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(@NonNull Call<PostModel> call, @NonNull Response<PostModel> response) {
                if (!response.isSuccessful()) {
                    binding.textViewResult.setText("code: " + response.code());
                    return;
                }

                PostModel postResponse = response.body();
                String content = "";
                content += "code: " + response.code() + "\n";
                if (postResponse != null) {
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "User ID: " + postResponse.getUserId() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getBody() + "\n\n";
                    binding.textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PostModel> call, @NonNull Throwable t) {
                binding.textViewResult.setText(t.getMessage());
            }
        });


    }

    private void getComments() {
        //getting comment section, not posts as before

//        Call<List<CommentModel>> call = retrofitApi.getComments(4);
//        Call<List<CommentModel>> call = retrofitApi.getComments("https://jsonplaceholder.typicode.com/posts/4/comments");
        Call<List<CommentModel>> call = retrofitApi.getComments("posts/4/comments");

        call.enqueue(new Callback<List<CommentModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<CommentModel>> call, @NonNull Response<List<CommentModel>> response) {
                if (!response.isSuccessful()) {
                    binding.textViewResult.setText("code: " + response.code());
                    return;
                }

                List<CommentModel> list = response.body();
                if (list != null) {
                    for (CommentModel comment : list) {
                        String content = "";
                        content += "ID:" + comment.getId() + "\n";
                        content += "Post ID:" + comment.getPostId() + "\n";
                        content += "Name:" + comment.getName() + "\n";
                        content += "Email:" + comment.getEmail() + "\n";
                        content += "Text:" + comment.getText() + "\n\n";
                        binding.textViewResult.append(content);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<CommentModel>> call, @NonNull Throwable t) {
                binding.textViewResult.setText(t.getMessage());
            }
        });

    }

    private void getPosts() {
        //getting posts section in url

        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<PostModel>> call = retrofitApi.getPosts(parameters);
//        Call<List<PostModel>> call = retrofitApi.getPosts(new Integer[]{2,5,6}, "id", "desc");
//        Call<List<PostModel>> call = retrofitApi.getPosts(new Integer[]{2,5,6}, null, null);

        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostModel>> call, @NonNull Response<List<PostModel>> response) {
                if (!response.isSuccessful()) {
                    binding.textViewResult.setText("code: " + response.code());
                    return;
                }

                List<PostModel> list = response.body();
                if (list != null) {
                    for (PostModel post : list) {
                        String content = "";
                        content += "ID:" + post.getId() + "\n";
                        content += "User ID:" + post.getUserId() + "\n";
                        content += "Title:" + post.getTitle() + "\n";
                        content += "Text:" + post.getBody() + "\n\n";
                        binding.textViewResult.append(content);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PostModel>> call, @NonNull Throwable t) {
                binding.textViewResult.setText(t.getMessage());
            }
        });
    }
}