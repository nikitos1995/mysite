package mysite.model;

import mysite.vo.Vacancy;

import java.util.List;

/**
 * Created by DSK on 02.02.2016.
 */
public interface Strategy
{
    List<Vacancy> getVacancies(int page);
}
