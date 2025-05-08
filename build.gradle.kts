// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false
}
// Project-level build.gradle.kts

buildscript {
    repositories {
        google()  // Firebase and Google services require this repository
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }

    dependencies {
        // Google services plugin for Firebase
        classpath(libs.google.services)
    }
}

