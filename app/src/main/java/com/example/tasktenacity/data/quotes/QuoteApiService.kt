package com.example.tasktenacity.data.quotes

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import javax.net.ssl.X509TrustManager

// Implements a trust manager that accepts all SSL certificates (for quotable api)
object TrustAllCertificates : X509TrustManager {
    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
}

// Provides a configured Ktor HTTP client for fetching quotes
object QuoteApiService {
    val client = HttpClient(CIO) {
        engine {
            https {
                // Sets the custom trust manager for HTTPS connections
                trustManager = TrustAllCertificates
            }
        }
        // Installs the ContentNegotiation plugin for JSON serialization using Gson
        install(ContentNegotiation) {
            gson()
        }
    }
}
