package main.core;

public class User {
    private Long userId;
    private String chatId;

    public User(Long userId, String chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getChatId() {
        return chatId;
    }
}
