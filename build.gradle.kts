// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(PluginNamespace.ANDROID_APPLICATION).version(PluginVersions.ANDROID_APPLICATION_VERSION).apply(false)
    id(PluginNamespace.ANDROID_KOTLIN).version(PluginVersions.ANDROID_KOTLIN_VERSION).apply(false)
    id(PluginNamespace.DETEKT).version(PluginVersions.DETEKT_VERSION).apply(false)
}
