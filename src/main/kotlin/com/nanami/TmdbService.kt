package com.nanami

import com.nanami.domain.Movie
import io.ktor.client.call.body
import io.ktor.http.isSuccess

object TmdbService {
    suspend fun getStarWars(): HttpResult =
        TmdbClient().get(Properties.ThirdParty.starWarsUrl).run {
            if (!status.isSuccess()) {
                HttpResult.Failure("oopsie doopsie...")
            } else {
                HttpResult.Success(body<Movie>())
            }
        }
}
