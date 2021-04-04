package re.buildConfig.assemblyLines

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class VersionUpdate(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.assemblyLine.id")}_VersionUpdate")
    name = "Version Update"
    description = "Replaces product versions in 'build.gradle' and updates build version in 'gradle.properties' file"
})