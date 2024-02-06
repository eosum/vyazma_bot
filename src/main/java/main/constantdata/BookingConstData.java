package main.constantdata;

import main.core.Room;
import main.database.DatabaseManager;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BookingConstData {
    private static Set<String> date;
    public static final Set<String> hours = new HashSet<>();
    public static final HashMap<String, Room> rooms = DatabaseManager.getRooms();

    static {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (int i = 0; i < 24; i++) {
            hours.add(LocalTime.of(i, 0).format(formatter));
        }
    }
}
