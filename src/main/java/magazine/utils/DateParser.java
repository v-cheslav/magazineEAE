package magazine.utils;

import magazine.Exeptions.SearchException;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pvc on 27.04.2016.
 */
public class DateParser {
    private static Logger log = Logger.getLogger(DateParser.class);


    public DateParser() {
    }

    public Calendar parseDate (String date) throws SearchException {
        if (date.equals("")) return null;
        Calendar calendar;
        try {
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Date reportDate = format.parse(date);
            calendar = Calendar.getInstance();
            calendar.setTime(reportDate);
        } catch (java.text.ParseException ex) {
            throw new SearchException("Не коректна дата!");
        }
        return calendar;
    }
}