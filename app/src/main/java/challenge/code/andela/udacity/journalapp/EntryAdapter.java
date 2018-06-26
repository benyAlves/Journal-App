package challenge.code.andela.udacity.journalapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.code.andela.udacity.journalapp.model.JournalEntry;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder>{

    private ArrayList<JournalEntry> entries;

    public EntryAdapter(ArrayList<JournalEntry> entries){
        this.entries = entries;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, null);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        JournalEntry entry = entries.get(position);
        holder.textViewBody.setText(entry.getBody());
        holder.textViewDate.setText(entry.getEntryDate());
    }

    @Override
    public int getItemCount() {
        return entries != null? entries.size(): 0;
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewEntryDate)
        TextView textViewDate;
        @BindView(R.id.textViewEntryBody)
        TextView textViewBody;

        public EntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
