package com.adeolaadesipe.singlechat.model;

public class Messages {
    private String name, number, lastMessage, profilePic, chatKey;
    private int unseenMessages;

    public Messages(String name, String number, String lastMessage, String profilePic, int unseenMessages, String chatKey) {
        this.name = name;
        this.number = number;
        this.lastMessage = lastMessage;
        this.unseenMessages = unseenMessages;
        this.profilePic = profilePic;
        this.chatKey = chatKey;
    }

    public String getName() {
        return name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getChatKey() {
        return chatKey;
    }

    public String getNumber() {
        return number;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public int getUnseenMessages() {
        return unseenMessages;
    }
}
