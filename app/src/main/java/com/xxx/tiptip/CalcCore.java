package com.xxx.tiptip;

import android.os.AsyncTask;

public class CalcCore {

    static final double DEFAULT_BILL = 0, DEFAULT_PERCENTAGE_OF_TIP = 0.15;
    static final int DEFAULT_NUMBER_OF_PEOPLE = 1;

    public static double tipAmount, totalAmount, amountPerPerson;
    public static double bill = DEFAULT_BILL,
                         percentageOfTip = DEFAULT_PERCENTAGE_OF_TIP;
    public static int    numberOfPeople = DEFAULT_NUMBER_OF_PEOPLE;

    public static class Calc extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            tipAmount = percentageOfTip * bill;
            totalAmount = tipAmount + bill;
            amountPerPerson = totalAmount / numberOfPeople;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            TipCalcFragment.updateView();
        }
    }
}
