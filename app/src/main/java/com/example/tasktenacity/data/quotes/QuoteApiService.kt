package com.example.tasktenacity.data.quotes

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson
import javax.net.ssl.X509TrustManager

object TrustAllCertificates : X509TrustManager {
    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
}

object QuoteApiService {
    val client = HttpClient(CIO) {
        engine {
            https {
                trustManager = TrustAllCertificates
            }
        }
        install(ContentNegotiation) {
            gson()
        }
    }
}
