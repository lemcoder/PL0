package pl.lemanski.pandaloop.presentation.visual.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcRecording: ImageVector
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
                    moveTo(20f, 36.375f)
                    quadToRelative(-3.417f, 0f, -6.396f, -1.292f)
                    quadToRelative(-2.979f, -1.291f, -5.208f, -3.5f)
                    quadToRelative(-2.229f, -2.208f, -3.5f, -5.187f)
                    reflectiveQuadTo(3.625f, 20f)
                    quadToRelative(0f, -3.417f, 1.292f, -6.396f)
                    quadToRelative(1.291f, -2.979f, 3.5f, -5.208f)
                    quadToRelative(2.208f, -2.229f, 5.187f, -3.5f)
                    reflectiveQuadTo(20f, 3.625f)
                    quadToRelative(3.417f, 0f, 6.396f, 1.292f)
                    quadToRelative(2.979f, 1.291f, 5.208f, 3.5f)
                    quadToRelative(2.229f, 2.208f, 3.5f, 5.187f)
                    reflectiveQuadTo(36.375f, 20f)
                    quadToRelative(0f, 3.417f, -1.292f, 6.396f)
                    quadToRelative(-1.291f, 2.979f, -3.5f, 5.208f)
                    quadToRelative(-2.208f, 2.229f, -5.187f, 3.5f)
                    reflectiveQuadTo(20f, 36.375f)
                    close()
                }
            }.build()
            return _backingField!!
        }
    }

private var _backingField: ImageVector? = null