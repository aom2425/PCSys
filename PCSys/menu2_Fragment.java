import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by G on 3/8/2018.
 *
 * - Sleep Computer
 *     rundll32.exe powrprof.dll,SetSuspendState 0,1,0
 * - Lock Workstation
 *     Rundll32.exe User32.dll,LockWorkStation
 * - Hibernate Computer
 *     rundll32.exe PowrProf.dll,SetSuspendState
 * - Restart Computer
 *     Shutdown.exe -r -t 00
 * - Shutdown Computer
 *     Shutdown.exe -s -t 00
 */
public class menu2_Fragment extends Fragment{
    View rootview;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu2_layout,container,false);

        Button powerPcButton = (Button) rootview.findViewById(R.id.button_powerPc);
        Button sleepPcButton = (Button) rootview.findViewById(R.id.button_sleep);
        Button sleepPcAndSoundButton = (Button) rootview.findViewById(R.id.button_sleepPcAndSaound);
        Button wakeUpPcAndSoundButton = (Button) rootview.findViewById(R.id.button_wakeUpPcAndSound);
        Button rebootPcButton = (Button) rootview.findViewById(R.id.button_reboot);
        Button wakePcButton = (Button) rootview.findViewById(R.id.button_wakeUp);

        // Power off/on the PC
        powerPcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSHConnection.sshSend(SSHConnection.authentication.pcUser,
                        SSHConnection.authentication.pcPass,
                        SSHConnection.authentication.pcIp,
                        SSHConnection.authentication.port,
                        "Shutdown.exe -s -t 00"); //nohup /home/streamer/soft/./streamer > /dev/null 2>&1 &
                Toast.makeText(getActivity().getBaseContext(), "Msg: POWER PC Sended!", Toast.LENGTH_SHORT).show();
            }
        });

        // Make sleep for PC
        sleepPcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSHConnection.sshSend(SSHConnection.authentication.pcUser,
                        SSHConnection.authentication.pcPass,
                        SSHConnection.authentication.pcIp,
                        SSHConnection.authentication.port,
                        "rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
                Toast.makeText(getActivity().getBaseContext(), "Msg: SLEEP PC Sended!", Toast.LENGTH_SHORT).show();
            }
        });

        // Make reboot for PC
        rebootPcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSHConnection.sshSend(SSHConnection.authentication.pcUser,
                        SSHConnection.authentication.pcPass,
                        SSHConnection.authentication.pcIp,
                        SSHConnection.authentication.port,
                        "Shutdown.exe -r -t 00");
                Toast.makeText(getActivity().getBaseContext(), "Msg: REBOOT PC Sended!", Toast.LENGTH_SHORT).show();
            }
        });

        // Make wake up for PC
        wakePcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSHConnection.sshSend(SSHConnection.authentication.cubieUser,
                        SSHConnection.authentication.cubiePass,
                        SSHConnection.authentication.cubieIp,
                        SSHConnection.authentication.port,
                        "/usr/bin/./wol " + SSHConnection.authentication.pcMac); // Waking up the PC
                Toast.makeText(getActivity().getBaseContext(), "Msg: WAKE UP PC Sended!", Toast.LENGTH_SHORT).show();
            }
        });
        // Make sleep for PC and sound
        sleepPcAndSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSHConnection.sshSend(SSHConnection.authentication.pcUser,
                        SSHConnection.authentication.pcPass,
                        SSHConnection.authentication.pcIp,
                        SSHConnection.authentication.port,
                        "rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
                /*SSHConnection.sshSend(SSHConnection.authentication.rpiUser,
                        SSHConnection.authentication.rpiPass,
                        SSHConnection.authentication.rpiIp,
                        SSHConnection.authentication.port,
                        "nohup /home/scripts/lirc/./power > /dev/null 2>&1 &");*/ // Power off the sound sys
                soundControl.sendMessage("powr");
                Toast.makeText(getActivity().getBaseContext(), "Msg: SLEEP FOR ALL Sended!", Toast.LENGTH_SHORT).show();
            }
        });

        // Make wake up for PC and sound
        wakeUpPcAndSoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SSHConnection.sshSend(SSHConnection.authentication.cubieUser,
                        SSHConnection.authentication.cubiePass,
                        SSHConnection.authentication.cubieIp,
                        SSHConnection.authentication.port,
                        "/usr/bin/./wol " + SSHConnection.authentication.pcMac); // Waking up the PC
                /*SSHConnection.sshSend(SSHConnection.authentication.rpiUser,
                        SSHConnection.authentication.rpiPass,
                        SSHConnection.authentication.rpiIp,
                        SSHConnection.authentication.port,
                        "nohup /home/scripts/lirc/./power > /dev/null 2>&1 &");*/ // Power on the sound sys
                soundControl.sendMessage("powr");
                Toast.makeText(getActivity().getBaseContext(), "Msg: WAKE UP FOR ALL Sended!", Toast.LENGTH_SHORT).show();
            }
        });

        return rootview;
    }
}
