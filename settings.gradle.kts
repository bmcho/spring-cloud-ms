plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "spring-cloud-ms"
include("x-config-server")
include("x-discovery-server")
include("x-api-gateway")
