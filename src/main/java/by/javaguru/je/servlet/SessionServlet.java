package by.javaguru.je.servlet;

import by.javaguru.je.dto.UserDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {
    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        var session = req.getSession();

        System.out.println(session.isNew());
        var user = session.getAttribute(USER);
        if (user == null)
            user = UserDto.builder()
                    .id(5)
                    .email("aa@ff.com")
                    .build();
        session.setAttribute(USER, user);
    }
}
