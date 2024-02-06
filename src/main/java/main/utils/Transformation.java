package main.utils;

import main.core.Room;
import main.core.Service;
import main.core.Task;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Transformation {
    /*public List<Service> transformServicesToList(ResultSet res) throws SQLException {
        List<Service> services = new ArrayList<>();
        while (res.next()) {
            Service service = new Service(
                    res.getLong("service_id"),
                    res.getString("name"),
                    res.getString("description"),
                    res.getLong("room_id"),
                    res.getString("employee_position")
            );

            services.add(service);
        }

        return services;
    }*/

    public List<String> transformServicesNamesToString(ResultSet res) throws SQLException {
        List<String> servicesName = new ArrayList<>();
        while (res.next()) {
            servicesName.add(res.getString("name"));
        }

        return servicesName;
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
}
