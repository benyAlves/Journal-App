package challenge.code.andela.udacity.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.code.andela.udacity.journalapp.model.JournalEntry;
import challenge.code.andela.udacity.journalapp.utils.TimeAgo;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private ArrayList<JournalEntry> entries;
    private OnClickJournalEntry mClickHandler;

    public EntryAdapter(OnClickJournalEntry mClickHandler){
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        JournalEntry entry = entries.get(position);
        holder.textViewBody.setText(entry.getBody());
        holder.textViewDate.setText(TimeAgo.getTimeAgo(entry.getEntryDate()));
        if(entry.getTitle() != null) {
            holder.textViewTitle.setText(entry.getTitle());
        }else{
            holder.textViewTitle.setVisibility(View.GONE);
        }
//        GlideApp.with(context)
//                .load(entry.getImageUrl())
//                .centerCrop()
//                .transition(DrawableTransitionOptions.withCrossFade()) //Optional
//                .skipMemoryCache(true)  //No memory cache
//                .diskCacheStrategy(DiskCacheStrategy.NONE)   //No disk cache
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        return false;
//                    }
//                })
//                .into(holder.imageViewEntry);


        //ViewCompat.setTransitionName(holder.imageViewEntry,"name"+position);
    }

    public ArrayList<JournalEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<JournalEntry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return entries != null? entries.size(): 0;
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textViewEntryDate)
        TextView textViewDate;
        @BindView(R.id.textViewEntryBody)
        TextView textViewBody;
        @BindView(R.id.textViewTitle)
        TextView textViewTitle;

        EntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(entries.get(getAdapterPosition()));
        }

    }


}
