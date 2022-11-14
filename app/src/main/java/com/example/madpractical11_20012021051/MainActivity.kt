package com.example.madpractical11_20012021051

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

class Notes(var title: String, var subTit1e:String, var Description: String,var modifiedTime:String, var isReminder :Boolean=false) {

    var remindertime: Long = System.currentTimeMillis()
    var id=noteIDGeneration()
    constructor(note: Notes):
    this(note.title,note.subTit1e,note.Description,note.modifiedTime,note.isReminder) {
        remindertime = note.remindertime
    }
        companion object {
            var idnote=0
            fun noteIDGeneration():Int{
                idnote++
                return idnote
            }
       var notesArray: List<Notes> = ArrayList()
    }
}

class NoteData {
    companion object {
        const val TABLE_NAME = "notes"
        const val COLUMN_ID = "id"
        const val COLUMN_NOTE_TITLE = "note_title"
        const val COLUMN_NOTE_SUB_TITLE="note_sun_title"
        const val COLUMN_NOTE_DESCRIPTION="noe_description"
        const val COLUMN_NOTE_SET_REMINDER="note_set_reminder"
        const val COLUMN_NOTE_REMINDER_TIME="note_set_reminder_time"
        const val COLUMN_TIMESTAMP="note_modified_timestamp"
        //CEATE TABLE SQL QUERY
        val CREATE_TABLE=("CREATE TABLE "+ TABLE_NAME+"("
                + COLUMN_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE_TITLE+"TEXT,"
                + COLUMN_NOTE_SUB_TITLE+"TEXT,"
                + COLUMN_NOTE_DESCRIPTION+"TEXT,"
                + COLUMN_NOTE_SET_REMINDER+"INTEGER,"
                + COLUMN_NOTE_REMINDER_TIME+"INTEGER,"
                + COLUMN_TIMESTAMP+"TEXT,"
                +")")
    }
}