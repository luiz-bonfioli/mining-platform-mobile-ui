package br.com.mining.mobile.service.synchronism

import java.nio.ByteBuffer

open class RequestHeader @JvmOverloads constructor(
    protected var service: Byte,
    protected var event: Byte,
    protected var operation: Byte,
    protected var content: ByteArray = ByteArray(0)
) {

    open fun payload(): ByteArray {
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