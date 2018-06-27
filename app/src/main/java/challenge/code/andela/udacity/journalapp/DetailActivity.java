package challenge.code.andela.udacity.journalapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.auth.AuthUI;

import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.code.andela.udacity.journalapp.model.JournalEntry;
import challenge.code.andela.udacity.journalapp.utils.GlideApp;

public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.tv_body)
    TextView tvBody;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private JournalEntry journalEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        journalEntry = extras.getParcelable(MainActivity.EXTRA_JOURNAL_ITEM);

        tvTitle.setText(journalEntry.getTitle() != null? journalEntry.getTitle(): "No Title");
        tvBody.setText(journalEntry.getBody());
        tvDate.setText(journalEntry.getEntryDate());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_entry:
                Intent intent = new Intent(this, WriteJournalActivity.class);
                intent.putExtra(MainActivity.EXTRA_JOURNAL_ITEM, journalEntry);
                startActivity(intent);
                return true;
            case R.id.delete_entry:
                Intent intentWrite = new Intent(this, WriteJournalActivity.class);
                intentWrite.putExtra(MainActivity.EXTRA_JOURNAL_ITEM, journalEntry);
                startActivity(intentWrite);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
