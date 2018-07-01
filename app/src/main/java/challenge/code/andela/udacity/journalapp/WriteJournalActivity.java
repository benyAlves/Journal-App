package challenge.code.andela.udacity.journalapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.code.andela.udacity.journalapp.model.JournalEntry;
import challenge.code.andela.udacity.journalapp.utils.Utils;

public class WriteJournalActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    public
    EditText tvTitle;
    @BindView(R.id.tv_body)
    public
    EditText tvBody;


    private DatabaseReference mDatabase;
    private JournalEntry journalEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_journal);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            journalEntry = extras.getParcelable(MainActivity.EXTRA_JOURNAL_ITEM);
            tvTitle.setText(journalEntry.getTitle());
            tvBody.setText(journalEntry.getBody());
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Utils.FIREBASE_CHILD).child(currentUser.getUid());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.write_entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_entry: saveJourney(); return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveJourney() {
        String title = tvTitle.getText().toString();
        String body = tvBody.getText().toString();
        String path = "https://i.imgur.com/GqKwJmS.jpg";
        if(body.trim().isEmpty()){
            tvBody.setError("Write something first!");
            return;
        }
        if(journalEntry != null){
            mDatabase.child(journalEntry.getPushId()).child("title").setValue(title);
            mDatabase.child(journalEntry.getPushId()).child("body").setValue(body);
            Intent intent = new Intent();
            intent.putExtra(MainActivity.EXTRA_JOURNAL_ITEM, journalEntry);
            setResult(Activity.RESULT_OK, intent);
        }else {
            JournalEntry journalEntry = new JournalEntry(title, body, new Date().getTime());

            DatabaseReference pushRef = mDatabase.push();
            String pushId = pushRef.getKey();
            journalEntry.setPushId(pushId);
            pushRef.setValue(journalEntry);
        }

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
