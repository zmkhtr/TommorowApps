package com.sahabatpnj.tommorowapps.Database;

import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.sahabatpnj.tommorowapps.Model.Note;
import com.sahabatpnj.tommorowapps.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static final String TAG = "DataManager";
    private static final String LIST_KEY = "listKey";

    public static void addOrUpdateNote(Note note, int position){
        List<Note> mList = getNoteList(note.getUserEmail());

        if (position > -1) {
            note.setContent(note.getContent());
            note.setTitle(note.getTitle());
            mList.set(position, note);
        } else {
            mList.add(note);
        }
        Hawk.put(LIST_KEY + note.getUserEmail(), mList);

    } // Function for adding or updating note

    public static List<Note> getNoteList(String userEmail){
        return Hawk.get(LIST_KEY + userEmail, new ArrayList<Note>());
    } // Function for get the list of note added

    public static Note getNote(Note note){
        List<Note> mList = getNoteList(note.getUserEmail());

        Note _note = new Note("#","#","#");
        for (Note mNote : mList) {
            if (mNote.getNoteId().equals(note.getNoteId())) {
                note = mNote;
            }
        }

        return note;
    } // Function for get note detail by id

    public static void deleteNote(Note note, int position){
        List<Note> mList = getNoteList(note.getUserEmail());

        mList.remove(position);
        Log.d(TAG, "deleteNote: DELETED");
        Hawk.put(LIST_KEY + note.getUserEmail(), mList);
    } // Function for delete specific note

    public static void deleteAllNote(String userEmail){
        Hawk.delete(LIST_KEY + userEmail);
    }
}
