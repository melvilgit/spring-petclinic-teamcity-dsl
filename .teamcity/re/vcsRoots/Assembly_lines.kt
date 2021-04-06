package re.vcsRoots


import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

class Assembly_lines(
) : GitVcsRoot({
    name = "Assembly_lines_Vcs_Root"
    url = "ssh://git@stash.guidewire.com/porta/assembly-gradle-plugin.git"
    branch = "refs/heads/master"
    authMethod = uploadedKey {
        uploadedKey = "sample_ssh_keys"
    }
})