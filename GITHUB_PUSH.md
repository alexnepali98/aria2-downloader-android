# Push to GitHub & Auto-Build APK

Your git repository is ready to push. Follow these steps to auto-build the APK via GitHub Actions.

## Step 1: Create Repository on GitHub

```bash
# Go to https://github.com/new
# Create new repository named: aria2-downloader-android
# Do NOT initialize with README (you have files already)
# Public or Private (your choice)
```

## Step 2: Add Remote and Push

```bash
cd /path/to/Aria2DownloaderAndroid

# Add remote (replace YOUR_USERNAME with alexnepali98)
git remote add origin https://github.com/alexnepali98/aria2-downloader-android.git

# Rename branch to main (GitHub default)
git branch -M main

# Push to GitHub
git push -u origin main
```

**Use your GitHub PAT when prompted for password:**
- Username: `alexnepali98`
- Password: Your GitHub Personal Access Token (with `repo` scope)

## Step 3: Watch the Build

1. Go to: `https://github.com/alexnepali98/aria2-downloader-android`
2. Click **Actions** tab
3. Watch the "Build APK" workflow run
4. **Takes ~5-10 minutes** (first run slower, includes dependency download)

## Step 4: Download APK

Once workflow completes (✅ green checkmark):

**Option A: From Artifacts**
- Click the workflow run
- Scroll down to "Artifacts"
- Click "app-debug" to download APK

**Option B: From Release**
- Go to **Releases** tab
- Find latest "APK Build" release
- Download `app-debug.apk`

## What Happens Automatically

✅ GitHub Actions detects your push
✅ Spins up Ubuntu VM with JDK 11
✅ Downloads Gradle + Android SDK (cached for future builds)
✅ Runs: `./gradlew assembleDebug`
✅ APK compiles in ~5-10 minutes
✅ Artifacts stored for 30 days
✅ Release created automatically

## After Download

Install on your device:
```bash
adb install app-debug.apk
```

Or email/AirDrop to phone and tap to install.

## Future Builds

Every time you push to `main` branch, GitHub Actions automatically:
- Rebuilds APK
- Creates new artifact
- Creates new release with APK

Just edit code locally, commit, and push — APK builds automatically.

---

## Troubleshooting

**Workflow fails with "Gradle not found"**
- Check `.gitignore` doesn't exclude gradlew
- Confirm gradlew is executable: `ls -la gradlew`

**"Gradle dependency download timeout"**
- Rerun the workflow (click "Re-run all jobs")
- GitHub might have had a temporary network hiccup

**"Build succeeds but APK not found"**
- Check Gradle build logs in workflow output
- Common: Missing SDK components
- Solution: Android SDK is auto-installed in Actions, should work

---

**Ready to push? Run the git commands above.** 🦊
