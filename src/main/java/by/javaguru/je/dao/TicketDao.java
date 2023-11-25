package by.javaguru.je.dao;

import by.javaguru.je.dto.TicketFilter;
import by.javaguru.je.entity.Ticket;
import by.javaguru.je.exception.DaoException;
import by.javaguru.je.utils.ConnectionManager;
import org.w3c.dom.html.HTMLOptGroupElement;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

public class TicketDao implements Dao<Long, Ticket> {
    private static final TicketDao INSTANCE = new TicketDao();

    private final static String SAVE_SQL = """
            INSERT INTO  ticket 
            (passport_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?,?,?,?,?);
            """;
    private final static String DELETE_SQL = """
            DELETE FROM ticket
            WHERE id = ?
            """;
    private final static String FIND_ALL_SQL = """
            SELECT id,passport_no,passenger_name,flight_id,seat_no,cost
            FROM ticket t
            """;
    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id= ?
            """;

    private final static String UPDATE_SQL = """
            UPDATE ticket
            SET passport_no=?,
            passenger_name=?,
            flight_id=?,
            seat_no=?,
            cost=?
            WHERE id = ?
                        """;
    private final static String FIND_BY_FLIGHT_ID = FIND_ALL_SQL + """
            WHERE t.flight_id=?
            """;

    private TicketDao() {
    }


    public static TicketDao getInstance() {
        return INSTANCE;
    }

    private Ticket buildTicket(ResultSet result) throws SQLException {

        return new Ticket(
                result.getLong("id"),
                result.getString("passport_no"),
                result.getString("passenger_name"),
                result.getLong("flight_id"),
                result.getString("seat_no"),
                result.getBigDecimal("cost")
        );
    }

    public List<Ticket> findAllByFlightId(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_FLIGHT_ID)) {
            List<Ticket> tickets = new ArrayList<>();
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tickets.add(buildTicket(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Ticket> findAll(TicketFilter filter) {
        List<Object> parameters = new ArrayList<>();
        List<String> whereSql = new ArrayList<>();
        if (filter.passengerName() != null) {
            parameters.add(filter.passengerName());
            whereSql.add("passenger_name = ?");
        }
        if (filter.seatNo() != null) {
            parameters.add("%" + filter.seatNo() + "%");
            whereSql.add("seat_no like ?");

        }
        parameters.add(filter.limit());
        parameters.add(filter.offset());
        var where = whereSql.stream().collect(Collectors.joining(
                " AND ",
                parameters.size() > 2 ? " WHERE " : " ",
                " LIMIT  ? OFFSET  ? "
        ));
        String sql = FIND_ALL_SQL + where;

        System.out.println(sql);//test

        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(buildTicket(result));
            }
            return tickets;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public List<Ticket> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Ticket> tickets = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(buildTicket(result));
            }
            return tickets;

        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            statement.setLong(1, id);

            var result = statement.executeQuery();

            Ticket ticket = null;

            if (result.next()) {

                ticket = buildTicket(result);

            }
            return Optional.ofNullable(ticket);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    /**
     * Сохранение/вставка данных в базу
     *
     * @param ticket
     * @return
     */
    @Override
    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.executeUpdate();

            var keys = statement.getGeneratedKeys();
            if (keys.next()) {
                ticket.setId(keys.getLong("id"));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ticket;
    }

    @Override
    public boolean update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerName());
            statement.setLong(3, ticket.getFlightId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    /**
     * Удаление данных из базы данных
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
