package re.buildConfig.assemblyLines.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class DockerizeAndPublish(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.assemblyLine.id")}_DockerizeAndPublish")
    name = "Dockerize And Publish"
    description = "Dockerizes the artifacts and publishes to artifactory"
})