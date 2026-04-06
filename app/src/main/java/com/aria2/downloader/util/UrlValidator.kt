package com.aria2.downloader.util

import android.util.Patterns
import okhttp3.HttpUrl.Companion.toHttpUrl

object UrlValidator {
    
    /**
     * Validate URL format and accessibility
     */
    fun isValidUrl(url: String): Boolean {
        return try {
            // Check basic format
            if (!Patterns.WEB_URL.matcher(url).matches()) {
                return false
            }
            
            // Ensure valid HTTP/HTTPS
            val httpUrl = url.toHttpUrl()
            val scheme = httpUrl.scheme
            
            return scheme == "http" || scheme == "https"
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Sanitize and normalize URL
     */
    fun normalizeUrl(url: String): String {
        return try {
            val httpUrl = url.trim().toHttpUrl()
            httpUrl.toString()
        } catch (e: Exception) {
            url.trim()
        }
    }
    
    /**
     * Extract filename from URL
     */
    fun getFilenameFromUrl(url: String): String {
        return try {
            val httpUrl = url.toHttpUrl()
            val path = httpUrl.pathSegments.lastOrNull() ?: "download"
            
            // Remove query parameters
            val filename = path.substringBefore("?")
            
            if (filename.isNotEmpty()) filename else "download"
        } catch (e: Exception) {
            "download"
        }
    }
    
    /**
     * Check if URL is HTTPS
     */
    fun isHttps(url: String): Boolean {
        return try {
            url.toHttpUrl().scheme == "https"
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Get domain from URL
     */
    fun getDomain(url: String): String {
        return try {
            url.toHttpUrl().host
        } catch (e: Exception) {
            ""
        }
    }
}
