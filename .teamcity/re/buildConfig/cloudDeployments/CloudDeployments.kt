package re.buildConfig.cloudDeployments

import jetbrains.buildServer.configs.kotlin.v2019_2.ParameterDisplay
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties


class CloudDeployments(private val props: ProjectProperties) : Project({
    val projectId = "${props.get("project.id")}_CloudDeployments"
    props.set("project.cloudDeployments.id", projectId)
    id(projectId)
    name = "Cloud Deployments (Non-production)"
    description = "Deploying and testing in non-production environments"

    params {
        param("env.PYTHONPATH", "%teamcity.build.workingDir%/src")
        password(
            "env.CLIENT_ID",
            "credentialsJSON:d07f5ad4-76df-4596-b7e7-98bbff2aeb1c",
            display = ParameterDisplay.HIDDEN
        )
        password(
            "env.CLIENT_SECRET",
            "credentialsJSON:a441f4ee-81a0-4e32-b593-cef707b4cf38",
            display = ParameterDisplay.HIDDEN
        )
    }

    features {
        feature {
            id = "PROJECT_EXT_1788"
            type = "JetBrains.SharedResources"
            param("quota", "1")
            param("name", "gcc_deployment")
            param("type", "quoted")
        }
    }

    val cloudDeploymentsBuildTypesList = arrayListOf(DeployInGWCPEnvironment(props), DeploymentHealthCheck(props))

    for (cloudDeploymentsBuildType in cloudDeploymentsBuildTypesList) {
        buildType(cloudDeploymentsBuildType)
    }
    buildTypesOrder = cloudDeploymentsBuildTypesList
})
