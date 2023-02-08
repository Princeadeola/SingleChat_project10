package com.adeolaadesipe.singlechat.model;

public class Chats {

    private String number, name ,message, date, time;

    public Chats(String number, String name, String message, String date, String time) {
        this.number = number;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
