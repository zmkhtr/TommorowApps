package com.sahabatpnj.tommorowapps.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sahabatpnj.tommorowapps.BaseActivity;
import com.sahabatpnj.tommorowapps.Database.DataManager;
import com.sahabatpnj.tommorowapps.Database.UserManager;
import com.sahabatpnj.tommorowapps.Model.Note;
import com.sahabatpnj.tommorowapps.R;

import java.util.UUID;

import static com.sahabatpnj.tommorowapps.UI.MainActivity.ADD_KEY;
import static com.sahabatpnj.tommorowapps.UI.MainActivity.NOTE_OBJECT_KEY;
import static com.sahabatpnj.tommorowapps.UI.MainActivity.POSITION_KEY;

public class AddOrEditNoteActivity extends BaseActivity {
    private static final String TAG = "AddOrEditNoteActivity";
    private EditText mTitle, mContents;
    private Button mSave;

    private boolean isAdd;
    private Note note;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_note);

        Intent intent = getIntent();
        isAdd = intent.getBooleanExtra(ADD_KEY, false);
        note = intent.getParcelableExtra(NOTE_OBJECT_KEY);
        position = intent.getIntExtra(POSITION_KEY, -1);
        Log.d(TAG, "onCreate: " + position);
        declareView();
        decideLayout();
        saveNote();
    }

    private void saveNote() {
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitle.getText().toString().trim();
                String contents = mContents.getText().toString().trim();
                if (title.isEmpty() || contents.isEmpty())
                    createToast("Please fill your title and content !!");
                else{
                    if(isAdd){
                        DataManager.addOrUpdateNote(new Note(title, contents, UserManager.getUserEmail()), position);
                    } else {
                        note.setTitle(title);
                        note.setContent(contents);
                        DataManager.addOrUpdateNote(note, position);
                    }
                    onBackPressed();
                }

            }
        });
    }

    private void declareView() {
        mTitle = findViewById(R.id.edtAddOrEditTitle);
        mContents = findViewById(R.id.edtAddOrEditContent);
        mSave = findViewById(R.id.btnAddOrEditSave);
    }

    private void decideLayout() {
        if (isAdd)
            setActionBar("Add Note", true, false);
        else {
            mTitle.setText(note.getTitle());
            mContents.setText(note.getContent());
            setActionBar("Edit Note", true, false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isAdd){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.edit_activity_menu, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (!isAdd){
            if (item.getItemId() == R.id.itemDelete) {
                createDialogDelete();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void createDialogDelete(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete confirmation");
        dialog.setMessage("Are you sure want to delete this note : " + note.getTitle() + " ?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataManager.deleteNote(note, position);
                onBackPressed();
                createToast("Note Deleted");
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.create();
        dialog.show();
    }

}
