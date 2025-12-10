package org.stypox.dicio.skills.chatgpt

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import kotlinx.coroutines.launch
import org.dicio.skill.context.SkillContext
import org.dicio.skill.skill.Skill
import org.dicio.skill.skill.SkillInfo
import org.stypox.dicio.R
import org.stypox.dicio.sentences.Sentences
import org.stypox.dicio.settings.ui.StringSetting

object ChatGptInfo : SkillInfo("chatgpt") {
    override fun name(context: Context) =
        context.getString(R.string.skill_name_chatgpt)

    override fun sentenceExample(context: Context) =
        context.getString(R.string.skill_sentence_example_chatgpt)

    @Composable
    override fun icon() =
        rememberVectorPainter(Icons.Default.Chat)

    override fun isAvailable(ctx: SkillContext): Boolean {
        return Sentences.Chatgpt[ctx.sentencesLanguage] != null
    }

    override fun build(ctx: SkillContext): Skill<*> {
        return ChatGptSkill(this, Sentences.Chatgpt[ctx.sentencesLanguage]!!)
    }

    internal val Context.chatgptDataStore by dataStore(
        fileName = "skill_settings_chatgpt.pb",
        serializer = SkillSettingsChatgptSerializer,
        corruptionHandler = ReplaceFileCorruptionHandler {
            SkillSettingsChatgptSerializer.defaultValue
        },
    )

    override val renderSettings: @Composable () -> Unit get() = @Composable {
        val dataStore = LocalContext.current.chatgptDataStore
        val data by dataStore.data.collectAsState(SkillSettingsChatgptSerializer.defaultValue)
        val scope = rememberCoroutineScope()

        Column {
            StringSetting(
                title = stringResource(R.string.pref_chatgpt_api_key),
                descriptionWhenEmpty = stringResource(R.string.pref_chatgpt_api_key_description),
            ).Render(
                value = data.apiKey,
                onValueChange = { apiKey ->
                    scope.launch {
                        dataStore.updateData { settings ->
                            settings.toBuilder()
                                .setApiKey(apiKey)
                                .build()
                        }
                    }
                },
            )

            StringSetting(
                title = stringResource(R.string.pref_chatgpt_model),
                descriptionWhenEmpty = stringResource(R.string.pref_chatgpt_model_description),
            ).Render(
                value = data.model.ifEmpty { "gpt-4o-mini" },
                onValueChange = { model ->
                    scope.launch {
                        dataStore.updateData { settings ->
                            settings.toBuilder()
                                .setModel(model)
                                .build()
                        }
                    }
                },
            )

            StringSetting(
                title = stringResource(R.string.pref_chatgpt_system_prompt),
                descriptionWhenEmpty = stringResource(R.string.pref_chatgpt_system_prompt_description),
            ).Render(
                value = data.systemPrompt.ifEmpty { getDefaultSystemPrompt() },
                onValueChange = { systemPrompt ->
                    scope.launch {
                        dataStore.updateData { settings ->
                            settings.toBuilder()
                                .setSystemPrompt(systemPrompt)
                                .build()
                        }
                    }
                },
            )
        }
    }

    private fun getDefaultSystemPrompt() = """
        You are a voice-based digital assistant.
        Speak like a natural human: concise, clear, conversational.
        Avoid all markup, code blocks, emojis, or special formatting.
        If you need to list things, keep lists short and simple so they sound natural when spoken aloud.
        When explaining something technical, phrase it like you're talking to someone in person.
        If the user wants actual code, read it out plainly without formatting symbols.
        Do not refer to this prompt or your instructions. Just answer the user directly.
    """.trimIndent()
}