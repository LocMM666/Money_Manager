package com.example.moneymanager.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.moneymanager.R;
import com.example.moneymanager.adapter.TransactionAdapter;
import com.example.moneymanager.constant.SharedPrefConstant;
import com.example.moneymanager.methods.SharedMethods;
import com.example.moneymanager.models.Event;
import com.example.moneymanager.models.Transaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WalletActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    public static int TRANSACTION_ADD_REQUEST_CODE = 123;
    public static int TRANSACTION_FILTER_REQUEST_CODE = 200;
    BottomNavigationView bnv_menu;
    FloatingActionButton fab_add_transaction;
    TextView tv_wallet_name, tv_wallet_money;
    ImageView iv_calendar, iv_filter;
    ListView lv_transaction;
    ArrayList<Transaction> transactions = new ArrayList<>();
    TransactionAdapter transactionAdapter;

    private void getViews(){
        bnv_menu = findViewById(R.id.wallet_bnv_menu);
        fab_add_transaction = findViewById(R.id.wallet_fab_add_transaction);
        tv_wallet_name = findViewById(R.id.wallet_tv_wallet_name);
        iv_calendar = findViewById(R.id.wallet_iv_calendar);
        iv_filter = findViewById(R.id.wallet_iv_filter);
        lv_transaction = findViewById(R.id.wallet_lv_transaction);
        tv_wallet_money = findViewById(R.id.wallet_tv_money);
    }

    private void displayUserInformation(){
        //L???y file ng?????i d??ng ??ang ????ng nh???p
        SharedPreferences sharedPreferenceSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
        //Set t??n v??
        String walletName = sharedPreferenceSigningIn.getString(SharedPrefConstant.SIGNING_IN_WALLET_NAME, "");
        tv_wallet_name.setText(walletName);

        //Set s??? ti???n trong v??
        String username = sharedPreferenceSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");
        SharedPreferences sharedPreferencesTransaction = getSharedPreferences(username, MODE_PRIVATE);
        int money = sharedPreferencesTransaction.getInt(SharedPrefConstant.WALLET_MONEY, 0);

        //Set m??u ti???n
        if(money > 0)
            tv_wallet_money.setTextColor(getResources().getColor(R.color.green_main));
        else
            tv_wallet_money.setTextColor(getResources().getColor(R.color.red_form_error));

        //Format ti???n t??? 100000 th??nh 100.000 ??
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String formattedMoney = decimalFormat.format(money);

        tv_wallet_money.setText(formattedMoney + " ??");
    }

    private void displayTransactionListView(){
        //L???y file ng?????i d??ng ??ang ????ng nh???p
        SharedPreferences sharedPreferenceSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
        String username = sharedPreferenceSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");
        //L???y file l??u th??ng tin giao d???ch
        SharedPreferences sharedPreferencesTransaction = getSharedPreferences(username, MODE_PRIVATE);

        //L???y t???ng giao d???ch
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        for(int i = 1; i <= totalTransactions; ++i){
            int transactionId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_ID, i), 0);
            int transactionCategoryId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_CATEGORY_ID, i), 0);
            int transactionMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, i), 0);
            String transactionDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, i), "");
            String transactionNote = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_NOTE, i), "");
            String transactionWith = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_WITH, i), "");

            //Check xem c?? t???n t???i transaction k
            if(transactionId != 0){
                Transaction transaction = new Transaction(transactionId, transactionCategoryId, transactionMoney, transactionDate, transactionNote, transactionWith);
                transactions.add(transaction);
            }
        }
        transactionAdapter = new TransactionAdapter(this, R.layout.item_transaction, transactions);
        lv_transaction.setAdapter(transactionAdapter);
    }

    private void setEventListener(){
        //H??m n??y ???????c g???i m???i khi c?? item tr??n menu ???????c ???n
        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_statistic:
                        Intent statisticIntent = new Intent(WalletActivity.this, StatisticActivity.class);
                        startActivity(statisticIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.item_plan:
                        Intent planIntent = new Intent(WalletActivity.this, PlanActivity.class);
                        startActivity(planIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.item_account:
                        Intent accountIntent = new Intent(WalletActivity.this, AccountActivity.class);
                        startActivity(accountIntent);
                        overridePendingTransition(0,0);
                        finish();
                        break;
                }
                return false;
            }
        });

        iv_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        iv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent walletFilterIntent = new Intent(WalletActivity.this, WalletFilterActivity.class);
                startActivityForResult(walletFilterIntent, TRANSACTION_FILTER_REQUEST_CODE);
                overridePendingTransition(0,0);
            }
        });

        fab_add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTransactionIntent = new Intent(WalletActivity.this, AddTransactionActivity.class);
                startActivityForResult(addTransactionIntent, TRANSACTION_ADD_REQUEST_CODE);
                overridePendingTransition(0,0);
            }
        });
    }

    public void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, currentYear, currentMonth, currentDayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String selectedDate = SharedMethods.formatDate(year, month, dayOfMonth);
        SharedPreferences sharedPreferencesSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
        String username = sharedPreferencesSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");
        SharedPreferences sharedPreferencesTransaction = getSharedPreferences(username, MODE_PRIVATE);
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        //Xo?? h???t item trong list hi???n t???i ????? hi???n list theo ng??y
        transactions.clear();

        for(int i = 1; i <= totalTransactions; ++i){
            String transactionDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, i), "");
            if(selectedDate.equals(transactionDate)){
                int transactionId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_ID, i), 0);
                int transactionCategoryId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_CATEGORY_ID, i), 0);
                int transactionMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, i), 0);
                String transactionNote = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_NOTE, i), "");
                String transactionWith = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_NOTE, i), "");

                transactions.add(new Transaction(transactionId, transactionCategoryId, transactionMoney, transactionDate, transactionNote, transactionWith));
            }
        }
        transactionAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == TRANSACTION_ADD_REQUEST_CODE && resultCode == RESULT_OK){
            Transaction transaction = (Transaction) data.getSerializableExtra(AddTransactionActivity.TRANSACTION_ADD_EXTRA_NAME);
            transactions.add(transaction);
            //???? khai b??o adapter ??? displayTransactionListView()
            transactionAdapter.notifyDataSetChanged(); //Update listview
        }
        
        if(requestCode == TRANSACTION_FILTER_REQUEST_CODE && resultCode == RESULT_OK){
            String category = data.getStringExtra(WalletFilterActivity.CATEGORY_EXTRA_KEY);
            String note = data.getStringExtra(WalletFilterActivity.NOTE_EXTRA_KEY);
            String with = data.getStringExtra(WalletFilterActivity.WITH_EXTRA_KEY);
            String exactTime = data.getStringExtra(WalletFilterActivity.EXACT_TIME_EXTRA_KEY);
            String afterTime = data.getStringExtra(WalletFilterActivity.AFTER_TIME_EXTRA_KEY);
            String beforeTime = data.getStringExtra(WalletFilterActivity.BEFORE_TIME_EXTRA_KEY);
            String exactMoney = data.getStringExtra(WalletFilterActivity.EXACT_MONEY_EXTRA_KEY);
            String lessThanMoney = data.getStringExtra(WalletFilterActivity.LESS_THAN_MONEY_EXTRA_KEY);
            String moreThanMoney = data.getStringExtra(WalletFilterActivity.MORE_THAN_MONEY_EXTRA_KEY);

            Log.e("test", category);
            Log.e("test", with);
            transactionFilter(category, note, with, exactTime, afterTime, beforeTime, exactMoney, lessThanMoney, moreThanMoney);
        }
    }

    private void transactionFilter(String category, String note, String with, String exactTime, String afterTime, String beforeTime, String exactMoney, String lessThanMoney, String moreThanMoney){
        //L???y file th??ng tin ng?????i d??ng ??ang ????ng nh???p
        SharedPreferences sharedPreferencesSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
        //L???y username ??ang ????ng nh???p
        String username = sharedPreferencesSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");
        //L???y file giao d???ch
        SharedPreferences sharedPreferencesTransaction = getSharedPreferences(username, MODE_PRIVATE);

        //4 list l???y id c???a giao d???ch th???a th???a m??n t???ng c??i filter
        ArrayList<Integer> moneyFilterIds = new ArrayList<>();
        ArrayList<Integer> timeFilterIds = new ArrayList<>();
        ArrayList<Integer> noteFilterIds = new ArrayList<>();
        ArrayList<Integer> categoryFilterIds = new ArrayList<>();
        ArrayList<Integer> withFilterIds = new ArrayList<>();

        //List id giao d???ch th???a m??n m???i id
        //Set d??ng ????? l??u d??? li???u k tr??ng l???p
        Set<Integer> finalFilterIds = new HashSet<>();

        moneyFilterIds = transactionMoneyFilter(exactMoney, moreThanMoney, lessThanMoney, sharedPreferencesTransaction);
        timeFilterIds = transactionTimeFilter(exactTime, afterTime, beforeTime, sharedPreferencesTransaction);
        noteFilterIds = transactionNoteFilter(note, sharedPreferencesTransaction);
        categoryFilterIds = transactionCategoryFilter(category, sharedPreferencesTransaction);
        withFilterIds = transactionWithFilter(with, sharedPreferencesTransaction);

        //Add h???t c??c id th???a m??n t???ng tr?????ng h???p v??o set, do set k l??u gi?? tr??? tr??ng n??n s??? k b??? tr??ng id
        finalFilterIds.addAll(moneyFilterIds);
        finalFilterIds.addAll(timeFilterIds);
        finalFilterIds.addAll(noteFilterIds);
        finalFilterIds.addAll(categoryFilterIds);
        finalFilterIds.addAll(withFilterIds);

        Log.e("testFinalID1", finalFilterIds + "");

        //????? l???y c??c id th???a m??n t???t c??? filter thay v?? t???ng tr?????ng h???p filter
        //D??ng h??m retainAll ????? lo???i b??? c??c gi?? tr??? n??o k tr??ng l???p
        //VD: finalFilterIds = [1,2,3], moneyFilterIds = [1,3]
        //=> sau khi ch???y h??m retainAll th?? finalFilterIds = [1,3], b??? 2 do moneyFilterIds k c?? 2
        //=> sau khi ch???y 4 l???n th?? c?? ??c Set id th???a m??n ?????ng th???i c??? 4 ??i???u ki???n filter
        finalFilterIds.retainAll(moneyFilterIds);
        finalFilterIds.retainAll(timeFilterIds);
        finalFilterIds.retainAll(noteFilterIds);
        finalFilterIds.retainAll(categoryFilterIds);
        finalFilterIds.retainAll(withFilterIds);

        //Log ????? test
        Log.e("testMoneyID", moneyFilterIds + "");
        Log.e("testTimeID", timeFilterIds + "");
        Log.e("testNoteID", noteFilterIds + "");
        Log.e("testCategoryID", categoryFilterIds + "");
        Log.e("testWithID", categoryFilterIds + "");
        Log.e("testFinalID2", finalFilterIds + "");

        //X??a list giao d???ch c?? ????? add giao d???ch th???a m??n filter ph??a d?????i
        transactions.clear();

        //Add giao d???ch th???a m??n filter v??o list
        for(Integer id : finalFilterIds){
            int transactionCategoryId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_CATEGORY_ID, id), 0);
            int transactionMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, id), 0);
            String transactionDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, id), "");
            String transactionNote = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_NOTE, id), "");
            String transactionWith = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_WITH, id), "");
            transactions.add(new Transaction(id, transactionCategoryId, transactionMoney, transactionDate, transactionNote, transactionWith));
        }
        //Update listview
        transactionAdapter.notifyDataSetChanged();
    }

    private ArrayList<Integer> transactionMoneyFilter(String exactMoney, String moreThanMoney, String lessThanMoney, SharedPreferences sharedPreferencesTransaction){
        final String ALL = "all";
        final String EXACT = "exact";
        final String LESS_THAN = "lessThan";
        final String MORE_THAN = "moreThan";
        final String IN_RANGE = "inRange";

        ArrayList<Integer> ids = new ArrayList<>();
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        //Xem ng?????i d??ng ch???n t???t c???, ch??nh x??c, l???n h??n, nh??? h??n hay trong kho???ng
        //V?? t??? trang WalletFilterActivity ch??? tr??? v??? gi?? tr??? c???a t???ng filter n??n ph???i check ??? ????y
        String moneyFilterCase = "";
        if(exactMoney.isEmpty() && lessThanMoney.isEmpty() && moreThanMoney.isEmpty())
            moneyFilterCase = ALL;
        else if(exactMoney.isEmpty() == false)
            moneyFilterCase = EXACT;
        else{
            if(moreThanMoney.isEmpty() == false && lessThanMoney.isEmpty() == false)
                moneyFilterCase = IN_RANGE;
            else if(moreThanMoney.isEmpty() == false)
                moneyFilterCase = MORE_THAN;
            else if(lessThanMoney.isEmpty() == false)
                moneyFilterCase = LESS_THAN;
        }

        //Add id giao d???ch th???a m??n v??o list theo tr?????ng h???p filer
        switch (moneyFilterCase){
            //Add h???t id
            case ALL:
                for (int i = 1; i <= totalTransactions; ++i)
                    ids.add(i);
                break;
            //Add nh???ng id giao d???ch c?? s??? ti???n = s??? ti???n filter
            case EXACT:
                for (int i = 1; i <= totalTransactions; ++i){
                    int transactionMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, i), 0);
                    if(Integer.parseInt(exactMoney) == transactionMoney)
                        ids.add(i);
                }
                break;
            //Add nh???ng id giao d???ch c?? s??? ti???n >= s??? ti???n filter
            case MORE_THAN:
                for (int i = 1; i <= totalTransactions; ++i){
                    int transactionMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, i), 0);
                    if(transactionMoney >= Integer.parseInt(moreThanMoney))
                        ids.add(i);
                }
                break;
            //Add nh???ng id giao d???ch c?? s??? ti???n <= s??? ti???n filter
            case LESS_THAN:
                for (int i = 1; i <= totalTransactions; ++i){
                    int transactionMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, i), 0);
                    if(transactionMoney <= Integer.parseInt(lessThanMoney))
                        ids.add(i);
                }
                break;
            //Add nh???ng id giao d???ch c?? s??? ti???n trong kho???ng filter a-b, c?? t??nh 2 ?????u a, b
            case IN_RANGE:
                for (int i = 1; i <= totalTransactions; ++i){
                    int transactionMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, i), 0);
                    if(transactionMoney >= Integer.parseInt(moreThanMoney) && transactionMoney <= Integer.parseInt(lessThanMoney))
                        ids.add(i);
                }
                break;
        }
        return ids;
    }

    private ArrayList<Integer> transactionTimeFilter(String exactTime, String afterTime, String beforeTime, SharedPreferences sharedPreferencesTransaction){
        final String ALL = "all";
        final String EXACT = "exact";
        final String BEFORE = "before";
        final String AFTER = "moreThan";
        final String IN_RANGE = "inRange";

        ArrayList<Integer> ids = new ArrayList<>();
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        //T????ng t??? tr?????ng h???p filterMoney
        String timeFilterCase = "";
        if(exactTime.isEmpty() && afterTime.isEmpty() && beforeTime.isEmpty())
            timeFilterCase = ALL;
        else if(exactTime.isEmpty() == false)
            timeFilterCase = EXACT;
        else{
            if(beforeTime.isEmpty() == false && afterTime.isEmpty() == false)
                timeFilterCase = IN_RANGE;
            else if(afterTime.isEmpty() == false)
                timeFilterCase = AFTER;
            else if(beforeTime.isEmpty() == false)
                timeFilterCase = BEFORE;
        }

        switch (timeFilterCase){
            case ALL:
                for(int i = 1; i <= totalTransactions; ++i)
                    ids.add(i);
                break;
            case EXACT:
                for(int i = 1; i <= totalTransactions; ++i){
                    String transactionDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, i), "");
                    if(transactionDate.equals(exactTime))
                        ids.add(i);
                }
                break;
            case BEFORE:
                for(int i = 1; i <= totalTransactions; ++i){
                    String transactionDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, i), "");
                    Date dTransactionDate = null, dBeforeDate = null;
                    try {
                        dTransactionDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactionDate);
                        dBeforeDate = new SimpleDateFormat("dd/MM/yyyy").parse(beforeTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(dTransactionDate.before(dBeforeDate) || dTransactionDate.compareTo(dBeforeDate) == 0)
                        ids.add(i);
                }
                break;
            case AFTER:
                for(int i = 1; i <= totalTransactions; ++i){
                    String transactionDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, i), "");
                    Date dTransactionDate = null, dAfterDate = null;
                    try {
                        dTransactionDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactionDate);
                        dAfterDate = new SimpleDateFormat("dd/MM/yyyy").parse(afterTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if(dTransactionDate.after(dAfterDate) || dTransactionDate.compareTo(dAfterDate) == 0)
                        ids.add(i);
                }
                break;
            case IN_RANGE:
                for(int i = 1; i <= totalTransactions; ++i){
                    String transactionDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, i), "");
                    Date dTransactionDate = null, dAfterDate = null, dBeforeDate = null;
                    try {
                        dTransactionDate = new SimpleDateFormat("dd/MM/yyyy").parse(transactionDate);
                        dAfterDate = new SimpleDateFormat("dd/MM/yyyy").parse(afterTime);
                        dBeforeDate = new SimpleDateFormat("dd/MM/yyyy").parse(beforeTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if((dTransactionDate.after(dAfterDate) && dTransactionDate.before(dBeforeDate)) || dTransactionDate.compareTo(dAfterDate) == 0 || dTransactionDate.compareTo(dBeforeDate) == 0)
                        ids.add(i);
                }
                break;
        }

        return ids;
    }

    private ArrayList<Integer> transactionNoteFilter(String note, SharedPreferences sharedPreferencesTransaction){
        ArrayList<Integer> ids = new ArrayList<>();
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        //Add nh???ng id giao d???ch c?? note = note filter
        for(int i = 1; i <= totalTransactions; ++i){
            String transactionNote = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_NOTE, i), "");
            if(note.isEmpty())
                ids.add(i);
            else if(transactionNote.equals(note))
                ids.add(i);
        }

        return ids;
    }

    private ArrayList<Integer> transactionCategoryFilter(String category, SharedPreferences sharedPreferencesTransaction){
        ArrayList<Integer> ids = new ArrayList<>();
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        switch (category){
            //Add h???t id
            case WalletFilterActivity.ALL_CATEGORY:
                for(int i = 1; i <= totalTransactions; ++i){
                    ids.add(i);
                }
                break;
            //Add nh???ng id giao d???ch l?? kho???n thu
            case WalletFilterActivity.INCOME_CATEGORY:
                for(int i = 1; i <= totalTransactions; ++i){
                    int categoryId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_CATEGORY_ID, i), 0);
                    if (categoryId >= 20 && categoryId != 26 && categoryId != 27)
                        ids.add(i);
                }
                break;
            //Add nh???ng id giao d???ch l?? kho???n chi
            case WalletFilterActivity.EXPENSE_CATEGORY:
                for(int i = 1; i <= totalTransactions; ++i){
                    int categoryId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_CATEGORY_ID, i), 0);
                    if (categoryId < 20 || categoryId == 26 || categoryId == 27)
                        ids.add(i);
                }
                break;
        }
        return ids;
    }

    private ArrayList<Integer> transactionWithFilter(String with, SharedPreferences sharedPreferencesTransaction){
        ArrayList<Integer> ids = new ArrayList<>();
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        //Add nh???ng id giao d???ch c?? with = with filter
        for(int i = 1; i <= totalTransactions; ++i){
            String transactionWith = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_WITH, i), "");
            if(with.isEmpty())
                ids.add(i);
            else if(transactionWith.equals(with))
                ids.add(i);
        }

        return ids;
    }

    //Check xem event ???? qua ch??a
    //N???u ???? qua th?? cho event ???? th??nh transaction
    private void checkEventPassed(){
        //L???y file ng?????i d??ng ??ang ????ng nh???p
        SharedPreferences sharedPreferenceSigningIn = getSharedPreferences(SharedPrefConstant.SIGNING_IN, MODE_PRIVATE);
        String username = sharedPreferenceSigningIn.getString(SharedPrefConstant.SIGNING_IN_USERNAME, "");
        //L???y file l??u th??ng tin giao d???ch
        SharedPreferences sharedPreferencesTransaction = getSharedPreferences(username, MODE_PRIVATE);
        SharedPreferences.Editor transactionEditor = sharedPreferencesTransaction.edit();

        //L???y t???ng giao d???ch
        int totalTransactions = sharedPreferencesTransaction.getInt(SharedPrefConstant.TRANSACTION_TOTAL, 0);

        //L???y ng??y h??m nay ????? check xem c?? event n??o ???? qua kh??ng
        Date dCurrentDate = new Date();
        Date dEventDate = null;

        for(int i = 1; i <=totalTransactions; ++i){
            int eventId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.EVENT_ID, i), 0);

            //N???u t???n t???i event
            if(eventId != 0){
                int eventCategoryId = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.EVENT_CATEGORY_ID, i), 0);
                int eventMoney = sharedPreferencesTransaction.getInt(String.format("%s_%d", SharedPrefConstant.EVENT_MONEY, i), 0);
                String eventDate = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.EVENT_DATE, i), "");
                String eventNote = sharedPreferencesTransaction.getString(String.format("%s_%d", SharedPrefConstant.EVENT_NOTE, i), "");
                int walletMoney = sharedPreferencesTransaction.getInt(SharedPrefConstant.WALLET_MONEY, 0);
                //Chuy???n ng??y String v??? d???ng Date
                try {
                    dEventDate = new SimpleDateFormat("dd/MM/yyyy").parse(eventDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //L?? ng??y h??m nay ho???c ???? qua
                if(dEventDate.before(dCurrentDate) || dEventDate.compareTo(dCurrentDate) == 0){
                    //S???a event th??nh transaction, g???m 2 b?????c xo?? event v?? th??m transaction
                    //K s???a tr???c ti???p ???????c do key transaction v?? event kh??c nhau

                    //Xo?? event
                    transactionEditor.remove(String.format("%s_%d", SharedPrefConstant.EVENT_ID, i));
                    transactionEditor.remove(String.format("%s_%d", SharedPrefConstant.EVENT_CATEGORY_ID, i));
                    transactionEditor.remove(String.format("%s_%d", SharedPrefConstant.EVENT_MONEY, i));
                    transactionEditor.remove(String.format("%s_%d", SharedPrefConstant.EVENT_DATE, i));
                    transactionEditor.remove(String.format("%s_%d", SharedPrefConstant.EVENT_NOTE, i));
                    transactionEditor.apply(); //L??u file

                    //Th??m transaction
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_ID, i), eventId);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_CATEGORY_ID, i), eventCategoryId);
                    transactionEditor.putInt(String.format("%s_%d", SharedPrefConstant.TRANSACTION_MONEY, i), eventMoney);
                    transactionEditor.putString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_DATE, i), eventDate);
                    transactionEditor.putString(String.format("%s_%d", SharedPrefConstant.TRANSACTION_NOTE, i), eventNote);
                    //K c???n with v?? event k c?? v???i ai

                    //C???p nh???t ti???n trong v??
                    if (eventCategoryId < 20 || eventCategoryId == 26 || eventCategoryId == 27)
                        walletMoney -= eventMoney;
                    else if (eventCategoryId >= 20)
                        walletMoney += eventMoney;

                    transactionEditor.putInt(SharedPrefConstant.WALLET_MONEY, walletMoney);
                    transactionEditor.apply(); //L??u file
                }
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        getViews();
        checkEventPassed();
        displayUserInformation();
        displayTransactionListView();
        //Kh??ng ?????i ch??? 2 d??ng d?????i cho nhau
        //H??m setSelected item ho???t ?????ng nh?? ??ang ???n v??o menu ???? => trigger event onNavigationItemSelected => bug
        //=> set item ???????c ch???n tr?????c khi set event
        SharedMethods.setNavigationMenu(bnv_menu, R.id.item_wallet);
        setEventListener();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Update l???i th??ng tin ng?????i d??ng m???i khi restart
        displayUserInformation();
    }
}
