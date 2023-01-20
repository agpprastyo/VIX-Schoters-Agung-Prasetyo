package com.agprastyo.newsapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agprastyo.newsapplication.R
import com.agprastyo.newsapplication.adapters.NewsAdapter
import com.agprastyo.newsapplication.databinding.FragmentBreakingNewsBinding
import com.agprastyo.newsapplication.ui.NewsActivity
import com.agprastyo.newsapplication.ui.NewsViewModel
import com.agprastyo.newsapplication.util.Constants.Companion.QUERY_PAGE_SIZE
import com.agprastyo.newsapplication.util.Resource



class BreakingNewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

//    private val TAG = "BreakingNewsFragment"


    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel


        binding.apply {

            setupRecyclerView()

            newsAdapter.setOnItemClickListener {
                val bundle = Bundle().apply {
                    putSerializable("article", it)
                }
                val detailAction = R.id.action_breakingNewsFragment_to_detailFragment
                findNavController().navigate(detailAction, bundle)
            }

        }

        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()

                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if (isLastPage) {
                            binding.rvBreakingNews.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                            activity,
                            "An error occurred: $message",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                else -> {}
            }
        }

//        newsAdapter.setOnItemClickListener {
//            val bundle = Bundle().apply {
//                putSerializable("article", it)
//            }
//            findNavController().navigate(
//                R.id.action_breakingNewsFragment_to_detailFragment,
//                bundle
//            )
//        }
//
//
//        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
//            when(response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//
//                    response.data?.let { newsResponse ->
//                        newsAdapter.differ.submitList(newsResponse.articles.toList())
//                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
//                        isLastPage = viewModel.breakingNewsPage == totalPages
//                        if (isLastPage) {
//                            rvBreakingNews.setPadding(0, 0, 0, 0)
//                        }
//                    }
//                }
//                is Resource.Error -> {
//                    hideProgressBar()
//                    response.message?.let { message ->
//                        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG)
//                            .show()
//                    }
//                }
//                is Resource.Loading -> {
//                    showProgressBar()
//                }
//            }
//        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingandNotLastPage = !isLoading && !isLastPage
            val isAtLastitem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingandNotLastPage && isAtLastitem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getBreakingNews("id")
                isScrolling = false
            }
            // else
        }
    }
    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}