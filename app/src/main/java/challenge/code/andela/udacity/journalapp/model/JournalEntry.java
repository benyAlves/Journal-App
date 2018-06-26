package challenge.code.andela.udacity.journalapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;


public class JournalEntry {

    private String body;
    private String imageUrl;
    private String entryDate;

    public JournalEntry(String body, String imageUrl, String entryDate) {
        this.body = body;
        this.imageUrl = imageUrl;
        this.entryDate = entryDate;
    }

    public JournalEntry() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }


    public String dateToString(Date date) {
        return new SimpleDateFormat("hh:mm").format(date);
    }
}
