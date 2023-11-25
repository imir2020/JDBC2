package by.javaguru.je.servlet;

import by.javaguru.je.entity.Ticket;
import by.javaguru.je.service.TicketService;
import by.javaguru.je.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/tickets")
public class TicketServlet extends HttpServlet {
    private final TicketService ticketService = TicketService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long flightId  = Long.valueOf(req.getParameter("flightId"));

        req.setAttribute("tickets",ticketService.findAllByFlightId(flightId));
        req.getRequestDispatcher(JspHelper.getPath("tickets")).forward(req,resp);
//        try(var writer = resp.getWriter()){
//            writer.write("<h1>Купленные билеты</h1>");
//            writer.write("<ul>");
//            ticketService.findAllByFlightId(flightId).stream().forEach(ticketDto ->
//                    writer.write("""
//                            <li>%s</li>
//                            """.formatted(ticketDto.seatNo())));
//            writer.write("</ul>");
//        }
    }
}
