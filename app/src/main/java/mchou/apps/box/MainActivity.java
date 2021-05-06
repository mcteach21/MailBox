package mchou.apps.box;

// https://stackoverflow.com/questions/42478637/how-to-read-emails-programmatically-in-android
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import javax.mail.*;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import mchou.apps.box.adapters.Mail;
import mchou.apps.box.adapters.MailListAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tests";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListe();
        readMessages();
    }

    RecyclerView recyclerView;
    MailListAdapter adapter;
    List<Mail> items = new ArrayList();
    SwipeRefreshLayout swipeContainer;

    private void initListe() {
        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //val layoutManager = GridLayoutManager(this, 3)

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(() ->  refreshListAsync());
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright);
    }

    private void readMessages() {

        new BackgroundTask(MainActivity.this) {
            @Override
            public void doInBackground() {

                items.clear();
                runOnUiThread(() -> {
                    swipeContainer.setRefreshing(true);
                });
                Properties props = new Properties();
                props.setProperty("mail.store.protocol", "imaps");
                try {
                    Session session = Session.getInstance(props, null);
                    Store store = session.getStore();
                    store.connect("imap.gmail.com", "adm.mc69@gmail.com", "Azerty123_");
                    Folder inbox = store.getFolder("INBOX");
                    inbox.open(Folder.READ_ONLY);

                    Log.i(TAG, "Inbox Messages : "+inbox.getMessageCount());

                    for (int i = 1; i <= inbox.getMessageCount(); i++) {
                        Message msg = inbox.getMessage(i);

                        Address[] addresses = msg.getFrom();
//                        StringBuffer sb = new StringBuffer();
//                        for (Address address : addresses) {
//                            sb.append(address.toString()+" | ");
//                        }

                        Multipart mp = (Multipart) msg.getContent();
                        BodyPart bp = mp.getBodyPart(0);

                        items.add(new Mail(addresses[0].toString(), msg.getSubject(), msg.getSentDate().toString(), bp.getContent().toString()));
                    }

//                    Multipart mp = (Multipart) msg.getContent();
//                    BodyPart bp = mp.getBodyPart(0);
//                    Log.i(TAG ,"SENT DATE:" + msg.getSentDate());
//                    Log.i(TAG ,"SUBJECT:" + msg.getSubject());
//                    Log.i(TAG ,"CONTENT:" + bp.getContent());
                } catch (Exception mex) {

                    Log.e(TAG ,"Error : "+mex.getMessage());
                    mex.printStackTrace();
                }

                runOnUiThread(() -> {
                    display();
                    swipeContainer.setRefreshing(false);
                });

            }

            @Override
            public void onPostExecute() {
                Log.i(TAG, "onPostExecute: i've finished!");
            }
        }.start();
    }
    //afficher messages
    private void display(){
        adapter = new MailListAdapter(items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutAnimation(
                AnimationUtils.loadLayoutAnimation(
                        MainActivity.this, R.anim.layout_fall_down_animation
                ));
    }

    private void refreshListAsync() {
        readMessages();
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