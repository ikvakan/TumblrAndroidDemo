object AppConfig {
    const val NAMESPACE = "com.ikvakan.tumblrdemo"
    const val COMPILE_SDK = 34
    const val APPLICATION_ID = "com.ikvakan.tumblrdemo"
    const val MIN_SDK = 26
    const val TAGRGET_SDK = 33
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    const val JVM_TARGET = "17"
    const val KOTLIN_COMPILER_EXTENSION_VERSION = "1.4.3"
}

object BuildConfig {
    object TYPE {
        const val BOOLEAN = "boolean"
        const val STRING = "String"
    }
    object NAME {
        const val LOGS = "LOGS"
        const val APP_DATABASE = "APP_DATABASE"
        const val API_BASE_URL = "API_BASE_URL"
        const val API_KEY = "API_KEY"
    }
}