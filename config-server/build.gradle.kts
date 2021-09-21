dependencies {
    api("org.springframework.cloud:spring-cloud-config-server")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
}

docker {
    springBootApplication {
        baseImage.set("openjdk:11")
        ports.set(setOf(8888))
        images.set(
            setOf(
                "pbrowngh/${project.name}:$version",
                "pbrowngh/${project.name}:latest"
            )
        )
    }
}
