package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.time.LocalDateTime;
import java.util.Random;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.DBExchanger;
import com.example.myapplication.Post;
import com.example.myapplication.User;



public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchRecyclerAdapter searchRecyclerAdapter;
    SearchView searchView;

    ArrayList<Post> postList;//DB
    String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Search");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recycler_view);//
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postList=new ArrayList<Post>();

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchTerm=query;
                this.search();
                return false;
            }

            public void search(){

                new Thread(){
                    @Override
                    public void run() {
                        //DB
                        DBExchanger dbExchanger=new DBExchanger();
                        postList=dbExchanger.searchPosts(searchTerm);
                        dbExchanger.close();
                        hand1.sendEmptyMessage(1);
                    }
                }.start();
            }
            final Handler hand1 = new Handler( new Handler.Callback()
            {
                @Override
                public boolean handleMessage(Message msg) {
                    searchRecyclerAdapter=new SearchRecyclerAdapter(postList);//DB
                    recyclerView.setAdapter(searchRecyclerAdapter);

                    //toast that says no results if there are no search results
                    if (postList.size() == 0) {
                        Toast.makeText(getApplicationContext(), "No results. Searching \"t\" should give results.",Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
