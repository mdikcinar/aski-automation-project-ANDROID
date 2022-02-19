package com.aski.askiotomasyon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment


class kuyuBilgileriFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_kuyu_bilgileri, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        getDialog()?.getWindow()?.setLayout(1000, 1500)
    }

}