package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class CallUpdateISSourceCode(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.tenant.id")}_CallUpdateISSourceCode")
    name = "Call Update IS Source Code"
    description = "Build to Call Update Is Source Code within Orange Bar/GWCP Project provisioning/ TC project for the configured cluster and tenant"
})