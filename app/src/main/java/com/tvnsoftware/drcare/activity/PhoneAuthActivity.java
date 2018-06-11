package com.tvnsoftware.drcare.activity;//package com.tvnsoftware.drcare.activity;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//import com.tvnsoftware.drcare.R;
//
//import java.util.concurrent.TimeUnit;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by Admin on 7/27/2017.
// */
//
//public class PhoneAuthActivity extends AppCompatActivity {
//
//    @BindView(R.id.et_phone_number)
//    EditText etPhoneNumber;
//    @BindView(R.id.btn_phone_register)
//    Button btnPhoneRegister;
//
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//    private String phone;
//    String mVerificationId = "";
//    PhoneAuthProvider.ForceResendingToken mResendToken;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.register_layout);
//        ButterKnife.bind(this);
//
//        phone = etPhoneNumber.getText().toString().trim();
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//                Log.d("PhoneAuthActivity", "onVerificationCompleted:" + phoneAuthCredential);
//                signInWithPhoneAuthCredential(phoneAuthCredential);
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
//                Log.w("PhoneAuthActivity", "onVerificationFailed", e.getCause());
//            }
//
//            @Override
//            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
//                Log.d("PhoneAuthActivity", "onCodeSent:" + verificationId);
//
//                // Save verification ID and resending token so we can use them later
//                mVerificationId = verificationId;
//                mResendToken = token;
//            }
//        });
//    }
//
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
//    }
//}
