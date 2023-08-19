object Dependencies {
    const val desugar = "com.android.tools:desugar_jdk_libs:1.2.0"

    object Accompanist {
        private const val version = "0.25.1"
        const val pager = "com.google.accompanist:accompanist-pager:$version"
    }

    object Kotlin {
        private const val version = "1.8.10"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
    }

    object Google {
        const val material = "com.google.android.material:material:1.6.1"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.10.1"
        const val viewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0-alpha02"

        object Compose {
            const val version = "1.4.3"
            const val compilerVersion = "1.4.2"

            const val ui = "androidx.compose.ui:ui:$version"
            const val material = "androidx.compose.material:material:$version"
            const val materialIconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val animation = "androidx.compose.animation:animation:$version"
            const val foundationLayout = "androidx.compose.foundation:foundation-layout:$version"

            const val activity = "androidx.activity:activity-compose:1.6.0-rc02"
            const val navigation = "androidx.navigation:navigation-compose:2.5.2"
            const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-alpha02"
        }
    }
}