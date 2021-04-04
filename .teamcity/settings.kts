import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.ui.add
import re.PIPELINE_CONFIG
import re.ProjectProperties
import re.buildConfig.assemblyLines.*
import re.buildConfig.cloudDeployments.*
import re.buildConfig.gwcpProjectProvisioning.*
import re.buildConfig.uploadBasePackages.UploadBasePackages
import java.lang.Exception


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

project {

    params.add(Parameter("teamcity.ui.settings.readOnly", "true"))

    val projectProperties = PIPELINE_CONFIG.map { project -> ProjectProperties(project.toMutableMap()) }
   try {
       for (props in projectProperties) {
           subProject pipelineProject@{
               val pipelineProjectId = props.get("branch.name").replace('-', '_')
               props.set("project.id", pipelineProjectId)
               id(pipelineProjectId)
               name = props.get("project.name")

               val subProjectsOrderList = arrayListOf<Project>()

               subProject(AssemblyLines(props))
               subProject uploadBasePackages@{
                   parentId(pipelineProjectId)
                   val uploadBasePackagesProjectId = "${pipelineProjectId}_UploadBasePackages"
                   props.set("project.uploadBasePackages.id", uploadBasePackagesProjectId)
                   id(uploadBasePackagesProjectId)
                   name = "Upload Base Packages"
                   subProjectsOrderList.add(this)

                   buildType(UploadBasePackages(props))
               }

               subProject GWCPProjectProvisioning@{
                   parentId(pipelineProjectId)
                   val gwcpProvisioningProjectId = "${pipelineProjectId}_GWCPProjectProvisioning"
                   props.set("project.gwcpProvisioning.id", gwcpProvisioningProjectId)
                   id(gwcpProvisioningProjectId)
                   name = "GWCP Project provisioning"
                   subProjectsOrderList.add(this)
                   val clusterOrderList = arrayListOf<Project>()

                   val tenantsList = props.filterKeys("project\\.tenant\\.\\d+")
                   tenantsList.forEach { tenant->
                       run {
                           var (clusterName, tenantName) = props.get(tenant).split("/")
                           clusterName = clusterName.trim()
                           tenantName = tenantName.trim()

                           subProject ClusterProject@{
                               parentId(gwcpProvisioningProjectId)
                               val clusterProjectId = "${gwcpProvisioningProjectId}_${clusterName}"
                               props.set("project.cluster.id", clusterProjectId)
                               id(clusterProjectId)
                               name = clusterName
                               clusterOrderList.add(this)
                               val tenantOrderList = arrayListOf<Project>()

                               subProject TenantProject@{
                                   parentId(pipelineProjectId)
                                   val tenantProjectId = "${clusterProjectId}_${tenantName}"
                                   props.set("project.tenant.id", tenantProjectId)
                                   id(tenantProjectId)
                                   name = tenantName
                                   tenantOrderList.add(this)

                                   val projectProvision = ProvisionProject(props)
                                   val patchCICD = PatchCICD(props)
                                   val callUpdateISSourceCode = CallUpdateISSourceCode(props)
                                   val integrationTest = IntegrationTest(props)
                                   val migrateCICD = MigrateCICD(props)
                                   val updateCICD = UpdateCICD(props)
                                   val deleteProject = DeleteProject(props)

                                   buildType(projectProvision)
                                   buildType(patchCICD)
                                   buildType(callUpdateISSourceCode)
                                   buildType(integrationTest)
                                   buildType(migrateCICD)
                                   buildType(updateCICD)
                                   buildType(deleteProject)

                                   buildTypesOrderIds = arrayListOf(
                                       projectProvision,
                                       patchCICD,
                                       callUpdateISSourceCode,
                                       integrationTest,
                                       migrateCICD,
                                       updateCICD,
                                       deleteProject
                                   )

                               }
                               subProjectsOrder = tenantOrderList
                           }
                       }
                   }
                   subProjectsOrder = clusterOrderList
               }

               subProject cloudDeployments@{
                   parentId(pipelineProjectId)
                   val cloudDeploymentsProjectId = "${pipelineProjectId}_CloudDeployments"
                   props.set("project.cloudDeployments.id", cloudDeploymentsProjectId)
                   id(cloudDeploymentsProjectId)
                   name = "Cloud Deployments"
                   subProjectsOrderList.add(this)

                   val deployInGWCPEnvironment = DeployInGWCPEnvironment(props)
                   val deploymentHealthCheck = DeploymentHealthCheck(props)

                   buildType(deployInGWCPEnvironment)
                   buildType(deploymentHealthCheck)
                   buildTypesOrderIds = arrayListOf(
                       deployInGWCPEnvironment,
                       deploymentHealthCheck
                   )
               }
               subProjectsOrder = subProjectsOrderList
           }
       }
   }catch (e: Exception){
       e.printStackTrace()
   }
}
