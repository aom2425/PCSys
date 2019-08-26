import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by G on 3/8/2018.
 */
public class soundControl {


    public static Boolean sendMessage(final String cmd){
        new AsyncTask<Integer, Void, Void>(){
            protected Void doInBackground(Integer... params) {
                try {
                    Socket socket = new Socket("192.168.1.204", 2002);
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    String outMsg = "";
                    outMsg = cmd;
                    out.write(outMsg);
                    out.flush();
                    out.close();
                    socket.close();
                }
                catch (Exception e) {
                    Log.d("TCP", "FFFFFFFFFFFFFFFFFFFFFFFFAAAAAIL");
                    e.printStackTrace();
                }
                return null;
            }
        }.execute(1);

        return true;
    }
}
