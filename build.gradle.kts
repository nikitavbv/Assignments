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
    compile("org.apache.httpcomponents", "httpclient", "4.5.8")
    compile("org.json", "json", "20090211")
    compile("com.sparkjava", "spark-core", "2.8.0")
    compile("org.slf4j", "slf4j-api", "1.7.7")
    compile("org.slf4j", "slf4j-log4j12", "1.7.7")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_9
}
