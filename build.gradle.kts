plugins {
    id("java")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // H2 Database
    implementation("com.h2database:h2:2.2.224")
    
    // Hibernate
    implementation("org.hibernate.orm:hibernate-core:6.4.4.Final")
    
    // Flyway
    implementation("org.flywaydb:flyway-core:10.10.0")
    
    // JUnit for testing
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("com.spacetravel.Main")
}