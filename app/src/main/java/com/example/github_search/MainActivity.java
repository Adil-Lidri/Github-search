package com.example.github_search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.github_search.model.GitUser;
import com.example.github_search.model.GitUserResponse;
import com.example.github_search.model.UserListViewModel;
import com.example.github_search.service.GitRepoServiceAPI;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public List<GitUser> data =new ArrayList<>();
    public static final String USERNAME_PARAM = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button buttonSearch = findViewById(R.id.buttonSearch);
        final EditText editTextUser = findViewById(R.id.editTextUser);
        ListView listViewUsers = findViewById(R.id.ListViewUser);

        final UserListViewModel listViewModel = new UserListViewModel(this, R.layout.users_list_view_layout, data);
        listViewUsers.setAdapter(listViewModel);


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonSearch.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String q = editTextUser.getText().toString();

                        GitRepoServiceAPI service = retrofit.create(GitRepoServiceAPI.class);
                        Call<GitUserResponse> gitUserResponseCall = service.searchUsers(q);

                        gitUserResponseCall.enqueue(
                                new Callback<GitUserResponse>() {
                                    @Override
                                    public void onResponse(Call<GitUserResponse> call, Response<GitUserResponse> response) {
                                        if(!response.isSuccessful()){
                                            Log.i("error", String.valueOf(response.code()));
                                            return;
                                        }
                                        GitUserResponse gitUserResponse = response.body();
                                        for (GitUser user: gitUserResponse.users){
                                            // Log.i("usename", user.;
                                            data.add(user);
                                        }
                                        listViewModel.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<GitUserResponse> call, Throwable t) {
                                        Log.i("error", "Error onFailure");

                                    }
                                }
                        );
                    }
                }
        );

        listViewUsers.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String username = data.get(i).username;

                        Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        intent.putExtra(USERNAME_PARAM, username);

                        startActivity(intent);
                    }
                }
        );
    }
}