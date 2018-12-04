package project.tables;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String date;
    private String time;

    private String subject;

    @Column(length = 1024)
    private String text;

    private boolean alreadySent = false;

    public Message(){}

    public Message(String date, String time, String subject, String text, User user){
        this.date = date;
        this.time = time;
        this.subject = subject;
        this.text = text;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAlreadySent(boolean alreadySent) {
        this.alreadySent = alreadySent;
    }

    public boolean isAlreadySent() {
        return alreadySent;
    }

    @Override
    public String toString() {
        return "\nmessage_id: " + id
                + "\nuser_id: " + user.getId()
                + "\ndate: " + date
                + "\ntime: " + time
                + "\nsubject: " + subject
                + "\nmessage: " + text;
    }
}
