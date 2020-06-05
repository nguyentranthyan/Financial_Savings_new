package com.example.financial_savings.controllers.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.DanhMuc;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.alerts.AlertConfirmModule;
import com.example.financial_savings.modules.checks.CheckEmptyModule;
import com.example.financial_savings.modules.others.CategoryNameModule;
import com.example.financial_savings.modules.icons.IconsDrawableModule;
import com.example.financial_savings.modules.randoms.RandomIDModule;

public class AddCateActivity extends AppCompatActivity implements IMappingView {
    private Button buttonSave;
    private ImageButton buttonIconCate, buttonCancel;
    private EditText editTextNameCate;
    private RadioGroup radioGroupCate;
    private RadioButton radioButton;
    private DBHelper dbHelper;
    private String icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);
        init();
        eventReturn();
        eventSave();
        eventIconCate();
    }

    private void eventIconCate() {
        buttonIconCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCateActivity.this, CateIconsActivity.class);
                startActivityForResult(intent, 555);
            }
        });
    }

    private void eventSave() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextNameCate.getText().toString();
                int select = radioGroupCate.getCheckedRadioButtonId();
                radioButton = findViewById(select);
                if(CheckEmptyModule.isEmpty(name, name, name)) {
                    if(icon != null) {
                        try {
                            dbHelper.getByName_DanhMuc(name);
                            Toast.makeText(getApplicationContext(), R.string.name_exist_cate_add, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            handlingSave(name);
                        }
                    } else Toast.makeText(getApplicationContext(), R.string.icon_empty_cate_add, Toast.LENGTH_SHORT).show();
                } else Toast.makeText(getApplicationContext(), R.string.empty_info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlingSave(final String name) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DanhMuc danhMuc = new DanhMuc();
                danhMuc.setMaDanhMuc(RandomIDModule.getCategoryID(dbHelper));
                danhMuc.setTenDanhMuc(name);
                danhMuc.setBieuTuong(icon);
                danhMuc.setLoaiDanhMuc(CategoryNameModule.getLabelByText(radioButton.getText().toString()));
                dbHelper.insert_DanhMuc(danhMuc);
                onBackPressed();
                Toast.makeText(getApplicationContext(), R.string.success_cate_add, Toast.LENGTH_SHORT).show();
            }
        };
        AlertConfirmModule.alertDialogConfirm(AddCateActivity.this, R.string.confirm_cate_add, runnable);
    }

    private void eventReturn() {
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddCateActivity.this, HomeCateActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 555 && resultCode == RESULT_OK) {
            icon = data.getStringExtra("icon");
            int resID = IconsDrawableModule.getResourcesDrawble(getApplicationContext(), icon);
            buttonIconCate.setImageResource(resID);
        }
    }

    @Override
    public void init() {
        buttonCancel = findViewById(R.id.buttonCancel_category_add);
        buttonSave = findViewById(R.id.buttonSave_category_add);
        buttonIconCate = findViewById(R.id.buttonIconCate_category_add);
        editTextNameCate = findViewById(R.id.editTextNameCate_category_add);
        radioGroupCate = findViewById(R.id.radioGroupCate_category_add);
        dbHelper = new DBHelper(this);
        editTextNameCate.requestFocus();
        getSupportActionBar().hide();
    }
}
