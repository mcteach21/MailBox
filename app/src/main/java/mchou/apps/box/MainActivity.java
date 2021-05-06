package mchou.apps.box;

// https://stackoverflow.com/questions/42478637/how-to-read-emails-programmatically-in-android
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import javax.mail.*;
import android.util.Log;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tests";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new BackgroundTask(MainActivity.this) {
            @Override
            public void doInBackground() {
                Properties props = new Properties();
                props.setProperty("mail.store.protocol", "imaps");
                try {
                    Session session = Session.getInstance(props, null);
                    Store store = session.getStore();
                    store.connect("imap.gmail.com", "adm.mc69@gmail.com", "Azerty123_");
                    Folder inbox = store.getFolder("INBOX");
                    inbox.open(Folder.READ_ONLY);

                    Log.i(TAG, "Inbox Messages : "+inbox.getMessageCount());

                    Message msg = inbox.getMessage(inbox.getMessageCount());
                    Address[] in = msg.getFrom();
                    for (Address address : in) {
                        Log.i(TAG , "doInBackground - FROM:" + address.toString());
                    }
                    Multipart mp = (Multipart) msg.getContent();
                    BodyPart bp = mp.getBodyPart(0);
                    Log.i(TAG ,"SENT DATE:" + msg.getSentDate());
                    Log.i(TAG ,"SUBJECT:" + msg.getSubject());
                    Log.i(TAG ,"CONTENT:" + bp.getContent());
                } catch (Exception mex) {

                    Log.e(TAG ,"Error : "+mex.getMessage());
                    mex.printStackTrace();
                }
            }

            @Override
            public void onPostExecute() {
                Log.i(TAG, "onPostExecute: i've finished!");
            }
        }.start();
    }

    public abstract class BackgroundTask {
        private AppCompatActivity activity;
        public void start() {
            new Thread(() -> {
                doInBackground();
                activity.runOnUiThread(() -> onPostExecute());
            }).start();
        }
        public BackgroundTask(AppCompatActivity activity) {
            this.activity = activity;
        }
        public abstract void doInBackground();
        public abstract void onPostExecute();
    }
}