apply(plugin = "org.springframework.boot")
apply(plugin = "org.jetbrains.kotlin.jvm")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

dependencies {
    implementation("org.springframework.cloud:spring-cloud-config-server")
}

