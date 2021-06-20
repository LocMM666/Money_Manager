package com.example.moneymanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.R;

public class AccountSettingsReportActivity extends AppCompatActivity {
    private ImageView ib_back;
    private EditText et_title, et_content;
    private TextView tv_title_alert, tv_content_alert;
    private Button btn_submit;

    private void getViews(){
        ib_back =  findViewById(R.id.account_settings_report_ib_back);
        et_content = findViewById(R.id.account_settings_report_et_content);
        et_title = findViewById(R.id.account_settings_report_et_title);
        btn_submit = findViewById(R.id.account_settings_report_btn_submit);
        tv_title_alert = findViewById(R.id.account_settings_report_tv_title_alert);
        tv_content_alert = findViewById(R.id.account_settings_report_tv_content_alert);

    }
    private void setEventListener(){
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_title.getText().toString().isEmpty()) {
                    et_title.setBackgroundTintList(getResources().getColorStateList(R.color.red_form_error));
                    tv_title_alert.setText("Không được để tiêu đề trống");
                }

                if (et_content.getText().toString().isEmpty()) {
                    et_content.setBackgroundTintList(getResources().getColorStateList(R.color.red_form_error));
                    tv_content_alert.setText("Không được để mô tả trống");
                }
                if (!et_title.getText().toString().isEmpty() && !et_content.getText().toString().isEmpty()) {
                    Toast.makeText(AccountSettingsReportActivity.this, "Gửi phản hồi thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings_report);
        getViews();
        setEventListener();
    }
}
