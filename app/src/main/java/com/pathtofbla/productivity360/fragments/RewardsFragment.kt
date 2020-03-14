package com.pathtofbla.productivity360.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pathtofbla.productivity360.R
import com.pathtofbla.productivity360.Reward
import com.pathtofbla.productivity360.RewardsRecyclerAdapter
import kotlinx.android.synthetic.main.add_reward_dialog.view.*
import kotlinx.android.synthetic.main.fragment_rewards.*


class RewardsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rewards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val points = 4500 //TODO I/O operation to get points here
        currentPoints.text = points.toString()

        val rewards = mutableListOf<Reward>()//TODO I/O operation to read rewards

        rewardsRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = RewardsRecyclerAdapter(rewards)
        rewardsRecyclerView.adapter = adapter

        addReward.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(activity)
            val inflater = layoutInflater
            val alertLayout = inflater.inflate(R.layout.add_reward_dialog, null)
            builder.setView(alertLayout)

            builder
                .setPositiveButton("Create", null)
                .setNegativeButton(
                    "Cancel"
                ) { dialogInterface: DialogInterface, _: Int ->
                }

            val alert = builder.create()

            //the positiveButton is defined here so I can check if the fields are filled in without closing the Dialog
            alert.setOnShowListener {
                val positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE)
                positiveButton.setOnClickListener {
                    val rewardName: String
                    val rewardPoints: Int
                    val rewardDescription: String

                    if (alertLayout.rewardNameTextView.text.toString().isNotEmpty()) {
                        rewardName = alertLayout.rewardNameTextView.text.toString()
                    } else {
                        alertLayout.rewardNameTextView.error = "Fill out a name"
                        return@setOnClickListener
                    }

                    if (alertLayout.rewardPointsTextView.text.toString().isNotEmpty()) {
                        rewardPoints = alertLayout.rewardPointsTextView.text.toString().toInt()
                    } else {
                        alertLayout.rewardPointsTextView.error = "Fill out how much"
                        return@setOnClickListener
                    }

                    val description = alertLayout.rewardDescriptionTextView.text.toString()
                    if (description.isEmpty()) {
                        alertLayout.rewardDescriptionTextView.error =
                            "Fill out a description"
                        return@setOnClickListener
                    } else if(description.length > 250) {
                        alertLayout.rewardDescriptionTextView.error =
                            "description too long"
                        return@setOnClickListener
                    } else {
                        rewardDescription =
                            alertLayout.rewardDescriptionTextView.text.toString()
                    }

                    val reward = Reward(rewardName, rewardPoints, rewardDescription)
                    rewards.add(reward)
                    adapter.notifyDataSetChanged()

                    alert.dismiss()
                }
            }

            alert.show()

            //removes the weird button BG and changes text color
            val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setBackgroundResource(0)
            negativeButton.setTextColor(Color.parseColor("#DEFFFFFF"))
            val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setBackgroundResource(0)
            positiveButton.setTextColor(Color.parseColor("#FFCA28"))
        }
    }
}

