package com.example.moneymanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moneymanager.R;
import com.example.moneymanager.constant.SharedPrefConstant;
import com.example.moneymanager.methods.SharedMethods;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AccountActivity extends AppCompatActivity{
    BottomNavigationView bnv_menu;
    LinearLayout ll_my_wallet, ll_tools, ll_settings, ll_about_us;
    FloatingActionButton fab_add_transaction;
    TextView tv_first_letter_wallet_name, tv_wallet_name, tv_email;

    private void getViews(){
        bnv_menu = findViewById(R.id.account_bnv_menu);
        ll_my_wallet = findViewById(R.id.account_ll_my_wallet);
        ll_tools = findViewById(R.id.account_ll_tools);
        ll_settings = findViewById(R.id.account_ll_settings);
        ll_about_us = findViewById(R.id.account_ll_about_us);
        fab_add_transaction = findViewById(R.id.account_fab_add_transaction);
        tv_first_letter_wallet_name = findViewById(R.id.account_tv_first_letter_wallet_name);
        tv_wallet_name = findViewById(R.id.account_tv_wallet_name);
        tv_email = findViewById(R.id.account_tv_email);
    }

    private void displayUserInformation(){
        SharedPreferences sharedPreferenceSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
        String walletName = sharedPreferenceSigningIn.getString(SharedPrefConstant.SIGNING_IN_WALLET_NAME, "");
        tv_wallet_name.setText(walletName);

        char firstLetterWalletName = walletName.charAt(0);
        tv_first_letter_wallet_name.setText(String.valueOf(firstLetterWalletName));

        String email = sharedPreferenceSigningIn.getString(SharedPrefConstant.SIGNING_IN_EMAIL, "");
        tv_email.setText(email);
    }


    private void setEventListener(){
        //Hàm này được gọi mỗi khi có item trên menu được ấn
        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_wallet:
                        Intent walletIntent = new Intent(AccountActivity.this, WalletActivity.class);
                        startActivity(walletIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.item_statistic:
                        Intent statisticIntent = new Intent(AccountActivity.this, StatisticActivity.class);
                        startActivity(statisticIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.item_plan:
                        Intent planIntent = new Intent(AccountActivity.this, PlanActivity.class);
                        startActivity(planIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }
                return false;
            }
        });

        fab_add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTransactionIntent = new Intent(AccountActivity.this, AddTransactionActivity.class);
                startActivity(addTransactionIntent);
                overridePendingTransition(0,0);
            }
        });

        ll_my_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myWalletIntent = new Intent(AccountActivity.this, AccountMyWalletActivity.class);
                startActivity(myWalletIntent);
                overridePendingTransition(0,0);
            }
        });

        ll_tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toolsIntent = new Intent(AccountActivity.this, AccountToolsActivity.class);
                startActivity(toolsIntent);
                overridePendingTransition(0,0);
            }
        });

        ll_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountSettingsIntent = new Intent(AccountActivity.this, AccountSettingsActivity.class);
                startActivity(accountSettingsIntent);
                overridePendingTransition(0,0);
            }
        });

        ll_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutUsIntent = new Intent(AccountActivity.this, AccountAboutUsActivity.class);
                startActivity(aboutUsIntent);
                overridePendingTransition(0,0);
            }
        });
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("lifecycle", "onCreate");
        setContentView(R.layout.activity_account);
        getViews();
        displayUserInformation();
        //Không đổi chỗ 2 dòng dưới cho nhau
        //Hàm setSelected item hoạt động như đang ấn vào menu đó => trigger event onNavigationItemSelected => bug
        //=> set item được chọn trước khi set event
        SharedMethods.setNavigationMenu(bnv_menu, R.id.item_account);
        setEventListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("lifecycle", "onRestart");
        displayUserInformation();
    }
}
