package com.B00277126;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends ActionBarActivity {


    private Spinner parkSpinner;
    private Spinner accSpinner;
    private TextView tvTotalPrice;
    private Spinner  spStay, spAdults, spOver16, spOver5;
    private EditText etArrival;
    private CheckBox cbPets, cbOver21;
    private int[] price = {35, 25, 15};
    private Spinner spYear;
    private Spinner spMonth;
    private Spinner spDay;
    private Information mInformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 33) {
            resetUI();
        }
    }

    private void initView() {
        parkSpinner = (Spinner) findViewById(R.id.spinner_park);
        accSpinner = (Spinner) findViewById(R.id.spinner_acco);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total);

        ArrayAdapter<CharSequence> parkAdapter = ArrayAdapter.createFromResource(this,
                R.array.parks, android.R.layout.simple_spinner_item);

        parkSpinner.setAdapter(parkAdapter);

        ArrayAdapter<CharSequence> accoAdapter = ArrayAdapter.createFromResource(this,
                R.array.accommodation, android.R.layout.simple_spinner_item);
        accSpinner.setAdapter(accoAdapter);


        ArrayAdapter<CharSequence> numAdapter = ArrayAdapter.createFromResource(this,
                R.array.numbers, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.month, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.day, android.R.layout.simple_spinner_item);

//        etArrival = (EditText) findViewById(R.id.et_arrival);
        spYear = (Spinner) findViewById(R.id.spinner_year);
        spYear.setAdapter(yearAdapter);
        spMonth = (Spinner) findViewById(R.id.spinner_month);
        spMonth.setAdapter(monthAdapter);
        spDay = (Spinner) findViewById(R.id.spinner_day);
        spDay.setAdapter(dayAdapter);
        spStay = (Spinner) findViewById(R.id.spinner_stay);
        spStay.setAdapter(numAdapter);
        spAdults = (Spinner) findViewById(R.id.spinner_adults);
        spAdults.setAdapter(numAdapter);
        spOver16 = (Spinner) findViewById(R.id.spinner_over16);
        spOver16.setAdapter(numAdapter);
        spOver5 = (Spinner) findViewById(R.id.spinner_over5);
        spOver5.setAdapter(numAdapter);
        cbPets = (CheckBox) findViewById(R.id.checkbox_pets);
        cbOver21 = (CheckBox) findViewById(R.id.checkbox_over21);
    }


    //TOTAL PRICE onclick
    public void calculate(View v) {
        try {
            int total =  getInformation().calculateTotalPrice();
           tvTotalPrice.setText(total + "￡");
        } catch (NullPointerException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //CHECK AVAILABILITY onclick
    public void check(View v) {
        Intent intent = new Intent(this, ThirdActivity.class);
        try {
            if (mInformation == null) {
                mInformation = getInformation();
            }
            mInformation.pet = cbPets.isChecked();
            mInformation.over21 = cbOver21.isChecked();
            intent.putExtra(Information.class.getName(), (android.os.Parcelable) mInformation);
            startActivityForResult(intent, 0);
        } catch (NullPointerException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public Information getInformation() {
        int parkPos = checkParkSelected();
        int accPos = checkAccSelected();

        Information information = checkInformation();
        SpinnerAdapter accSpAdapter = accSpinner.getAdapter();
        SpinnerAdapter parkSpAdapter = parkSpinner.getAdapter();

        information.accommodationPrice = price[accPos - 1];
        information.park = (String) parkSpAdapter.getItem(parkPos);
        information.accommodation = (String) accSpAdapter.getItem(accPos);

        return information;
    }

    private Information checkInformation() {
//        String arrival = etArrival.getText().toString().trim();
        String year = (String) spYear.getSelectedItem();
        String month = (String) spMonth.getSelectedItem();
        String day = (String) spDay.getSelectedItem();
        String date =year+"-"+month+"-"+day;
        String stay = String.valueOf(checkStaySelected());
        String adults = String.valueOf(checkAdultsSelected());
        String num16 = String.valueOf(checkOver16Selected());
        String num5 = String.valueOf(checkOver5Selected());

        if (TextUtils.isEmpty(stay) || TextUtils.isEmpty(adults) && TextUtils.isEmpty(num16) && TextUtils.isEmpty(num5)) {
            throw new NullPointerException("please enter the complete information");
        }

        if (TextUtils.isEmpty(num16)) {
            num16 = "NONE";
        }
        if (TextUtils.isEmpty(num5)) {
            num5 = "NONE";
        }
        if (TextUtils.isEmpty(adults)) {
            adults = "NONE";
        }

        if (mInformation == null)
            mInformation = new Information();

        mInformation.arrivalDate = date;
        mInformation.adultsNum = adults;
        mInformation.stayDays = stay;
        mInformation.under16 = num16;
        mInformation.under5 = num5;
        return mInformation;
    }

    private int checkParkSelected() {
        int parkPos = parkSpinner.getSelectedItemPosition();
        if (parkPos == 0) {
            throw new NullPointerException("Please select a park");
        }
        return parkPos;
    }

    private int checkAccSelected() {
        int accPos = accSpinner.getSelectedItemPosition();
        if (accPos == 0) {
            throw new NullPointerException("Please select a type of accommodation");
        }
        return accPos;
    }

    private int checkStaySelected() {
        int numPOS = spStay.getSelectedItemPosition();
        return numPOS + 1;
    }


    private int checkAdultsSelected() {
        int numPOS = spAdults.getSelectedItemPosition();
        return numPOS + 1;
    }

    private int checkOver16Selected() {
        int numPOS = spOver16.getSelectedItemPosition();
        return numPOS + 1;
    }

    private int checkOver5Selected() {
        int numPOS = spOver5.getSelectedItemPosition();
        return numPOS + 1;
    }




    public void resetUI(){
        parkSpinner.setSelection(0);
        accSpinner.setSelection(0);
//        etArrival.setText("");
        spStay.setSelection(0);
        spAdults.setSelection(0);
        spOver16.setSelection(0);
        spOver5.setSelection(0);
        cbPets.setChecked(false);
        cbOver21.setChecked(false);
        mInformation = null;
    }



}
