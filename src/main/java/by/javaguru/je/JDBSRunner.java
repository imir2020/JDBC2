package by.javaguru.je;

import by.javaguru.je.dao.FlightDao;
import by.javaguru.je.dao.TicketDao;
import by.javaguru.je.dto.TicketFilter;
import by.javaguru.je.entity.Flight;
import by.javaguru.je.entity.Ticket;
import by.javaguru.je.utils.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBSRunner {

    public static void main(String[] args) throws SQLException {

        var flightDao = FlightDao.getInstance();
        List<Flight> flights = flightDao.findAll();
        System.out.println(flights);
        System.out.println(flightDao.findAll());







//        var ticketDao = TicketDao.getInstance();
//
//       // var filter = new TicketFilter("Иван Иванов","A1",5,0);
//        var filter = new TicketFilter(null,null,5,0);
//        System.out.println(ticketDao.findAll(filter));









//        Ticket ticket = ticketDao.findById(5L).get();
//        System.out.println(ticket + " START TICKET");
//        ticket.setSeatNo("40L");
//        System.out.println(ticketDao.update(ticket));
//        System.out.println(ticketDao.findById(5L));








//        Ticket ticket = new Ticket();
//        ticket.setPassportNo("34FD88");
//        ticket.setPassengerName("Tars");
//        ticket.setFlightId(4L);
//        ticket.setSeatNo("77S");
//        ticket.setCost(BigDecimal.TEN);
//       // System.out.println(ticketDao.save(ticket));
//
//        System.out.println(ticketDao.delete(57L));


        //       System.out.println(getTicketsByFlightId(7L));
//
//       /** String sql = """//
//                SELECT * FROM ticket;
//                """;
//
//        try (var connection = ConnectionManager.open();
//             var statement = connection.createStatement()) {
//            var result = statement.executeQuery(sql);
//            while (result.next()) {
//                System.out.println(result.getLong("id"));
//                System.out.println("-------------------------");
//                System.out.println(result.getString("passenger_name"));
//            }
//        }
    }

    public static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        List<Long> tickets = new ArrayList<>();
        String sql = """
                SELECT * FROM ticket
                WHERE flight_id =?;
                """;


        try (var connection = ConnectionManager.open();
             var statement = connection.prepareStatement(sql)) {
            //Именно здесь происходит обмен данными, между параметром метода и базой
            statement.setLong(1, flightId);
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(result.getLong("id"));

            }
        }
        return tickets;
    }
}
