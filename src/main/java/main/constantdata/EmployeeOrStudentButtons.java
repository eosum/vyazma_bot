package main.constantdata;

import java.util.ArrayList;

public class EmployeeOrStudentButtons {

    private static ArrayList<String> buttonsNames = new ArrayList<>();

    static {
        buttonsNames.add("Сотрудник");
        buttonsNames.add("Студент");
    }

    public static ArrayList<String> getButtonsNames() {
        return buttonsNames;
    }
}
