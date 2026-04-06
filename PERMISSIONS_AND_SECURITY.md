# Permissions & Security — Aria2 Downloader Android

## Permissions Overview

The Aria2 Downloader Android app requires the following permissions to function:

### Internet & Network Permissions

| Permission | Purpose | Mandatory |
|-----------|---------|-----------|
| `android.permission.INTERNET` | Download files from remote servers | ✅ Yes |
| `android.permission.ACCESS_NETWORK_STATE` | Check network connectivity | ✅ Yes |

### Storage Permissions

| Permission | Platform | Purpose | Mandatory |
|-----------|----------|---------|-----------|
| `android.permission.READ_EXTERNAL_STORAGE` | Android 5-10 | Read downloaded files | ✅ Yes (legacy) |
| `android.permission.WRITE_EXTERNAL_STORAGE` | Android 5-10 | Save downloaded files | ✅ Yes (legacy) |
| `android.permission.MANAGE_EXTERNAL_STORAGE` | Android 11+ | Full file system access for downloads | ✅ Yes (Android 11+) |

### Media Permissions (Android 13+)

| Permission | Purpose | Mandatory |
|-----------|---------|-----------|
| `android.permission.READ_MEDIA_IMAGES` | Read image files | ⚠️ Context-dependent |
| `android.permission.READ_MEDIA_VIDEO` | Read video files | ⚠️ Context-dependent |
| `android.permission.READ_MEDIA_AUDIO` | Read audio files | ⚠️ Context-dependent |

### Notification Permission (Android 13+)

| Permission | Purpose | Mandatory |
|-----------|---------|-----------|
| `android.permission.POST_NOTIFICATIONS` | Show download progress notifications | ⚠️ Optional but recommended |

### Service Permissions

| Permission | Purpose | Mandatory |
|-----------|---------|-----------|
| `android.permission.FOREGROUND_SERVICE` | Run download service in background | ✅ Yes |
| `android.permission.FOREGROUND_SERVICE_DATA_SYNC` | Specify foreground service type | ✅ Yes |

---

## Runtime Permission Handling

### Android 6+ (API 23+) - Runtime Permissions

The app requests critical permissions at runtime using the Android permission API:

```kotlin
// In MainActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (!PermissionManager.hasAllPermissions(this)) {
            requestPermissions(
                PermissionManager.REQUIRED_PERMISSIONS,
                PermissionManager.PERMISSION_REQUEST_CODE
            )
        }
    }
    // ... continue with content setup
}
```

### Permission Checking

The `PermissionManager` utility provides convenient methods:

```kotlin
import com.aria2.downloader.util.PermissionManager

// Check if all required permissions are granted
if (PermissionManager.hasAllPermissions(context)) {
    // Safe to proceed with downloads
}

// Get missing permissions
val missing = PermissionManager.getMissingPermissions(context)
if (missing.isNotEmpty()) {
    // Request missing permissions
}
```

---

## Network Security Configuration

### HTTP/HTTPS Handling

The app uses `network_security_config.xml` to configure secure network policies:

```xml
<!-- Allow HTTPS by default (recommended) -->
<!-- Allow cleartext HTTP only for specific domains (testing/demo) -->
```

**Location:** `app/src/main/res/xml/network_security_config.xml`

#### Production Recommendations

For production deployments:

1. **Enable HTTPS only** — Remove cleartext HTTP support
   ```xml
   <domain-config cleartextTrafficPermitted="false">
       <domain includeSubdomains="true">*</domain>
   </domain-config>
   ```

2. **Implement certificate pinning** — Pin trusted certificates
   ```xml
   <pin-set expiration="2027-01-01">
       <pin digest="SHA-256">your-certificate-hash==</pin>
   </pin-set>
   ```

3. **Restrict to specific domains** — List allowed domains only
   ```xml
   <domain-config cleartextTrafficPermitted="false">
       <domain includeSubdomains="true">example.com</domain>
       <domain includeSubdomains="true">api.example.com</domain>
   </domain-config>
   ```

---

## File Provider & File Access

### Android 7+ (API 24+) - File URI Restrictions

The app uses `FileProvider` to securely share files:

**Provider Configuration:** `app/src/main/res/xml/file_paths.xml`

**Paths Exported:**
- `downloads/` — App-specific downloads directory
- `external_files/` — External files directory
- `cache/` — Cache directory
- `external_cache/` — External cache directory

**Usage in Code:**

```kotlin
val fileUri = FileProvider.getUriForFile(
    context,
    "${context.packageName}.fileprovider",
    downloadedFile
)

// Share with other apps
val shareIntent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_STREAM, fileUri)
    type = "application/octet-stream"
}
```

---

## URL Validation & Security

The `UrlValidator` utility provides secure URL handling:

```kotlin
import com.aria2.downloader.util.UrlValidator

// Validate URL format
if (UrlValidator.isValidUrl(url)) {
    // URL is properly formatted
}

// Check if HTTPS
if (UrlValidator.isHttps(url)) {
    // Secure connection
}

// Extract filename safely
val filename = UrlValidator.getFilenameFromUrl(url)

// Get domain for security checks
val domain = UrlValidator.getDomain(url)
```

---

## SSL/TLS Certificate Validation

### Secure HTTP Client Configuration

The `HttpClientFactory` provides secure OkHttpClient configuration:

```kotlin
import com.aria2.downloader.util.HttpClientFactory

// Create secure HTTP client
val client = HttpClientFactory.createHttpClient(context)

// Features:
// - Enforces TLS/SSL for HTTPS
// - Validates certificates
// - Configurable timeouts
// - Optional HTTP logging for debugging
```

### Manual Certificate Pinning

For enhanced security with specific servers:

```kotlin
val certificatePinner = CertificatePinner.Builder()
    .add("example.com", "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
    .build()

val client = OkHttpClient.Builder()
    .certificatePinner(certificatePinner)
    .build()
```

---

## Background Download Service

### Foreground Service Configuration

The `DownloadService` runs as a foreground service with:

```xml
<service
    android:name=".service.DownloadService"
    android:exported="true"
    android:foregroundServiceType="dataSync" />
```

**Foreground Service Types:**
- `dataSync` — For background data synchronization
- Shows notification while downloading

---

## Best Practices

### For Users

1. **Grant Permissions** — Allow storage and notification permissions for full functionality
2. **Use HTTPS URLs** — Prefer HTTPS downloads for security
3. **Verify URLs** — Copy URLs from trusted sources
4. **Check Storage** — Ensure sufficient disk space for downloads

### For Developers

1. **Validate URLs** — Always validate before downloading
   ```kotlin
   val url = userInput.trim()
   if (!UrlValidator.isValidUrl(url)) {
       showError("Invalid URL")
       return
   }
   ```

2. **Handle Permission Denials** — Gracefully handle denied permissions
   ```kotlin
   if (!PermissionManager.hasAllPermissions(context)) {
       showPermissionDialog()
   }
   ```

3. **Use HTTPS** — Configure network security for production
   ```xml
   <domain-config cleartextTrafficPermitted="false">
       <domain includeSubdomains="true">*</domain>
   </domain-config>
   ```

4. **Implement Error Handling** — Handle certificate and SSL errors
   ```kotlin
   .addNetworkInterceptor { chain ->
       try {
           chain.proceed(chain.request())
       } catch (e: SSLPeerUnverifiedException) {
           // Handle certificate validation failure
           throw e
       }
   }
   ```

5. **Test with Real URLs** — Test downloads with actual URLs
   ```
   https://example.com/file.zip
   https://api.example.com/download
   ```

---

## Troubleshooting

### "Permission Denied" Error

**Solution:** Ensure permissions are granted in Settings → Apps → Aria2 Downloader → Permissions

### "HTTPS Certificate Error"

**Solution:** Verify the server's SSL certificate is valid. For testing:
- Use `https://httpbin.org/get` (public test server)
- Check network security config allows the domain

### "HTTP Connection Failed"

**Solution:** 
1. Check network connectivity
2. Verify URL is correct
3. For HTTP (not HTTPS), ensure network security config allows it

### "File Not Found After Download"

**Solution:**
1. Check file permissions (Settings → Storage → Files)
2. Verify file path is accessible
3. Use FileProvider for file access

---

## Security Summary

| Feature | Status | Details |
|---------|--------|---------|
| Runtime Permissions | ✅ Implemented | Android 6+ support |
| HTTPS Support | ✅ Implemented | Default secure config |
| Certificate Validation | ✅ Implemented | System trust store |
| File Provider | ✅ Implemented | Android 7+ safe file access |
| Network Security | ✅ Configured | Cleartext HTTP configurable |
| URL Validation | ✅ Implemented | Format & scheme checking |
| SSL/TLS | ✅ Configured | Strong ciphers, TLS 1.2+ |

---

## References

- [Android Permissions Documentation](https://developer.android.com/guide/topics/permissions)
- [Network Security Configuration](https://developer.android.com/training/articles/security-config)
- [FileProvider Guide](https://developer.android.com/reference/androidx/core/content/FileProvider)
- [HTTPS Best Practices](https://developer.android.com/training/articles/security-ssl)
