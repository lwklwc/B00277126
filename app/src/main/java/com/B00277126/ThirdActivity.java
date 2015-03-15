package com.B00277126;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class ThirdActivity extends ActionBarActivity {

    private TextView tvArrival, tvStay, tvAdults, tvNum16, tvNum5, tvPark, tvAccommdation, tvPet, tvOver21;
    private Button btnChange, btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        initUI();
    }

    private void initUI() {
        final Information information = getIntent().getParcelableExtra(Information.class.getName());

        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save
                try {
                    PreUtils.saveObject(ThirdActivity.this, "Information" ,information);
                    Toast.makeText(ThirdActivity.this, "Save Successed！", Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(ThirdActivity.this, "Save Error！", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                setResult(33);
                ThirdActivity.this.finish();
            }
        });

        btnChange = (Button) findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThirdActivity.this.finish();
            }
        });

        tvArrival = (TextView) findViewById(R.id.tv_arrival);
        tvStay = (TextView) findViewById(R.id.tv_stay);
        tvAdults = (TextView) findViewById(R.id.tv_adults);
        tvNum16 = (TextView) findViewById(R.id.tv_num16);
        tvNum5 = (TextView) findViewById(R.id.tv_num5);
        tvPark = (TextView) findViewById(R.id.tv_park);
        tvAccommdation = (TextView) findViewById(R.id.tv_accommodation);
        tvPet = (TextView) findViewById(R.id.tv_bring_pet);
        tvOver21 = (TextView) findViewById(R.id.tv_over_21);

        tvPark.setText(information.park);
        tvAccommdation.setText(information.accommodation);

        tvArrival.setText(information.arrivalDate);
        tvStay.setText(information.stayDays);
        tvAdults.setText(information.adultsNum);
        tvNum16.setText(information.under16);
        tvNum5.setText(information.under5);

        tvPet.setText(information.pet ? "YES" : "NO");
        tvOver21.setText(information.over21 ? "YES" : "NO");

    }


}
