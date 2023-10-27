package com.example.timetablemanager.Pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;
import java.time.LocalTime;

public class OwnTimePickerDialog extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    onDateResultPickerDialog result;
    private LocalTime date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public OwnTimePickerDialog(onDateResultPickerDialog result) {
        date = LocalTime.now();
        this.result = result;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public OwnTimePickerDialog(onDateResultPickerDialog result, LocalTime date) {
        this.date = LocalTime.of(date.getHour(), date.getMinute());
        this.result = result;
    }
    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create a new instance of TimePickerDialog and return it.
        return new TimePickerDialog(getActivity(), this, date.getHour(), date.getMinute(), DateFormat.is24HourFormat(getActivity()));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        LocalTime time = LocalTime.of(i, i1);
        result.resultDialog(time);
    }
}
