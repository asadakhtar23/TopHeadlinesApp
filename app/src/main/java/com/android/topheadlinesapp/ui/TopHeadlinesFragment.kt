package com.android.topheadlinesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.android.topheadlinesapp.R
import com.android.topheadlinesapp.adapters.TopHeadlinesAdapter
import com.android.topheadlinesapp.databinding.FragmentTopHeadlinesBinding
import com.android.topheadlinesapp.interfaces.ItemClickListener
import com.android.topheadlinesapp.viewmodels.TopHeadlinesViewModel
import com.google.android.material.snackbar.Snackbar
import com.tha.core.models.topHeadlines.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopHeadlinesFragment : Fragment(), ItemClickListener {

    private val viewModel: TopHeadlinesViewModel by viewModels()
    private lateinit var binding: FragmentTopHeadlinesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_top_headlines, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        if(!viewModel.isDataInitialized)
            viewModel.getTopHeadlines("bbc-news")
    }

    private fun observeViewModel() {

        viewModel.responseTopHeadlines.observe(viewLifecycleOwner) { data ->
            val adapter = TopHeadlinesAdapter(data, this)
            binding.rvTopHeadlines.adapter = adapter
        }

        viewModel.showErrorMessage.observe(viewLifecycleOwner) { message ->
            showSnackBar(binding.root, message)
        }

        viewModel.showProgressDialog.observe(viewLifecycleOwner) { value ->
            if(value) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }

    }

    private fun gotoDetails(v: View, article: Article) {
        val action = TopHeadlinesFragmentDirections.actionGoToDetails(article)
        Navigation.findNavController(v).navigate(action)
    }

    private fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onItemClickListener(position: Int, data: Article) {
        gotoDetails(binding.rvTopHeadlines, data)
    }
}