package br.com.mining.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.model.ChecklistItem
import java.io.Serializable

@Entity
data class ChecklistItemEntity(
    @PrimaryKey
    override var id: String = "",
    override var name: String = "",
    override var checklistId: String = ""
) : Serializable, ChecklistItem {

    companion object {
        const val serialVersionUID = 1L
    }

}