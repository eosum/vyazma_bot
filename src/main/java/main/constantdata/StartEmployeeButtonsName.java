package main.constantdata;

import java.util.ArrayList;

public class StartEmployeeButtonsName {
    private static ArrayList<String> buttonsNames = new ArrayList<>();

    static {
        buttonsNames.add("Добавить новость");
        buttonsNames.add("Подтвердить гостя");
        buttonsNames.add("Новые задачи");
        buttonsNames.add("Завершенные задачи");
        buttonsNames.add("Просмотреть выехавших");
    }

    public static ArrayList<String> getButtonsNames()  {
        return buttonsNames;
    }
}
