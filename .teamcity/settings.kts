import jetbrains.buildServer.configs.kotlin.v2019_2.Parameter
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.project
import jetbrains.buildServer.configs.kotlin.v2019_2.version
import re.PIPELINE_CONFIG
import re.ProjectProperties
import re.buildConfig.SubProjectBuilder
import re.buildConfig.assemblyLines.buildTypes.Build
import re.buildConfig.assemblyLines.buildTypes.DockerizeAndPublish
import re.buildConfig.assemblyLines.buildTypes.PromoteArtifactsToS3
import re.buildConfig.assemblyLines.buildTypes.VersionUpdate
import re.buildConfig.cloudDeployments.*
import re.buildConfig.gwcpProjectProvisioning.*
import re.buildConfig.uploadBasePackages.UploadBasePackages
import re.buildConfig.assemblyLines.AssemblyLinesProject

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2020.1"

//project {
//
//    params.add(Parameter("teamcity.ui.settings.readOnly", "false"))
//    val projectProperties = PIPELINE_CONFIG.map { project -> ProjectProperties(project.toMutableMap()) }
//
//    for (props in projectProperties) {
//
//        val subProjectsOrderList = arrayListOf<Project>()
//        val pipelineProjectId = props.get("branch.name").replace('-', '_')
//        props.set("project.id", pipelineProjectId)
//        /* Branch Name Subproject */
//        val pipeLineProject = SubProjectBuilder(this)
//            .createSubProject(props.get("project.name"), pipelineProjectId)
//            .build()
//
//        /* Assembly Line subproject */
//        val assemblyLineProjectId = "${pipelineProjectId}_AssemblyLine"
//        props.set("project.assemblyLine.id", assemblyLineProjectId)
//        val assemblyLineProject = AssemblyLinesProject(props)
//        pipeLineProject.subProject(assemblyLineProject)
//        subProjectsOrderList.add(assemblyLineProject)
//
//
//        val uploadBasePackagesProjectId = "${pipelineProjectId}_UploadBasePackages"
//        props.set("project.uploadBasePackages.id", uploadBasePackagesProjectId)
//        val uploadBasePackagesProject = SubProjectBuilder(pipeLineProject)
//            .createSubProject("Upload Base Packages", uploadBasePackagesProjectId)
//            .withBuildTypeList(arrayListOf(UploadBasePackages(props)))
//            .build()
//        subProjectsOrderList.add(uploadBasePackagesProject)
//
//        val gwcpProvisioningProjectId = "${pipelineProjectId}_GWCPProjectProvisioning"
//        props.set("project.gwcpProvisioning.id", gwcpProvisioningProjectId)
//        val clusterOrderList = arrayListOf<Project>()
//        val gwcpProvisioningProject = SubProjectBuilder(pipeLineProject)
//            .createSubProject("GWCP Project provisioning", gwcpProvisioningProjectId)
//            .build()
//        subProjectsOrderList.add(gwcpProvisioningProject)
//
//        val tenantsList = props.getArrayList("clusters.tenants")
//
//        for (tenant in tenantsList) {
//            var (clusterName, tenantName) = tenant.toString().split(".")
//            clusterName = clusterName.trim()
//            tenantName = tenantName.trim()
//
//            val clusterProjectId = "${gwcpProvisioningProjectId}_${clusterName}"
//            props.set("project.cluster.id", clusterProjectId)
//
//            val clusterProject = SubProjectBuilder(gwcpProvisioningProject)
//                .createSubProject(clusterName, clusterProjectId)
//                .build()
//            clusterOrderList.add(clusterProject)
//
//
//            val tenantProjectId = "${clusterProjectId}_${tenantName}"
//            props.set("project.tenant.id", tenantProjectId)
//
//            SubProjectBuilder(clusterProject)
//                .createSubProject(tenantName, tenantProjectId)
//                .withBuildTypeList(
//                    arrayListOf(
//                        CallUpdateISSourceCode(props),
//                        DeleteProject(props),
//                        IntegrationTest(props),
//                        MigrateCICD(props),
//                        PatchCICD(props),
//                        ProvisionProject(props),
//                        UpdateCICD(props)
//
//                    )
//                )
//                .build()
//        }
//        gwcpProvisioningProject.subProjectsOrder = clusterOrderList
//
//        val cloudDeploymentsProjectId = "${pipelineProjectId}_CloudDeployments"
//        props.set("project.cloudDeployments.id", cloudDeploymentsProjectId)
//        val cloudDeploymentsProject = SubProjectBuilder(pipeLineProject)
//            .createSubProject("Cloud Deployments", cloudDeploymentsProjectId)
//            .withBuildTypeList(
//                arrayListOf(
//                    DeployInGWCPEnvironment(props),
//                    DeploymentHealthCheck(props)
//                )
//            )
//            .build()
//
//        subProjectsOrderList.add(cloudDeploymentsProject)
//
//        pipeLineProject.subProjectsOrder = subProjectsOrderList
//
//    }
//}

