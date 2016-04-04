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

    static ViewHolder viewHolder;

    public TipCalcFragment() {}

    public static void updateView() {
        viewHolder.tv_TipAmount.setText(format2digit(CalcCore.tipAmount));
        viewHolder.tv_TotalAmount.setText(format2digit(CalcCore.totalAmount));
        viewHolder.tv_AmountPerPerson.setText(format2digit(CalcCore.amountPerPerson));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //auto open soft keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tip_calc, container, false);

        viewHolder = new ViewHolder(view);

        viewHolder.et_bill.setText(format2digit(CalcCore.bill));
        viewHolder.et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
        viewHolder.et_number_of_people.setText(String.format("%d", CalcCore.numberOfPeople));

        viewHolder.et_bill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    CalcCore.bill = Double.valueOf(s.toString());
                    calculate();
                }
            }
        });
        viewHolder.et_bill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewHolder.et_bill.setText("");
                } else {
                    viewHolder.et_bill.setText(format2digit(CalcCore.bill));
                }
            }
        });

        viewHolder.et_percentage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    CalcCore.percentageOfTip = Double.valueOf(s.toString()) / 100;
                    calculate();
                }
            }
        });
        viewHolder.et_percentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewHolder.et_percentage.setText("");
                } else {
                    viewHolder.et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
                }
            }
        });

        viewHolder.et_number_of_people.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    CalcCore.numberOfPeople = Integer.valueOf(s.toString());
                    calculate();
                }
            }
        });
        viewHolder.et_number_of_people.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    viewHolder.et_number_of_people.setText("");
                } else {
                    viewHolder.et_number_of_people.setText(String.format("%d", CalcCore.numberOfPeople));
                }
            }
        });

        viewHolder.btnTipPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalcCore.percentageOfTip += 0.005;
                if (viewHolder.et_percentage.hasFocus())
                    viewHolder.et_percentage.setText(String.format("%.2f", CalcCore.percentageOfTip * 100));
                else
                    viewHolder.et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
                calculate();
            }
        });

        viewHolder.btnTipMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalcCore.percentageOfTip =Math.max(CalcCore.percentageOfTip - 0.005, 0);
                if (viewHolder.et_percentage.hasFocus())
                    viewHolder.et_percentage.setText(String.format("%.2f", CalcCore.percentageOfTip * 100));
                else
                    viewHolder.et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
                calculate();
            }
        });

        viewHolder.btnPeoplePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalcCore.numberOfPeople += 1;
                viewHolder.et_number_of_people.setText(String.format("%d", CalcCore.numberOfPeople));
                calculate();
            }
        });

        viewHolder.btnPeopleMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalcCore.numberOfPeople = Math.max(CalcCore.numberOfPeople - 1, 1);
                viewHolder.et_number_of_people.setText(String.format("%d", CalcCore.numberOfPeople));
                calculate();
            }
        });

        calculate();

        return view;
    }

    static String format2digit(double price) {
        return new DecimalFormat("#.00").format(price);
    }

    static String formatPercentage(double percentage) {
        return String.format("%.2f", percentage * 100);
    }

    static void calculate() {
        CalcCore.Calc calc = new CalcCore.Calc();
        calc.execute();
    }

    private static class ViewHolder {
        public TextView tv_TipAmount, tv_TotalAmount, tv_AmountPerPerson;
        public EditText et_bill, et_percentage, et_number_of_people;
        public Button btnTipPlus, btnTipMinus, btnPeoplePlus, btnPeopleMinus;

        ViewHolder(View view) {
            tv_TipAmount = (TextView) view.findViewById(R.id.textview_tip_value);
            tv_TotalAmount = (TextView) view.findViewById(R.id.textview_total_amount_value);
            tv_AmountPerPerson = (TextView) view.findViewById(R.id.textview_amount_per_person_value);
            et_bill = (EditText) view.findViewById(R.id.edittext_bill);
            et_percentage = (EditText) view.findViewById(R.id.edittext_percentage_of_tip);
            et_number_of_people = (EditText) view.findViewById(R.id.edittext_number_of_people);
            btnTipPlus = (Button) view.findViewById(R.id.btn_tip_plus);
            btnTipMinus = (Button) view.findViewById(R.id.btn_tip_minus);
            btnPeoplePlus = (Button) view.findViewById(R.id.btn_people_plus);
            btnPeopleMinus = (Button) view.findViewById(R.id.btn_people_minus);
        }
    }
}
