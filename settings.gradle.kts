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
        maven(url = "https://jitpack.io")
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
include(":data:auth")
include(":data:oauth")
include(":local:auth")
include(":remote:auth")
include(":domain:auth")
include(":domain:oauth")
include(":feature:navigator")
include(":feature:home")
include(":feature:video")
include(":feature:login")
include(":feature:upload")
include(":feature:profile")
include(":feature:mypage")
include(":feature:setting")
include(":domain:user")
include(":domain:video")
include(":domain:upload")
include(":data:user")
include(":data:video")
include(":remote:video")
include(":remote:user")
include(":remote:keyword")
include(":domain:keyword")
include(":data:keyword")

include(":local:user")
include(":core:cache")
include(":core:workmanager")
include(":core:security")
include(":local:video")
include(":feature:search")
include(":feature:detail")
