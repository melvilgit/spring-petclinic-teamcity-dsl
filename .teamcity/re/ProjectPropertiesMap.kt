package re

val PIPELINE_CONFIG = arrayOf(
        hashMapOf<String, Any>(
                "project.name" to "Dobson BC CC CM PC",
                "branch.name" to "releng_dobson_50_4-bc-cc-cm-pc",
                "clusters.tenants" to arrayListOf("mint.releng"),
                "products" to arrayListOf(PRODUCTS.BillingCenter, PRODUCTS.ClaimCenter, PRODUCTS.ContactManager, PRODUCTS.PolicyCenter),
                "pause_flag" to true
        ),
        hashMapOf<String, Any>(
                "project.name" to "Dobson BC CC CM PC DG",
                "branch.name" to "releng_dobson_50_4-bc-cc-cm-pc-dg",
                "clusters.tenants" to arrayListOf("mint.releng", "mdev.somedev"),
                "products" to arrayListOf(PRODUCTS.BillingCenter, PRODUCTS.ClaimCenter, PRODUCTS.ContactManager, PRODUCTS.PolicyCenter),
                "pause_flag" to true
        )

)

enum class PRODUCTS(val dependency_project_id: String, val acronym:String) {
    BillingCenter("InsuranceSuite_InsuranceSuiteDelivery_VIsCloud_BillingCenter_CustomerBuild", "BC"),
    ClaimCenter("InsuranceSuite_InsuranceSuiteDelivery_VIsCloud_ClaimCenter_CustomerBuild", "CC"),
    ContactManager("InsuranceSuite_InsuranceSuiteDelivery_VIsCloud_ContactManager_CustomerBuild", "CM"),
    PolicyCenter("InsuranceSuite_InsuranceSuiteDelivery_VIsCloud_PolicyCenter_CustomerBuild" ,"PC")
}


val COMMON = hashMapOf<String, String>()
