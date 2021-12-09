package com.pandorina.legendaryquotes.data

import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.model.Quote

object Resources {
    val quoteList = listOf(
        Quote(
            "If you want to live a happy life, tie it to a goal, not to people or things.",
            " Albert Einstein",
            1
        ),
        Quote(
            "Your time is limited, so don’t waste it living someone else’s life. Don’t be trapped by dogma which is living with the results of other people’s thinking.",
            "Steve Jobs",
            2,
            true
        ),
        Quote(
            "The whole secret of a successful life is to find out what is one’s destiny to do, and then do it.",
            "Henry Ford",
            3
        ),
        Quote(
            "Don’t settle for what life gives you; make life better and build something.",
            "Ashton Kutcher",
            4
        ),
        Quote(
            "Everything negative – pressure, challenges is all an opportunity for me to rise.",
            "Kobe Bryant",
            5,
            true
        ),
        Quote(
            "The advance of technology is based on making it fit in so that you don't really even notice it, so it's part of everyday life.",
            "Bill Gates",
            6
        ),
        Quote(
            "The villagers are the lord of the nation.",
            "Mustafa Kemal Atatürk",
            7
        ),
        Quote(
            "If food was a kingdom, Adana Kebab would definitely be king.",
            "Ahmet Faruk Çuha",
            8
        ),
        Quote(
            "Don\'t forget why you were born, son!",
            "Adem Çuha",
            9
        ),
        Quote(
            "Two things are unstoppable: A maglev train and last level french royal knights.",
            "Ahmet Faruk Çuha",
            10,
            true
        ),
        Quote(
            "Dear jedidroid;\nThere is an awesome gift for you in the favorite fragment.",
            "Ahmet Faruk Çuha",
            11,
            true
        )
    )

    val backgroundList = listOf(
        R.drawable.image_bacground_coast_2,
        R.drawable.image_background_dark_desert,
        R.drawable.image_background_blue_desert,
        R.drawable.image_background_dark_forest,
        R.drawable.image_background_mountain,
        R.drawable.image_background_desert,
        R.drawable.image_background_metropole,
        R.drawable.image_background_leafs,
        R.drawable.image_background_cliff,
        R.drawable.image_background_ocean,
        R.drawable.image_background_on_the_air,
        R.drawable.image_background_sunset,
        R.drawable.image_background_utah
    )

    val backgroundListForWidget = listOf(
        R.drawable.image_background_leafs,
        R.drawable.image_background_sunset,
        R.drawable.image_background_on_the_air,
        R.drawable.image_background_metropole,
        R.drawable.image_background_dark_forest
    )

    val quoteListForWidget = listOf(
        quoteList[4],
        quoteList[3],
        quoteList[2],
        quoteList[5]
    )
}