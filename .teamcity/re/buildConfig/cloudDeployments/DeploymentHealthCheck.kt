package re.buildConfig.cloudDeployments

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class DeploymentHealthCheck(private val props: ProjectProperties) : BuildType({
    id("${props.get("project.cloudDeployments.id")}_DeploymentHealthCheck")
    name = "Deployment Health Check"
    description = "Verify deployments are deployed, running and healthy"
})
