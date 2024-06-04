package pl.lemanski.pandaloop

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.di.SingletonDependencyProvider
import pl.lemanski.pandaloop.domain.model.navigation.Destination
import pl.lemanski.pandaloop.domain.model.navigation.NavigationEvent
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.platform.permission.PermissionManager
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.NavigationServiceImpl
import pl.lemanski.pandaloop.domain.service.navigation.back
import pl.lemanski.pandaloop.platform.PermissionManagerImpl
import pl.lemanski.pandaloop.platform.i18n.LocalizationImpl
import pl.lemanski.pandaloop.presentation.looper.LooperRouter
import pl.lemanski.pandaloop.presentation.recording.RecordingRouter
import pl.lemanski.pandaloop.presentation.start.StartRouter
import pl.lemanski.pandaloop.presentation.visual.theme.PandaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDependencies()

        setContent {
            PandaTheme {
                Surface {
                    val navHostController = rememberNavController()
                    navHostController.enableOnBackPressed(false)
                    val navigationController = rememberNavigationController()
                    val navigationState by navigationController.navigationState.collectAsState()

                    NavHost(
                        navController = navHostController,
                        startDestination = Destination.StartScreen::class.java.simpleName,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                    ) {
                        composable(Destination.StartScreen::class.java.simpleName) {
                            StartRouter()
                        }

                        composable(Destination.RecordingScreen::class.java.simpleName) {
                            BackHandler { navigationController.back() }
                            RecordingRouter()
                        }

                        composable(Destination.LoopScreen::class.java.simpleName) {
                            BackHandler { navigationController.back() }
                            LooperRouter()
                        }
                    }

                    LaunchedEffect(navigationState.destination::class) {
                        if (navHostController.currentDestination?.route == navigationState.destination.javaClass.simpleName) {
                            return@LaunchedEffect
                        }

                        when (navigationState.direction) {
                            NavigationEvent.Direction.BACKWARD -> navHostController.popBackStack()
                            NavigationEvent.Direction.FORWARD  -> navHostController.navigate(navigationState.destination.javaClass.simpleName)
                        }
                    }
                }
            }
        }
    }

    private fun initializeDependencies() {
        DependencyResolver.addProviders {
            listOf(
                SingletonDependencyProvider<PermissionManager>(PermissionManagerImpl(this@MainActivity)),
                SingletonDependencyProvider<NavigationService>(NavigationServiceImpl()),
                SingletonDependencyProvider<Localization>(LocalizationImpl(this@MainActivity)),
                SingletonDependencyProvider<Context>(this@MainActivity.applicationContext)
            )
        }
    }

    @Composable
    private fun rememberNavigationController(): NavigationService = remember {
        DependencyResolver.resolve<NavigationService>()
    }
}