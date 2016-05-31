package mysite.model;

import mysite.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DSK on 05.02.2016.
 */
public class MoikrugStrategy implements Strategy
{
    private static final String URL_FORMAT = "https://moikrug.ru/vacancies?page=%d&q=%s&city_id=%s";
    private String speciality;
    private String cityID;
    private boolean isEnd;

    public MoikrugStrategy(String speciality, String city)
    {
        this.speciality = speciality;
        this.cityID = getCityId(city);
    }

    @Override
    public List<Vacancy> getVacancies(int page)
    {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        Document document = null;

        try
        {
            if (!this.isEnd)
            {
                document = getDocument(speciality, cityID, page);
                if (document.html() == null)
                {
                    this.isEnd = true;
                    return vacancies;
                }

                Elements elements = document.getElementsByClass("job");
                if (!elements.isEmpty())
                {
                    for (Element element : elements)
                    {
                        Element title = element.getElementsByClass("title").first().getElementsByTag("a").first();
                        Element brand = element.getElementsByClass("company_name").first().getElementsByTag("a").first();
                        Element city = element.getElementsByClass("meta").first();
                        Elements salary = element.getElementsByClass("salary");
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(title.text());
                        vacancy.setCompanyName(brand.text());
                        vacancy.setCity(city.text());
                        if (salary.size() == 1)
                            vacancy.setSalary(salary.text());
                        else
                            vacancy.setSalary("");
                        vacancy.setSiteName("https://moikrug.ru/");
                        vacancy.setUrl("https://moikrug.ru/" + title.attr("href"));
                        vacancies.add(vacancy);
                    }
                }
                else
                {
                    return vacancies;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getSimpleName());
        }
        return vacancies;
    }

    protected Document getDocument(String speciality, String cityId, int page) throws IOException
    {
        Document document = Jsoup.connect(String.format(URL_FORMAT, page, speciality, cityId))
                .userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36")
                .referrer("none")
                .get();
        return document;
    }

    protected String getCityId(String city)
    {
        switch (city)
        {
            case "Rostov-on-Don":
                return "726";
            case "Moscow":
                return "678";
            case "Petersburg":
                return "679";
        }
        return "";
    }
}
