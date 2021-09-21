dependencies {
    api("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

docker {
    springBootApplication {
        baseImage.set("openjdk:11")
        ports.set(setOf(8761))
        images.set(
            setOf(
                "pbrowngh/${project.name}:$version",
                "pbrowngh/${project.name}:latest"
            )
        )
    }
}
