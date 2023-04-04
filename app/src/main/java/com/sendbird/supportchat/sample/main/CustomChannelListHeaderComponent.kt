package com.sendbird.supportchat.sample.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.sendbird.supportchat.sample.databinding.ComponentCustomChannelListBinding
import com.sendbird.uikit.modules.components.HeaderComponent

class CustomChannelListHeaderComponent : HeaderComponent() {
    private lateinit var binding: ComponentCustomChannelListBinding

    override fun onCreateView(context: Context, inflater: LayoutInflater, parent: ViewGroup, args: Bundle?): View {
        binding = ComponentCustomChannelListBinding.inflate(inflater, parent, false)
        return binding.root
    }

    fun setOnStartChatButtonClickListener(listener: OnClickListener) {
        binding.startChatButton.setOnClickListener(listener)
    }
}
