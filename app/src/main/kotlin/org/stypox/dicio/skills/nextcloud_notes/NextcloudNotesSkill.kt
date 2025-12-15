package org.stypox.dicio.skills.nextcloud_notes

import kotlinx.coroutines.flow.first
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.dicio.skill.context.SkillContext
import org.dicio.skill.skill.SkillInfo
import org.dicio.skill.skill.SkillOutput
import org.dicio.skill.standard.StandardRecognizerData
import org.dicio.skill.standard.StandardRecognizerSkill
import org.stypox.dicio.sentences.Sentences.NextcloudNotes
import org.stypox.dicio.skills.nextcloud_notes.NextcloudNotesInfo.nextcloudNotesDataStore
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NextcloudNotesSkill(
    correspondingSkillInfo: SkillInfo,
    data: StandardRecognizerData<NextcloudNotes>
) : StandardRecognizerSkill<NextcloudNotes>(correspondingSkillInfo, data) {

    override suspend fun generateOutput(ctx: SkillContext, inputData: NextcloudNotes): SkillOutput {
        val prefs = ctx.android.nextcloudNotesDataStore.data.first()

        // Validate settings
        if (prefs.serverAddress.isEmpty() || prefs.username.isEmpty() || prefs.password.isEmpty()) {
            return NextcloudNotesOutput.Failed(
                reason = NextcloudNotesOutput.FailureReason.SETTINGS_MISSING
            )
        }

        val (content, targetNote) = when (inputData) {
            is NextcloudNotes.AddNote -> {
                if (prefs.targetNote.isEmpty()) {
                    return NextcloudNotesOutput.Failed(
                        reason = NextcloudNotesOutput.FailureReason.TARGET_NOTE_MISSING
                    )
                }
                Pair(inputData.content ?: "", prefs.targetNote)
            }
            is NextcloudNotes.AddToShoppingList -> {
                if (prefs.targetShoppingList.isEmpty()) {
                    return NextcloudNotesOutput.Failed(
                        reason = NextcloudNotesOutput.FailureReason.TARGET_SHOPPING_LIST_MISSING
                    )
                }
                Pair(inputData.item ?: "", prefs.targetShoppingList)
            }
        }

        if (content.isEmpty()) {
            return NextcloudNotesOutput.Failed(
                reason = NextcloudNotesOutput.FailureReason.CONTENT_EMPTY
            )
        }

        // Add note via WebDAV
        return try {
            addNoteViaWebDAV(
                serverAddress = prefs.serverAddress,
                username = prefs.username,
                password = prefs.password,
                noteName = targetNote,
                content = content,
                isShoppingList = inputData is NextcloudNotes.AddToShoppingList
            )
            NextcloudNotesOutput.Success(
                noteName = targetNote,
                content = content,
                isShoppingList = inputData is NextcloudNotes.AddToShoppingList
            )
        } catch (e: Exception) {
            NextcloudNotesOutput.Failed(
                reason = NextcloudNotesOutput.FailureReason.CONNECTION_ERROR,
                errorMessage = e.message
            )
        }
    }

    private suspend fun addNoteViaWebDAV(
        serverAddress: String,
        username: String,
        password: String,
        noteName: String,
        content: String,
        isShoppingList: Boolean
    ) {
        val client = OkHttpClient()
        
        // Normalize server address (remove trailing slash)
        val normalizedServer = serverAddress.trimEnd('/')
        
        // Encode note name for URL (Nextcloud Notes stores in /Notes/ folder)
        // User provides the full filename with extension (e.g., "MyNote.md" or "Shopping.txt")
        val encodedNoteName = URLEncoder.encode(noteName, "UTF-8")
        
        // Nextcloud stores notes in WebDAV files under the Notes folder
        val noteUrl = "$normalizedServer/remote.php/dav/files/$username/Notes/$encodedNoteName"
        
        // Get existing content first
        val getRequest = Request.Builder()
            .url(noteUrl)
            .header("Authorization", Credentials.basic(username, password))
            .get()
            .build()
        
        val existingContent = try {
            val response = client.newCall(getRequest).execute()
            if (response.isSuccessful) {
                response.body?.string() ?: ""
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
        
        // Append new content with timestamp
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val newContent = if (isShoppingList) {
            // For shopping lists, add as a checkbox item
            if (existingContent.isEmpty()) {
                "- [ ] $content"
            } else {
                "$existingContent\n- [ ] $content"
            }
        } else {
            // For notes, add with timestamp
            if (existingContent.isEmpty()) {
                "[$timestamp] $content"
            } else {
                "$existingContent\n\n[$timestamp] $content"
            }
        }
        
        // Upload the updated content
        val putRequest = Request.Builder()
            .url(noteUrl)
            .header("Authorization", Credentials.basic(username, password))
            .put(newContent.toRequestBody("text/plain".toMediaType()))
            .build()
        
        val response = client.newCall(putRequest).execute()
        if (!response.isSuccessful) {
            throw Exception("Failed to add note: ${response.code} ${response.message}")
        }
    }
}
