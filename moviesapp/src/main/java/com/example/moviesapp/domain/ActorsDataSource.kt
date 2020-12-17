package com.example.moviesapp.domain

import com.example.moviesapp.data.models.Actor

class ActorsDataSource {
    fun getActors(): List<Actor> {
        return listOf(
            Actor(
                actorName = "Toni",
                actorImage = "https://avatars.mds.yandex.net/get-zen_doc/1221883/pub_5d298fe646f4ff00ad7c265b_5d29f8d68600e100aeb21638/scale_1200"
            ),
            Actor(
                actorName = "Tor",
                actorImage = "https://get.wallhere.com/photo/Thor-movies-superhero-Marvel-Cinematic-Universe-Chris-Hemsworth-screenshot-computer-wallpaper-fictional-character-comic-book-229046.jpg"
            ),
            Actor(
                actorName = "Black  widow",
                actorImage = "https://avatars.mds.yandex.net/get-zen_doc/1706517/pub_5cc9d79757047600b302f851_5cca5607007b5200b31cb0c3/scale_1200"
            ),
            Actor(
                actorName = "Xalk",
                actorImage = "https://content.muhdo.com/wp-content/uploads/2017/10/MU0217_digibook-24.jpg"
            ),
            Actor(
                actorName = "Cap",
                actorImage = "https://img2.goodfon.ru/wallpaper/nbig/5/c0/mstiteli-era-altrona-7694.jpg"
            ),
            Actor(
                actorName = "Tom xoland",
                actorImage = "https://s2.best-wallpaper.net/wallpaper/2560x1920/1805/Spider-Man-Avengers-Infinity-War_2560x1920.jpg"
            )
        )
    }
}