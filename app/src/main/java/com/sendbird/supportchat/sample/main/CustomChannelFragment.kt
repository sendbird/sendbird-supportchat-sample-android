package com.sendbird.supportchat.sample.main

import com.sendbird.android.message.BaseMessage
import com.sendbird.android.message.SendingStatus
import com.sendbird.uikit.activities.viewholder.MessageType
import com.sendbird.uikit.activities.viewholder.MessageViewHolderFactory
import com.sendbird.uikit.fragments.ChannelFragment
import com.sendbird.uikit.model.DialogListItem
import com.sendbird.uikit.utils.MessageUtils
import java.util.*

class CustomChannelFragment : ChannelFragment() {
    override fun makeMessageContextMenu(message: BaseMessage): MutableList<DialogListItem> {
        val items: MutableList<DialogListItem> = ArrayList()
        val status = message.sendingStatus
        if (status == SendingStatus.PENDING) return items

        val type = MessageViewHolderFactory.getMessageType(message)
        val copy = DialogListItem(
            com.sendbird.uikit.R.string.sb_text_channel_anchor_copy,
            com.sendbird.uikit.R.drawable.icon_copy
        )
        val edit = DialogListItem(
            com.sendbird.uikit.R.string.sb_text_channel_anchor_edit,
            com.sendbird.uikit.R.drawable.icon_edit
        )
        val save = DialogListItem(
            com.sendbird.uikit.R.string.sb_text_channel_anchor_save,
            com.sendbird.uikit.R.drawable.icon_download
        )
        val retry = DialogListItem(com.sendbird.uikit.R.string.sb_text_channel_anchor_retry, 0)
        val deleteFailed = DialogListItem(com.sendbird.uikit.R.string.sb_text_channel_anchor_delete, 0)

        var actions: Array<DialogListItem>? = null
        when (type) {
            MessageType.VIEW_TYPE_USER_MESSAGE_ME -> if (status == SendingStatus.SUCCEEDED) {
                actions = arrayOf(copy, edit)
            } else if (MessageUtils.isFailed(message)) {
                actions = arrayOf(retry, deleteFailed)
            }
            MessageType.VIEW_TYPE_USER_MESSAGE_OTHER -> actions = arrayOf(copy)
            MessageType.VIEW_TYPE_FILE_MESSAGE_VIDEO_ME, MessageType.VIEW_TYPE_FILE_MESSAGE_IMAGE_ME, MessageType.VIEW_TYPE_FILE_MESSAGE_ME -> actions =
                if (MessageUtils.isFailed(message)) {
                    arrayOf(retry, deleteFailed)
                } else {
                    arrayOf(save)
                }
            MessageType.VIEW_TYPE_FILE_MESSAGE_VIDEO_OTHER, MessageType.VIEW_TYPE_FILE_MESSAGE_IMAGE_OTHER, MessageType.VIEW_TYPE_FILE_MESSAGE_OTHER -> actions =
                arrayOf(save)
            MessageType.VIEW_TYPE_VOICE_MESSAGE_ME -> actions = if (MessageUtils.isFailed(message)) {
                arrayOf(retry, deleteFailed)
            } else {
                null
            }
            else -> {}
        }

        if (actions != null) {
            items.addAll(listOf(*actions))
        }
        return items
    }
}
