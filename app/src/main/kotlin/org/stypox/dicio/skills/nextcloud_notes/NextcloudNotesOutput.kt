package org.stypox.dicio.skills.nextcloud_notes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import org.dicio.skill.context.SkillContext
import org.dicio.skill.skill.SkillOutput
import org.stypox.dicio.R
import org.stypox.dicio.io.graphical.HeadlineSpeechSkillOutput
import org.stypox.dicio.util.getString

sealed interface NextcloudNotesOutput : SkillOutput {
    data class Success(
        val noteName: String,
        val content: String,
        val isShoppingList: Boolean,
    ) : NextcloudNotesOutput, HeadlineSpeechSkillOutput {
        override fun getSpeechOutput(ctx: SkillContext): String = if (isShoppingList) {
            ctx.getString(R.string.skill_nextcloud_notes_added_to_shopping_list, content)
        } else {
            ctx.getString(R.string.skill_nextcloud_notes_note_added, content)
        }

        @Composable
        override fun GraphicalOutput(ctx: SkillContext) {
            Column {
                Text(
                    text = if (isShoppingList) {
                        stringResource(R.string.skill_nextcloud_notes_added_to_shopping_list, content)
                    } else {
                        stringResource(R.string.skill_nextcloud_notes_note_added, content)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = androidx.compose.ui.Modifier.testTag("nextcloud_notes_success")
                )
                Text(
                    text = stringResource(R.string.skill_nextcloud_notes_saved_to, noteName),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    data class Failed(
        val reason: FailureReason,
        val errorMessage: String? = null
    ) : NextcloudNotesOutput, HeadlineSpeechSkillOutput {
        override fun getSpeechOutput(ctx: SkillContext): String = when (reason) {
            FailureReason.SETTINGS_MISSING -> ctx.getString(R.string.skill_nextcloud_notes_settings_missing)
            FailureReason.TARGET_NOTE_MISSING -> ctx.getString(R.string.skill_nextcloud_notes_target_note_missing)
            FailureReason.TARGET_SHOPPING_LIST_MISSING -> ctx.getString(R.string.skill_nextcloud_notes_target_shopping_list_missing)
            FailureReason.CONTENT_EMPTY -> ctx.getString(R.string.skill_nextcloud_notes_content_empty)
            FailureReason.CONNECTION_ERROR -> ctx.getString(
                R.string.skill_nextcloud_notes_connection_error,
                errorMessage ?: "Unknown error"
            )
        }
    }

    enum class FailureReason {
        SETTINGS_MISSING,
        TARGET_NOTE_MISSING,
        TARGET_SHOPPING_LIST_MISSING,
        CONTENT_EMPTY,
        CONNECTION_ERROR
    }
}
