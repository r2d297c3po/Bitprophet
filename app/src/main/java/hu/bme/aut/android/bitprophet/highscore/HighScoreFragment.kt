package hu.bme.aut.android.bitprophet.highscore

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.bitprophet.R
import hu.bme.aut.android.bitprophet.database.PriceDatabase
import hu.bme.aut.android.bitprophet.databinding.HighScroreFragmentBinding
import hu.bme.aut.android.bitprophet.round


class HighScoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: HighScroreFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.high_scrore_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PriceDatabase.getInstance(application).priceDatabaseDao

        val viewModelFactory =
            HighScoreViewModelFactory(
                dataSource,
                application
            )
        val highScoreViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(HighScoreViewModel::class.java)

        binding.highScoreViewModel = highScoreViewModel

        val manager = LinearLayoutManager(activity)
        binding.scoreList.layoutManager = manager
        val adapter =
            PriceTipAdapter()
        binding.scoreList.adapter = adapter



        highScoreViewModel.tips.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        highScoreViewModel.score.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.apply {
                    tvRound.text = getString(R.string.round) + it.round
                    tvPoint.text = getString(R.string.points) + it.point.round()
                }
            }
        })

        highScoreViewModel.highScore.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.tvHighRound.text = getString(R.string.high_round) + it.round + getString(R.string.rounds)
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }
}
