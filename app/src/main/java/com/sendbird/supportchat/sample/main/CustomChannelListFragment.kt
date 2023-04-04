package com.sendbird.supportchat.sample.main

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.params.GroupChannelCreateParams
import com.sendbird.android.shadow.okhttp3.*
import com.sendbird.android.shadow.okhttp3.MediaType.Companion.toMediaType
import com.sendbird.android.shadow.okhttp3.RequestBody.Companion.toRequestBody
import com.sendbird.supportchat.sample.R
import com.sendbird.uikit.SendbirdUIKit
import com.sendbird.uikit.activities.ChannelActivity
import com.sendbird.uikit.fragments.ChannelListFragment
import com.sendbird.uikit.modules.ChannelListModule
import com.sendbird.uikit.modules.components.HeaderComponent
import com.sendbird.uikit.vm.ChannelListViewModel
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CustomChannelListFragment : ChannelListFragment() {
    companion object {
        private val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }

    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(
        Interceptor { chain: Interceptor.Chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            )
        }).build()


    override fun onCreateModule(args: Bundle): ChannelListModule {
        val module = super.onCreateModule(args)
        module.setHeaderComponent(CustomChannelListHeaderComponent())
        return module
    }

    override fun onBindHeaderComponent(headerComponent: HeaderComponent, viewModel: ChannelListViewModel) {
        val component = headerComponent as CustomChannelListHeaderComponent
        component.setOnStartChatButtonClickListener {
            createGroupChannel(randomChannelTitle())
        }
    }

    private fun createGroupChannel(title: String) {
        val params = GroupChannelCreateParams()
        params.customType = "SALESFORCE_SUPPORT_CHAT_CHANNEL"
        params.userIds = listOf(SendbirdUIKit.getAdapter().userInfo.userId)
        params.name = title
        params.coverImage = createUserAgentCoverImage()
        GroupChannel.createChannel(params) { groupChannel, e ->
            if (e != null) {
                toastError("Couldn't start support chat")
                return@createChannel
            } else if (groupChannel != null) {
                startChat(groupChannel)
            }
        }
    }

    private fun startChat(channel: GroupChannel) {
        val json = "{\"subject\":\"${channel.name}\"," +
                "\"suppliedName\":\"${SendbirdUIKit.getAdapter().userInfo.nickname}\"," +
                "\"sendbirdUserId\":\"${SendbirdUIKit.getAdapter().userInfo.userId}\"," +
                "\"sendbirdChannelURL\":\"${channel.url}\"," +
                "\"isEinsteinBotCase\":false}"
        val body = json.toRequestBody(JSON)

        val request = Request.Builder()
            .url("https://sendbird11-dev-ed.develop.my.salesforce-sites.com/services/apexrest/cases/")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                leaveChannel(channel)
                toastError("Couldn't start support chat")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val context = this@CustomChannelListFragment.context
                    if (context != null) {
                        startActivity(ChannelActivity.newIntent(context, channel.url))
                    }
                } else {
                    leaveChannel(channel)
                    toastError("Couldn't start support chat")
                }
            }
        })
    }

    override fun onItemLongClicked(view: View, position: Int, channel: GroupChannel) {
        // TODO implement long click for channel items
    }

    private fun randomChannelTitle(): String = listOf(
        "Help me please",
        "It’s getting more difficult",
        "Serious help needed",
        "Why is this happening to me?",
        "Hello?",
        "Problem with my order",
        "Issue with my transaction",
        "Urgent help required",
        "App not working as intended",
        "What the..",
        "Hmmm...",
        "I smell a rat",
        "Curiosity killed the cat",
        "A little bird told me",
        "Birds of a feather flock together",
        "Not my cup of tea",
        "Two down, one to go",
        "Short end of the stick",
    ).random()

    private fun createUserAgentCoverImage(): File? {
        //create a file to write bitmap data
        if (context == null) return null
        val f = File(requireContext().cacheDir, "sendbird_user_agent_cover_image.png")
        if (f.exists() && f.length() > 0) return f
        f.createNewFile()

        //Convert bitmap to byte array
        val bitmap = BitmapFactory.decodeResource(requireContext().resources, R.drawable.user_agent_cover_image)
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
        val bitmapData = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(f)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        return f
    }
}
