package com.sendbird.supportchat.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sendbird.supportchat.sample.main.CustomChannelListFragment
import com.sendbird.supportchat.sample.main.CustomChannelSettingsFragment
import com.sendbird.uikit.fragments.ChannelListFragment
import com.sendbird.uikit.fragments.ChannelSettingsFragment
import com.sendbird.uikit.fragments.UIKitFragmentFactory

class UIKitCustomFragmentFactory : UIKitFragmentFactory() {
    override fun newChannelListFragment(args: Bundle): Fragment {
        return ChannelListFragment.Builder()
            .withArguments(args)
            .setUseHeader(true)
            .setCustomFragment(CustomChannelListFragment())
            .build()
    }

    override fun newChannelSettingsFragment(channelUrl: String, args: Bundle): Fragment {
        return ChannelSettingsFragment.Builder(channelUrl)
            .withArguments(args)
            .setUseHeader(true)
            .setCustomFragment(CustomChannelSettingsFragment())
            .build()
    }
}
