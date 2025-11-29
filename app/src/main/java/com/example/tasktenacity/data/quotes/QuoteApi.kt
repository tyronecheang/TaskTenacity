package com.example.tasktenacity.data.quotes

import io.ktor.client.call.*
import io.ktor.client.request.*

object QuoteApi {
    suspend fun getRandomQuote(): QuoteResponse {
        return QuoteApiService.client
            .get("https://api.quotable.io/random?tags=success|motivational|business|life")
            .body()
    }
}
