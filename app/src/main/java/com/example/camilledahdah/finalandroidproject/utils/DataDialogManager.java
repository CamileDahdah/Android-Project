package com.example.camilledahdah.finalandroidproject.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by camilledahdah on 4/20/18.
 */

public class DataDialogManager {



    public static void clickDate(final Button dateTextButton, Context context, final Long[] newUnixTime) {

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


        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,

                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                        month = month + 1;
                        Log.d("date", "onDateSet: dd/mm/yy: " + day + "/" + month + "/" + year);
                        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

                        try {
                            Date date = (Date) formatter.parse(day + "-" + month + "-" + year);
                            newUnixTime[0] = date.getTime();
                        } catch (Exception e) {
                            newUnixTime[0] = new Long(0);
                            e.printStackTrace();

                        }


                        String date = day + "/" + month + "/" + year;
                        dateTextButton.setText(date);

                    }
                }
                , year, month, day);

        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

    }
}
