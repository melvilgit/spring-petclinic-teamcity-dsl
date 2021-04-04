package re

class ProjectProperties(
    private val projectPropertiesMap: MutableMap<String, Any>
) {

    fun has(key: String): Boolean {
        return projectPropertiesMap.containsKey(key)
    }

    fun get(key: String): String {
        val v = projectPropertiesMap[key]

        return when (v) {
            is String -> v
            null -> if (COMMON.containsKey(key)) COMMON[key]!! else throw IllegalStateException("Property '$key' not found")
            else -> throw IllegalStateException("Property '$key' not a string")
        }
    }

    fun set(key: String, value: String) {
        projectPropertiesMap[key] = value
    }

    fun getArray(key: String): Array<*> {
        val v = projectPropertiesMap[key]

        if (v is Array<*>) {
            return v
        } else {
            throw IllegalStateException("Property '$key' not an array")
        }
    }

    fun getMap(key: String): Map<*,*> {
        val v = projectPropertiesMap[key]

        if (v is Map<*, *>) {
            return v
        } else {
            throw IllegalStateException("Property '$key' not a Map")
        }
    }

    fun filterKeys(pattern: String) : List<String>{
        return projectPropertiesMap.keys.filter { key-> key.matches(regex = Regex(pattern)) }
    }


}