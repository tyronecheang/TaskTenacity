package com.example.tasktenacity.data.quotes

class QuoteRepository {
    suspend fun loadQuote(): QuoteResponse {
        return QuoteApi.getRandomQuote()
    }
}
