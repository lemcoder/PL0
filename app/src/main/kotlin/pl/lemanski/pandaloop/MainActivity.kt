package pl.lemanski.pandaloop

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import pl.lemanski.pandaloop.domain.di.DependencyResolver
import pl.lemanski.pandaloop.domain.di.SingletonDependencyProvider
import pl.lemanski.pandaloop.domain.model.navigation.EffectsScreen
import pl.lemanski.pandaloop.domain.model.navigation.LoopScreen
import pl.lemanski.pandaloop.domain.model.navigation.RecordingScreen
import pl.lemanski.pandaloop.domain.model.navigation.SequencerScreen
import pl.lemanski.pandaloop.domain.model.navigation.StartScreen
import pl.lemanski.pandaloop.domain.platform.i18n.Localization
import pl.lemanski.pandaloop.domain.platform.permission.PermissionManager
import pl.lemanski.pandaloop.domain.service.navigation.NavigationService
import pl.lemanski.pandaloop.domain.service.navigation.NavigationServiceImpl
import pl.lemanski.pandaloop.domain.service.navigation.back
import pl.lemanski.pandaloop.platform.PermissionManagerImpl
import pl.lemanski.pandaloop.platform.i18n.LocalizationImpl
import pl.lemanski.pandaloop.presentation.looper.LooperRouter
import pl.lemanski.pandaloop.presentation.recording.RecordingRouter
import pl.lemanski.pandaloop.presentation.sequencer.SequencerRouter
import pl.lemanski.pandaloop.presentation.start.StartRouter
import pl.lemanski.pandaloop.presentation.visual.theme.PandaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDependencies()

        setContent {
            PandaTheme {
                Surface {
                    val navigationController = rememberNavigationController()
                    val navigationState by navigationController.navigationState.collectAsState()

                    BackHandler { navigationController.back() }

                    when (navigationState.destination) {
                        is EffectsScreen   -> { /* TODO */
                        }
                        is LoopScreen      -> LooperRouter()
                        is RecordingScreen -> RecordingRouter()
                        is SequencerScreen -> SequencerRouter()
                        StartScreen        -> StartRouter()
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