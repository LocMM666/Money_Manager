package com.example.moneymanager.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;
import com.example.moneymanager.dialog.WalletFilterChooseTimeRangeRangeDialog;
import com.example.moneymanager.methods.SharedMethods;

import java.util.Calendar;

public class WalletFilterChooseTimeRangeActivity extends AppCompatActivity {

    TextView tv_all, tv_after, tv_before, tv_in_range, tv_at;
    ImageButton ib_back;
    public static final int ID_SET_BEFORE_TIME_DIALOG = 1;
    public static final int ID_SET_AFTER_TIME_DIALOG = 2;
//    public static final int ID_SET_IN_RANGE_TIME_DIALOG = 3;
    public static final int ID_SET_AT_TIME_DIALOG = 4;
    DatePickerDialog.OnDateSetListener tv_add_before_date_listener, tv_add_after_date_listener, tv_add_at_date_listener;

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        Dialog dialog = null;
        switch(id) {
            case ID_SET_BEFORE_TIME_DIALOG :
                dialog = createSetBeforeTimeDialog();
                break;
            case ID_SET_AFTER_TIME_DIALOG :
                dialog = createSetAfterTimeDialog();
                break;
            case ID_SET_AT_TIME_DIALOG :
                dialog = createSetAtTimeDialog();
                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
    }

    private void createDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener){
        Calendar calendar = Calendar.getInstance();
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, listener, currentYear, currentMonth, currentDayOfMonth);
        datePickerDialog.show();
    }

    //Hiển thị year, month, dayOfMonth user chọn lên text view
    private void displayDateToTextView(int year, int month, int dayOfMonth, TextView tv){
        String selectedDate = SharedMethods.formatDate(year, month, dayOfMonth);
        tv.setText(selectedDate);
    }

    public Dialog createSetBeforeTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_wallet_filter_choose_time, null);
        TextView et_time = view.findViewById(R.id.wallet_filter_choose_money_range_tv_time);
        et_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDatePickerDialog(WalletFilterChooseTimeRangeActivity.this, tv_add_before_date_listener = new DatePickerDialog.OnDateSetListener() {
                    //Hàm này chạy khi ấn OK, trả lại year, month, dayOfMonth user chọn
                    //Gọi lại hàm displayDateToTextView() để hiển thị ngày lên cho user
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        displayDateToTextView(year, month, dayOfMonth, et_time);
                    }
                });
            }
        });
        builder.setView(view)
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",et_time.getText().toString());
                        setResult(1,returnIntent);
                        finish();

                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public Dialog createSetAfterTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_wallet_filter_choose_time, null);
        TextView et_time = view.findViewById(R.id.wallet_filter_choose_money_range_tv_time);
        et_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDatePickerDialog(WalletFilterChooseTimeRangeActivity.this, tv_add_after_date_listener = new DatePickerDialog.OnDateSetListener() {
                    //Hàm này chạy khi ấn OK, trả lại year, month, dayOfMonth user chọn
                    //Gọi lại hàm displayDateToTextView() để hiển thị ngày lên cho user
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        displayDateToTextView(year, month, dayOfMonth, et_time);
                    }
                });
            }
        });
        builder.setView(view)
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",et_time.getText().toString());
                        setResult(2,returnIntent);
                        finish();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    public Dialog createSetAtTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_wallet_filter_choose_time, null);
        TextView et_time = view.findViewById(R.id.wallet_filter_choose_money_range_tv_time);
        et_time.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDatePickerDialog(WalletFilterChooseTimeRangeActivity.this, tv_add_at_date_listener = new DatePickerDialog.OnDateSetListener() {
                    //Hàm này chạy khi ấn OK, trả lại year, month, dayOfMonth user chọn
                    //Gọi lại hàm displayDateToTextView() để hiển thị ngày lên cho user
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        displayDateToTextView(year, month, dayOfMonth, et_time);
                    }
                });
            }
        });
        builder.setView(view)
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("result",et_time.getText().toString());
                        setResult(4,returnIntent);
                        finish();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
    public void getView() {
        tv_all = findViewById(R.id.wallet_filter_choose_time_range_tv_all);
        tv_after = findViewById(R.id.wallet_filter_choose_time_range_tv_after);
        tv_before = findViewById(R.id.wallet_filter_choose_time_range_tv_before);
        tv_in_range = findViewById(R.id.wallet_filter_choose_time_range_tv_in_range);
        tv_at = findViewById(R.id.wallet_filter_choose_time_range_tv_at);
        ib_back = findViewById(R.id.wallet_filter_choose_time_range_ib_back);
    }
    public void setEventListener() {
        tv_all.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(0,returnIntent);
                finish();
            }
        });
        tv_after.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(ID_SET_AFTER_TIME_DIALOG);
            }
        });
        tv_before.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(ID_SET_BEFORE_TIME_DIALOG);
            }
        });
        tv_in_range.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                WalletFilterChooseTimeRangeRangeDialog walletFilterChooseTimeRangeRangeDialog = new WalletFilterChooseTimeRangeRangeDialog();
                walletFilterChooseTimeRangeRangeDialog.show(getSupportFragmentManager(),null);
            }
        });
        tv_at.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDialog(ID_SET_AT_TIME_DIALOG);
            }
        });
        ib_back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 finish();
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_filter_choose_time_range);
        getView();
        setEventListener();
    }
}
