package com.example.submissionawalintermediate

import com.example.submissionawalintermediate.response.ListStoryItem
import com.google.gson.annotations.SerializedName

object DataDummy {

    fun generateDummyStoryItems(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "www.photo.com/$i",
                "1-1-$i",
                "author $i",
                "desc $i",
                i+2.toDouble(),
                "id$i",
                i+1.toDouble()
            )
            items.add(story)
        }
        return items
    }
}
