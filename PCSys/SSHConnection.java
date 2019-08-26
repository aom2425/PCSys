import android.os.AsyncTask;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * Created by G on 3/8/2018.
 */
public class SSHConnection {

    /*
* !!!!!WARNING!!!!!
*  This data is authentic
*/
    public static class authentication {
        public static String pcIp = "xxx.xxx.x.xxx";
        public static String cubieIp = "xxx.xxx.x.xxx";
        public static String rpiIp = "xxx.xxx.x.xxx";
        public static int port = 22;

        public static String rpiUser = "root";
        public static String cubieUser = "root";
        public static String pcUser = "User";

        public static String rpiPass = "root";
        public static String cubiePass = rpiPass;
        public static String pcPass = "pass";

        public static String pcMac = "xx:xx:xx:xx:xx";
    }

    public static int sshSend (final String username,
                               final String password,
                               final String ip,
                               final int port,
                               final String command){

        new AsyncTask<Integer, Void, Void>(){
            protected Void doInBackground(Integer... params) {
                try {
                    executeRemoteCommand(username, password, ip, port, command);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

        }.execute(1);

        return 0;
    }

    public static String executeRemoteCommand(String username,String password,String ip,int port, String command)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, ip, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        channelssh.setOutputStream(baos);

        // Execute command
        // channelssh.setCommand("echo 'VEIKIA!!!!"+mCounter+"' >> /tmp/tr");
        channelssh.setCommand(command);
        channelssh.connect();


        // Waitting for job to finish then disconnect. NOTE: with nohup you do not
        // need to use this three lines
        int timeInSec = 0;
        while (channelssh.getExitStatus() == -1){
            try{
                Thread.sleep(1000);
                timeInSec++;
            }
            catch(Exception e){
                System.out.println(e);
            }
            if(timeInSec >=4){ // command have max 5 sec to exec
                break;
            }
        }

        channelssh.disconnect();
        session.disconnect();

        return baos.toString();
    }
}
