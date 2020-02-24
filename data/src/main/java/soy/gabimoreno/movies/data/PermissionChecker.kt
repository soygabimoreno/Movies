package soy.gabimoreno.movies.data

interface PermissionChecker {

    enum class Permission {
        COARSE_LOCATION
    }

    suspend fun check(permission: Permission): Boolean
}
