package com.catatancodingku.githubuserapp.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.catatancodingku.githubuserapp.AlarmReceiver
import com.catatancodingku.githubuserapp.callback.AlarmCallback
import com.catatancodingku.githubuserapp.databinding.FragmentCustomDialogBinding

class CustomDialog : DialogFragment() {

    private lateinit var binding: FragmentCustomDialogBinding
    private var isActive : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isActive = AlarmReceiver.isAlarmSet(context!!)

        Log.d("cek alarm", "onCreate: $isActive")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener { dialog?.dismiss() }
        binding.switchAlarm.isChecked = isActive



        binding.switchAlarm.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                alarmCallback?.onAlarmTurnON()
            } else {
                alarmCallback?.onAlarmCencel()
            }

        }
    }

    companion object {
        private var alarmCallback: AlarmCallback? = null

        fun setAlarmCallback(alarmCallback: AlarmCallback) {
            this.alarmCallback = alarmCallback
        }

    }

}