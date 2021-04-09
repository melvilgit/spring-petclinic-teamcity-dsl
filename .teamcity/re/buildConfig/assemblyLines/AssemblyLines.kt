package re.buildConfig.assemblyLines

import jetbrains.buildServer.configs.kotlin.v2019_2.ParameterDisplay
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties
import re.buildConfig.assemblyLines.buildTypes.*


class AssemblyLines(private val props: ProjectProperties) : Project({

    val projectId = "${props.get("project.id")}_AssemblyLine"
    props.set("project.assemblyLine.id", projectId)
    id(projectId)
    name = "Assembly Lines"
    description = "Portfolio Assembly"

    params {
        param("SAS-BUILD-EXTRAPARAM", """
            -v /var/run/docker.sock:/var/run/docker.sock
            -v ${'$'}HOME/.docker:/root/.docker
        """.trimIndent())
        param("TEAMCITY_PASSWORD", "%system.teamcity.auth.password%")
        password("sys-sas-artifactory-password", "credentialsJSON:73013dd5-ced3-45bf-8935-a43b93ceab46", display = ParameterDisplay.HIDDEN)
        param("sas-user", "sys-sas")
        param("TEAMCITY_USER", "%system.teamcity.auth.userId%")
        param("SAS-BUILD-AGENT-11", "dtr.guidewire.com/sys-sas/build-agent:openjdk11-latest")
        param("sys-sas-artifactory-user", "%sas-user%")
        param("SAS-BUILD-AGENT", "%SAS-BUILD-AGENT-8%")
        param("SAS-BUILD-AGENT-8", "dtr.guidewire.com/sys-sas/build-agent:jdk8-latest")
    }

/*TODO: enable the features when needed by the pipeline

    features {
        dockerECRRegistry {
            id = "${projectId}_PROJECT_EXT_1124"
            displayName = "Amazon ECR"
            registryId = "535771865967"
            credentialsProvider = accessKey {
                accessKeyId = "AKIAJRFLQ7NE6FZALREQ"
                secretAccessKey = "credentialsJSON:511d6871-707d-4bb8-9cac-bf6383dca70a"
            }
            regionCode = "eu-central-1"
            credentialsType = accessKeys()
        }
        dockerECRRegistry {
            id = "${projectId}_PROJECT_EXT_1125"
            displayName = "Amazon ECR (Orange Bar)"
            registryId = "627188849628"
            credentialsProvider = accessKey {
                accessKeyId = "AKIAZEB26LPOOFZD6ZGY"
                secretAccessKey = "credentialsJSON:2d4d134e-07ac-4704-8f48-01891e18bf6c"
            }
            regionCode = "us-west-2"
            credentialsType = accessKeys()
        }
    }
    */

    val assemblyLineBuildTypesList = arrayListOf(VersionUpdate(props), Build(props), DockerizeAndPublish(props), PromoteArtifactsToS3(props))

    for (assemblyLineBuildType in assemblyLineBuildTypesList) {
        buildType(assemblyLineBuildType)
    }
    buildTypesOrder = assemblyLineBuildTypesList

})
