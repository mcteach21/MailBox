package mchou.apps.box.adapters;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mail {
    static int AI_ID = 0;
    private int id;
    private String from;
    private String subject;
    private LocalDateTime date;
    private String body;

    public Mail(String from, String subject, String date_string, String body) {
        this.id = ++AI_ID;
        this.from = from;
        this.subject = subject;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//            LocalDateTime dateTime = LocalDateTime.parse(date_string, formatter);
            this.date = LocalDateTime.now();
        }

        this.body = body;
    }

    public int getId() {
        return id;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
