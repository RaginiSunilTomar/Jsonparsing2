package com.gauri.jsonparsing2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {

    ArrayList<Post> posts=new ArrayList<>();
    private CarsAdapter carsAdapter;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview=findViewById(R.id.recycler_view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        getCarsResponse();


        //handeling clicks for recycler view

        recyclerview.addOnItemTouchListener(new CarsAdapter.RecyclerTouchListener(this,
                recyclerview, new CarsAdapter.ClickListener() {


            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Current Position"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));




    }

    private void getCarsResponse() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInteface requestInteface=retrofit.create(RequestInteface.class);
        Call<List<Post>> call=requestInteface.getCarsJson();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts=new ArrayList<>(response.body());
                carsAdapter=new CarsAdapter(MainActivity.this,posts);
                recyclerview.setAdapter(carsAdapter);
                Toast.makeText(MainActivity.this,"Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
