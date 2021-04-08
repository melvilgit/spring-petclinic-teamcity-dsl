package re.buildConfig.gwcpProjectProvisioning

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties


class clusterSubProjects(private val props: ProjectProperties) : Project({
    val clusterName = props.get("clusterName")
    val clusterProjectId = "${props.get("project.gwcpProvisioning.id")}_${clusterName}"
    props.set("project.cluster.id", clusterProjectId)
    name = "${clusterName}"
    id(clusterProjectId)
    subProject(tenantSubProjects(props))

})