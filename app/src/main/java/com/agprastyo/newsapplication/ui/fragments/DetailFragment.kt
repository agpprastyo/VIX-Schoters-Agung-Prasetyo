package com.agprastyo.newsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.agprastyo.newsapplication.databinding.FragmentDetailBinding
import com.agprastyo.newsapplication.ui.NewsActivity
import com.agprastyo.newsapplication.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar



class DetailFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private val argsLazy: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = argsLazy.article

        binding.apply {
            fab.setOnClickListener {
                viewModel.saveArticle(article)
                Snackbar
                    .make(
                        view,
                        "Article saved succesfully",
                        Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }




    }

}


