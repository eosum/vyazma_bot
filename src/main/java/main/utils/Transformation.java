package main.utils;

import main.core.DeparturePersonInfo;
import main.core.Room;
import main.core.Service;
import main.core.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transformation {

    public static ArrayList<DeparturePersonInfo> transformDeparturePersonInfo(ResultSet rs) {
        ArrayList<DeparturePersonInfo> res = new ArrayList<>();

        try {
            while(rs.next()) {
                res.add(new DeparturePersonInfo(
                        rs.getString("date"),
                        rs.getInt("card_number"),
                        rs.getInt("room_id")
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static ArrayList<Task> transformRequests(ResultSet rs) {
        ArrayList<Task> res = new ArrayList<>();

        try {
            while(rs.next()) {
                res.add(new Task(
                        rs.getLong("service_id"),
                        rs.getString("problem_description"),
                        rs.getDate("chosen_time"),
                        rs.getDate("creation_time")
                ));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static HashMap<String, Room> transformRooms(ResultSet rs) {
        HashMap<String, Room> res = new HashMap<>();

        try {
            while(rs.next()) {
                res.put(rs.getString("room_id"),
                        new Room(
                            rs.getString("room_id"),
                            rs.getString("type"))
                );
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static HashMap<String, Service> transformServices(ResultSet rs) {
        HashMap<String, Service> res = new HashMap<>();

        try {
            while(rs.next()) {
                res.put(rs.getString("service_id"),
                        new Service(
                                rs.getString("service_id"),
                                rs.getString("name"))
                );
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
