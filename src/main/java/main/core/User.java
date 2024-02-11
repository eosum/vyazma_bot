package main.core;

public class User {
    private Long userId;
    private String chatId;
    private String role = "student";



    public User(Long userId, String chatId) {
        this.userId = userId;
        this.chatId = chatId;
    }

    public User(Long userId, String chatId, String role) {
        this.userId = userId;
        this.chatId = chatId;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public String getChatId() {
        return chatId;
    }
}
