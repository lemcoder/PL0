package pl.lemanski.pandaloop.presentation.visual.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcSpeakerPlay: ImageVector
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
                    moveTo(25.167f, 33.5f)
                    quadToRelative(-0.709f, 0.292f, -1.292f, -0.146f)
                    quadToRelative(-0.583f, -0.437f, -0.583f, -1.229f)
                    quadToRelative(0f, -0.333f, 0.208f, -0.604f)
                    quadToRelative(0.208f, -0.271f, 0.542f, -0.396f)
                    quadToRelative(3.541f, -1.292f, 5.687f, -4.333f)
                    quadToRelative(2.146f, -3.042f, 2.146f, -6.834f)
                    quadToRelative(0f, -3.791f, -2.146f, -6.833f)
                    quadToRelative(-2.146f, -3.042f, -5.687f, -4.292f)
                    quadToRelative(-0.334f, -0.125f, -0.542f, -0.416f)
                    quadToRelative(-0.208f, -0.292f, -0.208f, -0.667f)
                    quadToRelative(0f, -0.75f, 0.604f, -1.167f)
                    quadToRelative(0.604f, -0.416f, 1.271f, -0.166f)
                    quadTo(29.375f, 8f, 31.958f, 11.688f)
                    quadToRelative(2.584f, 3.687f, 2.584f, 8.27f)
                    quadToRelative(0f, 4.625f, -2.584f, 8.292f)
                    quadToRelative(-2.583f, 3.667f, -6.791f, 5.25f)
                    close()
                    moveTo(6.792f, 24.833f)
                    quadToRelative(-0.584f, 0f, -0.959f, -0.375f)
                    reflectiveQuadToRelative(-0.375f, -0.916f)
                    verticalLineToRelative(-7.084f)
                    quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                    reflectiveQuadToRelative(0.959f, -0.375f)
                    horizontalLineTo(12f)
                    lineToRelative(5.875f, -5.875f)
                    quadTo(18.5f, 8.667f, 19.312f, 9f)
                    quadToRelative(0.813f, 0.333f, 0.813f, 1.167f)
                    verticalLineToRelative(19.625f)
                    quadToRelative(0f, 0.875f, -0.813f, 1.208f)
                    quadToRelative(-0.812f, 0.333f, -1.437f, -0.292f)
                    lineTo(12f, 24.833f)
                    close()
                    moveToRelative(15.958f, 1.959f)
                    verticalLineTo(13.208f)
                    quadToRelative(2.208f, 0.75f, 3.5f, 2.625f)
                    quadToRelative(1.292f, 1.875f, 1.292f, 4.167f)
                    quadToRelative(0f, 2.333f, -1.292f, 4.167f)
                    quadToRelative(-1.292f, 1.833f, -3.5f, 2.625f)
                    close()
                    moveToRelative(-5.292f, -13.167f)
                    lineToRelative(-4.291f, 4.167f)
                    horizontalLineTo(8.125f)
                    verticalLineToRelative(4.416f)
                    horizontalLineToRelative(5.042f)
                    lineToRelative(4.291f, 4.209f)
                    close()
                    moveTo(13.542f, 20f)
                    close()
                }
            }.build()
            return _backingField!!
        }
    }

private var _backingField: ImageVector? = null