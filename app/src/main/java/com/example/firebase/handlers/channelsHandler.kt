package com.example.firebase.handlers

import com.example.firebase.models.channels
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class channelsHandler {
    var databases: FirebaseDatabase
    var channelRef: DatabaseReference

    init {
        databases = FirebaseDatabase.getInstance()
        channelRef = databases.getReference("yt_channels")
    }

    fun create(channel: channels): Boolean{
        val id = channelRef.push().key
        channel.id = id

        channelRef.child(id!!).setValue(channel)
        return true
    }

    fun update(channel: channels): Boolean{
        channelRef.child(channel.id!!).setValue(channel)
        return true
    }

    fun delete(channel: channels): Boolean{
        channelRef.child(channel.id!!).removeValue()
        return true
    }
}