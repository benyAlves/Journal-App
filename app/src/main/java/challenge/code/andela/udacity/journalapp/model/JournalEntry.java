package challenge.code.andela.udacity.journalapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Comparator;


@IgnoreExtraProperties
public class JournalEntry implements Parcelable, Comparator<JournalEntry>{

    private String pushId;
    private String title;
    private String body;
    private String imageUrl;
    private long entryDate;



    public JournalEntry(String title, String body, String imageUrl, long entryDate) {
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.entryDate = entryDate;
    }

    public JournalEntry(String title, String body, long entryDate) {
        this.title = title;
        this.body = body;
        this.entryDate = entryDate;
    }

    public JournalEntry() {
    }

    protected JournalEntry(Parcel in) {
        body = in.readString();
        imageUrl = in.readString();
        entryDate = in.readLong();
        title = in.readString();
        pushId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(imageUrl);
        dest.writeLong(entryDate);
        dest.writeString(title);
        dest.writeString(pushId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JournalEntry> CREATOR = new Creator<JournalEntry>() {
        @Override
        public JournalEntry createFromParcel(Parcel in) {
            return new JournalEntry(in);
        }

        @Override
        public JournalEntry[] newArray(int size) {
            return new JournalEntry[size];
        }
    };

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

    public long getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(long entryDate) {
        this.entryDate = entryDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    @Override
    public int compare(JournalEntry o1, JournalEntry o2) {
        if (o1.entryDate > o2.entryDate) {
            return -1;
        } else {
            return 1;
        }
    }
}
