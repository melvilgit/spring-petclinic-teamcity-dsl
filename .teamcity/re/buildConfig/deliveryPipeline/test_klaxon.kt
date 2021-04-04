package re.buildConfig.deliveryPipeline
//package re.buildConfig.deliveryPipeline


import org.json.JSONObject

val json = """
    {
        "data": [
            { "name": "aaa", "age": 11 },
            { "name": "bbb", "age": 22 },
        ],
        "otherdata" : "not needed"
    }
""".trimIndent()



fun main(args: Array<String>) {
    val jo = JSONObject(
            "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\",\"portfolio\":{\"age\":2}}"
    )
    println(jo.getJSONObject("portfolio").get("age"))
}