package br.com.mining.mobile.shared.builders

import br.com.mining.mobile.shared.protocol.Protocol

object TopicBuilder {
    fun buildCompanyTopic(topicTemplate: String): String {
        return topicTemplate.replace(
            Protocol.COMPANY_TOKEN, ""
//            AppRepository.INSTANCE.getConfigApplication().getCompanyKey()
        )
    }

    fun buildDeviceTopic(topicTemplate: String): String {
        return buildCompanyTopic(topicTemplate)
            .replace(
                Protocol.DEVICE_TOKEN, ""
//                AppRepository.INSTANCE.getConfigApplication().getDeviceToken()
            )
    }

    fun buildUserTopic(topicTemplate: String): String {
        return buildDeviceTopic(topicTemplate)
            .replace(Protocol.USER_TOKEN, "")
//            .replace(Protocol.USER_TOKEN, AppRepository.INSTANCE.getCurrentUser().getId())
    }

}