package com.example.moneymanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moneymanager.R;
import com.example.moneymanager.dialog.AccountToolsFindAtmDialog;
import com.example.moneymanager.dialog.AccountToolsFindBankDialog;

public class AccountToolsActivity extends AppCompatActivity {
    ImageButton ib_back;
    LinearLayout ll_find_atm, ll_find_bank;

    private void getViews(){
        ib_back = findViewById(R.id.account_tools_ib_back);
        ll_find_atm = findViewById(R.id.account_tools_ll_find_atm);
        ll_find_bank = findViewById(R.id.account_tools_ll_find_bank);
    }

    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });

        ll_find_atm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountToolsFindAtmDialog findAtmDialog = new AccountToolsFindAtmDialog();
                findAtmDialog.show(getSupportFragmentManager(), "");
            }
        });

        ll_find_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountToolsFindBankDialog findBankDialog = new AccountToolsFindBankDialog();
                findBankDialog.show(getSupportFragmentManager(), "");
            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_tools);
        getViews();
        setEventListener();
    }
}
