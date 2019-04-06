package com.testapp.testappkotlin.domain.model

data class VideoPage(val results: List<Video>,
                     val page: Int,
                     val totalPages: Int,
                     val totalResults: Int)