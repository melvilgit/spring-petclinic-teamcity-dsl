package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties


class ClusterProject(private val props: ProjectProperties) : Project({
    val gwcpProvisioningProjectId = props.get("project.gwcpProvisioning.id")
    val clusterName = props.get("project.cluster.name")
    name = clusterName
    val clusterProjectId = "${gwcpProvisioningProjectId}_${clusterName}"
    props.set("project.cluster.id", clusterProjectId)
    id(clusterProjectId)
    //val tenantProject = TenantProject(props)
    //subProject(tenantProject)

})
