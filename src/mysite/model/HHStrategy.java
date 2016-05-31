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
 * Created by DSK on 02.02.2016.
 */
public class HHStrategy implements Strategy
{
    private static final String URL_FORMAT = "http://hh.ru/search/vacancy?text=%s&page=%d";
    private String speciality;
    private String city;
    private boolean idEnd;

    public HHStrategy(String speciality, String city)
    {
        this.speciality = speciality;
        this.city = city;
    }

    @Override
    public List<Vacancy> getVacancies(int page)
    {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        page--;
        Document document = null;
        try
        {
            if (!this.idEnd)
            {
                document = getDocument(speciality, city, page);
                if (document.html() == null)
                {
                    this.idEnd = true;
                    return vacancies;
                }

                Elements elements = document.select("[data-qa=vacancy-serp__vacancy]");
                if (!elements.isEmpty())
                {
                    for (Element element : elements)
                    {
                        Elements title = element.select("[data-qa=vacancy-serp__vacancy-title]");
                        Elements brand = element.select("[data-qa=vacancy-serp__vacancy-employer]");
                        Elements city = element.select("[data-qa=vacancy-serp__vacancy-address]");
                        Elements salary = element.select("[data-qa=vacancy-serp__vacancy-compensation]");
                        Vacancy vacancy = new Vacancy();
                        vacancy.setTitle(title.get(0).text());
                        vacancy.setCompanyName(brand.get(0).text());
                        vacancy.setCity(city.get(0).text());
                        if (salary.size() == 1)
                            vacancy.setSalary(salary.get(0).text());
                        else
                            vacancy.setSalary("");
                        vacancy.setSiteName("http://hh.ua/");
                        vacancy.setUrl(title.first().attr("href"));
                        vacancies.add(vacancy);
                    }
                }
                else
                    return vacancies;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getSimpleName());
        }
        return vacancies;
    }

    protected Document getDocument(String speciality, String city, int page) throws IOException
    {
        Document document = Jsoup.connect(String.format(URL_FORMAT, speciality + "+" + city, page))
                    .userAgent("Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.97 Safari/537.36")
                    .referrer("none")
                    .get();
        return document;
    }
}
