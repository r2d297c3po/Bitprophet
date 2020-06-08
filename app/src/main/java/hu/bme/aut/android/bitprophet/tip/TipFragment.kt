package hu.bme.aut.android.bitprophet.tip

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.bitprophet.R
import hu.bme.aut.android.bitprophet.database.PriceDatabase
import hu.bme.aut.android.bitprophet.databinding.TipFragmentBinding
import hu.bme.aut.android.bitprophet.round
import hu.bme.aut.android.bitprophet.toDateHours


class TipFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Binding the layout and the viewmodel
        val binding: TipFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.tip_fragment, container, false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = PriceDatabase.getInstance(application)

        val viewModelFactory =
            TipViewModelFactory(
                dataSource,
                application
            )

        val tipViewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(TipViewModel::class.java)

        binding.tipViewModel = tipViewModel

        tipViewModel.recentTip.observe(viewLifecycleOwner, Observer {
            if (it?.realPrice != null || it == null) {
                binding.apply {
                    btnSubmit.isEnabled = true
                    etTip.isEnabled = true
                    tvTip.text = getString(R.string.make_your_prediction)
                }
            } else {
                binding.apply {
                    btnSubmit.isEnabled = false
                    etTip.isEnabled = false
                    tvTip.text = getString(R.string.your_tip_ready) + it.tipEndTime.toDateHours()
                }
            }
        })

        tipViewModel.score.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.apply {
                    tvRound.text = getString(R.string.round) + it.round
                    tvPoints.text = getString(R.string.points) + it.point.round()
                    btnToHighscore.isEnabled = true
                }
            }
            if (it == null) {
                binding.btnToHighscore.isEnabled = false
            }
        })

        binding.btnToHighscore.setOnClickListener {
            findNavController().navigate(R.id.action_tipFragment_to_highScroreFragment)
        }

        binding.btnSubmit.setOnClickListener {
            val etText = binding.etTip.text
            if (etText.isNotEmpty()) {
                val etTip = etText.toString()
                tipViewModel.onSubmit(etTip)
                etText.clear()
                createChannel(
                    getString(R.string.notification_channel_id),
                    getString(R.string.notification_channel_name)
                )
                findNavController().navigate(R.id.action_tipFragment_to_highScroreFragment)
            } else {
                binding.tvTip.apply {
                    text = getString(R.string.provide_tip)
                    setTextColor(Color.RED)
                }
            }
        }

        binding.lifecycleOwner = this

        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationChannel.enableVibration(true)
            notificationChannel.description = "Tip is over."

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
