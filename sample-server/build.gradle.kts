import com.bmuschko.gradle.docker.tasks.image.Dockerfile.Instruction
import com.bmuschko.gradle.docker.tasks.image.Dockerfile.EntryPointInstruction

dependencies {
    api("org.springframework.cloud:spring-cloud-config-client")
    api("org.springframework.cloud:spring-cloud-starter-gateway")
    api("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-log4j2")
    runtimeOnly("com.lmax:disruptor:3.4.4")
    runtimeOnly("org.springframework.cloud:spring-cloud-starter-bootstrap")
}

plugins {
    id("com.bmuschko.docker-remote-api")
}

tasks {
    dockerCreateDockerfile {

        copyFile("resources/log4j2.xml", "/opt/sample/config/")

        val mainClassName = "org.pbrowngh.sampleserver.SampleServerApplication"

        environmentVariable("SYSTEM_OPTS", "-Djava.security.egd=file:/dev/./urandom -Dlogging.config=file:/opt/sample/config/log4j2.xml -Dlogging.file.name=sample-server -Dlogging.httpaccess.file=${project.name}-httpaccess -Duser.timezone=America/Montreal")
        val originalInstructions = instructions.get().toMutableList()
        originalInstructions.removeAt(6)

        this.instructions.set(originalInstructions)
        entryPoint("sh", "-c", "java \$SYSTEM_OPTS -cp '/app/resources:/app/classes:/app/libs/*' $mainClassName")
    }
}

docker {
    springBootApplication {
        baseImage.set("openjdk:11")
        ports.set(setOf(8086))
        images.set(
            setOf(
                "pbrowngh/${project.name}:$version",
                "pbrowngh/${project.name}:latest"
            )
        )
    }
}
