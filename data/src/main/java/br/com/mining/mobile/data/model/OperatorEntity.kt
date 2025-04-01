package br.com.mining.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.model.Operator
import java.io.Serializable

@Entity(tableName = "operator")
data class OperatorEntity(
    @PrimaryKey
    override var id: String = "",
    override var name: String = "",
    override var register: String = ""
) : Serializable, Operator {

    companion object {
        const val serialVersionUID = 1L
    }

}