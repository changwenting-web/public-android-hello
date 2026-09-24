# Public Android Hello

A minimal native Android application with a valid Gradle project at the repository root.

The project uses Kotlin, Gradle Kotlin DSL, AndroidX, and a reproducible public release signing key so CI or platform build validation can produce an installable release APK.

## Requirements

- JDK 17
- Android SDK platform `android-35`

## Stack

| Item | Version |
|------|---------|
| Gradle | 8.11.1 |
| Android Gradle Plugin | 8.7.3 |
| Kotlin | 2.0.21 |
| compileSdk / targetSdk | 35 |
| minSdk | 24 |

## Build

```bash
./gradlew assembleRelease
```

The signed release APK is written to:

```text
app/build/outputs/apk/release/app-release.apk
```

The legacy SDK command-line build script is still available at `scripts/build_release_apk.sh`.
