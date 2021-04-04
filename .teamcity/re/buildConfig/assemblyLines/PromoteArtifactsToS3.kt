package re.buildConfig.assemblyLines

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class PromoteArtifactsToS3(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.assemblyLine.id")}_PromoteToS3")
    name = "Promote To Production S3 "
    description = "Promotes artifacts to S3 and updates version in build.gradle"
})