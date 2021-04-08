package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties

class gwcpProjectProvisioning(private val props: ProjectProperties) : Project({
    name = "GWCP Project Provisioning"
    val gwcpProvisioningProjectId = "${props.get("project.id")}_GWCPProjectProvisioning"
    props.set("project.gwcpProvisioning.id", gwcpProvisioningProjectId)
    id(gwcpProvisioningProjectId)
    val tenantsList = props.getArrayList("clusters.tenants")

    for (tenant in tenantsList) {
        var (clusterName, tenantName) = tenant.toString().split(".")
        props.set("clusterName", clusterName.trim())
        props.set("tenantName", tenantName.trim())
        val clusterProject = clusterSubProjects(props)

    }


})


