package com.sendbird.supportchat.sample

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sendbird.android.exception.SendbirdException
import com.sendbird.android.handler.InitResultHandler
import com.sendbird.uikit.SendbirdUIKit
import com.sendbird.uikit.adapter.SendbirdUIKitAdapter
import com.sendbird.uikit.consts.ReplyType
import com.sendbird.uikit.interfaces.UserInfo

class BaseApplication : Application() {
    companion object {
        // TODO Replace with your own APP_ID
        const val APP_ID = "13B6D179-33A5-4C0D-9162-E11DAC9358FC"

        // TODO Replace with your own USER_ID
        const val USER_ID = "sendbird"

        // TODO Replace with your own NICKNAME
        const val NICKNAME = "sendbird"

        // TODO Replace with your own PROFILE_URL
        const val PROFILE_URL = ""
        private val isInitialized: MutableLiveData<Boolean> = MutableLiveData()

        fun isInitialized(): LiveData<Boolean> = isInitialized
    }

    override fun onCreate() {
        super.onCreate()

        SendbirdUIKit.init(object : SendbirdUIKitAdapter {
            override fun getAppId(): String = APP_ID

            override fun getAccessToken(): String? = null

            override fun getUserInfo(): UserInfo = object : UserInfo {
                override fun getUserId(): String = USER_ID

                override fun getNickname(): String = NICKNAME

                override fun getProfileUrl(): String = PROFILE_URL
            }

            override fun getInitResultHandler(): InitResultHandler = object : InitResultHandler {
                override fun onInitFailed(e: SendbirdException) {}

                override fun onInitSucceed() {
                    isInitialized.postValue(true)
                }

                override fun onMigrationStarted() {}
            }
        }, this)

        SendbirdUIKit.setUIKitFragmentFactory(UIKitCustomFragmentFactory())
        SendbirdUIKit.setReplyType(ReplyType.NONE)
    }
}
