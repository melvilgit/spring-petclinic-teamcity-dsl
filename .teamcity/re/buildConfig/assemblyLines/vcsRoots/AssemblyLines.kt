package re.buildConfig.assemblyLines.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot
import re.ProjectProperties


class AssemblyLines(private val props : ProjectProperties) : GitVcsRoot({
    val branchName = props.get("project.id")
     id("${branchName}_AssemblyLinesGit")
     name = "Assembly_lines_Vcs_Root"
     url = "ssh://stash.guidewire.com/porta/assembly-lines.git"
     branch = "refs/heads/${props.get("branch.name")}"
     authMethod = uploadedKey {
        userName = "git"
        uploadedKey = "sys-releng-stash"
    }

})
