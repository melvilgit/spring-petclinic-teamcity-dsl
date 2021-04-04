package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class IntegrationTest(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.tenant.id")}_IntegrationTest")
    name = "Integration Test"
    description = "Integration Test Execution"
})