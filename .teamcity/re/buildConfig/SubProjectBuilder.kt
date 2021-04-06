package re.buildConfig

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

class SubProjectBuilder(private val parentProject : Project) {

private lateinit var  project:Project


fun withBuildTypeList(buildTypeList: ArrayList<BuildType>): SubProjectBuilder{
    for(buildType in buildTypeList) {project.buildType(buildType)}
    project.buildTypesOrderIds = buildTypeList
    return this
}

fun createSubProject(projectName: String, projectId: String) :  SubProjectBuilder{
      project = Project()
      parentProject.subProject(project)
      project.name= projectName
      project.id(projectId)
      return this
}

fun build() : Project {
    return project
}

}
