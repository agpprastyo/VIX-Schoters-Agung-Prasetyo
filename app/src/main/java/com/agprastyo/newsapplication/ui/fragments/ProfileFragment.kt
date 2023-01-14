package com.agprastyo.newsapplication.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.agprastyo.newsapplication.R

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val btnGithub = view?.findViewById<Button>(R.id.btn_github)
        btnGithub?.setOnClickListener {

            val url = "https://github.com/agpprastyo"
            val urlIntent = Intent(Intent.CATEGORY_BROWSABLE, Uri.parse(url))
            startActivity(urlIntent)
        }
    }



}