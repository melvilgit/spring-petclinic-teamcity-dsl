package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties


class TenantProject(private val props: ProjectProperties) : Project({
    val gwcpProvisioningProjectId = props.get("project.gwcpProvisioning.id")
    val clusterName = props.get("project.cluster.name")
    val tenantName = props.get("project.tenant.name")
    val tenantProjectId = "${gwcpProvisioningProjectId}_${clusterName}_${tenantName}"
    name = tenantName
    props.set("project.tenant.id", tenantProjectId)
    id(tenantProjectId)
    val projectProvision = ProvisionProject(props)
    val patchCICD = PatchCICD(props)
    val callUpdateISSourceCode = CallUpdateISSourceCode(props)
    val integrationTest = IntegrationTest(props)
    val migrateCICD = MigrateCICD(props)
    val updateCICD = UpdateCICD(props)
    val deleteProject = DeleteProject(props)

    buildType(projectProvision)
    buildType(patchCICD)
    buildType(callUpdateISSourceCode)
    buildType(integrationTest)
    buildType(migrateCICD)
    buildType(updateCICD)
    buildType(deleteProject)

    buildTypesOrderIds = arrayListOf(
            projectProvision,
            patchCICD,
            callUpdateISSourceCode,
            integrationTest,
            migrateCICD,
            updateCICD,
            deleteProject
    )
})