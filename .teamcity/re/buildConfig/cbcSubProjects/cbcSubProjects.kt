package re.buildConfig.cbcSubProjects

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties
import re.buildConfig.assemblyLines.AssemblyLines
import re.buildConfig.cloudDeployments.CloudDeployments
import re.buildConfig.gwcpProjectProvisioning.gwcpProjectProvisioning
import re.buildConfig.uploadBasePackages.UploadBasePackages

class CbcSubProjects(private val props: ProjectProperties) : Project({
    name = props.get("project.name")
    id(props.get("project.id"))

    subProject(AssemblyLines(props))
    subProject(gwcpProjectProvisioning(props))
    subProject(UploadBasePackages(props))
    subProject(CloudDeployments(props))

    params {
        param("cbc.branch.name", props.get("branch.name"))
    }

    features {
        feature {
            id = "PROJECT_EXT_1800"
            type = "JetBrains.SharedResources"
            param("quota", "1")
            param("name", "CBC_Version")
            param("type", "quoted")
        }
    }
})

