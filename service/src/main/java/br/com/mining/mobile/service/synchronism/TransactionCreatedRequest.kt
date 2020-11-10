package br.com.mining.mobile.service.synchronism

import br.com.mining.mobile.shared.utils.UUIDConverter
import java.nio.ByteBuffer
import java.util.*

class TransactionCreatedRequest(
    service: Byte,
    event: Byte,
    operation: Byte,
    private val transactionId: UUID
) : RequestHeader(service, event, operation, ByteArray(0)) {

    private val HEADER_DEFAULT_SIZE = 3
    private val TRANSACTION_SIZE = 16

    override fun payload(): ByteArray {
        val buffer = ByteBuffer.allocate(
            HEADER_DEFAULT_SIZE + TRANSACTION_SIZE +
                    content.size
        )
        buffer.put(service)
        buffer.put(event)
        buffer.put(operation)
        buffer.put(UUIDConverter.fromUUID(transactionId))
        buffer.put(content)
        return buffer.array()
    }

}
