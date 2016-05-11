package magazine.utils;
/**
 * Created by pvc on 19.03.2016.
 */

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

public class MySimpleDateFormat {

    private SimpleDateFormat dateFormat;

    public MySimpleDateFormat() {
        dateFormat = new SimpleDateFormat("dd MMMM yyyy 'p.'", myDateFormatSymbols);
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    private static DateFormatSymbols myDateFormatSymbols = new DateFormatSymbols() {
        @Override
        public String[] getMonths() {
            return new String[]{"січня", "лютого", "березня", "квітня", "травня", "червня", "лимня", "серпня", "вересня", "жовтня", "листопада", "грудня"};
        }
    };

}
