package com.example.moneymanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;

public class AddTransactionCategoryActivity extends AppCompatActivity {
    ImageButton ib_back;
    LinearLayout ll_food, ll_transportation, ll_bill, ll_home_decoration, ll_vehicle_maintenance, ll_health, ll_education,
            ll_houseware, ll_insurances, ll_personal_belonging, ll_pet, ll_home_service, ll_sport, ll_make_up,
            ll_online_service, ll_entertainment, ll_pay_interest, ll_transfer_money_out, ll_other_express, ll_salary,
            ll_profit, ll_transfer_money_in, ll_other_revenue, ll_debt_collect, ll_debt, ll_loan, ll_debt_pay;


    private void getViews(){
        ib_back = findViewById(R.id.add_transaction_category_ib_back);
        ll_food =findViewById(R.id.add_transaction_category_ll_food);
        ll_transportation = findViewById(R.id.add_transaction_category_ll_transportation);
        ll_bill = findViewById(R.id.add_transaction_category_ll_bill);
        ll_home_decoration = findViewById(R.id.add_transaction_category_ll_home_decoration);
        ll_vehicle_maintenance = findViewById(R.id.add_transaction_category_ll_vehicle_maintenance);
        ll_health = findViewById(R.id.add_transaction_category_ll_health);
        ll_education = findViewById(R.id.add_transaction_category_ll_education);
        ll_houseware = findViewById(R.id.add_transaction_category_ll_houseware);
        ll_insurances = findViewById(R.id.add_transaction_category_ll_insurances);
        ll_personal_belonging = findViewById(R.id.add_transaction_category_ll_personal_belonging);
        ll_pet = findViewById(R.id.add_transaction_category_ll_pet);
        ll_home_service = findViewById(R.id.add_transaction_category_ll_home_service);
        ll_sport = findViewById(R.id.add_transaction_category_ll_sport);
        ll_make_up = findViewById(R.id.add_transaction_category_ll_make_up);
        ll_online_service = findViewById(R.id.add_transaction_category_ll_online_service);
        ll_entertainment = findViewById(R.id.add_transaction_category_ll_entertainment);
        ll_pay_interest = findViewById(R.id.add_transaction_category_ll_pay_interest);
        ll_transfer_money_out = findViewById(R.id.add_transaction_category_ll_transfer_money_out);
        ll_other_express = findViewById(R.id.add_transaction_category_ll_other_express);
        ll_salary = findViewById(R.id.add_transaction_category_ll_salary);
        ll_profit = findViewById(R.id.add_transaction_category_ll_profit);
        ll_transfer_money_in = findViewById(R.id.add_transaction_category_ll_transfer_money_in);
        ll_other_revenue = findViewById(R.id.add_transaction_category_ll_other_revenue);
        ll_debt_collect = findViewById(R.id.add_transaction_category_ll_debt_collect);
        ll_debt = findViewById(R.id.add_transaction_category_ll_debt);
        ll_loan = findViewById(R.id.add_transaction_category_ll_loan);
        ll_debt_pay = findViewById(R.id.add_transaction_category_ll_debt_pay);
    }
    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });

        ll_food.setOnClickListener(v -> {categoryItemClick(1);});
        ll_transportation.setOnClickListener(v -> {categoryItemClick(2);});
        ll_bill.setOnClickListener(v -> {categoryItemClick(3);});
        ll_home_decoration.setOnClickListener(v -> {categoryItemClick(4);});
        ll_vehicle_maintenance.setOnClickListener(v -> {categoryItemClick(5);});
        ll_health.setOnClickListener(v -> {categoryItemClick(6);});
        ll_education.setOnClickListener(v -> {categoryItemClick(7);});
        ll_houseware.setOnClickListener(v -> {categoryItemClick(8);});
        ll_insurances.setOnClickListener(v -> {categoryItemClick(9);});
        ll_personal_belonging.setOnClickListener(v -> {categoryItemClick(10);});
        ll_pet.setOnClickListener(v -> {categoryItemClick(11);});
        ll_home_service.setOnClickListener(v -> {categoryItemClick(12);});
        ll_sport.setOnClickListener(v -> {categoryItemClick(13);});
        ll_make_up.setOnClickListener(v -> {categoryItemClick(14);});
        ll_online_service.setOnClickListener(v -> {categoryItemClick(15);});
        ll_entertainment.setOnClickListener(v -> {categoryItemClick(16);});
        ll_pay_interest.setOnClickListener(v -> {categoryItemClick(17);});
        ll_transfer_money_out.setOnClickListener(v -> {categoryItemClick(18);});
        ll_other_express.setOnClickListener(v -> {categoryItemClick(19);});
        ll_salary.setOnClickListener(v -> {categoryItemClick(20);});
        ll_profit.setOnClickListener(v -> {categoryItemClick(21);});
        ll_transfer_money_in.setOnClickListener(v -> {categoryItemClick(22);});
        ll_other_revenue.setOnClickListener(v -> {categoryItemClick(23);});
        ll_debt_collect.setOnClickListener(v -> {categoryItemClick(24);});
        ll_debt.setOnClickListener(v -> {categoryItemClick(25);});
        ll_loan.setOnClickListener(v -> {categoryItemClick(26);});
        ll_debt_pay.setOnClickListener(v -> {categoryItemClick(27);});
    }

    private void categoryItemClick(int categoryItemId) {
        Intent addTransactionIntent = new Intent();
        addTransactionIntent.putExtra(AddTransactionActivity.ADD_CATEGORY_ID_EXTRA_NAME, categoryItemId);
        setResult(RESULT_OK, addTransactionIntent);
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_category);
        getViews();
        setEventListener();
    }
}
