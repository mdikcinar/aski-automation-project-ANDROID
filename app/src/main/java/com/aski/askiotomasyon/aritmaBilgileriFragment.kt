package com.aski.askiotomasyon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment


class aritmaBilgileriFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_aritma_bilgileri, container, false)
        return view
    }

    override fun onResume() {
        super.onResume()
        getDialog()?.getWindow()?.setLayout(1000, 1500)
    }
}