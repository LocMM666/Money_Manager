package com.example.moneymanager.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moneymanager.R;
import com.example.moneymanager.adapter.EventAdapter;
import com.example.moneymanager.adapter.TransactionAdapter;
import com.example.moneymanager.constant.SharedPrefConstant;
import com.example.moneymanager.methods.SharedMethods;
import com.example.moneymanager.models.Event;
import com.example.moneymanager.models.Transaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PlanEventActivity extends AppCompatActivity {
    private static final int ADD_EVENT_REQUEST_CODE = 100;
    BottomNavigationView bnv_menu;
    ImageButton ib_back;
    FloatingActionButton fab_add_event;
    ArrayList<Event> events = new ArrayList<>();
    EventAdapter eventAdapter;
    ListView lv_event;

    private void getViews(){
        bnv_menu = findViewById(R.id.plan_event_bnv_menu);
        ib_back = findViewById(R.id.plan_event_ib_back);
        fab_add_event = findViewById(R.id.plan_event_fab_add_event);
        lv_event = findViewById(R.id.plan_event_lv_event);
    }

    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_wallet:
                        Intent walletIntent = new Intent(PlanEventActivity.this, WalletActivity.class);
                        startActivity(walletIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.item_statistic:
                        Intent statisticIntent = new Intent(PlanEventActivity.this, StatisticActivity.class);
                        startActivity(statisticIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.item_account:
                        Intent accountIntent = new Intent(PlanEventActivity.this, AccountActivity.class);
                        startActivity(accountIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }
                return false;
            }
        });

        fab_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addEventIntent = new Intent(PlanEventActivity.this, PlanEventAddEventActivity.class);
                startActivityForResult(addEventIntent, ADD_EVENT_REQUEST_CODE);
                overridePendingTransition(0,0);
            }
        });
    }

    private void displayEventListView(){
        //L???y file ng?????i d??ng ??ang ????ng nh???p
        SharedPreferences sharedPreferenceSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
        String username = sharedPreferenceSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");
        //L???y file l??u th??ng tin giao d???ch
        SharedPreferences sharedPreferencesTransaction = getSharedPreferences(username, MODE_PRIVATE);

        //L???y t???ng giao d???ch
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        for(int i=1; i<= totalTransactions; ++i){
            //L???y th??ng tin event
            int eventId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.EVENT_ID, i), 0);
            int eventCategoryId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.EVENT_CATEGORY_ID, i), 0);
            int eventMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.EVENT_MONEY, i), 0);
            String eventDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.EVENT_DATE, i), "");
            String eventNote = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.EVENT_NOTE, i), "");

            //Check xem c?? t???n t???i event k
            if(eventId != 0){
                Event event = new Event(eventId, eventCategoryId, eventMoney, eventDate, eventNote);
                events.add(event);
            }
        }

        eventAdapter = new EventAdapter(this, R.layout.item_transaction, events);
        lv_event.setAdapter(eventAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_EVENT_REQUEST_CODE && resultCode == RESULT_OK){
            Event event = (Event) data.getSerializableExtra(PlanEventAddEventActivity.EVENT_ADD_EXTRA_NAME);
            events.add(event);
            eventAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_event);
        getViews();
        displayEventListView();
        //Kh??ng ?????i ch??? 2 d??ng d?????i cho nhau
        //H??m setSelected item ho???t ?????ng nh?? ??ang ???n v??o menu ???? => trigger event onNavigationItemSelected => bug
        //=> set item ???????c ch???n tr?????c khi set event
        SharedMethods.setNavigationMenu(bnv_menu, R.id.item_plan);
        setEventListener();
    }
}