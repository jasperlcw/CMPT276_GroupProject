package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    ImageView avatartv;
    TextView name, email, phoneNumber, startDayProfile, dormHouse, myPost;
    RecyclerView postrecycle;
    ArrayList<Post> myPostList;
    ProgressDialog pd;
    FloatingActionButton editbtn;
    SearchRecyclerAdapter recyclerAdapter;
    private Context context;
    private LocalDate startDay;
    private int studentID;
    private boolean dataFetchStatus;
    private String userEmail, userPhone, userName, dormitory;

    public ProfileFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        avatartv = view.findViewById(R.id.profile_avatar);
        name = view.findViewById(R.id.profile_name);
        email = view.findViewById(R.id.profile_email);
        phoneNumber = view.findViewById(R.id.profile_phone);
        dormHouse = view.findViewById(R.id.profile_dormitory);
        myPost = view.findViewById(R.id.my_post);
        postrecycle = view.findViewById(R.id.my_recycler_posts);
        editbtn = view.findViewById(R.id.profile_edit);
        startDayProfile=view.findViewById(R.id.profile_start_date);
        pd = new ProgressDialog(getActivity());
        pd.setCanceledOnTouchOutside(false);

        myPostList=new ArrayList<Post>();
        this.loadMyPosts();

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(new Intent(getActivity(), EditProfilePage.class));
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        context = getContext();
        studentID = DataHolder.getInstance().getID();
        new grabInformation().execute();
    }

    private class grabInformation extends AsyncTask<Void, Void, Void> {
        private User student;

        @Override
        protected Void doInBackground(Void... voids) { //Grab student's information
            DBExchanger exchanger = new DBExchanger();
            dataFetchStatus = exchanger.getConnectionStatus();

            if (dataFetchStatus) {
                student = exchanger.getStudentByNumber(studentID);
            }

            exchanger.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) { //show the information
            super.onPostExecute(unused);

            if (dataFetchStatus) {
                userEmail = student.getEmail();
                userPhone = student.getPhoneNumber();
                dormitory = student.getAddress();
                startDay = student.getStartDate();
                userName = student.getFirstName() + " " + student.getMiddleName() + " " + student.getLastName();

                email.setText(userEmail);
                phoneNumber.setText(userPhone);
                dormHouse.setText(dormitory);
                startDayProfile.setText(startDay.toString());
                name.setText(userName);
            } else {
                Toast.makeText(context, "Unable to fetch profile information. Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadMyPosts(){ //Grab post of this user

        new Thread(){
            @Override
            public void run() {
                //DB
                DBExchanger dbExchanger = new DBExchanger();
                myPostList = dbExchanger.getPostsByStudent(studentID);

                dbExchanger.close();
                hand.sendEmptyMessage(1);
            }
        }.start();
    }

    final Handler hand = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            recyclerAdapter = new SearchRecyclerAdapter(myPostList);//DB
            postrecycle.setAdapter(recyclerAdapter);

        }
    };
}
