package com.sendbird.supportchat.sample.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sendbird.android.channel.GroupChannel
import com.sendbird.supportchat.sample.databinding.ComponentCustomChannelSettingsMenuBinding
import com.sendbird.uikit.modules.components.ChannelSettingsMenuComponent

class CustomChannelSettingsMenuComponent : ChannelSettingsMenuComponent() {
    private lateinit var binding: ComponentCustomChannelSettingsMenuBinding

    override fun onCreateView(context: Context, inflater: LayoutInflater, parent: ViewGroup, args: Bundle?): View {
        binding = ComponentCustomChannelSettingsMenuBinding.inflate(inflater, parent, false)
        binding.memberItem.setOnClickListener { v: View -> onMenuClicked(v, Menu.MEMBERS) }
        return binding.root
    }

    override fun notifyChannelChanged(channel: GroupChannel) {
        binding.notificationSwitch.isChecked = channel.myPushTriggerOption != GroupChannel.PushTriggerOption.OFF
        binding.memberCount.text = channel.memberCount.toString()
    }

    fun setOnNotificationClickListener(listener: View.OnClickListener) {
        binding.notificationItem.setOnClickListener(listener)
        binding.notificationSwitch.setOnClickListener(listener)
    }
}
