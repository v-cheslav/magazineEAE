package magazine.utils;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pvc on 27.04.2016.
 */
public class DateService {
    private static Logger log = Logger.getLogger(DateService.class);


    public DateService() {
    }



    public Calendar parseDate (String date) throws ParseException {
        if (date.equals("")) return null;
        Calendar calendar;
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date reportDate = format.parse(date);
            calendar = Calendar.getInstance();
            calendar.setTime(reportDate);

        return calendar;
    }

//    public Calendar parseDate (String date) throws SearchException {
//        if (date.equals("")) return null;
//        Calendar calendar;
//        try {
//            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//            Date reportDate = format.parse(date);
//            calendar = Calendar.getInstance();
//            calendar.setTime(reportDate);
//        } catch (java.text.ParseException ex) {
//            throw new SearchException("Не коректна дата!");
//        }
//        return calendar;
//    }
}