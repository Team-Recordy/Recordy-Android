import com.record.convention.extension.getBundle
import com.record.convention.extension.getLibrary
import com.record.convention.extension.implementation
import com.record.convention.extension.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RecordyFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("recordy.android.compose.library")
                apply("recordy.android.hilt")
            }

            dependencies {
                implementation(project(":core:ui"))
                implementation(project(":core:designsystem"))
                implementation(project(":core:model"))
                implementation(libs.getBundle("compose"))
            }
        }
    }
}