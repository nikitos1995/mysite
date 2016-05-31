package mysite;

import mysite.model.HHStrategy;
import mysite.model.Model;
import mysite.model.MoikrugStrategy;
import mysite.model.Provider;
import mysite.view.HtmlView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

/**
 * Created by DSK on 06.05.2016.
 */
public class SimpleServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String speciality = req.getParameter("speciality");
        String city = req.getParameter("city");
        int page = Integer.parseInt(req.getParameter("page"));

        InputStreamReader reader = new InputStreamReader(getServletContext().getResourceAsStream("WEB-INF/resources/vacancies.html"), "UTF-8");
        StringWriter template = new StringWriter();
        while (reader.ready())
        {
            template.write(reader.read());
        }

        HtmlView view = new HtmlView(template.toString());
        Model model = new Model(view, new Provider(new HHStrategy(speciality, city)), new Provider(new MoikrugStrategy(speciality, city)));
        Controller controller = new Controller(model);
        view.setController(controller);

        model.selectSpeciallity(speciality);
        model.selectCity(city);
        String html = model.getHtml(page, req.getQueryString());

        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(html);
    }
}
