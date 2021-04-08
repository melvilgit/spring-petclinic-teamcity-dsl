package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties

class gwcpProjectProvisioning(private val props: ProjectProperties) : Project({
    name = "GWCP Project Provisioning"
    val gwcpProvisioningProjectId = "${props.get("project.id")}_GWCPProjectProvisioning"
    props.set("project.gwcpProvisioning.id", gwcpProvisioningProjectId)
    id(gwcpProvisioningProjectId)
    val tenantsClusterList = props.getArrayList("clusters.tenants")

    for (tenantCluster in tenantsClusterList) {
        var (clusterName, tenantName) = tenantCluster.toString().split(".")
        props.set("clusterName", clusterName.trim())
        props.set("tenantName", tenantName.trim())
        subProject(clusterSubProjects(props))
    }
})


