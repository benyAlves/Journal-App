package challenge.code.andela.udacity.journalapp.utils;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import challenge.code.andela.udacity.journalapp.model.JournalEntry;

public class Utils {

    public static final String FIREBASE_CHILD = "journals";
    public static String dateToString(Date date) {
        return new SimpleDateFormat("hh:mm", new Locale("pt", "MZ")).format(date);
    }

    public static Comparator<JournalEntry> byDate = new Comparator<JournalEntry>() {
        public int compare(JournalEntry left, JournalEntry right) {
            if (left.getEntryDate() > right.getEntryDate()) {
                return -1;
            } else {
                return 1;
            }
        }
    };

}
