package com.android.nav_components

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class ExploreFragment : Fragment() {
    private lateinit var btnProduct: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnProduct = view.findViewById(R.id.btn_product)
        btnProduct.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.productActivity, null))
    }

}
