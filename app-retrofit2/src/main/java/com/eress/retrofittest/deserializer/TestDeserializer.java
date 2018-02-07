package com.eress.retrofittest.deserializer;

import android.util.Log;

import com.eress.retrofittest.result.TestResult;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class TestDeserializer implements JsonDeserializer<TestResult> {

    @Override
    public TestResult deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.d("test", "json.toString(): " + json.toString());
        return new Gson().fromJson(json, TestResult.class);
    }
}
