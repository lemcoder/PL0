package pl.lemanski.pandaloop.presentation.visual.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcEffects: ImageVector
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
                    moveTo(21.625f, 15.208f)
                    verticalLineToRelative(-2.625f)
                    horizontalLineToRelative(5.25f)
                    verticalLineTo(5.208f)
                    horizontalLineTo(29.5f)
                    verticalLineToRelative(7.375f)
                    horizontalLineToRelative(5.292f)
                    verticalLineToRelative(2.625f)
                    close()
                    moveToRelative(5.25f, 19.584f)
                    verticalLineTo(18.125f)
                    horizontalLineTo(29.5f)
                    verticalLineToRelative(16.667f)
                    close()
                    moveToRelative(-16.375f, 0f)
                    verticalLineToRelative(-7.084f)
                    horizontalLineTo(5.208f)
                    verticalLineToRelative(-2.666f)
                    horizontalLineToRelative(13.209f)
                    verticalLineToRelative(2.666f)
                    horizontalLineToRelative(-5.292f)
                    verticalLineToRelative(7.084f)
                    close()
                    moveToRelative(0f, -12.625f)
                    verticalLineTo(5.208f)
                    horizontalLineToRelative(2.625f)
                    verticalLineToRelative(16.959f)
                    close()
                }
            }.build()
            return _backingField!!
        }
    }

private var _backingField: ImageVector? = null