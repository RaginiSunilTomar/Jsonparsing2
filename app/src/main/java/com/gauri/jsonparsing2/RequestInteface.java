package com.gauri.jsonparsing2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInteface {
    @GET("todos")
    abstract//to make a request provided by retrofit
    Call<List<Post>> getCarsJson();//

}
