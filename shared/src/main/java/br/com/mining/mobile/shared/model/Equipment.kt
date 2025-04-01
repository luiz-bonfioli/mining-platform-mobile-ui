package br.com.mining.mobile.shared.model

interface Equipment : BaseEntity {
    var name: String
    var shortName: String
    var deviceId: String
}