enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "Recordy"
include(":app")
include(":core:common")
include(":core:buildconfig")
include(":core:ui")
include(":core:designsystem")
include(":core:network")
include(":core:datastore")
include(":core:model")
include(":data:recordy")
include(":data:auth")
include(":data:oauth")
include(":local:recordy")
include(":remote:recordy")
include(":domain:recordy")
include(":domain:auth")
include(":domain:oauth")
include(":feature:navigator")
include(":feature:home")
include(":feature:video")
include(":feature:login")
include(":feature:upload")
include(":feature:profile")
include(":feature:mypage")
