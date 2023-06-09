package com.sendbird.supportchat.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sendbird.supportchat.sample.main.CustomChannelFragment
import com.sendbird.supportchat.sample.main.CustomChannelListFragment
import com.sendbird.supportchat.sample.main.CustomChannelSettingsFragment
import com.sendbird.uikit.fragments.*

class UIKitCustomFragmentFactory : UIKitFragmentFactory() {
    override fun newChannelListFragment(args: Bundle): Fragment {
        return ChannelListFragment.Builder()
            .withArguments(args)
            .setUseHeader(true)
            .setCustomFragment(CustomChannelListFragment())
            .build()
    }

    override fun newChannelFragment(channelUrl: String, args: Bundle): Fragment {
        return ChannelFragment.Builder(channelUrl)
            .withArguments(args)
            .setUseHeader(true)
            .setCustomFragment(CustomChannelFragment())
            .build()
    }

    override fun newChannelSettingsFragment(channelUrl: String, args: Bundle): Fragment {
        return ChannelSettingsFragment.Builder(channelUrl)
            .withArguments(args)
            .setUseHeader(true)
            .setCustomFragment(CustomChannelSettingsFragment())
            .build()
    }

    override fun newMemberListFragment(channelUrl: String, args: Bundle): Fragment {
        return MemberListFragment.Builder(channelUrl)
            .withArguments(args)
            .setUseHeader(true)
            .setUseHeaderRightButton(false)
            .build()
    }
}
