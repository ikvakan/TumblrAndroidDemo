import java.io.FileInputStream
import java.util.Properties

plugins {
    id(PluginNamespace.ANDROID_APPLICATION)
    id(PluginNamespace.ANDROID_KOTLIN)
    id(PluginNamespace.DETEKT)
    kotlin("kapt")
}

android {
    namespace = AppConfig.NAMESPACE
    compileSdk = AppConfig.COMPILE_SDK

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TAGRGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField(BuildConfig.Type.BOOLEAN, BuildConfig.Name.LOGS, "true")
        buildConfigField(BuildConfig.Type.STRING, BuildConfig.Name.APP_DATABASE, "\"app_database\"")
        buildConfigField(
            BuildConfig.Type.STRING,
            BuildConfig.Name.API_BASE_URL,
            "\"https://api.tumblr.com/v2/\""
        )
        buildConfigField(
            BuildConfig.Type.STRING,
            BuildConfig.Name.API_KEY,
            "\"${getProperty("local.properties", "api_key") ?: ""}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(BuildConfig.Type.BOOLEAN, BuildConfig.Name.LOGS, "false")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = AppConfig.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KOTLIN_COMPILER_EXTENSION_VERSION
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    detekt {
        toolVersion = AppConfig.DETEKT_TOOL_VERSION
        config.setFrom(files("${rootProject.projectDir}/config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        autoCorrect = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    paging()
    glide()
    compose()
    timber()
    koin()
    navigation()
    detekt()
    room()
    retrofit()
    moshi()
}

fun getProperty(filename: String, propName: String): String? {
    val propsFile = rootProject.file(filename)

    return if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        props.getProperty(propName)
    } else {
        println("$filename does not exist!")
        null
    }
}
