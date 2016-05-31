package mysite.view;

import mysite.Controller;
import mysite.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.List;

/**
 * Created by DSK on 04.02.2016.
 */
public class HtmlView implements View
{
    private Controller controller;
    private String template;

    public HtmlView(String template)
    {
        this.template = template;
    }

    @Override
    public String getUpdatedFileContent(List<Vacancy> vacancies, String searchString)
    {
        Document document = null;
        try
        {
            document = getDocument();
            Element template = document.select(".template").get(0);

            Element templateClone = template.clone();
            templateClone.removeClass("template");
            templateClone.removeAttr("style");

            document.select("tr[class=vacancy]").remove();

            for (Vacancy vacancy : vacancies)
            {
                Element element = templateClone.clone();

                Element city = element.select(".city").get(0);
                city.text(vacancy.getCity());

                Element companyName = element.select(".companyName").get(0);
                companyName.text(vacancy.getCompanyName());

                Element salary = element.select(".salary").get(0);
                salary.text(vacancy.getSalary());

                Element url = element.getElementsByTag("a").get(0);
                url.text(vacancy.getTitle());
                url.attr("href", vacancy.getUrl());

                template.before(element.outerHtml());
            }
            Element previous = document.getElementById("previous");
            Element next = document.getElementById("next");
            int countPage = Integer.parseInt(searchString.substring(searchString.length() - 1));
            String s = searchString.substring(0, searchString.length() - 1);
            int previousPage = 0;
            if (countPage == 1)
                previousPage = countPage;
            else
                previousPage = countPage - 1;
            int nextPage = countPage + 1;
            previous.attr("href", "http://localhost/simple?" + s + previousPage);
            next.attr("href", "http://localhost/simple?" + s + nextPage);
            Element body = document.getElementById("body");

            if (countPage == 1)
                previous.attr("class", "disabled");
            if (vacancies.size() < 20)
                next.attr("class", "disabled");
            body.appendChild(previous);
            body.appendChild(next);


        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Some exception occurred");
        }
        return document.html();
    }

    protected Document getDocument() throws IOException
    {
        return Jsoup.parse(template);

    }

    @Override
    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod()
    {
        controller.onCitySelect("Rostov-on-Don");
    }

}
