package main.database;

import main.core.Room;
import main.core.Service;
import main.core.Task;
import main.utils.Transformation;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class DatabaseManager {

    public static boolean hasUserPermission(Long user) {
        long n = -1;
        try(Connection connection = DataSource.getConnection()) {
            String sql = "select * from students where student_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, user);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return false;
            n = rs.getLong("student_id");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return n > -1;
    }

    public static boolean createBooking(Long userId, String roomId, String date, String hour) {
        int n = 0;
        Integer cardNumber = getCardNumber(userId);
        try(Connection connection = DataSource.getConnection()) {
            String sql = "insert into bookings (room_id, card_number, start_time) values (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(roomId));
            ps.setInt(2, cardNumber);
            ps.setTimestamp(3, Timestamp.valueOf(date + " " + hour));
            System.out.println(n);
            n = ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static boolean createTask(Long userId, String serviceId, String problemDescription, String date, String hour) {
        int n = 0;
        Integer cardNumber = getCardNumber(userId);
        try(Connection connection = DataSource.getConnection()) {
            String sql = "insert into tasks (service_id, card_number, problem_description, chosen_time, creation_time) values (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(serviceId));
            ps.setInt(2, cardNumber);
            ps.setString(3, problemDescription);
            ps.setTimestamp(4, Timestamp.valueOf(date + " " + hour));
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            System.out.println(n);
            n = ps.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    public static HashMap<String, Room> getRooms() {

        try(Connection connection = DataSource.getConnection()) {
            String sql = "select rooms.room_id, room_types.type from rooms\n" +
                    "join room_types on room_types.type_id = rooms.type_id\n" +
                    "where rooms.type_id != 2 and rooms.type_id != 7;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return Transformation.transformRooms(rs);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean createDepartureApplication(Long userId, String date) {
        int n = 0;
        try(Connection connection = DataSource.getConnection()) {
            Integer cardNumber = getCardNumber(userId);
            String sql = "insert into departures (card_number, date) values (?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,cardNumber);
            ps.setDate(2, Date.valueOf(date));
            n = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }



    public static ArrayList<Task> getRequests(Long userId) {
        try(Connection connection = DataSource.getConnection()) {
            Integer cardNumber = getCardNumber(userId);
            String sql = "select * from tasks where card_number = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,cardNumber);
            ResultSet rs = ps.executeQuery();
            return Transformation.transformRequests(rs);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Integer getCardNumber(Long userId) {
        try(Connection connection = DataSource.getConnection()) {
            String sql = "select card_id from students where (student_id = ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, userId);
            ResultSet res = ps.executeQuery();
            res.next();
            return res.getInt("card_id");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static HashMap<String, Service> getServices() {
        try(Connection connection = DataSource.getConnection()) {
            String sql = "select service_id, name from services";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return Transformation.transformServices(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean createGuestApplication(Long userId, String[] params) {
        Integer guestId = getGuestId(params[1].split(" "));

        if (guestId == null) guestId = createGuest(params);

        return createApplication(userId, guestId, params);
    }

    private static Integer getGuestId(String[] passport) {
        Integer id = null;
        try(Connection connection = DataSource.getConnection()) {
            String query = "select guest_id from guests where (passport_seria = ? and passport_number = ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, passport[0]);
            ps.setString(2, passport[1]);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                id = rs.getInt("guest_id");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    private static Integer createGuest(String[] guestData) {
        String[] name = guestData[0].split(" ");
        String[] passport = guestData[1].split(" ");

        try(Connection connection = DataSource.getConnection()) {
            String query = "insert into guests (name, surname, middle_name, passport_seria, passport_number, passport_organization, passport_date, passport_division_code) values" +
                    "(?, ?, ?, ?, ?, ?, '?', ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name[0]);
            ps.setString(2, name[1]);
            ps.setString(3, name[2]);
            ps.setString(4, passport[0]);
            ps.setString(5, passport[1]);
            ps.setString(6, guestData[2]);
            ps.setDate(7, Date.valueOf(guestData[3]));
            ps.setString(8, guestData[4]);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return getGuestId(passport);
    }

    private static boolean createApplication(Long userId, Integer guestId, String[] params) {
        int n = 0;
        Integer cardNumber = getCardNumber(userId);
        try(Connection connection = DataSource.getConnection()) {
            String query = "insert into applications (card_number, guest_id, confirmation, date, start_time, end_time) values" +
                    "(?, ?, ?, '2024-02-03', '2024-02-04 10:00:00', '2024-02-04 16:00:00')";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, cardNumber);
            ps.setInt(2, guestId);
            ps.setBoolean(3, false);
            ps.setTimestamp(4, Timestamp.valueOf(params[5]));
            ps.setTime(5, Time.valueOf(params[6]));
            ps.setTime(6, Time.valueOf(params[7]));
            n  = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }
}
