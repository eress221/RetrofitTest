package com.eress.retrofittest.service;

import com.eress.retrofittest.result.TestResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TestService {

    @FormUrlEncoded
    @POST("member_info")
    Call<TestResult> requestMembershipPaymentInfo(
            @Field("m_id") String mId,
            @Field("phone") String phone
    );
}
