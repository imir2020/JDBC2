package by.javaguru.je.servlet;

import by.javaguru.je.service.FlightService;
import by.javaguru.je.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/flights")
public class FlightServlet extends HttpServlet {
    private final FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//        resp.setContentType("text/html");
//        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        req.setAttribute("flights",flightService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("flights")).forward(req,resp);


//        var writer = resp.getWriter();
//        writer.write("<h1> Список перелётов:</h1>");
//        writer.write("<ul>");
//        flightService.findAll().stream().forEach(flightDto ->
//                writer.write("""
//                        <li>
//                        <a href='/tickets?flightId=%d'>%s</a>
//                        </li>
//                        """.formatted(flightDto.id(), flightDto.description())));
//        writer.write("</ul>");

    }
}
