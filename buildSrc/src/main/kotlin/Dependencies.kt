import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    const val OK_HTTP = "com.squareup.okhttp3:okhttp:${DependencyVersions.OK_HTTP}"
    const val OK_HTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${DependencyVersions.OK_HTTP}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${DependencyVersions.RETROFIT}"
    const val MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${DependencyVersions.RETROFIT}"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${DependencyVersions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${DependencyVersions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${DependencyVersions.ROOM}"

    const val DETEKT_FORMATTING = "io.gitlab.arturbosch.detekt:detekt-formatting:${DependencyVersions.DETEKT}"
}

fun DependencyHandler.room() {
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_KTX)
    kapt(Dependencies.ROOM_COMPILER)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.MOSHI_CONVERTER)
    implementation(Dependencies.OK_HTTP)
    implementation(Dependencies.OK_HTTP_LOGGING_INTERCEPTOR)
}

fun DependencyHandler.detekt(){
    detektPlugins(Dependencies.DETEKT_FORMATTING)
}