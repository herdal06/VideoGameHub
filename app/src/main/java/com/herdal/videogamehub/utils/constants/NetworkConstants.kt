package com.herdal.videogamehub.utils.constants

object NetworkConstants {
    const val BASE_URL = "https://api.rawg.io/api/"

    object Endpoints {
        const val GAMES = "games"
        const val GAME_BY_ID = "games/{id}"

        const val GENRES ="genres"
        const val GENRE_BY_ID = "genres/{id}"

        const val SCREENSHOTS = "games/{id}/screenshots"

        const val STORES = "stores"

        const val TAGS = "tags"

        const val TRAILERS = "games/{id}/movies"
    }
}