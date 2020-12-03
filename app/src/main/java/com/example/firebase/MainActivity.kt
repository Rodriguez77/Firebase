package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.firebase.handlers.channelsHandler
import com.example.firebase.models.channels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var Channels: ArrayList<channels>
    lateinit var channelListView: ListView
    lateinit var channelsHandler: channelsHandler
    lateinit var channel: channels
    lateinit var btn_add: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        channelListView = findViewById(R.id.lv_main)
        btn_add = findViewById(R.id.button)
        Channels = ArrayList()
        channelsHandler = channelsHandler()
        registerForContextMenu(channelListView)



        btn_add.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.channels_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId) {
            R.id.menu_edit -> {
                channel = Channels[info.position]
                var intent = Intent(this,EditActivity::class.java)
                intent.putExtra("data", channel)
                startActivity(intent)
                true
            }
            R.id.menu_delete -> {
                if(channelsHandler.delete(Channels[info.position])){
                    Toast.makeText(applicationContext, "Youtube Channel deleted successfully", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onStart() {
        super.onStart()

        channelsHandler.channelRef.orderByChild("rank").addValueEventListener(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               Channels.clear()
                snapshot.children.forEach{
                        it -> val channel = it.getValue(channels::class.java)
                    Channels.add(channel!!)
                }

                val adapter = ArrayAdapter<channels>(applicationContext, android.R.layout.simple_list_item_1, Channels)
                channelListView.adapter = adapter
            }
            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add -> {
                startActivity(Intent(this, AddActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}