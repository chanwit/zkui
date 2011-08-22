grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
        mavenCentral()
        mavenRepo "http://zkgrails.googlecode.com/svn/repo"
        mavenRepo "http://203.158.7.11/artifactory/repo"

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenCentral()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
        def zkVersion = "5.0.8.FL.20110819"

        runtime "org.zkoss.zk.grails:zk:${zkVersion}"
        runtime "org.zkoss.zk.grails:zul:${zkVersion}"
        runtime "org.zkoss.common:zweb:${zkVersion}"
        // runtime 'org.zkoss.zk:zkplus:5.0.7.1'
        // runtime 'org.zkoss.zk:zhtml:5.0.7.1'
    }
}
