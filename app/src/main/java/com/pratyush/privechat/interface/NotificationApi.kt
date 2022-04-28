package com.pratyush.prive.`interface`

import com.pratyush.privechat.constants.Constants.Companion.CONTENT_TYPE
import com.pratyush.privechat.constants.Constants.Companion.SERVER_KEY
import com.pratyush.privechat.model.noti.PushNotification
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {

    @Headers("Authorization: key=$SERVER_KEY","Content-type:$CONTENT_TYPE")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification:PushNotification
    ): Response<ResponseBody>
}