package mysite;

import mysite.model.Model;

/**
 * Created by DSK on 02.02.2016.
 */
public class Controller
{
    private Model model;

    public Controller(Model model)
    {
        if (model == null)
            throw new IllegalArgumentException();
        this.model = model;
    }

    public void onCitySelect(String cityName)
    {
        model.selectCity(cityName);
    }
}
