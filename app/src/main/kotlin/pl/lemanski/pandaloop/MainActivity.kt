package pl.lemanski.pandaloop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import pl.lemanski.pandaloop.domain.PermissionManager
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.di.SingletonDependencyProvider
import pl.lemanski.pandaloop.platform.PermissionManagerImpl
import pl.lemanski.pandaloop.presentation.looper.LooperRouter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDependencies()

        setContent {
            MaterialTheme {
                LooperRouter()
            }
        }
    }

    private fun initializeDependencies() {
        DependencyResolver.addProviders {
            // TODO use map or something
            listOf(
                SingletonDependencyProvider<PermissionManager>(PermissionManagerImpl(this@MainActivity))
            )
        }
    }
}