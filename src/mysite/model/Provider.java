package mysite.model;

import mysite.vo.Vacancy;

import java.util.List;

/**
 * Created by DSK on 02.02.2016.
 */
public class Provider
{
    private Strategy strategy;

    public Provider(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public void setStrategy(Strategy strategy)
    {
        this.strategy = strategy;
    }

    public List<Vacancy> getVacancies(int page)
    {
        return strategy.getVacancies(page);
    }
}
