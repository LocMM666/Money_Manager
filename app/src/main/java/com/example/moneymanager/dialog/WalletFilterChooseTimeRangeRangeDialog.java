package com.example.moneymanager.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.moneymanager.R;
import com.example.moneymanager.methods.SharedMethods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WalletFilterChooseTimeRangeRangeDialog extends AppCompatDialogFragment {
    TextView tv_after, tv_before;
    DatePickerDialog.OnDateSetListener tv_add_before_date_listener, tv_add_after_date_listener;

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

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_wallet_filter_choose_time_range, null);
        tv_after = view.findViewById(R.id.wallet_filter_choose_time_range_after);
        tv_before = view.findViewById(R.id.wallet_filter_choose_time_range_before);
        tv_before.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDatePickerDialog(getActivity(), tv_add_before_date_listener = new DatePickerDialog.OnDateSetListener() {
                    //Hàm này chạy khi ấn OK, trả lại year, month, dayOfMonth user chọn
                    //Gọi lại hàm displayDateToTextView() để hiển thị ngày lên cho user
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        displayDateToTextView(year, month, dayOfMonth, tv_before);
                    }
                });
            }
        });
        tv_after.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                createDatePickerDialog(getActivity(), tv_add_after_date_listener = new DatePickerDialog.OnDateSetListener() {
                    //Hàm này chạy khi ấn OK, trả lại year, month, dayOfMonth user chọn
                    //Gọi lại hàm displayDateToTextView() để hiển thị ngày lên cho user
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        displayDateToTextView(year, month, dayOfMonth, tv_after);
                    }
                });
            }
        });
        builder.setView(view)
                .setNeutralButton("Nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d!=null) {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_NEUTRAL);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isClose = false;
                    String after = tv_after.getText().toString();
                    String before = tv_before.getText().toString();
                    Date d_after = null, d_before = null;
                    try {
                        d_after = new SimpleDateFormat("dd/MM/yyyy").parse(after);
                        d_before = new SimpleDateFormat("dd/MM/yyyy").parse(before);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (!d_after.after(d_before)) {
                        isClose = true;
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("after",after);
                        returnIntent.putExtra("before",before);
                        getActivity().setResult(3,returnIntent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), "Thời gian bắt đầu phải trước thời gian kết thúc!", Toast.LENGTH_LONG).show();
                    }
                    if (isClose) {
                        d.dismiss();
                    }
                }
            });
        }
    }
}
