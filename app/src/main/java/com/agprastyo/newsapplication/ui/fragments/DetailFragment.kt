package com.agprastyo.newsapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.agprastyo.newsapplication.R
import com.agprastyo.newsapplication.ui.NewsActivity
import com.agprastyo.newsapplication.ui.NewsViewModel


class DetailFragment : Fragment(R.layout.fragment_detail) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }
}