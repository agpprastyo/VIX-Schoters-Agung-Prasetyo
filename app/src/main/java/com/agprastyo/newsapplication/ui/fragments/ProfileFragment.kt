package com.agprastyo.newsapplication.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.agprastyo.newsapplication.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(R.layout.fragment_profile), View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileFragment = inflater.inflate(R.layout.fragment_profile, container, false)

        val btnGithub = profileFragment.findViewById<Button>(R.id.btn_github)
        btnGithub.setOnClickListener(this)

        val btnlinkedin = profileFragment.findViewById<Button>(R.id.btn_linkedin)
        btnlinkedin.setOnClickListener(this)
        return profileFragment
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_github -> {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse("https://github.com/agpprastyo")
                startActivity(intent)
            }
            R.id.btn_linkedin -> {
                val intent = Intent()
                intent.action = Intent.ACTION_VIEW
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                intent.data = Uri.parse("https://www.linkedin.com/in/agprastyo/")
                startActivity(intent)
            }
        }

    }

}