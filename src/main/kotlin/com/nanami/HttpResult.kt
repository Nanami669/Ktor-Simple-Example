package com.nanami

import com.nanami.domain.Movie

sealed class HttpResult {
    data class Success(
        val movie: Movie,
    ) : HttpResult()

    data class Failure(
        val error: String,
    ) : HttpResult()
}
