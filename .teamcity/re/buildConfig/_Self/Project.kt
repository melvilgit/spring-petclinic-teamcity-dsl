package re.buildConfig._Self

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.PIPELINE_CONFIG
import re.ProjectProperties
import re.buildConfig._Self.vcsRoots.AssemblyLines
import re.buildConfig.cbcSubProjects.CbcSubProjects


object Project : Project({
    description = "Release Engineering/Cloud Delivery Pipeline"

//    vcsRoot(HawaiiE2eApiIntegrationBddVcsRoot)
//    vcsRoot(PlanetAlphaMetricCollectorTemplate1)
//
//    template(PortfolioAssembly_DatadogMetricCollector)

    params {
        param("env.PIP_EXTRA_INDEX_URL", "https://pypi.org/simple")
        param("monitoring.docker.image", "artifactory.guidewire.com/releng-docker-release/cloud-delivery-pipeline-monitoring:1.0.2")
        param("env.PIP_INDEX_URL", "https://%artifactory.user%:%artifactory.password.url.encoded%@artifactory.guidewire.com/api/pypi/releng-pypi-release/simple")
        param("artifactory.user", "sys-releng-artf")
        param("python.392.docker.image", "artifactory.guidewire.com/hub-docker-remote/python:3.9.2-slim")
    }

    vcsRoot(AssemblyLines)

    val projectProperties = PIPELINE_CONFIG.map { project -> ProjectProperties(project.toMutableMap()) }
    for (props in projectProperties) {
        val pipelineProjectId = props.get("branch.name").replace('-', '_')
        props.set("project.id", pipelineProjectId)
        val cbcProject = CbcSubProjects(props)
        subProject(cbcProject)

    }
})



