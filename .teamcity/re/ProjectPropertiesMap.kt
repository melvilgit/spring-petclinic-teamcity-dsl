package re

val PIPELINE_CONFIG = arrayOf (
    hashMapOf<String, Any> (
        "project.name" to "Dobson BC CC CM PC",
        "branch.name" to "releng_dobson_50_4-bc-cc-cm-pc",
        "clusters.tenants" to arrayListOf("mint.releng"),
         "version_update_dependencies" to  arrayListOf("BillingCenter","ClaimCenter")
    ),
    hashMapOf<String, Any> (
        "project.name" to "Dobson BC CC CM PC DG",
        "branch.name" to "releng_dobson_50_4-bc-cc-cm-pc-dg",
        "clusters.tenants" to arrayListOf("mint.releng", "mdev.somedev")
    )

)

val COMMON = hashMapOf<String, String>()
