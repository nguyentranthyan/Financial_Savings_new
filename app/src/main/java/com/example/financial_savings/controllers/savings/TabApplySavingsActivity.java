package com.example.financial_savings.controllers.savings;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.TietKiem;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.SavingsDisplayModule;
import com.example.financial_savings.modules.savings.SavingsApplyModule;

import java.util.ArrayList;

public class TabApplySavingsActivity extends AppCompatActivity implements IMappingView {
    private ListView listView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savings_tab_apply);
        init();
        loadData();
    }

    private void loadData() {
        ArrayList<TietKiem> list = SavingsApplyModule.getSavingsApply(dbHelper);
        SavingsDisplayModule.showListViewHome_Savings(list, getApplicationContext(), listView, dbHelper);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void init() {
        listView = findViewById(R.id.listView_savings_tab_apply);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
