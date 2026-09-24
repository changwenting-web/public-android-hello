#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"
SDK_DIR="${ANDROID_HOME:-$HOME/Library/Android/sdk}"
BUILD_TOOLS="${BUILD_TOOLS:-$SDK_DIR/build-tools/35.0.0}"
ANDROID_JAR="${ANDROID_JAR:-$SDK_DIR/platforms/android-35/android.jar}"

AAPT2="$BUILD_TOOLS/aapt2"
D8="$BUILD_TOOLS/d8"
ZIPALIGN="$BUILD_TOOLS/zipalign"
APKSIGNER="$BUILD_TOOLS/apksigner"

OUT_DIR="$ROOT_DIR/build"
GEN_DIR="$OUT_DIR/generated"
CLASSES_DIR="$OUT_DIR/classes"
DEX_DIR="$OUT_DIR/dex"
KEYSTORE="$OUT_DIR/debug.keystore"
UNSIGNED_APK="$OUT_DIR/app-unsigned.apk"
ALIGNED_APK="$OUT_DIR/app-aligned.apk"
SIGNED_APK="$OUT_DIR/public-android-hello-release.apk"

rm -rf "$OUT_DIR"
mkdir -p "$GEN_DIR" "$CLASSES_DIR" "$DEX_DIR"

"$AAPT2" compile --dir "$ROOT_DIR/app/src/main/res" -o "$OUT_DIR/resources.zip"
"$AAPT2" link \
  -I "$ANDROID_JAR" \
  --manifest "$ROOT_DIR/app/src/main/AndroidManifest.xml" \
  --java "$GEN_DIR" \
  -o "$UNSIGNED_APK" \
  "$OUT_DIR/resources.zip"

javac -source 1.8 -target 1.8 \
  -bootclasspath "$ANDROID_JAR" \
  -d "$CLASSES_DIR" \
  $(find "$GEN_DIR" "$ROOT_DIR/app/src/main/java" -name '*.java' | sort)

"$D8" --min-api 23 --output "$DEX_DIR" $(find "$CLASSES_DIR" -name '*.class' | sort)
(cd "$DEX_DIR" && zip -q "$UNSIGNED_APK" classes.dex)

"$ZIPALIGN" -p -f 4 "$UNSIGNED_APK" "$ALIGNED_APK"

keytool -genkeypair \
  -keystore "$KEYSTORE" \
  -storepass android \
  -keypass android \
  -alias androiddebugkey \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -dname "CN=Android Debug,O=Public Android Hello,C=CN" >/dev/null

"$APKSIGNER" sign \
  --ks "$KEYSTORE" \
  --ks-key-alias androiddebugkey \
  --ks-pass pass:android \
  --key-pass pass:android \
  --out "$SIGNED_APK" \
  "$ALIGNED_APK"

"$APKSIGNER" verify --verbose "$SIGNED_APK"
echo "$SIGNED_APK"
