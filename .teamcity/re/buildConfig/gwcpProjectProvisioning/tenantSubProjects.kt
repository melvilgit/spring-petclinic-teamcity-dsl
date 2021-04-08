package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties
import re.buildConfig.gwcpProjectProvisioning.buildTypes.*

class tenantSubProjects(private val props: ProjectProperties) : Project({
    val tenantName = props.get("tenantName")
    val tenantProjectId = "${props.get("project.cluster.id")}_${tenantName}"
    props.set("project.tenant.id", tenantProjectId)
    name = tenantName
    id(tenantProjectId)
    val gwcpProjectProvisioningBuildTypes = arrayListOf(CallUpdateISSourceCode(props), DeleteProject(props), IntegrationTest(props), MigrateCICD(props), PatchCICD(props), ProvisionProject(props), UpdateCICD(props))
    for (gwcpProjectProvisioningBuildType in gwcpProjectProvisioningBuildTypes) buildType(gwcpProjectProvisioningBuildType)
})