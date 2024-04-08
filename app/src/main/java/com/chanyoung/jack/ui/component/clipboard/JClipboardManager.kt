package com.chanyoung.jack.ui.component.clipboard

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class JClipboardManager private constructor() {

    companion object {
        fun getClipboardManager(context : Context) : ClipboardManager {
            return ContextCompat.getSystemService(context, ClipboardManager::class.java)!!
        }

        @RequiresApi(Build.VERSION_CODES.R)
        fun debugClipMimeType(description: ClipDescription) {
            val html = description.hasMimeType(ClipDescription.MIMETYPE_TEXT_HTML)
            val intent = description.hasMimeType(ClipDescription.MIMETYPE_TEXT_INTENT)
            val plain = description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) //String, url
            val uriList = description.hasMimeType(ClipDescription.MIMETYPE_TEXT_URILIST)
            val unknown = description.hasMimeType(ClipDescription.MIMETYPE_UNKNOWN)
            Log.d(
                "clipType",
                "html $html intent $intent, plain $plain, uriList $uriList, unknown $unknown"
            )
        }

    }

}
