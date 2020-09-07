package br.com.mining.mobile.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.enums.MachineStatus
import br.com.mining.mobile.shared.model.Inbound

@Entity
data class InboundEntity(
    @PrimaryKey
    override var id: String = "",
    override var status: MachineStatus = MachineStatus.OPEN,
    override var transactionId: String = "",
    override var packageNumber: Int = 0,
    @ColumnInfo(name = "content", typeAffinity = ColumnInfo.BLOB)
    override var content: ByteArray = byteArrayOf(0)
) : Inbound