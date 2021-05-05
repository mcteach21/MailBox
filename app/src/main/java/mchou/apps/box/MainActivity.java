package mchou.apps.box;

// https://stackoverflow.com/questions/42478637/how-to-read-emails-programmatically-in-android
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import javax.mail.*;
import android.os.AsyncTask;

import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new MyAsynk().execute();
    }

    public class MyAsynk extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Properties props = new Properties();
            props.setProperty("mail.store.protocol", "imaps");
            try {
                Session session = Session.getInstance(props, null);
                Store store = session.getStore();
                store.connect("imap.gmail.com", "youremail@gmail.com", "password");
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_ONLY);
                Message msg = inbox.getMessage(inbox.getMessageCount());
                Address[] in = msg.getFrom();
                for (javax.mail.Address address : in) {
                    System.out.println("FROM:" + address.toString());
                }
                Multipart mp = (Multipart) msg.getContent();
                BodyPart bp = mp.getBodyPart(0);
                System.out.println("SENT DATE:" + msg.getSentDate());
                System.out.println("SUBJECT:" + msg.getSubject());
                System.out.println("CONTENT:" + bp.getContent());
            } catch (Exception mex) {
                mex.printStackTrace();
            }
            return null;
        }
    }
}