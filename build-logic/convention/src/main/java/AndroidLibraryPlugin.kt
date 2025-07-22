import com.android.build.gradle.LibraryExtension
import com.viskit.convention.configureKotlinAndroid
import com.viskit.convention.configureKotlinCoroutine
import com.viskit.convention.extension.getLibrary
import com.viskit.convention.extension.implementation
import com.viskit.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureKotlinCoroutine(this)
            }

            dependencies {
                implementation(libs.getLibrary("timber"))
                implementation(libs.getLibrary("amplitude"))
            }
        }
    }
}
