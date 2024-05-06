package pl.lemanski.pandaloop.platform.i18n

import android.content.Context
import pl.lemanski.pandaloop.R
import pl.lemanski.pandaloop.domain.platform.i18n.Localization

class LocalizationImpl(
    private val context: Context
) : Localization {
    override val timeSignature: String = context.getString(R.string.time_signature)
    override val tempo: String = context.getString(R.string.tempo)
    override val loop: String = context.getString(R.string.loop)
    override val recording: String = context.getString(R.string.recording)
    override val countdown: String = context.getString(R.string.countdown)
    override fun measures(beats: Int): String = context.resources.getQuantityString(R.plurals.measures, beats)

}