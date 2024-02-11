package main.constantdata;

import java.util.ArrayList;

public class StartStudentButtonsName {
    private static ArrayList<String> buttonsNames = new ArrayList<>();

    static {
        buttonsNames.add("Выбор сервиса");
        buttonsNames.add("Бронь комнаты");
        buttonsNames.add("Гостевой пропуск");
        buttonsNames.add("Мои заявки");
        buttonsNames.add("Оформление выезда");
    }

    public static ArrayList<String> getButtonsNames() {
        return buttonsNames;
    }
}
