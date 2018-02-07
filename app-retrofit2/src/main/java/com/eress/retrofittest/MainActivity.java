package com.eress.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.eress.retrofittest.deserializer.TestDeserializer;
import com.eress.retrofittest.result.TestResult;
import com.eress.retrofittest.service.TestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_test)
    TextView tvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        tvTest.setText("테스트");
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(TestResult.class, new TestDeserializer())
                .create();

        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("auth_token", "[token]") // Token 없을 경우 생략
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.test.co.kr/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient.build())
                .build();

        Call<TestResult> call = retrofit.create(TestService.class).requestMembershipPaymentInfo("001", "0101234678");

        // Synchronous Response
        final Call<TestResult> f_call = call;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TestResult testResult = f_call.execute().body();
                    Log.d("test", "Synchronous Response: " + testResult.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Asynchronous Response
        call.enqueue(new Callback<TestResult>() {
            @Override
            public void onResponse(Call<TestResult> call, Response<TestResult> response) {
                if (response.isSuccessful()) {
                    Log.d("test", "Asynchronous Response: " + response.body().toString());
                } else {
                    Log.d("test", "response.code(): " + response.code());
                    Log.d("test", "response.errorBody(): " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TestResult> call, Throwable t) {
                Log.d("test", "t.getStackTrace(): " + t.getStackTrace());
                Log.d("test", "t.getMessage(): " + t.getMessage());
            }
        });
    }
}
