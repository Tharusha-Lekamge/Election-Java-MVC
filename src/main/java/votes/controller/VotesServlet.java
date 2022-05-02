package votes.controller;

import constants.Routes;
import constants.UserLevels;
import party.model.Party;
import votes.dao.VotesDao;
import votes.model.Votes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static utilities.Utilities.contains;

@WebServlet(name = "VotesServlet", value = Routes.ENDPOINT_VOTES)
public class VotesServlet extends HttpServlet {

    private final VotesDao dao;

    public VotesServlet() {this.dao = new VotesDao();}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object levelObj = session.getAttribute("level");
        if (levelObj != null) {
            int level = (int) levelObj;
            if (UserLevels.VOTE_USER_LEVELS.contains(level)){

                int id = (int) session.getAttribute("id");
                Votes votesData = null;
                try {
                    votesData = dao.getVotes(id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                request.setAttribute("votesData", votesData);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/votes.jsp");
                dispatcher.forward(request, response);
            } else response.sendRedirect(Routes.ROUTE_LOGIN);
        } else response.sendRedirect(Routes.ROUTE_LOGIN);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}