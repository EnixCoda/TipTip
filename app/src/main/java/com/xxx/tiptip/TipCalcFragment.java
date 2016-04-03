package com.xxx.tiptip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class TipCalcFragment extends Fragment {

    private static final double DEFAULT_BILL = 0, DEFAULT_PERCENTAGE_OF_TIP = 0.15;
    private static final int DEFAULT_NUMBER_OF_PEOPLE = 1;

    public static double tipAmount, totalAmount, amountPerPerson,
            bill = DEFAULT_BILL,
            percentage_of_tip = DEFAULT_PERCENTAGE_OF_TIP;
    private static int number_of_people = DEFAULT_NUMBER_OF_PEOPLE;

    private static View view;

    public TipCalcFragment() {
    }

    public static void updateView() {
        DecimalFormat numberFormat = new DecimalFormat("#.00");

        TextView tv_TipAmount = (TextView) view.findViewById(R.id.textview_tip_value);
        tv_TipAmount.setText(numberFormat.format(tipAmount));

        TextView tv_TotalAmount = (TextView) view.findViewById(R.id.textview_total_amount_value);
        tv_TotalAmount.setText(numberFormat.format(totalAmount));

        TextView tv_AmountPerPerson = (TextView) view.findViewById(R.id.textview_amount_per_person_value);
        tv_AmountPerPerson.setText(numberFormat.format(amountPerPerson));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tip_calc, container, false);

        final Button calcBtn = (Button) view.findViewById(R.id.btn_calculate);
        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalcCore calcCore = new CalcCore();
                calcCore.execute(bill, percentage_of_tip, (double) number_of_people);
            }
        });

        final EditText et_bill = (EditText) view.findViewById(R.id.edittext_bill);
        et_bill.setText(format2(bill));
        et_bill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0)
                    bill = Double.valueOf(s.toString());
            }
        });
        et_bill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_bill.setText("");
                } else {
                    et_bill.setText(format2(bill));
                }
            }
        });

        final EditText et_percentage = (EditText) view.findViewById(R.id.edittext_percentage_of_tip);
        et_percentage.setText(format2(percentage_of_tip * 100) + "%");
        et_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && s.charAt(s.length()-1) != '%')
                    percentage_of_tip = Double.valueOf(s.toString()) / 100;
            }
        });
        et_percentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_percentage.setText("");
                } else {
                    et_percentage.setText(format2(percentage_of_tip * 100) + "%");
                }
            }
        });

        final EditText et_number_of_people = (EditText) view.findViewById(R.id.edittext_number_of_people);
        et_number_of_people.setText(Integer.toString(number_of_people));
        et_number_of_people.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0)
                    number_of_people = Integer.valueOf(s.toString());
            }
        });
        et_number_of_people.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et_number_of_people.setText("");
                } else {
                    et_number_of_people.setText(Integer.toString(number_of_people));
                }
            }
        });

        final Button btnPlus = (Button) view.findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentage_of_tip += 0.005;
                et_percentage.setText(format2(percentage_of_tip * 100) + "%");
            }
        });

        final Button btnMinus = (Button) view.findViewById(R.id.btn_minus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percentage_of_tip -= 0.005;
                et_percentage.setText(format2(percentage_of_tip * 100) + "%");
            }
        });

        return view;
    }

    String format2(double price) {
        return new DecimalFormat("#.00").format(price);
    }
}
