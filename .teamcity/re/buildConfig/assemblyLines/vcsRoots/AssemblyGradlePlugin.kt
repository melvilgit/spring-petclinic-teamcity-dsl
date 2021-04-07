package re.buildConfig.assemblyLines.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import re.ProjectProperties


class AssemblyGradlePlugin(private val props: ProjectProperties) : GitVcsRoot({
    val branchName = props.get("project.id")
    id("${branchName}_AssemblyGradlePlugin")
    name = "Assembly Gradle Plugin"
    url = "ssh://git@stash.guidewire.com/porta/assembly-gradle-plugin.git"
    branchSpec = "+:refs/heads/*"
    authMethod = uploadedKey {
        userName = "git"
        uploadedKey = "sas access key"
    }
})

