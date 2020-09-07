package br.com.mining.mobile.data

import androidx.room.TypeConverter
import br.com.mining.mobile.shared.enums.MachineStatus
import br.com.mining.mobile.shared.enums.TransactionState
import br.com.mining.mobile.shared.enums.TransactionType

class Converters {

    @TypeConverter
    fun fromTransactionStatus(status: TransactionState): String =
        status.name

    @TypeConverter
    fun stringToTransactionStatus(status: String): TransactionState =
        TransactionState.valueOf(status)

    // Init - InboundStatus
    @TypeConverter
    fun fromInboundStatus(status: MachineStatus): String =
        status.name

    @TypeConverter
    fun stringToInboundStatus(status: String): MachineStatus =
        MachineStatus.valueOf(status)

    //TransactionType
    @TypeConverter
    fun fromTransactionType(status: TransactionType): String =
        status.name

    @TypeConverter
    fun stringToTransactionType(status: String): TransactionType =
        TransactionType.valueOf(status)

}