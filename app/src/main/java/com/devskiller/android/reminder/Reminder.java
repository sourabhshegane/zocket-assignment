package com.devskiller.android.reminder;

import androidx.annotation.NonNull;

public class Reminder {
    private int id;
    private String title;
    private boolean done;
    private long when;

    public Reminder(int id, @NonNull String title, long when) {
        this(title, when);
        this.id = id;
    }

    public Reminder(@NonNull String title, long when) {
        this.title = title;
        this.when = when;
    }

    public String getTitle() {
        return title;
    }

    public Reminder setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isDone() {
        return done;
    }

    public Reminder setDone(boolean done) {
        this.done = done;
        return this;
    }

    public long getWhen() {
        return when;
    }

    public Reminder setWhen(long when) {
        this.when = when;
        return this;
    }

    public int getId() {
        return id;
    }
}
