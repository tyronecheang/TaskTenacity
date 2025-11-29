package com.example.tasktenacity.data.quotes

// Data class representing the structure of a quote returned by the API
data class QuoteResponse(
    val _id: String,     // Unique identifier for the quote
    val content: String, // The text/content of the quote
    val author: String   // The author of the quote
)
