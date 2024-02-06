package main.database;

import main.core.Task;
import main.utils.Transformation;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseManager {
    public static ArrayList<Task> getRequests(Long userId) {
        try(Connection connection = DataSource.getConnection()) {
            Integer cardNumber = getCardNumber(userId);
            String sql = "select * from tasks where card_number = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,cardNumber);
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

    public static ResultSet getServicesNames() {
        try(Connection connection = DataSource.getConnection()) {
            String sql = "select name from services";
            PreparedStatement ps = connection.prepareStatement(sql);
            return ps.executeQuery();
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
                    "(?, ?, ?, ?, ?, ?, '2017-12-01', ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name[0]);
            ps.setString(2, name[1]);
            ps.setString(3, name[2]);
            ps.setString(4, passport[0]);
            ps.setString(5, passport[1]);
            ps.setString(6, guestData[2]);
            //ps.setString(7, guestData[3]);
            ps.setString(7, guestData[4]);

            ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return getGuestId(passport);
    }

    private static boolean createApplication(Long userId, Integer guestId, String[] params) {
        int n = 0;
        try(Connection connection = DataSource.getConnection()) {
            String query = "insert into applications (card_number, guest_id, confirmation, date, start_time, end_time) values" +
                    "(335082, ?, ?, '2024-02-03', '2024-02-04 10:00:00', '2024-02-04 16:00:00')";
            PreparedStatement ps = connection.prepareStatement(query);
           // ps.setString(1, userId);
            ps.setInt(1, guestId);
            ps.setBoolean(2, false);
            n  = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

}
