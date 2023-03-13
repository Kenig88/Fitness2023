package com.example.fitness2023.utils

import android.app.AlertDialog
import android.content.Context
import com.example.fitness2023.R

object DialogManager { //25
    fun showDialog(context: Context, messageId: Int, listener: Listener){
        val builder = AlertDialog.Builder(context)
        var dialog: AlertDialog? = null
        builder.setTitle(R.string.attention)
        builder.setMessage(messageId)

        builder.setPositiveButton(R.string.reset_progress){ _,_->
            listener.onClick()
            dialog?.dismiss()
        }
        dialog = builder.create()

        builder.setNegativeButton(R.string.cancel){ _,_->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }

    interface Listener{
        fun onClick()
    }
}