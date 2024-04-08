package com.chanyoung.jack.application

import android.util.Patterns
import java.util.regex.Pattern

class WebUrlUtil {
    companion object {
        val WEB_URL: Pattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
        )

        fun addHttpPrefix(url : String) : String {
            return if (!WEB_URL.matcher(url).find()) {
                "https://www.$url"
            } else {
                url
            }
        }

        fun isValidUrl(url: String) : Boolean = Patterns.WEB_URL.matcher(url).matches()

    }
}