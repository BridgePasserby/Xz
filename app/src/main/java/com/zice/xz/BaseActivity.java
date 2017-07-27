package com.zice.xz;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.zice.xz.utils.DataModeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showErrorDialog(String code) {

    }

    public void showDateDialog(Context context, String selectTime, final DialogClickListener dialogClickListener) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_date);
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.date_picker_dialog);
        final TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.time_picker_dialog);
        Button btnConform = (Button) dialog.findViewById(R.id.btn_conform);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        int year = datePicker.getYear();
        Date date = null;
        try {
            DataModeUtils.DataTime dataTime = DataModeUtils.parseDateTime(selectTime);
            if (dataTime != null) {
                datePicker.updateDate(dataTime.year, dataTime.month, dataTime.day);
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            date = simpleDateFormat.parse(String.valueOf(year - 2));
            if (date != null) {
                long ts = date.getTime();
                datePicker.setMinDate(ts);
                datePicker.setMaxDate(new Date().getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timePicker.setIs24HourView(true);
        dialog.setTitle("选择日期");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        btnConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String dateTimeFormat = "%d-%d-%d %d:%d";
                final String dateTime = String.format(dateTimeFormat, year, month, day, hour, minute);
                dialogClickListener.conformListener(dateTime);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public interface DialogClickListener {
        void conformListener(String dateTime);

        void cancelListener();
    }

}
