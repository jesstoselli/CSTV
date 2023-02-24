package dev.jessto.desafiocstv.ui.matchesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dev.jessto.desafiocstv.databinding.FragmentMatchesListBinding
import org.koin.android.ext.android.inject

class MatchesListFragment : Fragment() {

    private val viewModel: MatchesViewModel by inject()

    private var _binding: FragmentMatchesListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMatchesListBinding.inflate(inflater, container, false)

        val adapter = MatchesListAdapter(requireContext(), MatchesListAdapter.MatchesListClickListener { match ->
            viewModel.navigateToMatchDetails(match)
        })

        viewModel.matchesList.observe(viewLifecycleOwner, Observer { matchesList ->
            adapter.submitList(matchesList)
            binding.rvMatchesList.adapter = adapter
        })

        viewModel.navigateToMatchDetails.observe(viewLifecycleOwner, Observer { match ->
            if (match != null) {
                val title = match.leagueName + " " + match.series

                val action = MatchesListFragmentDirections.actionMatchesListFragmentToMatchDetailsFragment(title, match)
                findNavController().navigate(action)

                viewModel.returnFromMatchDetails()
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
