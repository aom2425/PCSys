import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;

import java.util.Calendar;
import java.util.Properties;

/**
 * Created by G on 3/8/2018.
 */
public class menu4_Fragment extends Fragment{
    View rootview;
    @Nullable
    @Override


   /* public void onCreate(){
        super.onCreate();
    }/**/

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu4_layout,container,false);


        //Button sleepMode = (Button) rootview.findViewById(R.id.sleepMode);
        Button addAlarmBtn = (Button) rootview.findViewById(R.id.addAlarm);
        Button cancelAlarmBtn = (Button) rootview.findViewById(R.id.cancelButton);
        final TextView timerView = (TextView) rootview.findViewById(R.id.textViewTime);
        final TimePicker timePicker = (TimePicker) rootview.findViewById(R.id.timePicker);
        final Alarm alarm = new Alarm();

        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(0);
        timePicker.setCurrentMinute(0);



        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hoursFromPicker = timePicker.getCurrentHour();
                int minutesFromPicker = timePicker.getCurrentMinute();

                String msg = "Timer for power off: " + Integer.toString(hoursFromPicker) + "hour, " + Integer.toString(minutesFromPicker) + "minutes";
                Toast.makeText(getActivity().getBaseContext(), msg, Toast.LENGTH_LONG).show();

                if (hoursFromPicker == 0)
                    hoursFromPicker = 1;

                // @@@ main set of alarm
                alarm.SetAlarm(getActivity().getBaseContext(), hoursFromPicker * minutesFromPicker * 60 * 1000); //hours * minutes * seconds * miliseconds

                // @@@ timer counter
                CountDownTimer timer;
                timer = new CountDownTimer(hoursFromPicker * minutesFromPicker * 60 * 1000, 1000) { // hoursFromPicker * minutesFromPicker * 60 * 1000
                    public void onTick(long millisUntilFinished) {
                        timerView.setText( millisUntilFinished /60/60/ 1000 +" hours, "+millisUntilFinished /60/ 1000 +" minutes, "+ millisUntilFinished / 1000+" seconds");
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        timerView.setText("Done!");
                    }

                };
                timer.start();
            }
        });

        cancelAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm.CancelAlarm(getActivity().getBaseContext());

            }
        });

        return rootview; //super.onCreateView(inflater, container, savedInstanceState);
    }




    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
        }
    }

    public void showTimePickerDialog(View v) {
      //  DialogFragment newFragment = new TimePickerFragment();
        //newFragment.show(this, "timePicker");
    }

}
