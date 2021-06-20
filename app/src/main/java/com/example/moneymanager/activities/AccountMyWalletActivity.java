package com.example.moneymanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moneymanager.R;

public class AccountMyWalletActivity extends AppCompatActivity {
    ImageButton ib_back;

    private void getViews(){
        ib_back = findViewById(R.id.account_my_wallet_ib_back);
    }

    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_my_wallet);
        getViews();
        setEventListener();
    }
}
