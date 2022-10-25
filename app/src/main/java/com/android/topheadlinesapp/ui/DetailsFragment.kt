package com.android.topheadlinesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.android.topheadlinesapp.R
import com.android.topheadlinesapp.databinding.FragmentDetailsBinding
import com.android.topheadlinesapp.viewmodels.DetailsViewModel
import com.bumptech.glide.Glide

class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var binding: FragmentDetailsBinding

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_details, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.articleData
        binding.article = article

        Glide.with(requireActivity())
            .load(viewModel.getImageUrl(article))
            .placeholder(R.drawable.placeholder)
            .into(binding.ivLarge)
    }

}