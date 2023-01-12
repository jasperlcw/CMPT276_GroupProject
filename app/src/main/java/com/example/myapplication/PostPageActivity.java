package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;


public class PostPageActivity extends AppCompatActivity {

    private static final String EXTRA_ID = "com.example.myapplication.PostPageActivity - postID";

    TextView postUserName, postTitle, postTime, postDescription;
    ImageButton postImage, sendButton, backButtonPost;
    EditText commentTextInsert;
    private int postId;
    private Context context;
    private AsyncTask<Void, Void, Void> grabFromDb;
    private AsyncTask<Void, Void, Void> pushToDb;

    RecyclerView recyclerView;
    ReplyRecyclerAdapter replyRecyclerAdapter;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Post Page");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        loadingBar = new ProgressDialog(this);

        setContentView(R.layout.postpage);
        recyclerView = findViewById(R.id.recycle_comment);
        extractDataFromIntent();
        setupViewIds();
        context = getApplicationContext();

//        setBackButtonListener();
        grabFromDb = new GrabFromDbTask();
        grabFromDb.execute();
        sendButtonListener();

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

    private class GrabFromDbTask extends AsyncTask<Void, Void, Void> {

        private Post toDisplay;
        private User authorOfPost;
        private ArrayList<Reply> replyList;


        @Override
        protected Void doInBackground(Void... voids) { //this method does all the db connections

            DBExchanger exchanger = new DBExchanger();
            if (exchanger.getConnectionStatus()) {
                toDisplay = exchanger.getPostByID(postId);
                authorOfPost = toDisplay.getPostAuthor();
                replyList = exchanger.getReplyByParent(postId);
            }
            exchanger.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) { //this code block will run after db operations finish
            super.onPostExecute(unused);

            String displayName = authorOfPost.getFirstName() + " " + authorOfPost.getMiddleName() + " " + authorOfPost.getLastName();

            postTitle.setText(toDisplay.getPostTitle());
            postTime.setText(toDisplay.getPostTime().toString().replace('T', ' '));
            postUserName.setText(displayName);
            postDescription.setText(toDisplay.getPostContent());

            Glide.with(com.example.myapplication.PostPageActivity.this).load(toDisplay.getPictureLink()).into(postImage);
            //replyList.add(new Reply(null,"test", LocalDateTime.now(),1,1)); test with local data
            //replyList.add(new Reply(null,"test", LocalDateTime.now(),2,2));
            replyRecyclerAdapter = new ReplyRecyclerAdapter(replyList);
            recyclerView.setAdapter(replyRecyclerAdapter);

        }
    }

    private void setupViewIds() {
        postUserName = findViewById(R.id.post_user_name);
        postTitle = findViewById(R.id.post_title);
        postTime = findViewById(R.id.post_time);
        postDescription = findViewById(R.id.post_description);
        postImage = findViewById(R.id.post_page_image);
        sendButton = findViewById(R.id.send_button);
        commentTextInsert = findViewById(R.id.comment_text_insert);

//        backButtonPost = findViewById(R.id.backButtonPost);

    }
//
//    private void setBackButtonListener() {
//        backButtonPost.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(context, "This is the back button.", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }

    //credit to Brian Fraser's tutorial: https://www.youtube.com/watch?v=9OYNH067Xa0&list=PL-suslzEBiMraghfX9LAADAEw8nQftSFS&index=21
    private void extractDataFromIntent() {
        Intent intent = getIntent();
        postId = intent.getIntExtra(EXTRA_ID, -1);
    }

    //credit to Brian Fraser's tutorial: https://www.youtube.com/watch?v=9OYNH067Xa0&list=PL-suslzEBiMraghfX9LAADAEw8nQftSFS&index=21
    public static Intent makeIntent(Context context, int postId) {
        Intent intent = new Intent(context, PostPageActivity.class);
        intent.putExtra(EXTRA_ID, postId);
        return intent;
    }

    private void sendButtonListener() {
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(commentTextInsert.getText().toString().isEmpty()){
                    Toast.makeText(context, "Please fill out following fields before replying.", Toast.LENGTH_LONG).show();
                }
                else{
                    pushToDb = new PushToDbTask(commentTextInsert.getText().toString());
                    pushToDb.execute();
                }

            }
        });
    }

    private class PushToDbTask extends AsyncTask<Void, Void, Void>  {
        String toSend;

        public PushToDbTask(String toSend){
            this.toSend=toSend;
        }
        @Override
        protected Void doInBackground(Void... voids) { //this method does all the db connections

            DBExchanger exchanger = new DBExchanger();
            if (exchanger.getConnectionStatus()) {
                User currentUser = exchanger.getStudentByNumber(DataHolder.getInstance().getID());
                LocalDateTime ld = LocalDateTime.now();
                Reply newReply=new Reply(currentUser,commentTextInsert.getText().toString(),ld,new Random().nextInt(999999),postId);//
                exchanger.addReply(newReply);
            }
            exchanger.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) { //this code block will run after db operations finish
            super.onPostExecute(unused);
                Toast.makeText(context, "Sent", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
        }
    }
}