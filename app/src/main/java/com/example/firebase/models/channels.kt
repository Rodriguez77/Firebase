package com.example.firebase.models
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

class channels (var id: String? = "", var title: String? = "",
                var link: String? = "", var rank: Int? = 0, var reason: String? = ""):Serializable {


    override fun toString(): String {
        return  "Ranking: $rank \n\n"+
                "Name of Channel: $title \n\n"+
                "Youtube link: $link \n\n"+
                "My reason for liking the channel: $reason\n\n"
    }
}
