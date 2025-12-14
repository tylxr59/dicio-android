package org.stypox.dicio.skills.flashlight

import org.dicio.skill.context.SkillContext
import org.stypox.dicio.R
import org.stypox.dicio.io.graphical.HeadlineSpeechSkillOutput
import org.stypox.dicio.util.getString

class FlashlightOutput(
    private val success: Boolean,
    private val turnedOn: Boolean,
) : HeadlineSpeechSkillOutput {
    override fun getSpeechOutput(ctx: SkillContext): String = if (success) {
        if (turnedOn) {
            ctx.getString(R.string.skill_flashlight_turned_on)
        } else {
            ctx.getString(R.string.skill_flashlight_turned_off)
        }
    } else {
        ctx.getString(R.string.skill_flashlight_error)
    }
}
