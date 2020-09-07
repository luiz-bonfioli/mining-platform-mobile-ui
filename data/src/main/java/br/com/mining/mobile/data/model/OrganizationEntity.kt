package br.com.mining.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.model.Organization
import br.com.mining.mobile.shared.model.Site
import java.io.Serializable

@Entity
data class OrganizationEntity(
    @PrimaryKey
    override var id: String = "",
    override var name: String = ""
) : Serializable, Organization {

    companion object {
        const val serialVersionUID = 1L
    }

}