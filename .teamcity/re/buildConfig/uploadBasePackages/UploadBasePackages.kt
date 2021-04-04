package re.buildConfig.uploadBasePackages

import jetbrains.buildServer.configs.kotlin.v2019_2.BuildType
import re.ProjectProperties

class UploadBasePackages(private val props : ProjectProperties) : BuildType({
    id("${props.get("project.uploadBasePackages.id")}_UploadBasePackages")
    name = "Upload Base Packages"
    description = "Upload base packages to S3 and ECR"
})