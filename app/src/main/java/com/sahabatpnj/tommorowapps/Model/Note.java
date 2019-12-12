package com.sahabatpnj.tommorowapps.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Note implements Parcelable {
    private String noteId = UUID.randomUUID().toString();
    private String title;
    private String content;
    private String userEmail;

    public Note(String title, String content, String userEmail) {
        this.title = title;
        this.content = content;
        this.userEmail = userEmail;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.noteId);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.userEmail);
    }

    protected Note(Parcel in) {
        this.noteId = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.userEmail = in.readString();
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
