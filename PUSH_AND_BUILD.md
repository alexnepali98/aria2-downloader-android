# Push to GitHub & Get APK in 15 Minutes

## Your GitHub Credentials
```
Username: alexnepali98
PAT: [Your Personal Access Token with repo scope]
```

---

## 🎯 Quick Start (5 minutes)

### 1️⃣ Create GitHub Repo
Go to: **https://github.com/new**
- Repository name: `aria2-downloader-android`
- Visibility: **Public** (recommended for free Actions minutes)
- **DO NOT** initialize with README/License/gitignore (you have these already)
- Click **Create repository**

### 2️⃣ Push Your Code
```bash
# Navigate to your project directory
cd /path/to/Aria2DownloaderAndroid

# Add GitHub as remote
git remote add origin https://github.com/alexnepali98/aria2-downloader-android.git

# Rename master → main (GitHub standard)
git branch -M main

# Push everything to GitHub
git push -u origin main
```

When Git asks for credentials:
```
Username: alexnepali98
Password (paste your PAT): [Your Personal Access Token with repo scope]
```

### 3️⃣ Watch Automatic Build (5-10 minutes)
1. Go to: `https://github.com/alexnepali98/aria2-downloader-android`
2. Click **Actions** tab
3. You'll see "Build APK" workflow running
4. Wait for ✅ green checkmark

### 4️⃣ Download APK (1 minute)
Once build completes:

**Option A: Via Artifacts**
- Click the passing workflow run
- Scroll down → **Artifacts** section
- Click `app-debug` folder
- Download `app-debug.apk`

**Option B: Via Releases**
- Click **Releases** tab
- Latest release will be "APK Build #1" (or higher number)
- Download `app-debug.apk`

---

## 🚀 What Just Happened

GitHub Actions automatically:

1. **Detected your push** to `main` branch
2. **Spun up Ubuntu Linux VM** in GitHub's cloud
3. **Installed JDK 11** (Java compiler)
4. **Downloaded Android SDK** (tools to build Android apps)
5. **Downloaded Gradle dependencies** (libraries your app needs)
6. **Compiled your Kotlin code** → bytecode
7. **Packaged everything** into APK
8. **Stored APK** as artifact (30-day retention)
9. **Created GitHub Release** with the APK

**All automatic. You just pushed once.**

---

## 📦 Install APK on Device

### Via adb (Command Line)
```bash
adb install app-debug.apk
```

### Via Android Emulator
Drag the APK file into the emulator window. It installs automatically.

### Via Phone
1. Email/AirDrop APK to your phone
2. Tap the file to install
3. Accept permissions
4. Done

---

## 🔄 Future Builds

**To rebuild APK in the future:**

1. Edit code locally
2. Commit changes: `git commit -am "Your message"`
3. Push to GitHub: `git push origin main`
4. GitHub Actions automatically rebuilds
5. Download new APK from Actions/Releases

**No extra steps needed. Every push = automatic new APK.**

---

## ⚙️ GitHub Actions Workflow Details

Your project includes `.github/workflows/build-apk.yml` which:

- **Triggers on:** Every push to `main` or `master` branch
- **Runs on:** Ubuntu latest (free tier)
- **Steps:**
  - Checkout code
  - Set up JDK 11
  - Make gradlew executable
  - Run: `./gradlew assembleDebug`
  - Upload APK as artifact
  - Create release with APK

**Estimated time:** 5-10 minutes (first build slower due to dependency downloads, subsequent builds ~3-5 min)

---

## ❓ Troubleshooting

### Build Fails with "Gradle wrapper not found"
- Confirm `gradlew` file is in project root
- Check file is executable: `ls -la gradlew`
- It should start with: `-rwxr-xr-x`

### Build Fails with "SDK not found"
- GitHub Actions auto-installs Android SDK
- This is rare, usually means your build.gradle has wrong SDK version
- Check: `compileSdk 34` in `app/build.gradle.kts`

### Build Succeeds but APK Not Downloaded
- Check the workflow output for actual build success (should say "BUILD SUCCESSFUL")
- APK should be at: `app/build/outputs/apk/debug/app-debug.apk`
- Artifact retention is 30 days

### Taking Too Long?
- First build downloads ~500MB of dependencies (Gradle, SDK, libraries)
- Can take 10-15 minutes first time
- Subsequent builds are cached, take 3-5 minutes

---

## 🔐 GitHub PAT Security Note

Your PAT has been provided. Keep it safe:
- Don't commit it to git (it's already in .gitignore if you use `git credential` manager)
- Don't share it in screenshots
- It has full repo access, so guard it like a password

For better security on future projects:
- Create GitHub tokens with minimal scopes
- Use GitHub's built-in `GITHUB_TOKEN` (auto-provided in Actions)

---

## ✅ Success Checklist

- [ ] Repository created on GitHub
- [ ] Git remote added: `git remote -v` shows origin
- [ ] Code pushed: `git push origin main` succeeds
- [ ] Actions tab shows "Build APK" workflow
- [ ] Workflow completes with ✅ (green checkmark)
- [ ] Artifact downloaded: `app-debug.apk` in your Downloads
- [ ] APK installed on device/emulator
- [ ] App launches and works

---

## 📞 Next Steps

**To test the app:**
1. Launch "Aria2 Downloader" on your device
2. Tap "+" button to start new download
3. Paste a test URL: `https://www.w3.org/WAI/WCAG21/Techniques/pdf/G18.pdf` (2.5 MB)
4. Watch real-time progress with speed/ETA
5. Test pause/resume/cancel buttons
6. Check History tab for completed downloads

**To make changes:**
1. Edit code locally (in Android Studio or VibeCode)
2. Commit: `git commit -am "Your change description"`
3. Push: `git push origin main`
4. APK rebuilds automatically in 3-5 minutes

---

**Ready to push? Follow steps 1-4 above. You'll have APK in hand in ~15 minutes.** 🦊
