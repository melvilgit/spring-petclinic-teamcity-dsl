package re.buildConfig.cbcSubProjects

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties
import re.buildConfig.assemblyLines.AssemblyLinesProject
import re.buildConfig.gwcpProjectProvisioning.gwcpProjectProvisioning
import re.buildConfig.uploadBasePackages.UploadBasePackages

class cbcSubProjects(private val props: ProjectProperties) : Project({
    name = props.get("project.name")
    id(props.get("project.id"))

    subProject(AssemblyLinesProject(props))
    subProject(gwcpProjectProvisioning(props))
    subProject(UploadBasePackages(props))
})

