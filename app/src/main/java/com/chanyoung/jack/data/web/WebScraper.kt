package com.chanyoung.jack.data.web

import com.chanyoung.jack.application.WebUrlUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException
import javax.inject.Inject

class WebScraper @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private val _query = "meta[property^=og:]"
    private val _property = "property"
    private val _image = "og:image"
    private val _title = "og:title"
    private val  _content = "content"

    suspend fun setScrap(data: String): Pair<String?, String?> = withContext(Dispatchers.IO) {

        var title: String? = null
        var imageUrl: String? = null

        if(data.isEmpty() || !WebUrlUtil.checkUrlPrefix(data)) return@withContext Pair("", "")

        try {
            val request = Request.Builder().url(data).build()
            val response = okHttpClient.newCall(request).execute()

            if(!response.isSuccessful) return@withContext Pair("", "")

            val document = Jsoup.connect(data).get()
            val elements = document.select(_query)
            elements?.let {
                it.forEach { element ->
                    when (element.attr(_property)) {
                        _title -> {
                            element.attr(_content)?.let {
                                title = element.attr(_content)
                            }
                        }
                        _image -> {
                            element.attr(_content)?.let {
                                imageUrl = element.attr(_content)
                            }
                        }
                    }
                }
            }
            Pair(title, imageUrl)
        } catch (e: IOException) {
            Pair("", "")
        }
    }
}