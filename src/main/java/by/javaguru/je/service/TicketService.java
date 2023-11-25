package by.javaguru.je.service;

import by.javaguru.je.dao.TicketDao;
import by.javaguru.je.dto.TicketDto;
import by.javaguru.je.entity.Ticket;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private static final TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    private TicketService(){}

    public static TicketService getInstance(){
        return INSTANCE;
    }

    public List<TicketDto> findAllByFlightId(Long flightId){
        return ticketDao.findAllByFlightId(flightId).stream().map(
              ticket -> new TicketDto(ticket.getId(),ticket.getFlightId(),ticket.getSeatNo())
        ).collect(Collectors.toList());
    }
}
