package re.buildConfig.gwcpProjectProvisioning.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class DeleteProject(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.tenant.id")}_DeleteProject")
    name = "Delete Project"
    description = "Verify Confirmation and delete pipeline"
})