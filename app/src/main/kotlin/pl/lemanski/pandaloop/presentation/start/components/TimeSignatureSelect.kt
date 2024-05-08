package pl.lemanski.pandaloop.presentation.start.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationResult
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.launch
import pl.lemanski.pandaloop.R
import pl.lemanski.pandaloop.core.TimeSignature
import pl.lemanski.pandaloop.domain.viewModel.start.StartContract
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun TimeSignatureSelect(
    textSelect: StartContract.State.TimeSignatureSelect,
    modifier: Modifier = Modifier,
) {
    val state = remember { mutableIntStateOf(textSelect.options.indexOf(textSelect.selected)) }
    val fadingEdgeGradient = rememberFadingEdgeGradient()
    val coroutineScope = rememberCoroutineScope()
    val itemsSize = remember { textSelect.options.size }

    val numbersColumnSize = 56.dp
    val halvedNumbersColumnSize = numbersColumnSize / 2
    val halvedNumbersColumnSizePx = with(LocalDensity.current) { halvedNumbersColumnSize.toPx() }

    fun animatedStateValue(offset: Float): Int {
        val rawValue = state.intValue - (offset / halvedNumbersColumnSizePx).toInt()
        // Ensure that the index is positive for list
        return if (rawValue < 0) {
            (itemsSize + rawValue) % itemsSize
        } else {
            rawValue % itemsSize
        }
    }

    val animatedOffset = remember { Animatable(0f) }
    val coercedAnimatedOffset = animatedOffset.value % halvedNumbersColumnSizePx
    val animatedStateValue = animatedStateValue(animatedOffset.value)

    val context = LocalContext.current
    val fontFamily = remember { FontFamily(typeface = ResourcesCompat.getFont(context, R.font.bravura)!!) }

    Column(
        modifier = Modifier
            .requiredHeight(numbersColumnSize * 2)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = textSelect.label,
            style = MaterialTheme.typography.labelMedium,
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .draggable(orientation = Orientation.Vertical, state = rememberDraggableState { deltaY ->
                    coroutineScope.launch {
                        animatedOffset.snapTo(animatedOffset.value + deltaY)
                    }
                }, onDragStopped = { velocity ->
                    coroutineScope.launch {
                        val endValue = animatedOffset.fling(initialVelocity = velocity, animationSpec = exponentialDecay(frictionMultiplier = 20f), adjustTarget = { target ->
                            val coercedTarget = target % halvedNumbersColumnSizePx
                            val coercedAnchors = List(3) { index ->
                                halvedNumbersColumnSizePx * (index - 1)
                            }
                            val coercedPoint = coercedAnchors.minByOrNull { abs(it - coercedTarget) }!!
                            val base = halvedNumbersColumnSizePx * (target / halvedNumbersColumnSizePx).toInt()
                            coercedPoint + base
                        }).endState.value

                        state.intValue = animatedStateValue(endValue)
                        textSelect.onSelectedChanged(
                            textSelect.options.getOrNull(state.intValue) ?: ""
                        )
                        animatedOffset.snapTo(0f)
                    }
                })
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .drawWithContent {
                    drawContent()
                    drawRect(
                        brush = fadingEdgeGradient, blendMode = BlendMode.DstIn
                    )
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset { IntOffset(x = 0, y = coercedAnimatedOffset.roundToInt()) }
                ) {
                    repeat(2) { index ->
                        Text(
                            text = textSelect.options.getOrNull((animatedStateValue - (index + 1) + itemsSize) % itemsSize)?.visualName
                                ?: "",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(y = halvedNumbersColumnSize * (index + 1) * (-1)),
                            style = MaterialTheme.typography.titleLarge.copy(fontFamily = fontFamily),
                        )
                    }

                    Text(
                        text = textSelect.options.getOrNull((animatedStateValue + itemsSize) % itemsSize)?.visualName
                            ?: "",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.titleLarge.copy(fontFamily = fontFamily),
                    )

                    repeat(2) { index ->
                        Text(
                            text = textSelect.options.getOrNull((animatedStateValue + (index + 1) + itemsSize) % itemsSize)?.visualName
                                ?: "",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset(y = halvedNumbersColumnSize * (index + 1)),
                            style = MaterialTheme.typography.titleLarge.copy(fontFamily = fontFamily),
                        )
                    }
                }
            }
        }
    }
}

private suspend fun Animatable<Float, AnimationVector1D>.fling(
    initialVelocity: Float,
    animationSpec: DecayAnimationSpec<Float>,
    adjustTarget: ((Float) -> Float)?,
    block: (Animatable<Float, AnimationVector1D>.() -> Unit)? = null,
): AnimationResult<Float, AnimationVector1D> {
    val targetValue = animationSpec.calculateTargetValue(value, initialVelocity)
    val adjustedTarget = adjustTarget?.invoke(targetValue)

    return if (adjustedTarget != null) {
        animateTo(
            targetValue = adjustedTarget,
            initialVelocity = initialVelocity,
            block = block
        )
    } else {
        animateDecay(
            initialVelocity = initialVelocity,
            animationSpec = animationSpec,
            block = block,
        )
    }
}

@Composable
private fun rememberFadingEdgeGradient() = remember {
    Brush.verticalGradient(
        0f to Color.Transparent,
        0.5f to Color.Black,
        1f to Color.Transparent
    )
}

private val String.visualName: String
    get() = when (this) {
        TimeSignature.COMMON.name      -> "\uE084\uE08E\uE084"
        TimeSignature.THREE_FOURS.name -> "\uE083\uE08E\uE084"
        else                           -> ""
    }
