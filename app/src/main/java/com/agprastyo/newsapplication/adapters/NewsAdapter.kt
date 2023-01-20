package com.agprastyo.newsapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.agprastyo.newsapplication.GlideApp
import com.agprastyo.newsapplication.databinding.ItemArticlePreviewBinding
import com.agprastyo.newsapplication.models.Article


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private var onItemClickListener: ((Article) -> Unit)? = null


    inner class ArticleViewHolder(val binding: ItemArticlePreviewBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticlePreviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
//        return ArticleViewHolder(
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.item_article_preview,
//                parent,
//                false
//            )
//        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.binding.apply {
            GlideApp.with(holder.itemView).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source?.name
            tvTitle.text = article.title
//            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt

            setOnItemClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }



    fun addItem(article: Article, position: Int) {
        val curList = differ.currentList.toMutableList()
        curList.add(position, article)
        differ.submitList(curList)
    }

    fun deleteArticle(article: Article) {
        val curList = differ.currentList.toMutableList()
        curList.remove(article)
        differ.submitList(curList)
    }

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }


}