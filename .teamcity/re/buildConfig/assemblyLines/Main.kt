package re.buildConfig.assemblyLines
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import re.buildConfig.*
 class Main:  Project({
    description = "Release Engineering/Cloud Delivery Pipeline"
     AssemblyLinesProject
     subProjectsOrder = arrayListOf(AssemblyLinesProject())
})