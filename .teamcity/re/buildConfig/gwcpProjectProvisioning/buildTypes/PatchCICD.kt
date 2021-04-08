package re.buildConfig.gwcpProjectProvisioning.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class PatchCICD(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.tenant.id")}_PatchCICD")
    name = "Patch CICD"
    description = "Patch CICD Code to include Release Engineering calls to Kafka etc"
})