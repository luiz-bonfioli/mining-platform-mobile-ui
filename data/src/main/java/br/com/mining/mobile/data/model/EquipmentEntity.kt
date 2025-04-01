package br.com.mining.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.model.Equipment

@Entity(tableName = "equipment")
class EquipmentEntity(
    @PrimaryKey
    override var id: String = "",
    override var name: String = "",
    override var shortName: String = "",
    override var deviceId: String = ""
) : Equipment {
}