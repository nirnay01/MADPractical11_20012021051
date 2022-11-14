package com.example.madpractical11_20012021051

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
       if(intent!=null && context!=null){
           val note =Note()
           note.id=intent.getIntExtra(Note.NOTE_ID_KEY,-1)
           note.title=intent.getStringExtra(Note.NOTE_TITLE_KEY)!!
           note.subtitle=intent.getStringExtra(Note.NOTE_SUBTILTE_KEY)!!
           note.Descripton=intent.getStringExtra(Note.NOTE_DESCRIPTION_KEY)!!
           note.modifiedTime=intent.getStringExtra(Note.NOTE_MODIFIED_TIME_KEY)!!
           note.remindertime=intent.getLongExtra(Note.NOTE_REMINDER_TOME_KEY,0)
           note.isReminder=true
           Log.i(TAG, "onReceive: Note:$note")
           notificationDailog(
               context,
               NoteViewActivity::class.java
               note.title,
               note.Description,
               note
           )
       }
    }
}