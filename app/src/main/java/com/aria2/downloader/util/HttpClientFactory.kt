package com.aria2.downloader.util

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyStore
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

object HttpClientFactory {
    
    /**
     * Create OkHttpClient with secure defaults
     */
    fun createHttpClient(context: Context? = null, enableLogging: Boolean = false): OkHttpClient {
        val builder = OkHttpClient.Builder()
        
        // Set timeouts
        builder.apply {
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            callTimeout(60, TimeUnit.SECONDS)
        }
        
        // Add logging interceptor (only in debug builds)
        if (enableLogging) {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            builder.addInterceptor(loggingInterceptor)
        }
        
        // Configure SSL/TLS
        try {
            val trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm()
            )
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers = trustManagerFactory.trustManagers
            
            if (trustManagers.isNotEmpty() && trustManagers[0] is X509TrustManager) {
                val trustManager = trustManagers[0] as X509TrustManager
                
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, arrayOf(trustManager), null)
                
                builder.sslSocketFactory(sslContext.socketFactory, trustManager)
            }
        } catch (e: Exception) {
            // Use default SSL context if configuration fails
        }
        
        return builder.build()
    }
    
    /**
     * Get trust manager for certificate validation
     */
    fun getTrustManager(): X509TrustManager {
        val trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm()
        )
        trustManagerFactory.init(null as KeyStore?)
        
        return trustManagerFactory.trustManagers[0] as X509TrustManager
    }
}
