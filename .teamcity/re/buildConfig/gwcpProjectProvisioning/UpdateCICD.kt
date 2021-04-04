package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class UpdateCICD(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.tenant.id")}_UpdateCICD")
    name = "Update CICD"
    description = "Update CICD Code"
})