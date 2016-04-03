package com.xxx.tiptip;

import android.os.AsyncTask;

public class CalcCore extends AsyncTask<Double, Void, Void>{

    @Override
    protected Void doInBackground(Double... params) {
        if (params.length == 3) {
            double bill = params[0],
                    percentageOfTip = params[1],
                    numberOfPeople = params[2];
            double
                    tipAmount = percentageOfTip * bill,
                    totalAmount = tipAmount + bill,
                    amountPerPerson = totalAmount / numberOfPeople;
            TipCalcFragment.tipAmount = tipAmount;
            TipCalcFragment.totalAmount = totalAmount;
            TipCalcFragment.amountPerPerson = amountPerPerson;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        TipCalcFragment.updateView();
    }
}
