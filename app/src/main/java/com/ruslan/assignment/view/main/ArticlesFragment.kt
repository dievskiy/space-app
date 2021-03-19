package com.ruslan.assignment.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ruslan.assignment.R
import com.ruslan.assignment.databinding.FragmentArticlesBinding
import com.ruslan.assignment.di.Injector
import com.ruslan.assignment.view.adapters.ArticlesAdapter
import com.ruslan.assignment.view.convertDpToPx
import com.ruslan.assignment.view.decorators.SpacingItemDecoration
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

class ArticlesFragment : Fragment() {

    private lateinit var adapter: ArticlesAdapter
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var viewModel: ArticleViewModel

    private var uiStateJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticlesAdapter(viewLifecycleOwner)
        viewModel =
            ArticleViewModel(requireActivity().application, Injector.articlesRepo)

        binding.viewmodel = viewModel

        binding.recyclerView.apply {
            adapter = this@ArticlesFragment.adapter
            val spacing = resources.getDimension(R.dimen.spacing_rv)
            addItemDecoration(SpacingItemDecoration(requireContext().convertDpToPx(spacing)))
        }

        viewModel.articles.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        setUiStateObserver()
    }

    override fun onStop() {
        uiStateJob?.cancel()
        viewModel.cancelJob()
        super.onStop()
    }

    private fun setUiStateObserver() {
        uiStateJob = lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect {
                binding.progressBar.isVisible = (it == ArticlesUiState.Loading)
                binding.textState.apply {
                    isVisible =
                        (it == ArticlesUiState.Empty || it is ArticlesUiState.NetworkError)
                    text = when (it) {
                        ArticlesUiState.Empty -> getString(R.string.articles_empty)
                        is ArticlesUiState.NetworkError -> getString(R.string.network_error)
                        else -> ""
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticlesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

}
