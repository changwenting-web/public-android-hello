# Public Android Hello

A minimal public Android application with a valid Gradle project at the repository root.

## Requirements

- JDK 17
- Android SDK platform `android-35`

## Build

```bash
./gradlew assembleRelease
```

The release APK is written to:

```text
app/build/outputs/apk/release/app-release-unsigned.apk
```

The legacy SDK command-line build script is still available at `scripts/build_release_apk.sh`.
