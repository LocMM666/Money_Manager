package com.example.moneymanager.activities;

import android.app.DatePickerDialog;
import android.content.Context;
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
import com.example.moneymanager.models.Transaction;

import java.util.Calendar;

public class AddTransactionActivity extends AppCompatActivity{
    public static int ADD_CATEGORY_CODE = 2000;
    public static String ADD_CATEGORY_ID_EXTRA_NAME = "categoryId";
    ImageButton ib_back;
    TextView tv_add_category, tv_add_date, tv_add_wallet, tv_add_appointment, tv_add_transaction;
    EditText et_add_money, et_add_note, et_add_person, et_add_event;
    ImageView iv_category;
    DatePickerDialog.OnDateSetListener tv_add_date_listener, tv_add_appointment_listener;
    //-1 = chưa chọn nhóm
    int transactionCategoryId = -1;

    private void getViews(){
        ib_back = findViewById(R.id.add_transaction_ib_back);
        tv_add_category = findViewById(R.id.add_transaction_tv_add_category);
        tv_add_date = findViewById(R.id.add_transaction_tv_add_date);
        tv_add_wallet = findViewById(R.id.add_transaction_tv_add_wallet);
        tv_add_appointment = findViewById(R.id.add_transaction_tv_add_appointment);
        et_add_money = findViewById(R.id.add_transaction_et_add_money);
        et_add_note = findViewById(R.id.add_transaction_et_add_note);
        et_add_person = findViewById(R.id.add_transaction_et_add_person);
        et_add_event = findViewById(R.id.add_transaction_et_add_event);
        iv_category = findViewById(R.id.add_transaction_iv_category);
        tv_add_transaction = findViewById(R.id.add_transaction_tv_add_transaction);
    }

    //Đăng ký bộ lắng nghe khi ấn OK chọn ngày cho add date và add appointment
    //Phải đăng ký riêng do sử dụng 2 lịch khác nhau
    private void registerDateListener(){
        tv_add_date_listener = new DatePickerDialog.OnDateSetListener() {
            //Hàm này chạy khi ấn OK, trả lại year, month, dayOfMonth user chọn
            //Gọi lại hàm displayDateToTextView() để hiển thị ngày lên cho user
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                displayDateToTextView(year, month, dayOfMonth, tv_add_date);
            }
        };

        tv_add_appointment_listener = new DatePickerDialog.OnDateSetListener() {
            //Hàm này chạy khi ấn OK, trả lại year, month, dayOfMonth user chọn
            //Gọi hàm displayDateToTextView() để hiển thị ngày lên cho user
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                displayDateToTextView(year, month, dayOfMonth, tv_add_appointment);
            }
        };
    }

    private void displayTodayDate(){
        Calendar calendar = Calendar.getInstance();
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);
        displayDateToTextView(currentYear, currentMonth, currentDayOfMonth, tv_add_date);
    }

    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });
        tv_add_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTransactionCategoryIntent = new Intent(AddTransactionActivity.this, AddTransactionCategoryActivity.class);
                startActivityForResult(addTransactionCategoryIntent, ADD_CATEGORY_CODE);
                overridePendingTransition(0,0);
            }
        });
        tv_add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gọi hàm createDatePickerDialog(context, listener) để tạo lịch
                createDatePickerDialog(AddTransactionActivity.this, tv_add_date_listener);
            }
        });
        tv_add_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gọi hàm createDatePickerDialog(context, listener) để tạo lịch
                createDatePickerDialog(AddTransactionActivity.this, tv_add_appointment_listener);
            }
        });

        tv_add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy người dùng đang đăng nhập
                SharedPreferences sharedPreferencesSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
                String username = sharedPreferencesSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");

                //Lấy file dữ liệu wallet người dùng (Tên file là tên username)
                SharedPreferences sharedPreferencesTransaction = getSharedPreferences(username, MODE_PRIVATE);
                SharedPreferences.Editor transactionEditor = sharedPreferencesTransaction.edit();

                int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);
                int nextTransactionIndex = totalTransactions + 1;
                int walletMoney = sharedPreferencesTransaction.getInt(SharedPrefConstant.WALLET_MONEY, 0);
                int transactionMoney = Integer.parseInt(et_add_money.getText().toString());
                String transactionDate = tv_add_date.getText().toString();
                String transactionNote = et_add_note.getText().toString();

                //Check lỗi
                if(transactionCategoryId == -1){
                    tv_add_category.setText("Hãy chọn nhóm");
                    tv_add_category.setTextColor(getResources().getColor(R.color.red_form_error));
                }
                else{
                    //Cập nhật tiền trong ví
                    //Check giao dịch trừ hay cộng tiền, có thể check id hoặc theo màu ô editText
                    if(et_add_money.getCurrentTextColor() == getResources().getColor(R.color.red_form_error))
                        walletMoney -= transactionMoney;
                    else
                        walletMoney += transactionMoney;


                    //Lưu vào file
                    transactionEditor.putInt(SharedPrefConstant.TRANSACTION_TOTAL, nextTransactionIndex);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_ID, nextTransactionIndex), nextTransactionIndex);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_CATEGORY_ID, nextTransactionIndex), transactionCategoryId);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, nextTransactionIndex), transactionMoney);
                    transactionEditor.putString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, nextTransactionIndex), transactionDate);
                    transactionEditor.putString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_NOTE, nextTransactionIndex), transactionNote);
                    transactionEditor.putInt(SharedPrefConstant.WALLET_MONEY, walletMoney);
                    transactionEditor.apply();
                    //Tạo object transaction mới
                    Transaction transaction = new Transaction(nextTransactionIndex, transactionCategoryId, transactionMoney, transactionDate, transactionNote);
                    //Gửi đến trang wallet qua intent
                    Intent walletIntent = new Intent(AddTransactionActivity.this, WalletActivity.class);
                    walletIntent.putExtra(WalletActivity.TRANSACTION_ADD_EXTRA_NAME, transaction);
                    setResult(RESULT_OK, walletIntent);
                    finish();
                }
            }
        });
    }


    //Tạo DialogDatePicker (cái lịch), nhận tham số là bộ lắng nghe cho từng lịch
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

    private void setMoneyTextColor (int categoryItemId) {
        if (categoryItemId < 20 || categoryItemId == 26 || categoryItemId == 27) {
            et_add_money.setTextColor(getResources().getColor(R.color.red_form_error));
            et_add_money.setBackgroundTintList(getResources().getColorStateList(R.color.design_default_color_error));
        }
        else if (categoryItemId >= 20) {
            et_add_money.setTextColor(getResources().getColor(R.color.green_main));
            et_add_money.setBackgroundTintList(getResources().getColorStateList(R.color.green_main));
        }
    }

    //Hàm chạy khi finish activity đc gọi bằng startActivityForResult()
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CATEGORY_CODE) {
            if (resultCode == RESULT_OK) {
                transactionCategoryId = data.getIntExtra(ADD_CATEGORY_ID_EXTRA_NAME, 0);

                SharedMethods.setCategoryInformation(transactionCategoryId, iv_category, tv_add_category);
                setMoneyTextColor(transactionCategoryId);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        getViews();
        registerDateListener();
        displayTodayDate();
        setEventListener();
    }
}
