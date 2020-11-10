package br.com.mining.mobile.shared.utils

import java.nio.ByteBuffer
import java.util.*

object UUIDUtils {

    fun generationUUID(buffer: ByteBuffer): UUID {
        val id = ByteArray(16)
        buffer[id]
        return UUIDConverter.toUUID(id)
    }
}