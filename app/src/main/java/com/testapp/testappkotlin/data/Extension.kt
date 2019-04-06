package com.testapp.testappkotlin.data

import android.util.Base64
import com.testapp.testappkotlin.BuildConfig
import okhttp3.*
import okio.Buffer
import retrofit2.Retrofit
import java.io.File

inline fun <reified T> Retrofit.service(): T = create(T::class.java)

val defaultHeaders = arrayOf("Accept" to "application/json",
        "Content-Type" to "application/json")

fun OkHttpClient.Builder.addHeaders(vararg headers: Pair<String, String>): OkHttpClient.Builder = addInterceptor {
    it.proceed(it.request().newBuilder().apply {
        hashMapOf<String, String>().apply { putAll(headers) }.forEach { addHeader(it.key, it.value) }
    }.build())
}

fun OkHttpClient.Builder.withApiKey(): OkHttpClient.Builder = addInterceptor {
    it.proceed(it.request().run { newBuilder().apply {
        url(url().newBuilder().addQueryParameter("api_key",BuildConfig.TMDB_API_KEY).build())
    }}.build())
}

fun OkHttpClient.Builder.addHeadersWithDefault(vararg headers: Pair<String, String>): OkHttpClient.Builder =
        addHeaders(*defaultHeadersArray(*headers))

fun defaultHeadersMap(vararg pairs: Pair<String, String>): Map<String, String> =
        defaultHeadersArray(*pairs).toMap()

fun defaultHeadersArray(vararg pairs: Pair<String, String>): Array<Pair<String, String>> =
        arrayOf(*defaultHeaders, *pairs)

fun convertSignIntoBase64(login: String, pass: String): String {
    val text = "$login:$pass"
    val data = text.toByteArray(charset("UTF-8"))

    return Base64.encodeToString(data, Base64.DEFAULT)
}

fun File.toRequestBody(): RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this)

fun String.toRequestBody(): RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), this)

fun Response.bodyToString(): String {
    try {
        return cloneResponseBody().string()
    } catch (e: Exception) {
        return "{}"
    }
}

fun Response.cloneResponseBody(): ResponseBody = ResponseBody
        .create(body()!!.contentType(), body()!!.contentLength(), Buffer().apply { writeAll(body()?.source())})

fun Response.buildResponse(message: String): Response = newBuilder().message(message)
        .code(code())
        .body(body())
        .headers(headers())
        .networkResponse(networkResponse())
        .handshake(handshake())
        .build()

fun Interceptor.Chain.next(): Response = proceed(request())
