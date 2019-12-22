package com.ngenge.apps.notestaker.models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "notes")
public class Note implements Parcelable {



    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private long dateTime;


    protected Note(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        dateTime = in.readLong();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public Note(String title, String description, long dateTime) {
        this.dateTime = dateTime;
        this.description = description;
        this.title = title;

    }
    public long getDateTime() {
        return dateTime;
    }



    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeLong(dateTime);
    }
}
