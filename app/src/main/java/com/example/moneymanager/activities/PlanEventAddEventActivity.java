package com.example.moneymanager.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moneymanager.R;
import com.example.moneymanager.constant.SharedPrefConstant;
import com.example.moneymanager.methods.SharedMethods;
import com.example.moneymanager.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlanEventAddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private static final int ADD_CATEGORY_REQUEST_CODE = 100;
    public static final String EVENT_ADD_EXTRA_NAME = "event";
    ImageButton ib_back;
    TextView tv_add_event, tv_category, tv_date, tv_wallet;
    ImageView iv_category;
    EditText et_money, et_note;
    int eventCategoryId = -1;

    private void getViews(){
        ib_back = findViewById(R.id.plan_event_add_event_ib_back);
        tv_add_event = findViewById(R.id.plan_event_add_event_tv_add_event);
        tv_category = findViewById(R.id.plan_event_add_event_tv_category);
        tv_date = findViewById(R.id.plan_event_add_event_tv_date);
        tv_wallet = findViewById(R.id.plan_event_add_event_tv_wallet);
        et_money = findViewById(R.id.plan_event_add_event_et_money);
        et_note = findViewById(R.id.plan_event_add_event_et_note);
        iv_category = findViewById(R.id.plan_event_add_event_iv_category);
    }

    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });

        tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTransactionCategoryIntent = new Intent(PlanEventAddEventActivity.this, AddTransactionCategoryActivity.class);
                startActivityForResult(addTransactionCategoryIntent, ADD_CATEGORY_REQUEST_CODE);
                overridePendingTransition(0,0);
            }
        });

        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                int tomorrowDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int tomorrowMonth = calendar.get(Calendar.MONTH);
                int tomorrowYear = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PlanEventAddEventActivity.this, PlanEventAddEventActivity.this, tomorrowYear, tomorrowMonth, tomorrowDayOfMonth);
                datePickerDialog.show();
            }
        });

        tv_add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validInformation = true;
                //Check ng??y nh???p k b???ng ho???c d?????i ng??y hi???n t???i
                String eventDate = tv_date.getText().toString();
                Date dEventDate = null;
                Date dCurrentDate = new Date();

                try {
                    dEventDate = new SimpleDateFormat("dd/MM/yyyy").parse(eventDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(dEventDate.before(dCurrentDate) || dEventDate.compareTo(dCurrentDate) == 0){
                    tv_date.setText("Ng??y nh???p ph???i l???n h??n ng??y hi???n t???i");
                    tv_date.setTextColor(getResources().getColor(R.color.red_form_error));
                    validInformation = false;
                }

                //Check ch??a ch???n category
                if(eventCategoryId == -1){
                    tv_category.setText("H??y ch???n nh??m");
                    tv_category.setTextColor(getResources().getColor(R.color.red_form_error));
                    validInformation = false;
                }


                if(validInformation){
                    //L???y file ng?????i d??ng ??ang ????ng nh???p
                    SharedPreferences sharedPreferencesSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
                    String username = sharedPreferencesSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");

                    //L???y file giao d???ch
                    SharedPreferences sharedPrefConstantTransaction = getSharedPreferences(username, MODE_PRIVATE);
                    SharedPreferences.Editor transactionEditor = sharedPrefConstantTransaction.edit();

                    int eventMoney = Integer.parseInt(et_money.getText().toString());
                    String eventNote = et_note.getText().toString();

                    //L???y t???ng giao d???ch
                    int totalTransaction = sharedPrefConstantTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);


                    //Id giao d???ch ti???p theo = t???ng + 1
                    int nextEventId = totalTransaction + 1;

                    //L??u th??ng tin v??o file
                    transactionEditor.putInt(SharedPrefConstant.TRANSACTION_TOTAL, nextEventId);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.EVENT_ID, nextEventId), nextEventId);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.EVENT_CATEGORY_ID, nextEventId), eventCategoryId);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.EVENT_MONEY, nextEventId), eventMoney);
                    transactionEditor.putString(String.format("%s_%d", SharedPrefConstant.EVENT_DATE, nextEventId), eventDate);
                    transactionEditor.putString(String.format("%s_%d", SharedPrefConstant.EVENT_NOTE, nextEventId), eventNote);
                    transactionEditor.apply();

                    Event event = new Event(nextEventId, eventCategoryId, eventMoney, eventDate, eventNote);
                    Intent planEventIntent = new Intent(PlanEventAddEventActivity.this, PlanEventActivity.class);
                    planEventIntent.putExtra(EVENT_ADD_EXTRA_NAME, event);
                    setResult(RESULT_OK, planEventIntent);
                    finish();
                    overridePendingTransition(0,0);
                }
            }
        });
    }

    //Hi???n th??? ng??y m???c ?????nh l?? ng??y mai
    private void displayTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        int tomorrowDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int tomorrowMonth = calendar.get(Calendar.MONTH);
        int tomorrowYear = calendar.get(Calendar.YEAR);

        tv_date.setText(SharedMethods.formatDate(tomorrowYear, tomorrowMonth, tomorrowDayOfMonth));
    }

    private void setMoneyTextColor (int categoryItemId) {
        if (categoryItemId < 20 || categoryItemId == 26 || categoryItemId == 27) {
            et_money.setTextColor(getResources().getColor(R.color.red_form_error));
            et_money.setBackgroundTintList(getResources().getColorStateList(R.color.design_default_color_error));
        }
        else if (categoryItemId >= 20) {
            et_money.setTextColor(getResources().getColor(R.color.green_main));
            et_money.setBackgroundTintList(getResources().getColorStateList(R.color.green_main));
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        tv_date.setText(SharedMethods.formatDate(year, month, dayOfMonth));
        tv_date.setTextColor(getResources().getColor(R.color.black));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CATEGORY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                eventCategoryId = data.getIntExtra(AddTransactionActivity.ADD_CATEGORY_ID_EXTRA_NAME, 0);

                SharedMethods.setCategoryInformation(eventCategoryId, iv_category, tv_category);
                setMoneyTextColor(eventCategoryId);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_event_add_event);
        getViews();
        setEventListener();
        displayTomorrowDate();
    }


}
