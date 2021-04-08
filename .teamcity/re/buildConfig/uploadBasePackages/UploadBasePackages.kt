package re.buildConfig.uploadBasePackages

import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.ProjectProperties

class UploadBasePackages(private val props: ProjectProperties) : Project({
    val uploadBasePackagesProjectId = "${props.get("project.id")}_UploadBasePackages"
    props.set("project.uploadBasePackages.id", uploadBasePackagesProjectId)
    id(uploadBasePackagesProjectId)
    name = "Upload Base Packages"
    description = "Upload base packages to S3 and ECR"
})