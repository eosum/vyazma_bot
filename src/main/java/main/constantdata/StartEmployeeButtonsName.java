package main.constantdata;

import java.util.ArrayList;

public class StartEmployeeButtonsName {
    private static ArrayList<String> buttonsNames = new ArrayList<>();

    static {
        buttonsNames.add("Добавить новость");
        buttonsNames.add("Выбрать задачи");
        buttonsNames.add("Подтвердить гостя");
        buttonsNames.add("Мои задачи");
        buttonsNames.add("Просмотреть выехавших");
    }

    public static ArrayList<String> getButtonsNames() {
        return buttonsNames;
    }
}
