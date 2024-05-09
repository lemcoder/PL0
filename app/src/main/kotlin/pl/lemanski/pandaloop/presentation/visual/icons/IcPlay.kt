package pl.lemanski.pandaloop.presentation.visual.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcPlay: ImageVector
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
                    moveTo(15.542f, 30f)
                    quadToRelative(-0.667f, 0.458f, -1.334f, 0.062f)
                    quadToRelative(-0.666f, -0.395f, -0.666f, -1.187f)
                    verticalLineTo(10.917f)
                    quadToRelative(0f, -0.75f, 0.666f, -1.146f)
                    quadToRelative(0.667f, -0.396f, 1.334f, 0.062f)
                    lineToRelative(14.083f, 9f)
                    quadToRelative(0.625f, 0.375f, 0.625f, 1.084f)
                    quadToRelative(0f, 0.708f, -0.625f, 1.083f)
                    close()
                }
            }.build()
            return _backingField!!
        }
    }

private var _backingField: ImageVector? = null