import com.beust.kobalt.*
import com.beust.kobalt.api.*
import com.beust.kobalt.plugin.packaging.assemble

// val repos = repos()

val p = project {
    name = "afm"
    group = "com.cout970"
    artifactId = name
    version = "1.0-SNAPSHOT"
    directory = "afm"



    dependencies {

    }


    dependenciesTest {
        compile("org.testng:testng:6.9.9")
    }


    sourceDirectories {
        path("src/main/kotlin")
        path("src/main/java")
    }


    sourceDirectoriesTest {
        path("src/main/kotlin")
        path("src/main/java")
    }


    assemble {
        jar {
        }
    }


}