import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.publicandroidhello"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.publicandroidhello"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }

    val keystoreFile = rootProject.file("release.keystore")
    signingConfigs {
        create("public") {
            storeFile = file("public.keystore")
            storePassword = "public123"
            keyAlias = "public"
            keyPassword = "public123"
        }
        if (keystoreFile.exists()) {
            create("release") {
                storeFile = keystoreFile
                val localProps = Properties().apply {
                    val f = rootProject.file("local.properties")
                    if (f.exists()) f.inputStream().use { load(it) }
                }
                storePassword = localProps.getProperty("RELEASE_STORE_PASSWORD")
                    ?: System.getenv("RELEASE_STORE_PASSWORD")
                keyAlias = localProps.getProperty("RELEASE_KEY_ALIAS")
                    ?: System.getenv("RELEASE_KEY_ALIAS") ?: "release"
                keyPassword = localProps.getProperty("RELEASE_KEY_PASSWORD")
                    ?: System.getenv("RELEASE_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = if (keystoreFile.exists()) {
                signingConfigs.getByName("release")
            } else {
                signingConfigs.getByName("public")
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
}
