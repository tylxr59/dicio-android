package org.stypox.dicio.skills.chatgpt

import kotlinx.coroutines.flow.first
import org.dicio.skill.context.SkillContext
import org.dicio.skill.skill.SkillInfo
import org.dicio.skill.skill.SkillOutput
import org.dicio.skill.standard.StandardRecognizerData
import org.dicio.skill.standard.StandardRecognizerSkill
import org.stypox.dicio.sentences.Sentences.Chatgpt
import org.stypox.dicio.skills.chatgpt.ChatGptInfo.chatgptDataStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatGptSkill(
    correspondingSkillInfo: SkillInfo,
    data: StandardRecognizerData<Chatgpt>
) : StandardRecognizerSkill<Chatgpt>(correspondingSkillInfo, data) {
    
    private val client = OkHttpClient()
    
    override suspend fun generateOutput(ctx: SkillContext, inputData: Chatgpt): SkillOutput {
        val prefs = ctx.android.chatgptDataStore.data.first()
        
        val question = when (inputData) {
            is Chatgpt.Query -> inputData.question
        } ?: return ChatGptOutput(null, "")

        if (prefs.apiKey.isEmpty()) {
            return ChatGptOutput(null, question) // Error: no API key configured
        }

        return try {
            val response = callChatGptApi(
                question = question,
                apiKey = prefs.apiKey,
                model = prefs.model.ifEmpty { "gpt-4o-mini" },
                systemPrompt = prefs.systemPrompt.ifEmpty { getDefaultSystemPrompt() }
            )
            ChatGptOutput(response, question)
        } catch (e: Exception) {
            ChatGptOutput(null, question)
        }
    }

    private suspend fun callChatGptApi(
        question: String,
        apiKey: String,
        model: String,
        systemPrompt: String
    ): String = withContext(Dispatchers.IO) {
        val json = JSONObject().apply {
            put("model", model)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "system")
                    put("content", systemPrompt)
                })
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", question)
                })
            })
        }

        val requestBody = json.toString()
            .toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw Exception("API call failed: ${response.code}")
            }
            
            val responseBody = response.body?.string()
                ?: throw Exception("Empty response")
            
            val jsonResponse = JSONObject(responseBody)
            jsonResponse.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content")
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