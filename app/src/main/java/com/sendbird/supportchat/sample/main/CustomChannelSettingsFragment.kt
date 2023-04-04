package com.sendbird.supportchat.sample.main

import android.os.Bundle
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.handler.CompletionHandler
import com.sendbird.uikit.fragments.ChannelSettingsFragment
import com.sendbird.uikit.modules.ChannelSettingsModule
import com.sendbird.uikit.modules.components.ChannelSettingsMenuComponent
import com.sendbird.uikit.vm.ChannelSettingsViewModel

class CustomChannelSettingsFragment : ChannelSettingsFragment() {
    override fun onCreateModule(args: Bundle): ChannelSettingsModule {
        val module = super.onCreateModule(args)
        module.setChannelSettingsMenuComponent(CustomChannelSettingsMenuComponent())
        return module
    }

    override fun onBindSettingsMenuComponent(
        menuComponent: ChannelSettingsMenuComponent,
        viewModel: ChannelSettingsViewModel,
        channel: GroupChannel?
    ) {
        super.onBindSettingsMenuComponent(menuComponent, viewModel, channel)
        (menuComponent as CustomChannelSettingsMenuComponent).setOnNotificationClickListener {
            if (channel == null) return@setOnNotificationClickListener
            channel.setMyPushTriggerOption(
                if (channel.myPushTriggerOption == GroupChannel.PushTriggerOption.OFF) {
                    GroupChannel.PushTriggerOption.ALL
                } else {
                    GroupChannel.PushTriggerOption.OFF
                }, CompletionHandler { e -> if (e != null) toastError("Couldn't set notification option") })
        }
    }
}
