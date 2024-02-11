package main.core;

public class DeparturePersonInfo {
    private String date;
    private Integer cardNumber;
    private Integer roomNumber;

    public DeparturePersonInfo(String date, Integer cardNumber, Integer roomNumber) {
        this.date = date;
        this.cardNumber = cardNumber;
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Номер пропуска: " + cardNumber + "\nКомната: " + roomNumber + "\nДата отъезда: " + date;
    }
}
