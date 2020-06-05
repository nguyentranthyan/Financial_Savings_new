package com.example.financial_savings.controllers.accounts;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricPrompt;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.financial_savings.R;
import com.example.financial_savings.controllers.mains.HomeActivity;
import com.example.financial_savings.entities.SinhVien;
import com.example.financial_savings.entities.TaiKhoan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginInfoActivity extends AppCompatActivity implements IMappingView {
    private EditText editTextPassword;
    private Button buttonLogin, buttonForgotPass, buttonExit;
    private ImageView imgPicture;
    private TextView textViewName, textViewFingerprint;
    private ImageButton buttonFingerprint;
    private DBHelper dbHelper;
    private TaiKhoan taiKhoan;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_login_info);
        init();
        loadData();
        eventForgotPassword();
        eventLogout();
        eventLogin();
        new BiometricUtils();
        eventFingerprint();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Boolean checkBiometricSupport() {

        KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        PackageManager packageManager = this.getPackageManager();

        if (!keyguardManager.isKeyguardSecure()) {
            //notifyUser("Lock screen security not enabled in Settings");
            return false;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {

            //notifyUser("Fingerprint authentication permission not enabled");
            return false;
        }

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            return true;
        }

        return true;
    }

//    private void notifyUser(String message) {
//        Toast.makeText(this,
//                message,
//                Toast.LENGTH_LONG).show();
//    }
//    private CancellationSignal cancellationSignal;
//
//    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//    private CancellationSignal getCancellationSignal() {
//
//        cancellationSignal = new CancellationSignal();
//        cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
//            @Override
//            public void onCancel() {
//                notifyUser("Cancelled via signal");
//            }
//        });
//        return cancellationSignal;
//    }
    private void eventFingerprint() {
        Executor executor = Executors.newSingleThreadExecutor();
        FragmentActivity activity = this;
        final BiometricPrompt biometricPrompt = new BiometricPrompt(activity, executor, new BiometricPrompt.AuthenticationCallback() {

            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    //TODO: Called when an unrecoverable error has been encountered and the operation is complete.
                    String TAG = null;
                    Log.d(TAG, "An unrecoverable error occurred");
                    //notifyUser("Authentication error: " + errString);
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                String TAG = null;
                //TODO: Called when a biometric is recognized.
                Log.d(TAG, "Fingerprint recognised successfully");
                //notifyUser("Authentication Succeeded");
                Intent intent = new Intent(LoginInfoActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                String TAG = null;
                //TODO: Called when a biometric is valid but not recognized.
                Log.d(TAG, "Fingerprint not recognised");
            }

        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Cảm biến vân tay")
                .setSubtitle("Xác thực đảm bảo đó là bạn")
                .setDescription("Vui lòng đặt ngón tay của bạn trên máy quét để xác minh danh tính của bạn")
                .setNegativeButtonText ( "Sử dụng mật khẩu tài khoản")
                .build();

        buttonFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }

    private void eventLogin() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pasword = editTextPassword.getText().toString();
                if(CheckEmptyModule.isEmpty(pasword, pasword, pasword)) {
                    if(taiKhoan.getMatKhau().equals(pasword)) {
                        Intent intent = new Intent(LoginInfoActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else Toast.makeText(getApplicationContext(), R.string.password_incorrect, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eventLogout() {
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        taiKhoan.setCode(0);
                        dbHelper.update_TaiKhoan(taiKhoan);
                        Intent intent = new Intent(LoginInfoActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                };
                AlertConfirmModule.alertDialogConfirm(LoginInfoActivity.this, R.string.mes_logout_info, runnable);
            }
        });
    }

    private void eventForgotPassword() {
        buttonForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginInfoActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        taiKhoan = dbHelper.getByCode_TaiKhoan(1);
        SinhVien sinhVien = dbHelper.getByID_SinhVien(taiKhoan.getEmail());
        textViewName.setText(sinhVien.getTen());
        imgPicture.setImageBitmap(convertStringToBitMap(sinhVien.getHinhAnh()));
    }
    public static Bitmap convertStringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    @Override
    public void init() {
        editTextPassword = findViewById(R.id.editTextPassword_login_info);
        buttonLogin = findViewById(R.id.buttonLogin_login_info);
        buttonForgotPass = findViewById(R.id.buttonForgotPass_login_info);
        buttonExit = findViewById(R.id.buttonExit_login_info);
        imgPicture = findViewById(R.id.imgPicture_login_info);
        textViewName = findViewById(R.id.textViewName_login_info);
        textViewFingerprint = findViewById(R.id.textViewFingerprint_login_info);
        buttonFingerprint = findViewById(R.id.buttonFingerprint_login_info);
        dbHelper = new DBHelper(this);
        editTextPassword.requestFocus();
        getSupportActionBar().hide();
    }
}
