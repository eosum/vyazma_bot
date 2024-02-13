package main.services;

import main.core.User;
import main.database.DatabaseManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationService {
    public static boolean isCardBlocked(Long userId) {
        return DatabaseManager.isCardBlocked(userId);
    }
    public static boolean hasRights(Long userId, String role) {
        return DatabaseManager.hasRights(userId, role);
    }

    public static boolean checkDateFormat(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean checkTimeFormat(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            LocalTime.parse(time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean validateTaskInput(String[] task) {
        return task.length == 2 && checkPhoneNumber(task[0]) && checkTask(task[1]);
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        int i = 0;
        for (; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) return false;
        }
        return i == 12;
    }

    public static boolean checkTask(String task) {
        int cnt = 0;
        for (String i: task.split(", ")) {
            cnt++;
            try {
                Integer.parseInt(i);
            }
            catch (NumberFormatException e) {
                return false;
            }
        }
        return cnt > 0;
    }
}
