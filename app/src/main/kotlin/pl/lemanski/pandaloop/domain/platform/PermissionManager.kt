package pl.lemanski.pandaloop.domain.platform

interface PermissionManager {
    suspend fun askPermission(permission: Permission): PermissionState

    fun checkPermissionState(permission: Permission): PermissionState

    fun Permission.toPlatformPermission(): Any

    enum class PermissionState {
        GRANTED,
        DENIED,
        NOT_DETERMINED
    }

    enum class Permission {
        RECORD_AUDIO
    }
}