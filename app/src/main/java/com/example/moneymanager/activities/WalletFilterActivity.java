package com.example.moneymanager.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;



public class WalletFilterActivity extends AppCompatActivity{
    ImageButton ib_back;
    String[] rangeGroup = {"Tất cả", "Khoản chi", "Khoản thu"};

    Spinner sp_group;
    TextView tv_range_money, tv_range_time;

    String group = "Tất cả",
            at_time = "", before_time = "", after_time = "",
            sp_money = "", sp_more_than_money = "", sp_less_than_money = "";



    private void getViews() {
        tv_range_money = findViewById(R.id.wallet_filter_tv_money_range);
        tv_range_time = findViewById(R.id.wallet_filter_tv_range_time);
        sp_group = findViewById(R.id.wallet_filter_sp_group);
        ib_back = findViewById(R.id.add_transaction_category_ib_back);
    }

    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });
        tv_range_money.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletFilterActivity.this,WalletFilterChooseMoneyRangeActivity.class);
                startActivityForResult(intent,1);
            }
        });
        tv_range_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalletFilterActivity.this,WalletFilterChooseTimeRangeActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }


    public void getSpinner() {
        ArrayAdapter<String> adapterGroup = new ArrayAdapter<String>(WalletFilterActivity.this,
                R.layout.spinner_wallet_filter, rangeGroup);
        adapterGroup.setDropDownViewResource(R.layout.spinner_wallet_filter_dropdown);
        sp_group.setAdapter(adapterGroup);
        sp_group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: group = "Tất cả";
                        break;
                    case 1: group = "Khoản chi";
                        break;
                    case 2: group = "khoản thu";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_filter);
        getViews();
        setEventListener();
        getSpinner();
    }
}
