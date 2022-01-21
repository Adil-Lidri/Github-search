package com.example.github_search.service;


import com.example.github_search.model.GitRepo;
import com.example.github_search.model.GitUserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitRepoServiceAPI {
    @GET("search/users")
    public Call<GitUserResponse> searchUsers(@Query("q") String query);

    @GET("users/{u}/repos")
    public Call<List<GitRepo>> userRepos(@Path("u") String username);

}
