package com.eress.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.eress.retrofittest.deserializer.TestDeserializer;
import com.eress.retrofittest.result.TestResult;
import com.eress.retrofittest.service.TestService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

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

        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                // Token 없을 경우 생략
                request.addHeader("auth_token", "[token]");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://www.test.co.kr")
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .build();

        TestService testService = restAdapter.create(TestService.class);

        // Synchronous Response
        final TestService f_testService = testService;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TestResult testResult = f_testService.requestMembershipPaymentInfo("001", "0101234678");
                    Log.d("test", "Synchronous Response: " + testResult.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Asynchronous Response
        testService.requestMembershipPaymentInfo("001", "01012345678", new Callback<TestResult>() {
            @Override
            public void success(TestResult testResult, Response response) {
                Log.d("test", "Asynchronous Response: " + testResult.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("test", "error.getUrl(): " + error.getUrl());
                Log.d("test", "error.getMessage(): " + error.getMessage());
            }
        });
    }
}
