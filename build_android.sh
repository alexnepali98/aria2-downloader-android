#!/bin/bash

# Android APK Build Script
# This script downloads Gradle and builds the APK

set -e

PROJECT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$PROJECT_DIR"

echo "🔨 Aria2 Downloader — APK Build Script"
echo "========================================"
echo ""

# Check Java
echo "✓ Checking Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Installing OpenJDK 11..."
    # This won't work in sandbox, but try anyway
    apt-get update -qq && apt-get install -y openjdk-11-jdk-headless 2>/dev/null || {
        echo "❌ Cannot install Java (no sudo access)"
        echo "Install Java 11+ on your system and try again"
        exit 1
    }
fi

JAVA_VERSION=$(java -version 2>&1 | head -1)
echo "✓ Found: $JAVA_VERSION"
echo ""

# Download Gradle if needed
GRADLE_HOME="$HOME/.gradle"
GRADLE_VERSION="8.2.1"
GRADLE_ZIP="$GRADLE_HOME/gradle-$GRADLE_VERSION.zip"

if [ ! -f "$GRADLE_ZIP" ]; then
    echo "⬇️  Downloading Gradle $GRADLE_VERSION..."
    mkdir -p "$GRADLE_HOME"
    wget -q https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip -O "$GRADLE_ZIP" || {
        echo "❌ Failed to download Gradle"
        exit 1
    }
fi

GRADLE_BIN="$GRADLE_HOME/gradle-$GRADLE_VERSION/bin/gradle"
if [ ! -f "$GRADLE_BIN" ]; then
    echo "📦 Extracting Gradle..."
    unzip -q "$GRADLE_ZIP" -d "$GRADLE_HOME"
fi

echo "✓ Gradle ready"
echo ""

# Build APK
echo "🏗️  Building APK..."
"$GRADLE_BIN" assembleDebug

APK_PATH="app/build/outputs/apk/debug/app-debug.apk"
if [ -f "$APK_PATH" ]; then
    APK_SIZE=$(ls -lh "$APK_PATH" | awk '{print $5}')
    echo ""
    echo "✅ BUILD SUCCESS"
    echo "========================================"
    echo "APK ready: $APK_PATH"
    echo "Size: $APK_SIZE"
    echo ""
    echo "Install:"
    echo "  adb install $APK_PATH"
    echo ""
else
    echo "❌ APK not found"
    exit 1
fi
