package com.herdal.videogamehub.utils

/*
Delete the <p> tag in the data from the @GenreUiModel description.
 */
fun removePTag(str: String?): String? {
    return str?.substring(3, str.length - 4) // each description has the same <p> tag
}