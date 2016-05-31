package mysite.view;

import mysite.Controller;
import mysite.vo.Vacancy;


import java.util.List;

/**
 * Created by DSK on 04.02.2016.
 */
public interface View
{
    String getUpdatedFileContent(List<Vacancy> vacancies, String searchString);
    void setController(Controller controller);
}
