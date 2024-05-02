package pl.lemanski.pandaloop.platform

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import pl.lemanski.pandaloop.domain.platform.PermissionManager
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// TODO add activity lifecycle callbacks
class PermissionManagerImpl(
    private val activity: ComponentActivity
) : PermissionManager {
    private var launcher: ActivityResultLauncher<String>? = null

    override suspend fun askPermission(permission: PermissionManager.Permission): PermissionManager.PermissionState =
        suspendCoroutine { continuation ->
            launcher = activity.activityResultRegistry
                .register(
                    key = UUID.randomUUID().toString(),
                    contract = ActivityResultContracts.RequestPermission(),
                    callback = { isGranted ->
                        launcher?.unregister()
                        continuation.resume(if (isGranted) PermissionManager.PermissionState.GRANTED else PermissionManager.PermissionState.DENIED)
                    }
                ).also {
                    it.launch(permission.toPlatformPermission())
                }
        }

    override fun checkPermissionState(permission: PermissionManager.Permission): PermissionManager.PermissionState =
        when (activity.checkSelfPermission(permission.toPlatformPermission())) {
            PackageManager.PERMISSION_DENIED -> PermissionManager.PermissionState.DENIED
            PackageManager.PERMISSION_GRANTED -> PermissionManager.PermissionState.GRANTED
            else -> PermissionManager.PermissionState.NOT_DETERMINED
        }

    override fun PermissionManager.Permission.toPlatformPermission(): String = when (this) {
        PermissionManager.Permission.RECORD_AUDIO -> android.Manifest.permission.RECORD_AUDIO
    }
}