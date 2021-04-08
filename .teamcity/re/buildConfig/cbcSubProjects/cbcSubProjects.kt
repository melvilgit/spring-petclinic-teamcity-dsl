package re.buildConfig.cbcSubProjects

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties
import re.buildConfig.assemblyLines.AssemblyLinesProject


class cbcSubProjects(private val props: ProjectProperties) : Project({
    name = props.get("project.name")
    id(props.get("project.id"))
    val assemblyLineProjectobj = AssemblyLinesProject(props)
    subProject(assemblyLineProjectobj)
})