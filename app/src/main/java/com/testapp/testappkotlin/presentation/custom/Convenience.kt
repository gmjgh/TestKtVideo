package com.testapp.testappkotlin.presentation.custom

import kotlinx.coroutines.*


fun onUi(parentJob: Job = Job(), closure: () -> Unit) = GlobalScope.launch(Dispatchers.Main + parentJob) {
    closure()
}

fun onBackground(parentJob: Job = Job(), closure: () -> Unit) = GlobalScope.launch(Dispatchers.IO + parentJob) {
    closure()
}

fun <T> onImmediate(parentJob: Job = Job(), closure: () -> T?): T? = runBlocking {
    withContext(Dispatchers.IO + parentJob) {
        closure()
    }
}