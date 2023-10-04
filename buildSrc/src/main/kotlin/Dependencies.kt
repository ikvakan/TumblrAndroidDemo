import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${DependencyVersions.OK_HTTP}"
    const val OK_HTTP_LOGGING_INTERCEPTOR =
        "com.squareup.okhttp3:logging-interceptor:${DependencyVersions.OK_HTTP}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${DependencyVersions.RETROFIT}"
    const val MOSHI_CONVERTER =
        "com.squareup.retrofit2:converter-moshi:${DependencyVersions.RETROFIT}"
    const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${DependencyVersions.MOSHI_KOTLIN}"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${DependencyVersions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${DependencyVersions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${DependencyVersions.ROOM}"

    const val DETEKT_FORMATTING =
        "io.gitlab.arturbosch.detekt:detekt-formatting:${DependencyVersions.DETEKT}"

    const val NAVIGATION_COMPOSE =
        "androidx.navigation:navigation-compose:${DependencyVersions.NAVIGATION_COMPOSE}"

    const val COMPOSE_BOM = "androidx.compose:compose-bom:${DependencyVersions.COMPOSE_BOM}"
    const val COMPOSE_UI = "androidx.compose.ui:ui"
    const val COMPOSE_UI_GRAPHICS = "androidx.compose.ui:ui-graphics"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview"
    const val COMPOSE_MATERIAL_3 = "androidx.compose.material3:material3"
    const val LIFECYCLE_RUNTIME_COMPOSE =
        "androidx.lifecycle:lifecycle-runtime-compose:${DependencyVersions.LIFECYCLE_RUNTIME_COMPOSE}"

    const val KOIN = "io.insert-koin:koin-androidx-compose:${DependencyVersions.KOIN}"

    const val TIMBER = "com.jakewharton.timber:timber:${DependencyVersions.TIMBER}"

    const val GLIDE = "com.github.bumptech.glide:compose:${DependencyVersions.GLIDE}"
}

fun DependencyHandler.glide() {
    implementation(Dependencies.GLIDE)
}

fun DependencyHandler.compose() {
    implementation(platform(Dependencies.COMPOSE_BOM))
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_UI_GRAPHICS)
    implementation(Dependencies.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Dependencies.COMPOSE_MATERIAL_3)
    implementation(Dependencies.LIFECYCLE_RUNTIME_COMPOSE)
}

fun DependencyHandler.timber() {
    implementation(Dependencies.TIMBER)
}

fun DependencyHandler.koin() {
    implementation(Dependencies.KOIN)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.NAVIGATION_COMPOSE)
}

fun DependencyHandler.room() {
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_KTX)
    kapt(Dependencies.ROOM_COMPILER)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.OK_HTTP)
    implementation(Dependencies.OK_HTTP_LOGGING_INTERCEPTOR)
    implementation(Dependencies.MOSHI_CONVERTER)
}

fun DependencyHandler.moshi() {
    implementation(Dependencies.MOSHI_KOTLIN)
}

fun DependencyHandler.detekt() {
    detektPlugins(Dependencies.DETEKT_FORMATTING)
}
