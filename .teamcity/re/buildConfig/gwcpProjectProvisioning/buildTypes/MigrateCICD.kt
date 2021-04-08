package re.buildConfig.gwcpProjectProvisioning.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class MigrateCICD(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.tenant.id")}_MigrateCICD")
    name = "Migrate CICD"
    description = "Migrate CICD Code"
})