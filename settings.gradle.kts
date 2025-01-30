pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }

            mavenContent {
                releasesOnly()
            }
        }

        mavenCentral {
            content {
                includeGroup("com.google.dagger")
                includeGroup("com.google.dagger.hilt.android")
            }
            mavenContent {
                releasesOnly()
            }
        }

        gradlePluginPortal()

        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

plugins {
    id("com.android.settings") version "8.8.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // fetch libraries from google maven (https://maven.google.com)
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }

        // fetch libraries from maven central
        mavenCentral {
            mavenContent {
                releasesOnly()
            }
        }

        // fetch snapshot libraries from sonatype
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/") {
            mavenContent {
                snapshotsOnly()
            }
        }

        // fetch font awesome libraries from jitpack
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "pokesphere-app"
include(":app")
include(":datalayer")
include(":domainlayer")
include(":localdatalayer")
