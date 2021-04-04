package re.buildConfig.cloudDeployments

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class DeployInGWCPEnvironment(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.cloudDeployments.id")}_DeployInGWCPEnvironment")
    name = "Deploy In GWCP Environment"
    description = "Deploys docker images from Build & Dockerize Application tenant TC builds"
})