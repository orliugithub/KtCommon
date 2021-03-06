package com.tunaikita.log.base

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orliu.kotlin.common.R
import com.orliu.kotlin.common.tools.screenWidthPixels

/**
 * Created by liujianping on 17/11/15.
 */
abstract class BaseDialog : DialogFragment(), DialogInterface.OnKeyListener {

    interface onDismissListener {
        fun onDismiss()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.StyleDialogNormal)
        isCancelable = false
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(getLayoutId(), container, false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (onBackPressed()) dialog.setOnKeyListener(this)
        return view
    }

    override fun onResume() {
        super.onResume()
        //dialog.window!!.setWindowAnimations(R.style.StyleDialogAnim)
        dialog.window?.setLayout((context?.screenWidthPixels()!!.times(0.8)).toInt(), -2)
        initView()
    }

    abstract fun getLayoutId(): Int

    abstract fun initView()

    /**
     * 物理返回键是否响应dismiss
     */
    abstract fun onBackPressed(): Boolean

    override fun show(fm: FragmentManager, tag : String){
        try {
            if (isAdded) return
            if (fm.isDestroyed) return

            fm.beginTransaction().add(this, tag).commitAllowingStateLoss()
        }catch (e:Exception){}
    }

    override fun dismiss() {
        if (!isAdded) return
        dismissAllowingStateLoss()
    }

    override fun onKey(p0: DialogInterface?, p1: Int, p2: KeyEvent?): Boolean {
        return when (p1) {
            KeyEvent.KEYCODE_BACK -> {
                dismissAllowingStateLoss()
                true
            }
            else -> {
                false
            }
        }
    }
}