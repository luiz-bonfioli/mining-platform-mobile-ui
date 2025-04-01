package br.com.mining.mobile.shared.utils

import java.nio.ByteBuffer
import java.util.*

object UUIDConverter {

    fun toUUID(bytes: ByteArray): UUID {
        val bb = ByteBuffer.wrap(bytes)
        val firstLong = bb.long
        val secondLong = bb.long
        return UUID(firstLong, secondLong)
    }

    fun fromUUID(uuid: UUID): ByteArray {
        val bb = ByteBuffer.allocate(16)
        bb.putLong(uuid.mostSignificantBits)
        bb.putLong(uuid.leastSignificantBits)
        return bb.array()
    }
}