package com.pathtofbla.productivity360

import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.add_reward_dialog.view.*
import kotlinx.android.synthetic.main.info_dialog.view.*
import kotlinx.android.synthetic.main.reward_cell.view.*


class RewardsRecyclerAdapter(private var rewards: MutableList<Reward>) :
    RecyclerView.Adapter<RewardsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reward_cell, parent, false)
        val reward =
            Reward("x", 0, "x") //placeholder reward to be overwritten in the onBindViewHolder()

        return RewardsViewHolder(view, reward, rewards, this)
    }

    override fun getItemCount() = rewards.size

    override fun onBindViewHolder(holder: RewardsViewHolder, position: Int) {
        holder.reward = rewards[position]

        holder.view.reward.text = rewards[position].name
        holder.view.pointsNeeded.text = rewards[position].points.toString()
        holder.view.rewardProgressBar.max = rewards[position].points

        val currentPoints = 4500 //TODO I/O operation to get points here
        holder.view.rewardProgressBar.progress = currentPoints

        if (currentPoints >= rewards[position].points) {
            holder.view.redeem.setTextColor(0xFFFFC107.toInt()) //makes redeem gold
        }
    }
}

/**
 * @param reward - Used to know which reward object to edit
 * @param rewards - Used to delete and edit rewards in the list
 * @param adapter - Used to update the adapter after the data in rewards has changed
 */
class RewardsViewHolder(
    val view: View,
    var reward: Reward,
    var rewards: MutableList<Reward>,
    var adapter: RewardsRecyclerAdapter
) : RecyclerView.ViewHolder(view) {
    private val currentPoints = 4500 //TODO I/O operation to get points here

    init {
        view.setOnLongClickListener {
            val builder =
                MaterialAlertDialogBuilder(view.context) //alertDialog to show description of reward
            val activity = view.context as AppCompatActivity
            val inflater = activity.layoutInflater
            val alertLayout = inflater.inflate(R.layout.info_dialog, null)

            alertLayout.dialogTitle.text = "Reward Info"
            alertLayout.dialogDescription.text = reward.description
            builder.setView(alertLayout)

            builder.setPositiveButton(
                "Edit"
            ) { dialogInterface: DialogInterface, _: Int ->
                //the positive button opens up custom alertDialog to edit reward
                val editAlertLayout = inflater.inflate(
                    R.layout.add_reward_dialog,
                    null
                )

                editAlertLayout.createReward.text = "Edit Reward"

                val editBuilder =
                    MaterialAlertDialogBuilder(view.context) //alertDialog to edit reward
                editBuilder.setView(editAlertLayout)

                editBuilder.setPositiveButton("Edit", null)
                    .setNegativeButton(
                        "Cancel"
                    ) { editDialogInterface: DialogInterface, _: Int ->
                        editDialogInterface.cancel() //closes both popups
                    }

                editAlertLayout.rewardNameTextView.setText(reward.name)
                editAlertLayout.rewardPointsTextView.setText(reward.points.toString())
                editAlertLayout.rewardDescriptionTextView.setText(reward.description)

                val editAlert = editBuilder.create()

                //the positiveButton is defined here so I can check if the fields are filled in without closing the Dialog
                editAlert.setOnShowListener {
                    val positiveButton = editAlert.getButton(AlertDialog.BUTTON_POSITIVE)
                    positiveButton.setOnClickListener {
                        val rewardName: String
                        val rewardPoints: Int
                        val rewardDescription: String

                        if (editAlertLayout.rewardNameTextView.text.toString().isNotEmpty()) {
                            rewardName = editAlertLayout.rewardNameTextView.text.toString()
                        } else {
                            editAlertLayout.rewardNameTextView.error = "Fill out a name"
                            return@setOnClickListener
                        }

                        if (editAlertLayout.rewardPointsTextView.text.toString().isNotEmpty()) {
                            rewardPoints =
                                editAlertLayout.rewardPointsTextView.text.toString().toInt()
                        } else {
                            editAlertLayout.rewardPointsTextView.error = "Fill out how much"
                            return@setOnClickListener
                        }

                        val description = editAlertLayout.rewardDescriptionTextView.text.toString()
                        if (description.isEmpty()) {
                            editAlertLayout.rewardDescriptionTextView.error =
                                "Fill out a description"
                            return@setOnClickListener
                        } else if(description.length > 250) {
                            editAlertLayout.rewardDescriptionTextView.error =
                                "description too long"
                            return@setOnClickListener
                        } else {
                            rewardDescription =
                                editAlertLayout.rewardDescriptionTextView.text.toString()
                        }

                        view.rewardProgressBar.max = reward.points
                        view.rewardProgressBar.progress = currentPoints

                        val newReward = Reward(rewardName, rewardPoints, rewardDescription)
                        val index = rewards.indexOf(reward)
                        rewards[index] = newReward

                        if (currentPoints >= rewardPoints) {
                            view.redeem.setTextColor(0xFFFFC107.toInt())
                        } else {
                            view.redeem.setTextColor(0xFF656565.toInt())
                        }

                        adapter.notifyDataSetChanged() //TODO I/O operation to edit reward
                        dialogInterface.cancel()
                        editAlert.dismiss()
                    }
                }


                editAlert.show()

                //removes the weird button BG and changes text color
                val negativeButton = editAlert.getButton(DialogInterface.BUTTON_NEGATIVE)
                negativeButton.setBackgroundResource(0)
                negativeButton.setTextColor(Color.parseColor("#DEFFFFFF"))
                val positiveButton = editAlert.getButton(DialogInterface.BUTTON_POSITIVE)
                positiveButton.setBackgroundResource(0)
                positiveButton.setTextColor(Color.parseColor("#FFCA28"))
            }.setNegativeButton(
                "Delete"
            ) { dialogInterface: DialogInterface, _: Int ->
                //TODO I/O operation to remove reward
                rewards.remove(reward)
                adapter.notifyDataSetChanged()
            }

            val alert = builder.create()
            alert.show()

            //removes the weird button BG and changes text color
            val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
            negativeButton.setBackgroundResource(0)
            negativeButton.setTextColor(Color.parseColor("#f44336"))
            val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
            positiveButton.setBackgroundResource(0)
            positiveButton.setTextColor(Color.parseColor("#FFCA28"))
            return@setOnLongClickListener true
        }

        view.redeem.setOnClickListener {
            val builder =
                MaterialAlertDialogBuilder(view.context) //alertDialog to show description of reward
            val activity = view.context as AppCompatActivity
            val inflater = activity.layoutInflater
            val alertLayout = inflater.inflate(R.layout.info_dialog, null)

            if (currentPoints < reward.points) {
                alertLayout.dialogTitle.text = "You're so gullible"
                alertLayout.dialogDescription.text = "Stop dreaming and get to work!"
                builder.setView(alertLayout)

                builder.setPositiveButton(
                    "OK!"
                ) { dialogInterface: DialogInterface, _: Int -> }
                    .setNegativeButton(
                        "...sorry"
                    ) { dialogInterface: DialogInterface, _: Int ->
                    }

                val alert = builder.create()
                alert.show()

                val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
                negativeButton.setBackgroundResource(0)
                negativeButton.setTextColor(Color.parseColor("#DEFFFFFF"))
                val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
                positiveButton.setBackgroundResource(0)
                positiveButton.setTextColor(Color.parseColor("#FFCA28"))
            } else {
                alertLayout.dialogTitle.text = "Redeem Reward"
                alertLayout.dialogDescription.text = "Are you sure?"
                builder.setView(alertLayout)
                builder.setPositiveButton(
                    "YES!"
                ) { dialogInterface: DialogInterface, _: Int ->
                    //TODO I/O operation to subtract current points
                    rewards.remove(reward)
                    adapter.notifyDataSetChanged()
                }
                    .setNegativeButton(
                        "Nevermind"
                    ) { dialogInterface: DialogInterface, _: Int ->
                    }

                val alert = builder.create()
                alert.show()

                val negativeButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE)
                negativeButton.setBackgroundResource(0)
                negativeButton.setTextColor(Color.parseColor("#DEFFFFFF"))
                val positiveButton = alert.getButton(DialogInterface.BUTTON_POSITIVE)
                positiveButton.setBackgroundResource(0)
                positiveButton.setTextColor(Color.parseColor("#FFCA28"))
            }
        }
    }
}
