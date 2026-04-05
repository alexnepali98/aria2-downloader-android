FROM gitpod/workspace-full:latest

# Install Android SDK and build tools
RUN apt-get update && apt-get install -y \
    openjdk-11-jdk-headless \
    google-android-sdk-platform-tools \
    && apt-get clean

# Setup Android SDK
RUN mkdir -p /home/gitpod/android-sdk && \
    wget -q https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip && \
    unzip -q sdk-tools-linux-4333796.zip -d /home/gitpod/android-sdk && \
    rm sdk-tools-linux-4333796.zip && \
    chown -R gitpod:gitpod /home/gitpod/android-sdk

ENV ANDROID_HOME=/home/gitpod/android-sdk
ENV PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/tools/bin:$ANDROID_HOME/platform-tools

# Accept Android SDK licenses (non-interactive)
RUN yes | sdkmanager --licenses || true
RUN sdkmanager "platforms;android-34" "build-tools;34.0.0" --silent || true

# Install Gradle (optional, gradlew will be used)
RUN gradle --version || echo "Gradle not needed - using gradlew"
