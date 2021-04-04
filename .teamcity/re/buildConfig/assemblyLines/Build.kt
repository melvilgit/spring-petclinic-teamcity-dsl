package re.buildConfig.assemblyLines

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class Build(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.assemblyLine.id")}_Build")
    name = "Build"
    description = "Builds all packages specified in 'build.gradle'"
})