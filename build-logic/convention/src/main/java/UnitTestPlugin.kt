import com.android.build.api.dsl.LibraryExtension
import com.record.convention.extension.getLibrary
import com.record.convention.extension.libs
import com.record.convention.extension.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class UnitTestPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                testImplementation(libs.getLibrary("kotlinx.coroutines.test"))
                testImplementation(libs.getLibrary("mockito"))
                testImplementation(libs.getLibrary("junit"))
                testImplementation(libs.getLibrary("robolectric"))
            }

            extensions.configure<LibraryExtension> {
                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                    }
                }
            }
        }
    }
}