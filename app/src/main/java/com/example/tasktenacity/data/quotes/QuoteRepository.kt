package com.example.tasktenacity.data.quotes

// Repository class responsible for fetching quotes
class QuoteRepository {
    // Suspended function that retrieves a random quote from the Quote API
    suspend fun loadQuote(): QuoteResponse {
        return QuoteApi.getRandomQuote()  // Calls the QuoteApi to get a random quote
    }
}
