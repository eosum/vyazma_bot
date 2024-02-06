package main.constantdata;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class BookingTime {
    private static Set<String> date;
    public static final Set<String> hours = new HashSet<>();

    static {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (int i = 0; i < 24; i++) {
            hours.add(LocalTime.of(i, 0).format(formatter));
        }
    }

    public static Set<String> getDate() {
        date = new HashSet<>();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        date.add(today.format(formatter));
        date.add(today.plusDays(1).format(formatter));
        date.add(today.plusDays(2).format(formatter));

        return date;
    }

}
