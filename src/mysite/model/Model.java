package mysite.model;

import mysite.view.View;
import mysite.vo.Vacancy;

import java.util.ArrayList;

/**
 * Created by DSK on 04.02.2016.
 */
public class Model
{
    private View view;
    private Provider[] providers;
    private String speciallity;
    private String city;

    public Model(View view, Provider... providers)
    {
        if (view == null || providers == null || providers.length == 0)
            throw new IllegalArgumentException();
        this.view = view;
        this.providers = providers;
    }

    public void selectSpeciallity(String speciallity)
    {
        this.speciallity = speciallity;
    }

    public void selectCity(String city)
    {
        this.city = city;
    }
    public String getHtml(int page, String searchString)
    {
        ArrayList<Vacancy> vacancies = new ArrayList<>();
        for (Provider provider : providers)
            vacancies.addAll(provider.getVacancies(page));
        return view.getUpdatedFileContent(vacancies, searchString);
    }
}
