package com.example.plateful.utils;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.plateful.models.DTOs.MealDTO;
import com.example.plateful.views.mealslist.CategoryAreaListView;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil{

    public static ShowPlans showPlans;
    public static void showCalendarPicker(MealDTO mealDTO, FragmentManager fragmentManager) {

        Calendar calendar = Calendar.getInstance();

        long startOfWeek = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_WEEK, 7);
        long endOfWeek = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setStart(startOfWeek);
        constraintsBuilder.setEnd(endOfWeek);
        constraintsBuilder.setValidator(new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                return date >= startOfWeek && date <= endOfWeek;
            }
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel parcel, int i) {

            }
        });

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a day this week")
                .setCalendarConstraints(constraintsBuilder.build())
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()) // Default selection: today
                .build();
        datePicker.show(fragmentManager, "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Cairo"));
            selectedCalendar.setTimeInMillis(selection);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String selectedDate = format.format(selectedCalendar.getTime());
            String dateName =  DateUtil.getFormattedDate(selectedDate);
            showPlans.saveMealToPlan(mealDTO,dateName);
        });
    }

    public static String getFormattedDate(String olddate) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = inputFormat.parse(olddate);
                SimpleDateFormat outputFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                return outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }

}
