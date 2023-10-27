package com.example.timetablemanager.Pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.time.LocalTime;

public class OwnDatePickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    onTimeResultPickerDialog result;
    private LocalDate date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public OwnDatePickerDialog(onTimeResultPickerDialog result) {
        date = LocalDate.now();
        this.result = result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public OwnDatePickerDialog(onTimeResultPickerDialog result, LocalDate date) {
        this.date = LocalDate.of(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        this.result = result;
    }
    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of TimePickerDialog and return it.
        return new DatePickerDialog(requireContext(), this, date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
        result.resultDialog(this.date);
    }
}
