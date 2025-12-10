package org.stypox.dicio.skills.chatgpt

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SkillSettingsChatgptSerializer : Serializer<SkillSettingsChatgpt> {
    override val defaultValue: SkillSettingsChatgpt = SkillSettingsChatgpt.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SkillSettingsChatgpt {
        try {
            return SkillSettingsChatgpt.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: SkillSettingsChatgpt, output: OutputStream) {
        t.writeTo(output)
    }
}