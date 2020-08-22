package br.com.mining.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.model.User
import java.io.Serializable

@Entity
data class UserEntity(
    @PrimaryKey
    override var id: String = "",
    override var name: String = "",
    override var email: String = "",
    override var phone: String = "",
    override var password: String  = "",
    override var passwordConfirmation: String = ""
) : Serializable, User {

    companion object {
        const val serialVersionUID = 1L
    }

}