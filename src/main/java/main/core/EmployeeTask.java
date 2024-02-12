package main.core;

import java.util.Date;

public class EmployeeTask {

    private Integer roomNumber;
    private Integer cardNumber;
    private String problemDescription;
    private Date chosenTime;

    public EmployeeTask(String problemDescription, Date chosenTime, Integer roomNumber, Integer cardNumber) {
        this.problemDescription = problemDescription;
        this.chosenTime = chosenTime;
        this.roomNumber = roomNumber;
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "Номер комнаты: " + roomNumber +
                "\nВыбранное время: " + chosenTime +
                "\nОписание проблемы: " + problemDescription +
                "\nНомер карты студента: " + cardNumber;
    }


}
