package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class PostDetailsActivity extends AppCompatActivity {


    String  postId;
    ImageView avatar, image;
    TextView name, time, title, description;
    RecyclerView recyclerView;
    ActionBar actionBar;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Post Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        postId = getIntent().getStringExtra("pid");
        recyclerView = findViewById(R.id.recycle_comment);
        avatar = findViewById(R.id.post_detail_avatar);
        image = findViewById(R.id.post_detail_image);
        name = findViewById(R.id.post_detail_name);
        time = findViewById(R.id.post_detail_time);
        title = findViewById(R.id.post_detail_title);
        description = findViewById(R.id.post_detail_description);

        progressDialog = new ProgressDialog(this);





    }

    private void loadComments() {


    }



    private void postComment() {

    }






    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}
