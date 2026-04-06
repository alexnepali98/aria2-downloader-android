package com.aria2.downloader

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.aria2.downloader.ui.navigation.AppNavHost
import com.aria2.downloader.ui.screens.settings.SettingsViewModel
import com.aria2.downloader.ui.theme.Aria2DownloaderTheme
import com.aria2.downloader.util.PermissionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Request permissions on Android 6+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!PermissionManager.hasAllPermissions(this)) {
                requestPermissions(
                    PermissionManager.REQUIRED_PERMISSIONS,
                    PermissionManager.PERMISSION_REQUEST_CODE
                )
            }
        }
        
        setContent {
            val navController = rememberNavController()
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val darkModeEnabled by settingsViewModel.darkModeEnabled.collectAsState()
            val systemDarkTheme = isSystemInDarkTheme()

            Aria2DownloaderTheme(
                darkTheme = darkModeEnabled || systemDarkTheme,
                dynamicColor = true
            ) {
                AppNavHost(navController = navController)
            }
        }
    }
    
    /**
     * Handle permission request results
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == PermissionManager.PERMISSION_REQUEST_CODE) {
            // Permission request was handled
            // App will continue to work with granted permissions
            // If critical permissions are denied, user will see errors during download
        }
    }
}
