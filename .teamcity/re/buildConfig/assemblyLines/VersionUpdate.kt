package re.buildConfig.assemblyLines

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import re.ProjectProperties
import re.vcsRoots.*
class VersionUpdate(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.assemblyLine.id")}_VersionUpdate")
    name = "Version Update"
    description = "Replaces product versions in 'build.gradle' and updates build version in 'gradle.properties' file"
    vcs {
        root(Assembly_lines())
    }

    params {
        password("env.sample_password", "1234")

    }
    this.params.param("asd","eeee")

})
