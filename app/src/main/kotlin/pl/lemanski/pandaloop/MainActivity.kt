package pl.lemanski.pandaloop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.di.SingletonDependencyProvider
import pl.lemanski.pandaloop.domain.navigation.Destination
import pl.lemanski.pandaloop.domain.navigation.NavigationController
import pl.lemanski.pandaloop.domain.navigation.NavigationEvent
import pl.lemanski.pandaloop.domain.platform.PermissionManager
import pl.lemanski.pandaloop.platform.PermissionManagerImpl
import pl.lemanski.pandaloop.presentation.looper.LooperRouter
import pl.lemanski.pandaloop.presentation.recording.RecordingRouter
import pl.lemanski.pandaloop.presentation.start.StartRouter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDependencies()

        setContent {
            val navHostController = rememberNavController()
            navHostController.enableOnBackPressed(false)
            val navigationController = rememberNavigationController()
            val navigationState by navigationController.navigationState.collectAsState()


            NavHost(
                navController = navHostController,
                startDestination = Destination.StartScreen::class.java.simpleName, // TODO get from navigation state (recomposition issues)
            ) {
                composable(Destination.StartScreen::class.java.simpleName) {
                    StartRouter()
                }

                composable(Destination.RecordingScreen::class.java.simpleName) {
                    BackHandler { navigationController.goBack() }
                    RecordingRouter()
                }

                composable(Destination.LoopScreen::class.java.simpleName) {
                    BackHandler { navigationController.goBack() }
                    LooperRouter()
                }
            }

            LaunchedEffect(navigationState.destination::class) {
                when (navigationState.direction) {
                    NavigationEvent.Direction.BACKWARD -> navHostController.popBackStack()
                    NavigationEvent.Direction.FORWARD  -> navHostController.navigate(navigationState.destination::class.java.simpleName)
                }
            }
        }
    }

    private fun initializeDependencies() {
        DependencyResolver.addProviders {
            // TODO use map or something
            listOf(
                SingletonDependencyProvider<PermissionManager>(PermissionManagerImpl(this@MainActivity)),
                SingletonDependencyProvider(NavigationController())
            )
        }
    }

    @Composable
    private fun rememberNavigationController(): NavigationController = remember {
        DependencyResolver.resolve<NavigationController>()
    }
}