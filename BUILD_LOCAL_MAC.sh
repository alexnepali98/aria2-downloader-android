#!/bin/bash

# Aria2 Downloader Android - Local Build Script for macOS
# Usage: bash BUILD_LOCAL_MAC.sh

set -e

echo "🚀 Aria2 Downloader Android Build"
echo "=================================="

# Check if Android SDK is installed
if [ -z "$ANDROID_HOME" ]; then
    echo "❌ ANDROID_HOME not set"
    echo "Install Android Studio or set ANDROID_HOME to your SDK path"
    echo "Example: export ANDROID_HOME=/Users/aakash/Library/Android/sdk"
    exit 1
fi

echo "✅ ANDROID_HOME: $ANDROID_HOME"
echo "✅ Java: $(java -version 2>&1 | head -1)"

echo ""
echo "Building APK..."
echo ""

chmod +x gradlew
./gradlew clean
./gradlew assembleDebug

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

if [ -f "$APK_PATH" ]; then
    SIZE=$(ls -lh "$APK_PATH" | awk '{print $5}')
    echo ""
    echo "✅ BUILD SUCCESS!"
    echo "📦 APK: $APK_PATH ($SIZE)"
    echo ""
    echo "Install with: adb install $APK_PATH"
else
    echo "❌ APK not found at $APK_PATH"
    exit 1
fi
