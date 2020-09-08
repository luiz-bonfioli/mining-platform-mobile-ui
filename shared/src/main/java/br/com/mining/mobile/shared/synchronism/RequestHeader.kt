package br.com.mining.mobile.shared.synchronism

import java.nio.ByteBuffer

open class RequestHeader(
    protected var service: Byte,
    protected var event: Byte,
    protected var operation: Byte,
    protected var content: ByteArray = ByteArray(0)
) {
    val payload: ByteArray
        get() {
            val buffer = ByteBuffer.allocate(3 + content.size)
            buffer.put(service)
            buffer.put(event)
            buffer.put(operation)
            buffer.put(content)
            return buffer.array()
        }

    companion object {
        protected const val GENERIC: Byte = 0x00
    }
}
