package re.buildConfig._Self

import jetbrains.buildServer.configs.kotlin.v2019_2.ParameterDisplay
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.RelativeId
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry
import re.PIPELINE_CONFIG
import re.ProjectProperties
import re.buildConfig.cbcSubProjects.cbcSubProjects


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
        param("env.ARTIFACTORY_USERNAME", "sys-devops")
        password("artifactory.password.url.encoded", "credentialsJSON:32964489-fc1d-401a-a78a-8aaa09f01e40", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARTIFACTORY_PASSWORD", "credentialsJSON:ffdcf9ef-ccaa-426a-9033-82a549de9181", display = ParameterDisplay.HIDDEN)
    }

    subProjectsOrder = arrayListOf(RelativeId("PortfolioAssembly"))

    val projectProperties = PIPELINE_CONFIG.map { project -> ProjectProperties(project.toMutableMap()) }
    for (props in projectProperties) {
        val pipelineProjectId = props.get("branch.name").replace('-', '_')
        props.set("project.id", pipelineProjectId)
        val cbcProject = cbcSubProjects(props)
        subProject(cbcProject)

    }
})



