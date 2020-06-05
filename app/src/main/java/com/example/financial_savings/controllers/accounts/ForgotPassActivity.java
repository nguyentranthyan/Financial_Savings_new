package com.example.financial_savings.controllers.accounts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.emails.GMailSender;
import com.example.financial_savings.entities.TaiKhoan;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.randoms.RandomIDModule;

public class ForgotPassActivity extends AppCompatActivity implements IMappingView {
    private EditText editTextEmail;
    private Button buttonSave;
    private ImageButton buttonReturn;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_forgot_email);
        init();
        eventReturn();
        eventSendEmail();
    }

    private void eventSendEmail() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                try {
                    if(CheckEmptyModule.isEmpty(email, email, email)) {
                        String codeRandom = String.valueOf(RandomIDModule.getRandomID());
                        TaiKhoan taikhoan = dbHelper.getByID_TaiKhoan(email);
                        taikhoan.setMatKhau(codeRandom);
                        dbHelper.update_TaiKhoan(taikhoan);
                        sendEmail(email, codeRandom);
                    } else Toast.makeText(getApplicationContext(), R.string.empty_email, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), R.string.email_incorrect, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendEmail(String email, String codeRandom) {
        String[] send_email = getResources().getStringArray(R.array.send_email);
        String subject = send_email[0];
        String message = send_email[1] + " " + codeRandom;
        String username = "vodinhchon@gmail.com";
        String password = "vodinhchonIT0908";
        try {
            GMailSender gMailSender = new GMailSender(username, password);
            gMailSender.sendMail(subject, message, username, email);
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }
    }

    private void eventReturn() {
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void init() {
        editTextEmail = findViewById(R.id.editTextEmail_forgot_email);
        buttonSave = findViewById(R.id.buttonSave_forgot_email);
        buttonReturn = findViewById(R.id.buttonReturn_forgot_email);
        dbHelper = new DBHelper(this);
        editTextEmail.requestFocus();
        getSupportActionBar().hide();
    }
}
