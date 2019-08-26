import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.widget.Toast;
import android.os.Vibrator;

//http://stackoverflow.com/questions/4459058/alarm-manager-example
public class Alarm extends BroadcastReceiver
{
    final public static String ONE_TIME = "onetime";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        Toast.makeText(context, "Powering off the systems", Toast.LENGTH_LONG).show();
        SSHConnection.sshSend(SSHConnection.authentication.pcUser,
                SSHConnection.authentication.pcPass,
                SSHConnection.authentication.pcIp,
                SSHConnection.authentication.port,
                "rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
        soundControl.sendMessage("powr");/**/

        wl.release();
    }

    public void SetAlarm(Context context, int time)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        i.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1 * 1000, pi); // Millisec * Second * Minute
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + time, pi);

        //Toast.makeText(context, Integer.toString(time), Toast.LENGTH_LONG).show();
    }

    public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}/**/