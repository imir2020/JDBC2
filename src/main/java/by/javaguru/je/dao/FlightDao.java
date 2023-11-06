package by.javaguru.je.dao;

import by.javaguru.je.entity.Flight;
import by.javaguru.je.entity.FlightStatus;
import by.javaguru.je.entity.Ticket;
import by.javaguru.je.exception.DaoException;
import by.javaguru.je.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {
    private final static FlightDao INSTANCE = new FlightDao();
    private final static String FIND_ALL_SQL = """
            
            
            SELECT     id,
                       flight_no,
                       departure_date,
                       departure_airport_code,
                       arrival_date,
                       arrival_airport_code,
                       aircraft_id,
                       status
            FROM flight
            """;

    private FlightDao() {
    }
    public static FlightDao getInstance(){
        return INSTANCE;
    }

    @Override
    public boolean update(Flight flight) {
        return false;
    }

    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Flight> flights = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(buildFlight(result));
            }
            return flights;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Flight buildFlight(ResultSet result) throws SQLException {
        return new Flight(
                result.getLong("id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status"))
        );
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Ticket save(Flight ticket) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
