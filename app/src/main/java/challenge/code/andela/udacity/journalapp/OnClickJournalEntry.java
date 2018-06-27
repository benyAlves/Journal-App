package challenge.code.andela.udacity.journalapp;

import android.widget.ImageView;

import challenge.code.andela.udacity.journalapp.model.JournalEntry;

public interface OnClickJournalEntry {
    void onClick(int adapterPosition, JournalEntry journalEntry);
}
