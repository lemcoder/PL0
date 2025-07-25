package pl.lemanski.pandaloop.presentation.visual.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcRemove: ImageVector
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
                    moveTo(9.833f, 21.292f)
                    quadToRelative(-0.541f, 0f, -0.916f, -0.375f)
                    reflectiveQuadToRelative(-0.375f, -0.959f)
                    quadToRelative(0f, -0.541f, 0.375f, -0.916f)
                    reflectiveQuadToRelative(0.916f, -0.375f)
                    horizontalLineToRelative(20.334f)
                    quadToRelative(0.541f, 0f, 0.916f, 0.395f)
                    quadToRelative(0.375f, 0.396f, 0.375f, 0.938f)
                    quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                    reflectiveQuadToRelative(-0.916f, 0.375f)
                    close()
                }
            }.build()
            return _backingField!!
        }
    }

private var _backingField: ImageVector? = null