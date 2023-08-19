import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

class CommonModulePlugin: Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins()
            dependenciesConf()
        }
    }

    private fun Project.applyPlugins() {
        plugins.run {
            apply("com.android.library")
            apply("kotlin-android")
            apply("com.vanniktech.maven.publish")
        }
    }

    private fun Project.dependenciesConf() {
        dependencies.apply {
            implementation(Dependencies.AndroidX.coreKtx)
            implementation(Dependencies.AndroidX.viewmodelKtx)

            implementation(Dependencies.AndroidX.Compose.ui)
            implementation(Dependencies.AndroidX.Compose.material)
            implementation(Dependencies.AndroidX.Compose.foundationLayout)
            implementation(Dependencies.AndroidX.Compose.animation)
            implementation(Dependencies.AndroidX.Compose.viewmodel)
        }
    }

    private fun DependencyHandler.implementation(dependency: String) {
        add("implementation", dependency)
    }

}