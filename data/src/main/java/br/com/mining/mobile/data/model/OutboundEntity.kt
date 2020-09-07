package br.com.mining.mobile.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.enums.MachineStatus
import br.com.mining.mobile.shared.model.Outbound

@Entity
data class OutboundEntity(
    @PrimaryKey
    override var id: String = "",
    override var status: MachineStatus = MachineStatus.OPEN,
    override var transactionId: String = "",
    override var numberOfPackage: Int = 0,
    @ColumnInfo(name = "content", typeAffinity = ColumnInfo.BLOB)
    override var content: ByteArray  = byteArrayOf(0)
) : Outbound {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as OutboundEntity

        if (id != other.id) return false
        if (transactionId != other.transactionId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + transactionId.hashCode()
        return result
    }
}