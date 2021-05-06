package mchou.apps.box.adapters;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Mail {
    static int AI_ID = 0;
    private int id;
    private String sender;
    private String from;
    private String subject;
    private String date;
    private String body;
    String pattern = "dd-MM HH:mm";

    public Mail(String from_name, String from_adress, String subject, Date date, String body) {
        this.id = ++AI_ID;
        this.sender = from_name;
        this.from = from_adress;
        this.subject = subject;
        this.body = body;
        this.date = new SimpleDateFormat(pattern).format(date);
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }
    public String getFrom() {
        return from;
    }
    public String getSubject() {
        return subject;
    }
    public String getDate() {
        return date;
    }
    public String getBody() {
        return body;
    }
}
