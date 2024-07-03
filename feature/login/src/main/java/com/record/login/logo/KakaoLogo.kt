import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun kakaoLogo(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "Kakao",
            defaultWidth = 18.dp,
            defaultHeight = 16.dp,
            viewportWidth = 18f,
            viewportHeight = 16f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero,
            ) {
                moveTo(9f, 0f)
                curveTo(4.3066f, 0f, 0.5f, 2.9857f, 0.5f, 6.6618f)
                curveTo(0.5f, 8.9117f, 1.9202f, 10.9011f, 4.0932f, 12.1124f)
                lineTo(3.42579f, 15.6189f)
                curveTo(3.4139f, 15.681f, 3.4207f, 15.7453f, 3.4454f, 15.8036f)
                curveTo(3.4701f, 15.8619f, 3.5116f, 15.9116f, 3.5647f, 15.9466f)
                curveTo(3.6178f, 15.9815f, 3.6801f, 16.0001f, 3.7437f, 16f)
                curveTo(3.8074f, 15.9999f, 3.8696f, 15.9811f, 3.9226f, 15.9459f)
                lineTo(7.91808f, 13.2812f)
                curveTo(8.2775f, 13.3177f, 8.6387f, 13.3359f, 9f, 13.3357f)
                curveTo(13.6934f, 13.3357f, 17.5f, 10.35f, 17.5f, 6.6739f)
                curveTo(17.5f, 2.9978f, 13.6934f, 0f, 9f, 0f)
                close()
            }
        }.build()
    }
}
