# Spring Rest Docs Demo
This demo repository contains a controller with both a GET and a POST endpoint which we will document with Rest Assured tests and spring rest docs.

## Run Demo
To get everything working simply run
```java
gradle clean bootRun
```

Then fire up the browser to: http://localhost:8080/docs/index.html

## Quick Explanation

The dependencies can be found in [build.gradle](build.gradle):

* A plugin which converts ascidoc into static html
```$xslt
plugins {
	id 'org.asciidoctor.convert' version '1.5.3'
}
```
* Dependencies required:
```$xslt
dependencies {
	asciidoctor 'org.springframework.restdocs:spring-restdocs-asciidoctor:2.0.3.RELEASE'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.restdocs:spring-restdocs-restassured'
}
```


* Define where the generated ascidoc snippets are going to be stored
```$xslt
ext {
	snippetsDir = file('build/generated-snippets')
}

test {
	outputs.dir snippetsDir
}

asciidoctor {
    outputDir "$buildDir/docs"
	inputs.dir snippetsDir
	dependsOn test
}

```

* A new task to copy these rest docs html files into the resources directory do it gets included in the bootiful jar that gets created:
```$xslt
task publishDocsToArtifact(type: Copy) {
	dependsOn asciidoctor
	from  new File(asciidoctor.outputDir, "html5")
	include "*.html"
	into "$buildDir/resources/main/static/docs"

}

bootJar {
	dependsOn publishDocsToArtifact
}
```

All tests can be found in [SpringRestDocsDemoApplicationTests](src/test/java/com/simonjamesrowe/springrestdocsdemo/SpringRestDocsDemoApplicationTests.java)

