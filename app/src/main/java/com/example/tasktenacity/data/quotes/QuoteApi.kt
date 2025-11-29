package com.example.tasktenacity.data.quotes

import io.ktor.client.call.*
import io.ktor.client.request.*

object QuoteApi {
    // Suspend function to fetch a random motivational quote from the Quotable API
    suspend fun getRandomQuote(): QuoteResponse {
        // Make a GET request to the Quotable API with specific tags
        // Tags used: success, motivational, business, and life
        // The response is parsed into a QuoteResponse object
        return QuoteApiService.client
            .get("https://api.quotable.io/random?tags=success|motivational|business|life")
            .body() // Extracts the body of the response as a QuoteResponse
    }
}
