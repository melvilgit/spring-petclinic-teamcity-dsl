package re.buildConfig.assemblyLines

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties

class AssemblyLines(private val props: ProjectProperties) : Project({
    val pipelineProjectId = props.get("branch.name").replace('-', '_')
    id("${pipelineProjectId}_AssemblyLine")
    name = "Assembly Lines"
    description = "Portfolio Automations"
    props.set("project.assemblyLine.id", "${pipelineProjectId}_AssemblyLine")
    val versionUpdate = VersionUpdate(props)
    val build = Build(props)
    val dockerizeAndPublish = DockerizeAndPublish(props)
    val promoteToS3 = PromoteArtifactsToS3(props)

    buildType(versionUpdate)
    buildType(build)
    buildType(dockerizeAndPublish)
    buildType(promoteToS3)
    buildTypesOrderIds = arrayListOf(
            versionUpdate,
            build,
            dockerizeAndPublish,
            promoteToS3
    )

})
