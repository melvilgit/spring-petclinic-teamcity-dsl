package re.buildConfig.assemblyLines.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.AbsoluteId
import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import jetbrains.buildServer.configs.kotlin.v2019_2.FailureAction
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import re.PRODUCTS
import re.ProjectProperties

class VersionUpdate(private val props: ProjectProperties) : BuildType({
    val buildId = "${props.get("project.assemblyLine.id")}_VersionUpdate"
    id(buildId)
    name = "Version Update"
    description = "Replaces product versions in 'build.gradle' and updates build version in 'gradle.properties' file"

    if (props.getMap("build.pause").get("flag") == "false") {
        check(paused == false) {
            "Unexpected paused: '$paused'"
        }
        paused = true

    }


    val paramsHash = hashMapOf<String, String>(
            "env.METRIC_PREFIX" to "releng.cbc.pd_teamcity.pal_version_update",
            "dd.tags.source.type" to "pal-gradle"
    )

    /* dependencies*/
    @Suppress("UNCHECKED_CAST")
    val productDependencies = props.getArrayList("products") as? ArrayList<PRODUCTS>
    if (productDependencies != null) {
        for (product in productDependencies) this.dependencies.snapshot(AbsoluteId(product.dependency_project_id)) {
            onDependencyFailure = FailureAction.FAIL_TO_START
            synchronizeRevisions = false
        }
    }

    /* parameters */

    if (productDependencies != null) {
        for (product in productDependencies) this.params.param("env.${product.acronym}_BUILD_NO", "%dep.${product.dependency_project_id}.build.number%")
    }

    for ((key, value) in paramsHash) {
        this.params.param(key, value)
    }

    steps {
        script {
            name = "Update build.gradle"
            id = "RUNNER_1"
            scriptContent = "python AssemblyLineUtil.py  -BC_Build_Number ${'$'}{BC_BUILD_NO} -CC_Build_Number ${'$'}{CC_BUILD_NO} -CM_Build_Number ${'$'}{CM_BUILD_NO} -PC_Build_Number  ${'$'}{PC_BUILD_NO} -Template_File ./build.gradle_template -Destination_File ./build.gradle"
            dockerImage = "artifactory.guidewire.com/hub-docker-remote/python:latest"
            dockerRunParameters = """
                -v "${'$'}SSH_AUTH_SOCK:/tmp/ssh_auth_sock"
                -e "SSH_AUTH_SOCK=/tmp/ssh_auth_sock"
            """.trimIndent()
        }
        script {
            name = "Update Gradle.properties"
            id = "RUNNER_2"
            scriptContent = """
                #!/bin/bash
                
                
                CURRENT_VERSION=`cat gradle.properties | grep -oP "(?<=^version=)[\d\.]*"`  #will exracts example 1.0.1
                CURRENT_MAJOR_VERSION=`echo ${'$'}CURRENT_VERSION | awk -F "." '{print ${'$'}1F}'`
                CURRENT_MINOR_VERSION=`echo ${'$'}CURRENT_VERSION | awk -F "." '{print ${'$'}2F}'`
                CURRENT_PATCH_VERSION=`echo ${'$'}CURRENT_VERSION | awk -F "." '{print ${'$'}3F}'`
                CURRENT_BUILD_VERSION=`echo ${'$'}CURRENT_VERSION | awk -F "." '{print ${'$'}4F}'`
                NEW_BUILD_VERSION=`echo "${'$'}((${'$'}CURRENT_BUILD_VERSION+1))"`
                
                NEW_VERSION="${'$'}CURRENT_MAJOR_VERSION.${'$'}CURRENT_MINOR_VERSION.${'$'}CURRENT_PATCH_VERSION.${'$'}NEW_BUILD_VERSION" # new version will be major.minor.patch.build
                sed -i "s/version=${'$'}CURRENT_VERSION/version=${'$'}NEW_VERSION/g"  gradle.properties
                echo "##teamcity[setParameter name='env.NEW_VERSION' value='${'$'}{NEW_VERSION}']"
            """.trimIndent()
        }
        script {
            name = "Commit and Push Changes"
            id = "RUNNER_3"
            scriptContent = """
                #!/bin/bash
                git config user.name "TeamCity"
                git config user.email "sas-user@guidewire.com"
                git add build.gradle gradle.properties && git commit -m "Updating build.gradle to BC=${'$'}{BC_BUILD_NO}, CC=${'$'}{CC_BUILD_NO}, CM=${'$'}{CM_BUILD_NO}, PC=${'$'}{PC_BUILD_NO}" -m "Updating gradle.properties to ${'$'}{NEW_VERSION}" && git pull && git push
            """.trimIndent()
        }
    }


})

//ReleaseEngineering_CloudDeliveryPipelines_releng_dobson_50_4_bc_cc_cm_pc_AssemblyLine_VersionUpdate

