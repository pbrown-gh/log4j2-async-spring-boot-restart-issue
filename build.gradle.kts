plugins {
    base
    java
    idea
    id("org.jetbrains.kotlin.jvm")
    id("org.springframework.boot") apply false
    id("com.bmuschko.docker-spring-boot-application") apply false
}

val gradleVersionProperty: String by project
val springBootVersion: String by project
val springCloudStarterParentBomVersion: String by project

if (JavaVersion.current() != JavaVersion.VERSION_11) {
    throw GradleException("This build must be run with java 11")
} else {
    println("Building source with java version " + JavaVersion.current())
}

idea {
    project {
        jdkName = JavaVersion.VERSION_11.name
    }
}

tasks.withType<Wrapper> {
    gradleVersion = gradleVersionProperty
    distributionType = Wrapper.DistributionType.ALL
}

allprojects {

    version = "$version"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.spring.io/snapshot")
        maven("https://repo.spring.io/milestone")
    }
}

subprojects {
    apply {
        plugin("java-library")
        plugin("org.springframework.boot")
        plugin("com.bmuschko.docker-spring-boot-application")
    }

    dependencies {
        api(enforcedPlatform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))
        api(enforcedPlatform("org.springframework.cloud:spring-cloud-dependencies:$springCloudStarterParentBomVersion"))

        runtimeOnly("org.springframework.boot:spring-boot-starter-aop")
        runtimeOnly("org.springframework.retry:spring-retry")
        runtimeOnly("org.springframework.retry:spring-retry")
    }

    configurations.forEach {
        it.exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        it.exclude(group = "ch.qos.logback", module = "logback-classic")
    }

    if (project.name == "sample-server") {
        registerCopyLogFileTask()
    }
}

fun Project.registerCopyLogFileTask() {
    tasks.register<Copy>("copyLogConfigToContainerStaging") {
        group = "Docker"
        description = "Copies log4j2 configuration file into the docker container assembly folder"
        from(project(":sample-server").file("src/main/resources/log4j2.xml"))
        into("${project.buildDir}/docker")
    }
}
