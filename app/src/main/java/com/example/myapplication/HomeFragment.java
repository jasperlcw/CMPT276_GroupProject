
package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Post> postList;
    SearchRecyclerAdapter searchRecyclerAdapter;
    FloatingActionButton search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,false);

        postList=new ArrayList<Post>();
//        posts.add(new PostTest("title", "description", "price", 1,
//                "name", "time", 2));
        //posts.add(new Post(null,"title","title", LocalDateTime.now(),1));
        //posts.add(new Post(null,"test","test", LocalDateTime.now(),1));

        recyclerView = view.findViewById(R.id.post_recycler_view);

        this.getPost();


        search = view.findViewById(R.id.home_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

        return  view;
    }

    public void getPost(){
        new Thread(){
            @Override
            public void run() {
                //DB
                DBExchanger dbExchanger=new DBExchanger();
                postList=dbExchanger.getPostsByNumber(0,50);

                dbExchanger.close();
                hand1.sendEmptyMessage(1);
            }
        }.start();
    }
    final Handler hand1 = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            searchRecyclerAdapter=new SearchRecyclerAdapter(postList);//DB
            recyclerView.setAdapter(searchRecyclerAdapter);

        }
    };

}

