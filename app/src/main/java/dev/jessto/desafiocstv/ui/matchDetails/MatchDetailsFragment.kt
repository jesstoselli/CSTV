package dev.jessto.desafiocstv.ui.matchDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dev.jessto.desafiocstv.R
import dev.jessto.desafiocstv.databinding.FragmentMatchDetailsBinding
import dev.jessto.desafiocstv.ui.ApiStatus
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

        val teamLeftAdapter = PlayersLeftListAdapter(requireContext())
        val teamRightAdapter = PlayersRightListAdapter(requireContext())

        viewModel.opponentsList.observe(viewLifecycleOwner, Observer { opponentsList ->
            teamLeftAdapter.submitList(opponentsList[0].players)
            teamRightAdapter.submitList(opponentsList[1].players)

            binding.rvTeam1.adapter = teamLeftAdapter
            binding.rvTeam2.adapter = teamRightAdapter

        })

        viewModel.apiStatus.observe(viewLifecycleOwner, Observer { apiStatus ->

            when (apiStatus) {
                ApiStatus.LOADING -> Log.i("MatchDetailsFragment", "LOADING")
                ApiStatus.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.ccMatchInfo.visibility = View.VISIBLE
                }
                ApiStatus.ERROR -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.llError.visibility = View.VISIBLE
                }
            }
        })

        return binding.root

    }

    private fun setUpUI(match: MatchDTO) {
        with(binding) {

            tvMatchTime.text = match.scheduledTime.toString() // TODO: Fix date presentation

            if (match.teams != null) {

                tvNameTeam1.text = match.teams[0].name
                tvNameTeam2.text = match.teams[1].name

                Glide.with(requireContext())
                    .load(match.teams[0].badgeImg)
                    .placeholder(R.drawable.bg_team_logo_placeholder)
                    .fallback(R.drawable.ic_badge_fallback)
                    .fitCenter()
                    .skipMemoryCache(true)
                    .into(ivLogoTeam1)

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
