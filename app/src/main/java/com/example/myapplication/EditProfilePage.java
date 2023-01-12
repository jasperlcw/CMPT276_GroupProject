package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.DragAndDropPermissionsCompat;

public class EditProfilePage extends AppCompatActivity {

    private EditText editDormitory, editEmail, editPhone;
    private ImageButton cancelButton, checkButton;
    private ImageView editProfilePic;
    private int currentStudentId;
    private Context context;
    private boolean dataFetchStatus;
    private String initialEmail, initialPhone, initialDormitory;

    private static final int CAMERA_REQUEST = 1888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);
        context = getApplicationContext();
        currentStudentId = DataHolder.getInstance().getID();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");
        editProfilePic = findViewById(R.id.edit_profile_avatar);
        editProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        setupViewIds();
        setButtonListeners();
        new GrabFromDbTask().execute();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            editProfilePic.setImageBitmap(photo);
        }
    }

    private class GrabFromDbTask extends AsyncTask<Void, Void, Void> {
        private User toEdit;

        @Override
        protected Void doInBackground(Void... voids) { //grabs information to change text fields
            DBExchanger exchanger = new DBExchanger();
            dataFetchStatus = exchanger.getConnectionStatus();

            if (dataFetchStatus) {
                toEdit = exchanger.getStudentByNumber(currentStudentId);
            }
            
            exchanger.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) { //changes the text fields
            super.onPostExecute(unused);

            if (dataFetchStatus) {
                initialEmail = toEdit.getEmail();
                initialPhone = toEdit.getPhoneNumber();
                initialDormitory = toEdit.getAddress();

                editEmail.setText(initialEmail);
                editPhone.setText(initialPhone);
                editDormitory.setText(initialDormitory);
            } else {
                Toast.makeText(context, "Unable to fetch profile information. Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

        private class pushEditsTask extends AsyncTask<Void, Void, Void> {
        boolean pushConStatus;

        @Override
        protected Void doInBackground(Void... voids) {
            DBExchanger exchanger = new DBExchanger();
            pushConStatus = exchanger.getConnectionStatus();
            if (pushConStatus && dataFetchStatus) {
                exchanger.editDormNumber(editDormitory.getText().toString(), currentStudentId);
                exchanger.editPhoneNumber(editPhone.getText().toString(), currentStudentId);
                exchanger.editEmail(editEmail.getText().toString(), currentStudentId);
            }
            exchanger.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            if (pushConStatus && dataFetchStatus) {
                Toast.makeText(context, "Profile successfully updated.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Unable to update profile information. Please check your connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkSameStrings() {
        boolean sameEmail = initialEmail.contentEquals(editEmail.getText());
        boolean samePhone = initialPhone.contentEquals(editPhone.getText());
        boolean sameDormitory = initialDormitory.contentEquals(editDormitory.getText());

        return (sameEmail && samePhone && sameDormitory);
    }

    private void setupViewIds() {
        cancelButton = findViewById(R.id.cancel_button);
        checkButton = findViewById(R.id.check_button);
        editProfilePic = findViewById(R.id.edit_profile_avatar);
        editDormitory = findViewById(R.id.edit_dormitory);
        editEmail = findViewById(R.id.edit_email);
        editPhone = findViewById(R.id.edit_phone);
    }

    private void setButtonListeners() {
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkSameStrings()) {
                    Toast.makeText(context, "Changes discarded.", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!checkSameStrings()) {
                    new pushEditsTask().execute(); //this will also make a Toast popup to notify users what happened
                    finish();
                } else {
                    Toast.makeText(context, "No edits have been made yet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
