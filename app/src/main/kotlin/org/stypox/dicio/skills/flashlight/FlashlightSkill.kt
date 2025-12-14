package org.stypox.dicio.skills.flashlight

import android.hardware.camera2.CameraManager
import androidx.core.content.getSystemService
import org.dicio.skill.context.SkillContext
import org.dicio.skill.skill.SkillOutput
import org.dicio.skill.standard.StandardRecognizerData
import org.dicio.skill.standard.StandardRecognizerSkill
import org.stypox.dicio.sentences.Sentences.Flashlight

class FlashlightSkill(
    correspondingSkillInfo: FlashlightInfo,
    data: StandardRecognizerData<Flashlight>
) : StandardRecognizerSkill<Flashlight>(correspondingSkillInfo, data) {

    override suspend fun generateOutput(ctx: SkillContext, inputData: Flashlight): SkillOutput {
        val cameraManager = ctx.android.getSystemService<CameraManager>()
        val cameraId = cameraManager?.cameraIdList?.firstOrNull()

        if (cameraManager == null || cameraId == null) {
            return FlashlightOutput(success = false, turnedOn = false)
        }

        return try {
            when (inputData) {
                is Flashlight.TurnOn -> {
                    cameraManager.setTorchMode(cameraId, true)
                    FlashlightOutput(success = true, turnedOn = true)
                }
                is Flashlight.TurnOff -> {
                    cameraManager.setTorchMode(cameraId, false)
                    FlashlightOutput(success = true, turnedOn = false)
                }
            }
        } catch (e: Exception) {
            FlashlightOutput(success = false, turnedOn = false)
        }
    }
}
