package br.com.mining.mobile.shared.model

interface User : BaseEntity {
    var name: String

    var password: String

    var passwordConfirmation: String

    var email: String

    var phone: String

}