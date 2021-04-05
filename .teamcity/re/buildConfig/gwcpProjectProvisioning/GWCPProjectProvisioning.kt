package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties

class GWCPProjectProvisioning(private val props: ProjectProperties) : Project({
    val pipelineProjectId = props.get("branch.name").replace('-', '_')
    val gwcpProvisioningProjectId = "${pipelineProjectId}_GWCPProjectProvisioning"
    props.set("project.gwcpProvisioning.id", gwcpProvisioningProjectId)
    id(gwcpProvisioningProjectId)

    name = "GWCP Project provisioning"
    description = "GWCP Project provisioning"
    val clusterOrderList = arrayListOf<Project>()
    val tenantsList = props.filterKeys("project\\.tenant\\.\\d+")
    for (tenant in tenantsList) {
        var (clusterName, tenantName) = props.get(tenant).split("/")
        clusterName = clusterName.trim()
        tenantName = tenantName.trim()
        props.set("project.cluster.name",clusterName)
        props.set("project.tenant.name", tenantName)
        val clusterProject = ClusterProject(props)
        subProject(clusterProject)
    }

})
