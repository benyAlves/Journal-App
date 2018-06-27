package challenge.code.andela.udacity.journalapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import challenge.code.andela.udacity.journalapp.model.JournalEntry;
import challenge.code.andela.udacity.journalapp.utils.Utils;

public class MainActivity extends AppCompatActivity implements OnClickJournalEntry{

    private static final int RC_SIGN_IN = 111;
    private static final String TAG = "MainActivity";
    public static final String EXTRA_JOURNAL_ITEM = "journal-item";
    public static final String EXTRA_JOURNAL_IMAGE_TRANSITION_NAME = "transition-name";
    private static final String DATA = "data";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ChildEventListener mChildEventListener;
    private EntryAdapter entryAdapter;
    private ArrayList<JournalEntry> listEntries;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child(Utils.FIREBASE_CHILD).child(mFirebaseAuth.getUid());
        listEntries = new ArrayList<>();

        showProgressBar();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mFirebaseAuth.getCurrentUser() != null) {
                    Log.i(TAG, "onCreate");
                    if(savedInstanceState == null) {
                        attachDatabaseReadListener();
                    }else {
                        hideProgressBar();
                        listEntries = savedInstanceState.getParcelableArrayList(DATA);
                        entryAdapter = new EntryAdapter(listEntries, getApplicationContext(), MainActivity.this);
                        recyclerView.setAdapter(entryAdapter);
                    }
                } else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setTheme(R.style.LoginTheme)
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.PhoneBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(linearLayoutManager);
        entryAdapter = new EntryAdapter(listEntries, getApplicationContext(), this);
        recyclerView.setAdapter(entryAdapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putParcelableArrayList(DATA, listEntries);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    finish();
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                   showToast(R.string.no_internet_connection);
                    return;
                }

                showToast(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                AuthUI.getInstance().signOut(this);
                detachDatabaseReadListener();
                return true;
            case R.id.add_entry:
                Intent intent = new Intent(this, WriteJournalActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showToast(int messageResource) {
        Toast.makeText(this, getResources().getString(messageResource), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(int adapterPosition, JournalEntry journalEntry) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(EXTRA_JOURNAL_ITEM, journalEntry);
        //intent.putExtra(EXTRA_JOURNAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(imageViewEntry));

//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                this,
//                imageViewEntry,
//                ViewCompat.getTransitionName(imageViewEntry));

        startActivity(intent);
    }

    private void attachDatabaseReadListener() {
            if (mChildEventListener == null) {
                mChildEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        hideProgressBar();
                        JournalEntry journalEntry = dataSnapshot.getValue(JournalEntry.class);
                        listEntries.add(journalEntry);
                        entryAdapter.notifyDataSetChanged();
                    }

                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        entryAdapter.notifyDataSetChanged();
                    }

                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                mDatabase.addChildEventListener(mChildEventListener);
            }
    }

    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mDatabase.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
}
