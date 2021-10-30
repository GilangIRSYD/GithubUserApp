package com.catatancodingku.githubuserapp.callback

interface DatabaseCallback<T> {
    fun onDelete(data : T)
}