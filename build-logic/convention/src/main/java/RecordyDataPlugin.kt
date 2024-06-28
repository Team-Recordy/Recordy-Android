import com.record.convention.extension.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RecordyDataPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("recordy.android.library")
                apply("recordy.android.hilt")
            }

            dependencies {
                implementation(project(":core:network"))
            }
        }
    }
}