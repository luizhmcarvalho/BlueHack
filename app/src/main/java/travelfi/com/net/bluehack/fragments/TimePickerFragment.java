package travelfi.com.net.bluehack.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by luiz on 06/11/16.
 */

public class TimePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static String date;

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        TimePickerFragment.date = date;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        setDate(sdf.format(c));
    }

}