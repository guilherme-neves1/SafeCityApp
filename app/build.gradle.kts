import java.util.Properties

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.safecityapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.safecityapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        val mapsApiKey = getMapsApiKey()
        resValue("string", "MAPS_API_KEY", mapsApiKey)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

fun getMapsApiKey(): String {
    val properties = Properties()
    properties.load(project.rootProject.file("local.properties").inputStream())
    return properties.getProperty("MAPS_API_KEY") ?: ""
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.material:material:1.12.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.2.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.activity:activity:1.9.0")
    implementation ("androidx.appcompat:appcompat:1.6.1") // Verifique se essa versão está correta
    implementation ("com.google.android.material:material:1.9.0") // Verifique se essa versão está correta
    // Outras dependências

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}