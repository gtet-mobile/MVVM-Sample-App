package com.example.mvvmsampleapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Define an extension function on CoroutineScope
fun CoroutineScope.launchOnCoroutineScope(block: suspend CoroutineScope.() -> Unit) {
    // Launch a coroutine on the IO dispatcher
    launch(Dispatchers.IO) {
        block()
    }
}