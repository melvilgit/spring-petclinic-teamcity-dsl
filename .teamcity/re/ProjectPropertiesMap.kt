package re

val PIPELINE_CONFIG = arrayOf (
    hashMapOf<String, Any> (
        "project.name" to "Dobson BC CC CM PC1",
        "branch.name" to "releng_dobson_50_4-bc-cc-cm-pc",
        "project.tenant.1" to "mint/releng"


        //,
//        "project.applications" to arrayOf ("bc", "cc", "cm", "pc")
    ),
    hashMapOf<String, Any> (
        "project.name" to "Dobson BC CC CM PC DG",
        "branch.name" to "releng_dobson_50_4-bc-cc-cm-pc-dg",
        "project.tenant.1" to "mint/releng",
        "project.tenant.2" to "mdev/somedev"
    )

)

val COMMON = hashMapOf<String, String>()
