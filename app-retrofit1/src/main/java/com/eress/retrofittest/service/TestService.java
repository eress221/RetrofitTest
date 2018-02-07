package com.eress.retrofittest.service;

import com.eress.retrofittest.result.TestResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface TestService {

    // Synchronous Request
    @FormUrlEncoded
    @POST("/member_info")
    TestResult requestMembershipPaymentInfo(
            @Field("m_id") String mId,
            @Field("phone") String phone
    );

    // Asynchronous Request
    @FormUrlEncoded
    @POST("/member_info")
    void requestMembershipPaymentInfo(
            @Field("m_id") String mId,
            @Field("phone") String phone,
            Callback<TestResult> callback
    );
}
