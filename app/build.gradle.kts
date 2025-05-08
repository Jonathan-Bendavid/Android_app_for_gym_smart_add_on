plugins {
    alias(libs.plugins.android.application)
    // Add the Google services Gradle plugin
}

android {
    namespace = "com.example.GymFiTech"
    compileSdk = 34

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }


    defaultConfig {
        applicationId = "com.example.GymFiTech"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.firebase.database) // Check for the latest version
    implementation (libs.firebase.auth)
    implementation (libs.mpandroidchart)


}
apply(plugin = "com.google.gms.google-services")