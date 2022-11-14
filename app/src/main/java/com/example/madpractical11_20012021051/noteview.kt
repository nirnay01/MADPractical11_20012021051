package com.example.madpractical11_20012021051

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import com.example.madpractical11_20012021051.databinding.ActivityNoteviewBinding


class noteview : AppCompatActivity() {
    private lateinit var binding:ActivityNoteviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var note=intent.getSerializableExtra("Object") as ContactsContract.CommonDataKinds.Note
        binding=ActivityNoteviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(note){
            binding.noteTitle.text=this.title
            binding.noteSubtitle.text=this.subtitle
            binding.noteContent.text=this.Description
            binding.noteDate.text=this.modifiedTime
            this.calcReminder()
            if(this.isReminder)
            {
                binding.noteReminderDataTime.visibility=View.VISIBLE
                binding.noteReminderDateTime.text=this.getReminderText()
            }
            else
                binding.noteReminderDateTime.visibility=View.GONE
        }
    }
}