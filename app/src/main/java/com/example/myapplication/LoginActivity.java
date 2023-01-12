package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;


public class LoginActivity extends AppCompatActivity {

    private EditText ID, startDate;
    private LocalDate ldStart;
    private Button blogin;
    private ProgressDialog loadingBar;
    private Context context;

    private int IDi;

    Message message;
    Bundle b = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");

        // initialising the layout items
        ID = findViewById(R.id.login_student_id);
        startDate = findViewById(R.id.login_start_date);
        blogin = findViewById(R.id.login_button);
        loadingBar = new ProgressDialog(this);
        context = getApplicationContext();

        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validate(ID.getText().toString(), startDate.getText().toString());
            }
        });
    }
    public void Validate(String userID, String startDate) {

        //stop gap measure for iteration 2
        //need to implement more refined user friendly feedback for invalid login
        boolean validInput = false;

        try {
            ldStart = LocalDate.parse(startDate);
            IDi = Integer.parseInt(userID);
            validInput = true;
        } catch (Exception e) {
            Toast.makeText(context, "Type in 100000001 for StudentID, 2021-07-01 for date to login.", Toast.LENGTH_LONG).show();
        }

        if (validInput) {
            new Thread() {
                @Override
                public void run() {
                    DBExchanger dbExchanger = new DBExchanger();

                    boolean dlogin = dbExchanger.verifyLogin(IDi, ldStart);
                    dbExchanger.close();
                    message = Message.obtain(hand1);
                    b.putBoolean(null, dlogin);
                    message.setData(b);
                    hand1.sendMessage(message);
                }
            }.start();
        }
    }
    final Handler hand1 = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg) {
            if(message.getData().getBoolean(null)){
                DataHolder.getInstance().setID(IDi);
                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(context, "Type in 100000001 for StudentID, 2021-07-01 for date to login.", Toast.LENGTH_LONG).show();
            }
            return true;
        }
    });

}

