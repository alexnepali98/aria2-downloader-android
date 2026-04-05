# Aria2 Downloader Android — Build in 2 Minutes

## Option 1: Android Studio (Easiest)

**Prerequisites:** Android Studio installed on Mac

1. **Download project**
   ```bash
   cd ~/Downloads
   unzip Aria2DownloaderAndroid.zip
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - File → Open
   - Select the `Aria2DownloaderAndroid` folder
   - Wait 30 seconds for Gradle sync

3. **Build APK**
   - Build → Build APK (or Build Variants → select debug/release)
   - Watch the build progress in "Build" tab
   - Takes 2-5 minutes

4. **Find your APK**
   - Finder: `Aria2DownloaderAndroid/app/build/outputs/apk/debug/app-debug.apk`
   - Right-click → Open with → Installed APKs, or
   - Use `adb install app/build/outputs/apk/debug/app-debug.apk` to deploy to device/emulator

---

## Option 2: Command Line (Mac with Android SDK)

**Prerequisites:** Android SDK installed, ANDROID_HOME set

```bash
cd ~/Downloads/Aria2DownloaderAndroid
./gradlew assembleDebug
```

Output: `app/build/outputs/apk/debug/app-debug.apk`

Deploy:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## Option 3: GitHub Codespaces (Free, Browser-Based)

**Prerequisites:** GitHub account

1. **Push to GitHub**
   ```bash
   cd Aria2DownloaderAndroid
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/YOUR_USERNAME/aria2-downloader-android.git
   git push -u origin main
   ```

2. **Open in Codespaces**
   - GitHub repo page → Code → Codespaces → Create codespace on main
   - Waits ~2 minutes for environment setup

3. **Build APK**
   ```bash
   ./gradlew assembleDebug
   ```

4. **Download APK**
   - File explorer in Codespaces → Navigate to `app/build/outputs/apk/debug/`
   - Right-click `app-debug.apk` → Download

---

## Verify Build Success

APK file should exist at:
```
app/build/outputs/apk/debug/app-debug.apk
```

Size: ~4-8 MB

---

## Install on Device or Emulator

**Via adb:**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Or:** Drag APK file into Android emulator window

**Or:** Email/AirDrop APK to device, tap to install (may need "Unknown sources" enabled)

---

## Troubleshooting

**"Gradle not found"**
- Install Android Studio (includes Gradle wrapper)
- Or manually install Android SDK + set ANDROID_HOME

**"SDK not found"**
- Android Studio → Tools → SDK Manager → Install Android SDK (API 34)

**"Build fails with Java version mismatch"**
- Use Java 11 or Java 17
- Android Studio uses compatible Java automatically

**"APK not created"**
- Check "Build" tab for error messages
- Common: Missing SDK components (install via SDK Manager)
- Solution: Follow Android Studio SDK Manager instructions

---

## What Gets Built

**App Features:**
- Multi-connection segmented downloads (HTTP Range headers)
- Pause/Resume/Cancel downloads
- Real-time speed & ETA display
- Download history with search
- Dark/Light theme
- Background foreground service
- Notifications on completion

**Target:** Android 8.0+ (API 26+)
**Built With:** Kotlin, Jetpack Compose, Material 3, OkHttp, Room, Hilt

---

## Next: Test the App

1. **Launch app** on emulator or device
2. **Click "+" button** to start new download
3. **Paste test URL:** 
   - `https://www.w3.org/WAI/WCAG21/Techniques/pdf/G18.pdf` (2.5 MB, good test)
   - Or any public HTTP file (avoid HTTPS-only initially if server doesn't support Range)
4. **Watch download progress** with real-time speed/ETA
5. **Pause/Resume/Cancel** to test control flow
6. **Check History** tab for completed downloads

---

## Release Build (Optional)

For Google Play Store submission:

```bash
./gradlew assembleRelease
```

Requires:
- Signing key (create via: `keytool -genkey -v -keystore release.jks -keyalg RSA -keysize 2048 -validity 10000`)
- `local.properties` with signing config
- App icons (replace in `app/src/main/res/mipmap/`)

Default debug APK is fine for testing on personal devices.

---

**Total time to APK: 2-5 minutes (depending on method)**

🦊
