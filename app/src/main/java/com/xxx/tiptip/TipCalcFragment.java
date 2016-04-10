package com.xxx.tiptip;

import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class TipCalcFragment extends Fragment {

    public static ViewHolder viewHolder;

    public static void updateView() {
        viewHolder.updateView();
    }

    public TipCalcFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //auto open soft keyboard
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tip_calc, container, false);
        viewHolder = new ViewHolder(rootView);
        return rootView;
    }

    void calculate() {
        CalcCore.Calc calc = new CalcCore.Calc();
        calc.execute();
    }

    private class ViewHolder {
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

//            btnTipPlus.getBackground().setColorFilter(getResources()
//                    .getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);

            //TODO: custom button color for enabled & disabled
            //TODO: custom button shape, like circle

            //init EditTexts with default values
            et_bill.setText(format2digit(CalcCore.bill));
            et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
            et_number_of_people.setText(formatInteger(CalcCore.numberOfPeople));

            et_bill.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        CalcCore.bill = new BigDecimal(s.toString());
                        calculate();
                    }
                }
            });
            et_bill.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        et_bill.setText("");
                    } else {
                        et_bill.setText(format2digit(CalcCore.bill));
                    }
                }
            });

            et_percentage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        Log.v("pot before divide", format2digit(CalcCore.percentageOfTip));
                        CalcCore.percentageOfTip = new BigDecimal(s.toString()).multiply(new BigDecimal(0.01));
                        Log.v("pot after divide", format2digit(CalcCore.percentageOfTip));
                        calculate();
                    }
                }
            });
            et_percentage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        et_percentage.setText("");
                    } else {
                        et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
                    }
                }
            });

            et_number_of_people.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        CalcCore.numberOfPeople = new BigDecimal(s.toString());
                        calculate();
                    }
                }
            });
            et_number_of_people.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        et_number_of_people.setText("");
                    } else {
                        et_number_of_people.setText(formatInteger(CalcCore.numberOfPeople));
                    }
                }
            });

            btnTipPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("pot before add", format2digit(CalcCore.percentageOfTip));
                    CalcCore.percentageOfTip = CalcCore.percentageOfTip.add(new BigDecimal(0.005));
                    Log.v("pot after add", format2digit(CalcCore.percentageOfTip));
                    et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
                }
            });

            btnTipMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CalcCore.percentageOfTip.subtract(new BigDecimal(0.005)).compareTo(new BigDecimal(0)) >= 0) {
                    Log.v("pot before minus", format2digit(CalcCore.percentageOfTip));
                        CalcCore.percentageOfTip = CalcCore.percentageOfTip.subtract(new BigDecimal(0.005));
                    Log.v("pot after minus", format2digit(CalcCore.percentageOfTip));
                    } else {
                        CalcCore.percentageOfTip = new BigDecimal(0);
                    }
                    et_percentage.setText(formatPercentage(CalcCore.percentageOfTip));
                }
            });

            btnPeoplePlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CalcCore.numberOfPeople = CalcCore.numberOfPeople.add(new BigDecimal(1));
                    et_number_of_people.setText(formatInteger(CalcCore.numberOfPeople));
                }
            });

            btnPeopleMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CalcCore.numberOfPeople.subtract(new BigDecimal(1)).compareTo(new BigDecimal(1)) >= 0) {
                        CalcCore.numberOfPeople = CalcCore.numberOfPeople.subtract(new BigDecimal(1));
                    } else {
                        CalcCore.numberOfPeople = new BigDecimal(1);
                    }
                    et_number_of_people.setText(formatInteger(CalcCore.numberOfPeople));
                }
            });
        }

        String format2digit(BigDecimal number) {
            return new DecimalFormat("#.00").format(number);
        }

        String formatPercentage(BigDecimal percentage) {
            return new DecimalFormat("#.0").format(percentage.multiply(new BigDecimal(100)));
        }

        String formatInteger(BigDecimal i) {
            return new DecimalFormat("#").format(i);
        }

        public void updateView() {
            tv_TipAmount.setText(format2digit(CalcCore.tipAmount));
            tv_TotalAmount.setText(format2digit(CalcCore.totalAmount));
            tv_AmountPerPerson.setText(format2digit(CalcCore.amountPerPerson));
        }
    }
}
