package com.example.madpractical11_20012021051

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // Database Version
        private const val DATABASE_VERSION = 1
        // Database Name
        private const val DATABASE_NAME = "notes_db"
    }
    // Creating Tables
    override fun onCreate(db: SQLiteDatabase) {
        // create notes table
        db.execSQL(NoteData.CREATE_TABLE)
    }
    // Upgrading database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NoteData.TABLE_NAME)
        // Create tables again
        onCreate(db)
    }
    fun insertNote(note: Note): Long {
        // get writable database as we want to write data
        val db = this.writableDatabase
        // insert row
        val id = db.insert(NoteData.TABLE_NAME, null, getValues(note))
        // close db connection
        db.close()
        // return newly inserted row id
        return id
    }
    private fun getValues(note: Note): ContentValues {
        val values = ContentValues()
        // `id` will be inserted automatically.
        // no need to add them
        values.put(NoteData.COLUMN_NOTE_TITLE, note.title)
        values.put(NoteData.COLUMN_NOTE_SUB_TITLE, note.subTitle)
        values.put(NoteData.COLUMN_NOTE_DESCRIPTION, note.Description)
        values.put(NoteData.COLUMN_NOTE_REMINDER_TIME, note.remindertime)
        values.put(NoteData.COLUMN_NOTE_SET_REMINDER, note.isReminder)
        values.put(NoteData.COLUMN_TIMESTAMP, note.modifiedTime)
        return values
    }
    fun getNote(id: Long): Note {
        // get readable database as we are not inserting anything
        val db = this.readableDatabase
        val cursor = db.query(
            NoteData.TABLE_NAME,
            arrayOf(NoteData.COLUMN_ID,
                NoteData.COLUMN_NOTE_TITLE,
                NoteData.COLUMN_NOTE_SUB_TITLE,
                NoteData.COLUMN_NOTE_DESCRIPTION,
                NoteData.COLUMN_NOTE_SET_REMINDER,
                NoteData.COLUMN_NOTE_REMINDER_TIME,
                NoteData.COLUMN_TIMESTAMP),
            NoteData.COLUMN_ID.toString() + "=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        val note = getNote(cursor)

        // close the db connection
        cursor!!.close()
        return note
    }
    private fun getNote(cursor: Cursor): Note
    {
        // prepare note object
        val note = Note(
            cursor.getString(cursor.getColumnIndexOrThrow(NoteData.COLUMN_NOTE_TITLE)),
            cursor.getString(cursor.getColumnIndexOrThrow(NoteData.COLUMN_NOTE_SUB_TITLE)),
            cursor.getString(cursor.getColumnIndexOrThrow(NoteData.COLUMN_NOTE_DESCRIPTION)),
            cursor.getString(cursor.getColumnIndexOrThrow(NoteData.COLUMN_TIMESTAMP))
        )
        note.isReminder = (cursor.getInt(cursor.getColumnIndexOrThrow(NoteData.COLUMN_NOTE_SET_REMINDER))==1)
        note.remindertime = cursor.getLong(cursor.getColumnIndexOrThrow(NoteData.COLUMN_NOTE_REMINDER_TIME))
        note.id = cursor.getInt(cursor.getColumnIndexOrThrow(NoteData.COLUMN_ID))
        return note
    }
    // Select All Query
    val allNotes: ArrayList<Note>
        get() {
            val notes = ArrayList<Note>()
            // Select All Query
            val selectQuery = "SELECT  * FROM " + NoteData.TABLE_NAME.toString() + " ORDER BY " +
                    NoteData.COLUMN_TIMESTAMP.toString() + " DESC"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    notes.add(getNote(cursor))
                } while (cursor.moveToNext())
            }
            // close db connection
            db.close()
            // return notes list
            return notes
        }
    val notesCount: Int
        get() {
            val countQuery = "SELECT  * FROM " + NoteData.TABLE_NAME
            val db = this.readableDatabase
            val cursor = db.rawQuery(countQuery, null)
            val count = cursor.count
            cursor.close()
            // return count
            return count
        }
    fun updateNote(note: Note): Int {
        val db = this.writableDatabase
        // updating row
        return db.update(
            NoteData.TABLE_NAME,
            getValues(note),
            NoteData.COLUMN_ID + " = ?",
            arrayOf(note.id.toString())
        )
    }
    fun deleteNote(note: Note) {
        val db = this.writableDatabase
        db.delete(
            NoteData.TABLE_NAME,
            NoteData.COLUMN_ID + " = ?",
            arrayOf(note.id.toString())
        )
        db.close()
    }
}