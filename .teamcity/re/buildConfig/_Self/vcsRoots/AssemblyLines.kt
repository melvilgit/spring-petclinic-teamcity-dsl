package re.buildConfig._Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot


object AssemblyLines : GitVcsRoot({
    id("AssemblyLinesGit")
    name = "Assembly_lines_Vcs_Root"
    url = "ssh://stash.guidewire.com/porta/assembly-lines.git"
    branch = "refs/heads/%cbc.branch.name%"
    authMethod = uploadedKey {
        userName = "git"
        uploadedKey = "sys-releng-stash"
    }

})
