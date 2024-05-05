package pl.lemanski.pandaloop.presentation.visual.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcSpeakerMute: ImageVector
    get() {
        if (_backingField != null) {
            return _backingField!!
        } else {
            _backingField = ImageVector.Builder(
                defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp,
                viewportWidth = 40.0f,
                viewportHeight = 40.0f
            ).apply {
                path(
                    fill = SolidColor(Color.Black),
                    fillAlpha = 1f,
                    stroke = null,
                    strokeAlpha = 1f,
                    strokeLineWidth = 1.0f,
                    strokeLineCap = StrokeCap.Butt,
                    strokeLineJoin = StrokeJoin.Miter,
                    strokeLineMiter = 1f,
                    pathFillType = PathFillType.NonZero
                ) {
                    moveTo(32.417f, 36.417f)
                    lineTo(28f, 31.958f)
                    quadToRelative(-0.708f, 0.459f, -1.458f, 0.875f)
                    quadToRelative(-0.75f, 0.417f, -1.542f, 0.667f)
                    quadToRelative(-0.708f, 0.292f, -1.271f, -0.146f)
                    quadToRelative(-0.562f, -0.437f, -0.562f, -1.229f)
                    quadToRelative(0f, -0.333f, 0.187f, -0.604f)
                    quadToRelative(0.188f, -0.271f, 0.521f, -0.396f)
                    quadToRelative(0.583f, -0.208f, 1.146f, -0.458f)
                    quadToRelative(0.562f, -0.25f, 1.062f, -0.584f)
                    lineToRelative(-6.125f, -6.166f)
                    verticalLineToRelative(5.875f)
                    quadToRelative(0f, 0.916f, -0.812f, 1.229f)
                    quadToRelative(-0.813f, 0.312f, -1.396f, -0.313f)
                    lineToRelative(-5.917f, -5.875f)
                    horizontalLineTo(6.625f)
                    quadToRelative(-0.583f, 0f, -0.937f, -0.375f)
                    quadToRelative(-0.355f, -0.375f, -0.355f, -0.916f)
                    verticalLineToRelative(-7.084f)
                    quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                    reflectiveQuadToRelative(0.917f, -0.375f)
                    horizontalLineToRelative(4.583f)
                    lineTo(3.375f, 7.333f)
                    quadToRelative(-0.417f, -0.375f, -0.396f, -0.937f)
                    quadToRelative(0.021f, -0.563f, 0.396f, -0.938f)
                    quadToRelative(0.417f, -0.416f, 0.937f, -0.416f)
                    quadToRelative(0.521f, 0f, 0.938f, 0.416f)
                    lineTo(34.333f, 34.5f)
                    quadToRelative(0.417f, 0.417f, 0.417f, 0.958f)
                    quadToRelative(0f, 0.542f, -0.417f, 0.959f)
                    quadToRelative(-0.416f, 0.375f, -0.958f, 0.375f)
                    reflectiveQuadToRelative(-0.958f, -0.375f)
                    close()
                    moveTo(25f, 6.417f)
                    quadTo(29.208f, 8f, 31.792f, 11.688f)
                    quadToRelative(2.583f, 3.687f, 2.583f, 8.27f)
                    quadToRelative(0f, 2.125f, -0.583f, 4.125f)
                    quadToRelative(-0.584f, 2f, -1.709f, 3.75f)
                    lineToRelative(-1.916f, -1.916f)
                    quadToRelative(0.791f, -1.334f, 1.187f, -2.855f)
                    quadToRelative(0.396f, -1.52f, 0.396f, -3.104f)
                    quadToRelative(0f, -3.833f, -2.146f, -6.916f)
                    quadToRelative(-2.146f, -3.084f, -5.729f, -4.25f)
                    quadToRelative(-0.333f, -0.125f, -0.521f, -0.396f)
                    quadToRelative(-0.187f, -0.271f, -0.187f, -0.646f)
                    quadToRelative(0f, -0.75f, 0.583f, -1.167f)
                    quadToRelative(0.583f, -0.416f, 1.25f, -0.166f)
                    close()
                    moveToRelative(-9.375f, 13.166f)
                    close()
                    moveToRelative(11.208f, 3.042f)
                    lineToRelative(-3.666f, -3.708f)
                    verticalLineToRelative(-5.542f)
                    quadToRelative(1.916f, 0.917f, 3.062f, 2.687f)
                    quadToRelative(1.146f, 1.771f, 1.146f, 3.938f)
                    quadToRelative(0f, 0.667f, -0.125f, 1.333f)
                    quadToRelative(-0.125f, 0.667f, -0.417f, 1.292f)
                    close()
                    moveToRelative(-6.875f, -6.917f)
                    lineToRelative(-4.333f, -4.333f)
                    lineToRelative(2.125f, -2.083f)
                    quadToRelative(0.583f, -0.625f, 1.396f, -0.292f)
                    quadToRelative(0.812f, 0.333f, 0.812f, 1.167f)
                    close()
                    moveToRelative(-2.625f, 10.834f)
                    verticalLineToRelative(-5.25f)
                    lineToRelative(-3.5f, -3.5f)
                    horizontalLineTo(7.958f)
                    verticalLineToRelative(4.416f)
                    horizontalLineToRelative(5f)
                    close()
                }
            }.build()
            return _backingField!!
        }
    }

private var _backingField: ImageVector? = null