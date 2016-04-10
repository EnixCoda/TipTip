package com.xxx.tiptip;

import android.os.AsyncTask;

import java.math.BigDecimal;

public class CalcCore {

    private static final BigDecimal DEFAULT_BILL = new BigDecimal(0), DEFAULT_PERCENTAGE_OF_TIP = new BigDecimal(0.15);
    private static final BigDecimal DEFAULT_NUMBER_OF_PEOPLE =  new BigDecimal(1);

    public static BigDecimal tipAmount, totalAmount, amountPerPerson;
    public static BigDecimal bill = DEFAULT_BILL,
                         percentageOfTip = DEFAULT_PERCENTAGE_OF_TIP;
    public static BigDecimal    numberOfPeople = DEFAULT_NUMBER_OF_PEOPLE;

    public static class Calc extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            tipAmount = percentageOfTip.multiply(bill);
            totalAmount = tipAmount.add(bill);
            amountPerPerson = totalAmount.divide(numberOfPeople, BigDecimal.ROUND_UP);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TipCalcFragment.updateView();
        }
    }
}
