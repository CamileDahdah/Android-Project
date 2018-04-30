package com.example.camilledahdah.finalandroidproject.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
/**
 * Created by camilledahdah on 4/20/18.
 */

public class DataDialogManager {

    public static long clickDate(final Button dateTextButton, Context context){

        Calendar cal = new Calendar() {
            @Override
            protected void computeTime() {

            }

            @Override
            protected void computeFields() {

            }

            @Override
            public void add(int field, int amount) {

            }

            @Override
            public void roll(int field, boolean up) {

            }

            @Override
            public int getMinimum(int field) {
                return 0;
            }

            @Override
            public int getMaximum(int field) {
                return 0;
            }

            @Override
            public int getGreatestMinimum(int field) {
                return 0;
            }

            @Override
            public int getLeastMaximum(int field) {
                return 0;
            }
        }.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        long unixTime = cal.getTime().getTime();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        Log.d("date", "onDateSet: dd/mm/yy: " + day + "/" + month + "/" + year);

                        String date = day + "/" + month + "/" + year;
                        dateTextButton.setText(date);

                    }
                }
                ,year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

        Log.d("time", "time: " + unixTime);
        return unixTime;
    }
}
