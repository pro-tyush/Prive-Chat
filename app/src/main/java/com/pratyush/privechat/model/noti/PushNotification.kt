package com.pratyush.privechat.model.noti

import com.pratyush.privechat.model.noti.NotificationData

data class PushNotification(
    var data: NotificationData,
    var to:String
)