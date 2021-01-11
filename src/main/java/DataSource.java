import java.sql.*;
import java.util.*;

public class DataSource {
    private static final String URL = "jdbc:postgresql://localhost/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "48994899";
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static final String SELECT_PLANES = "SELECT DISTINCT plane.model, plane.seats\n" +
            "FROM airport, plane\n" +
            "WHERE airport.code = ?\n" +
            "AND plane.airportid = airport.id";
    private static final String SELECT_PILOTS = "SELECT count(pilots.id)\n" +
            "FROM airport, pilots, planes_pilots, plane\n" +
            "WHERE airport.code = ? and\n" +
            "pilots.airportid = airport.id\n" +
            "AND public.plane.airportid = airport.id\n" +
            "AND planes_pilots.plane = plane.id\n" +
            "AND planes_pilots.pilot = pilots.id\n" +
            "and public.plane.model = ?";

    private DataSource() {
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the PostgreSQL successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public static int getNumberOfPassenger(String airport) {
        int result = 0;
        int numberOfCrew = 2;
        connection = getConnection();
        Map<String, Integer> planes = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement(SELECT_PLANES);
            preparedStatement.setString(1, airport);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String model = resultSet.getString(1);
                int seats = resultSet.getInt(2);
                planes.put(model, seats);
            }

            String[] planesArray = planes.keySet().toArray(new String[0]);
            Integer[] seatsArray = planes.values().toArray(new Integer[0]);

            for (int i = 0; i < planesArray.length; i++) {
                preparedStatement = connection.prepareStatement(SELECT_PILOTS);
                preparedStatement.setString(1, airport);
                preparedStatement.setString(2, planesArray[i]);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int pilots = resultSet.getInt(1);
                    result = result + (pilots / numberOfCrew) * seatsArray[i];
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return result;
    }
}

