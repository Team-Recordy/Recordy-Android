import org.gradle.api.Plugin
import org.gradle.api.Project

class RecordyLocalPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("recordy.android.library")
                apply("recordy.android.hilt")
            }
        }
    }
}
