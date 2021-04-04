package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class ProvisionProject(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.tenant.id")}_ProvisionProject")
    name = "Provision Project"
    description = "Provision Project"
})