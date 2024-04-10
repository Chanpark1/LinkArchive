package com.chanyoung.jack.application

import android.util.Patterns
import com.chanyoung.jack.R
import java.util.regex.Pattern

class WebUrlUtil {
    companion object {
        fun checkUrlPrefix(url : String) : Boolean {

            return url.startsWith("https://")
                    || url.startsWith("http://")
                    || url.startsWith("https://www.")
                    || url.startsWith("http://www.")
        }

    }
}

object ErrorMessages {
    const val GROUP_EMPTY : String = "Select your Group \uD83D\uDE22"
    const val TITLE_EMPTY : String = "Title is empty \uD83D\uDE22"
    const val URL_EMPTY : String = "Link Url is empty \uD83D\uDE22"
    const val INVALID_URL : String = "Invalid URL \uD83D\uDE22"
    const val GROUP_EXISTS : String= "Group already exists!"
}