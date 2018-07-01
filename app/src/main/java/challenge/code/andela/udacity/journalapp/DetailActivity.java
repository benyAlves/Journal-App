package challenge.code.andela.udacity.journalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.code.andela.udacity.journalapp.model.JournalEntry;
import challenge.code.andela.udacity.journalapp.utils.TimeAgo;
import challenge.code.andela.udacity.journalapp.utils.Utils;

public class DetailActivity extends AppCompatActivity {


    public static final int EDIT_REQUEST = 1;
    private static final String TAG = "DetailActivity";
    @BindView(R.id.tv_body)
    public
    TextView tvBody;
    @BindView(R.id.tv_title)
    public
    TextView tvTitle;
    @BindView(R.id.tv_date)
    public
    TextView tvDate;
    private JournalEntry journalEntry;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Utils.FIREBASE_CHILD).child(currentUser.getUid());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        journalEntry = extras.getParcelable(MainActivity.EXTRA_JOURNAL_ITEM);

        tvTitle.setText(journalEntry.getTitle() != null? journalEntry.getTitle(): "No Title");
        tvBody.setText(journalEntry.getBody());
        tvDate.setText(TimeAgo.getTimeAgo(journalEntry.getEntryDate()));


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
                startActivityForResult(intent, EDIT_REQUEST);
                return true;
            case R.id.delete_entry:
                mDatabase.child(journalEntry.getPushId()).removeValue();
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        if (requestCode == DetailActivity.EDIT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {
                    journalEntry = data.getExtras().getParcelable(MainActivity.EXTRA_JOURNAL_ITEM);
                    Log.i(TAG, "onActivityResult"+ journalEntry.getTitle());
                }
            } else if (resultCode == 0) {
                Log.i(TAG, "cancelled");
            }
        }


    }

}
