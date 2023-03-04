package com.ichi.moviedatabase.core

sealed class Result<T> {
    class Loading<T>: Result<T>()
    class Success<T>(val data: T): Result<T>()
    // Can add a class Error here in the future if needed
}
