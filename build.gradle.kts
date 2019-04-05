plugins {
    java
}

group = "com.nikitavbv"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    compile("org.knowm.xchart", "xchart", "3.2.2")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}