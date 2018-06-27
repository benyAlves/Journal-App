package challenge.code.andela.udacity.journalapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static final String FIREBASE_CHILD = "journals";
    public static String dateToString(Date date) {
        return new SimpleDateFormat("hh:mm").format(date);
    }

}
