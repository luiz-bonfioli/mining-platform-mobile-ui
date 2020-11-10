package br.com.mining.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.model.Checklist
import java.io.Serializable

@Entity(tableName = "checklist")
data class ChecklistEntity(
    @PrimaryKey
    override var id: String = "",
    override var name: String = ""
) : Serializable, Checklist {

    companion object {
        const val serialVersionUID = 1L
    }

}