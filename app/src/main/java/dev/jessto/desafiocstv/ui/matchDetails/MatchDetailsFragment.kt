package dev.jessto.desafiocstv.ui.matchDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dev.jessto.desafiocstv.R
import dev.jessto.desafiocstv.databinding.FragmentMatchDetailsBinding
import dev.jessto.desafiocstv.ui.model.MatchDTO
import org.koin.android.ext.android.inject

class MatchDetailsFragment : Fragment() {

    private val viewModel: MatchDetailsViewModel by inject()

    private val args: MatchDetailsFragmentArgs by navArgs()

    private var _binding: FragmentMatchDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMatchDetailsBinding.inflate(inflater, container, false)

        val match = args.selectedMatch

        if (match != null) {
            viewModel.getOpponentsList(match.id.toString())
            setUpUI(match)
        }

        val team1Adapter = PlayersListAdapter(requireContext())
        val team2Adapter = PlayersListAdapter(requireContext())

        viewModel.opponentsList.observe(viewLifecycleOwner, Observer { opponentsList ->
            team1Adapter.submitList(opponentsList[0].players)
            team2Adapter.submitList(opponentsList[1].players)

            binding.rvTeam1.adapter = team1Adapter
            binding.rvTeam2.adapter = team2Adapter

        })

        return binding.root

    }

    private fun setUpUI(match: MatchDTO) {
        with(binding) {
            tvNameTeam1.text = match.teams[0].name
            tvNameTeam2.text = match.teams[1].name

            tvMatchTime.text = match.scheduledTime.toString() // TODO: Fix date presentation

            if (match.teams[0].badgeImg != null) {
                Glide.with(requireContext())
                    .load(match.teams[0].badgeImg)
                    .placeholder(R.drawable.bg_team_logo_placeholder)
                    .fallback(R.drawable.ic_badge_fallback)
                    .fitCenter()
                    .skipMemoryCache(true)
                    .into(ivLogoTeam1)
            }

            if (match.teams[1].badgeImg != null) {
                Glide.with(requireContext())
                    .load(match.teams[1].badgeImg)
                    .placeholder(R.drawable.bg_team_logo_placeholder)
                    .fallback(R.drawable.ic_badge_fallback)
                    .fitCenter()
                    .skipMemoryCache(true)
                    .into(ivLogoTeam2)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
