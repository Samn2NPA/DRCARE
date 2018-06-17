package com.tvnsoftware.drcare.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tvnsoftware.drcare.Application;
import com.tvnsoftware.drcare.R;
import com.tvnsoftware.drcare.model.users.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tvnsoftware.drcare.Application.users;
import static com.tvnsoftware.drcare.Utils.Constants.CURRENT_USER_KEY;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    public static final String EXTRA_ROLE = "EXTRA_ROLE";

    public static FirebaseDatabase database;
    public static DatabaseReference dbRefer;

    @BindView(R.id.bt_login)
    Button mBtnLogin;
    @BindView(R.id.bt_qr_code)
    Button mBtnQrCode;
    @BindView(R.id.edt_login)
    EditText edtLoginId;
    @BindView(R.id.cvLogin)
    CardView cvLogin;
    @BindView(R.id.fabRegister)
    FloatingActionButton fabRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        dbRefer = database.getReference();

        users  = User.getUserList();
        //ToDo something
//       /* mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //login();
//                Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(i);
//            }
//        });*/
        mBtnQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new ZxingOrient(LoginActivity.this).initiateScan();
            }

        });
//        if(CoreManager.getInstance().getUserData() != null){
//            transferToPage(CoreManager.getInstance().getUserData().getRoleCode());
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /* ZxingOrientResult scanResult =
                ZxingOrient.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            if (scanResult.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(this, "Scanned: " + scanResult.getContents(), Toast.LENGTH_LONG).show();
                //loginQRCode(scanResult.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }*/

    }

    @OnClick(R.id.bt_login)
    public void onClickLogin() {
        String inputCode = edtLoginId.getText().toString();

        List<User> userList = users;
        if (inputCode.isEmpty() || inputCode.length() == 0 || inputCode.equals("") || inputCode == null)
            Toast.makeText(this, "Please enter UserID to login", Toast.LENGTH_SHORT).show();
        else {

            //roleID = 1: patient     || roleID = 0: doctor      || roleID = -1: login failed
            int roleID = User.checkIsUser(inputCode.toLowerCase(), userList);

            switch (roleID){
                case -1: Toast.makeText(this, "Wrong User ID!", Toast.LENGTH_SHORT).show();
                        edtLoginId.setText("");
                        break;
                default: Intent i = new Intent(LoginActivity.this,MainActivity.class);
                    CURRENT_USER_KEY = User.getKeyByUserCode(inputCode);
                    Log.d(TAG, "TEST: role ID = " + roleID + " || CURRENT_USER_KEY :: " + CURRENT_USER_KEY);
                    i.putExtra(EXTRA_ROLE, roleID);

                    startActivity(i);
                    finish();
            }
        }
    }

/*
    @OnClick({R.id.bt_login, R.id.fabRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabRegister:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(null);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setEnterTransition(null);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(this, fabRegister, fabRegister.getTransitionName());
                    startActivity(new Intent(this, RegisterActivity.class), options.toBundle());
                } else {
                    startActivity(new Intent(this, RegisterActivity.class));
                }
                break;
            case R.id.bt_login:
                Explode explode = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    explode = new Explode();
                }
                explode.setDuration(500);

                onClickLogin();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(explode);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setEnterTransition(explode);
                }
//                ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
//                Intent i2 = new Intent(this,MainActivity.class);
//                startActivity(i2, oc2.toBundle());
                break;
        }
    }

*/

//    private void loginQRCode(String userCode) {
//        User data = null;
//        for (User u : FakeData.getUsers()) {
//            if (u.getUserCode().equals(userCode.toUpperCase())) {
//                data = u;
//            }
//        }
//        if (data != null) {
//            transferToPage(data.getRoleCode());
//        } else {
//            Toast.makeText(this, "Invalid UserCode", Toast.LENGTH_LONG).show();
//        }
//    }


    /*private void transferToPage(int userRole) {
        //1: Doctor page
        if (1 == userRole) {
            transferToDoctor();
        } else {
            transferToPatientPage();
        }
    }

    private void transferToPatientPage() {
        //Patient activity hello
        Intent i = new Intent(LoginActivity.this, HistoryActivity.class);
        startActivity(i);
    }

    private void transferToDoctor() {
        //Doctor activity
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }*/

}
