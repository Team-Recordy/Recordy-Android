import com.android.build.gradle.LibraryExtension
import com.record.convention.configureBuildConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class BuildConfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                configureBuildConfig(this)
            }
        }
    }
}