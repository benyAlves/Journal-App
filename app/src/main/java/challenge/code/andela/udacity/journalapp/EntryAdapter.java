package challenge.code.andela.udacity.journalapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.code.andela.udacity.journalapp.model.JournalEntry;
import challenge.code.andela.udacity.journalapp.utils.GlideApp;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private final Context context;
    private ArrayList<JournalEntry> entries;
    private OnClickJournalEntry mClickHandler;

    public EntryAdapter(ArrayList<JournalEntry> entries, Context context, OnClickJournalEntry mClickHandler){
        this.entries = entries;
        this.context = context;
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
        if(entry.getTitle() != null) {
            holder.textViewDate.setText(entry.getTitle());
        }else{
            holder.textViewDate.setText(entry.getEntryDate());
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

    @Override
    public int getItemCount() {
        return entries != null? entries.size(): 0;
    }

    public class EntryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textViewEntryDate)
        TextView textViewDate;
        @BindView(R.id.textViewEntryBody)
        TextView textViewBody;

        public EntryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(getAdapterPosition(), entries.get(getAdapterPosition()));
        }

    }


}
