package com.sahabatpnj.tommorowapps.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sahabatpnj.tommorowapps.Adapter.NoteAdapter;
import com.sahabatpnj.tommorowapps.BaseActivity;
import com.sahabatpnj.tommorowapps.Database.DataManager;
import com.sahabatpnj.tommorowapps.Database.UserManager;
import com.sahabatpnj.tommorowapps.Model.Note;
import com.sahabatpnj.tommorowapps.R;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    public static final String ADD_KEY = "addKey";
    public static final String NOTE_OBJECT_KEY = "noteObjectKey";
    public static final String POSITION_KEY = "positionKey";

    private FloatingActionButton mFloatingActionButton;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String actionbarTitle = "Halo, " + UserManager.getUserData(UserManager.getUserEmail()).getName();
        adapter = new NoteAdapter();
        setActionBar(actionbarTitle,false,false);
        declareView();
        fabClick();
        initRecyclerView();
    }

    private void declareView(){
        mFloatingActionButton = findViewById(R.id.floating_action_button);
        recyclerView = findViewById(R.id.recyclerViewMainListNote);
    }

    private void initRecyclerView(){
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        adapter.setmListNote(DataManager.getNoteList(UserManager.getUserEmail()));
        Log.d(TAG, "initRecyclerView: " + DataManager.getNoteList(UserManager.getUserEmail()));
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new NoteAdapter.OnItemClick() {
            @Override
            public void onItemClick(Note note, int position) {
                Intent intent = new Intent(MainActivity.this, AddOrEditNoteActivity.class);
                intent.putExtra(NOTE_OBJECT_KEY, note);
                intent.putExtra(POSITION_KEY, position);
                startActivity(intent);
            }
        });
    }

    private void fabClick(){
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrEditNoteActivity.class);
                intent.putExtra(ADD_KEY, true);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemLogout) {
           createDialogLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void createDialogLogout(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Logout confirmation");
        dialog.setMessage("Are you sure want to logout?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
                finish();
                UserManager.logoutUser();
                createToast("Logout successful");
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

    @Override
    protected void onResume() {
        super.onResume();

        adapter.setmListNote(DataManager.getNoteList(UserManager.getUserEmail()));
    }
}
