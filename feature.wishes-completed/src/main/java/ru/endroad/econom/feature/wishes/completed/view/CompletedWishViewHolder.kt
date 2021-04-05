package ru.endroad.econom.feature.wishes.completed.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_completed_wish.view.*
import ru.endroad.component.core.inflate
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.completed.R

class CompletedWishViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_completed_wish)) {

	fun bind(wish: Wish) {
		itemView.wish_name.text = wish.name
		itemView.wish_info.text = wish.info
		val color = itemView.context.resources.getColor(wish.importance.colorId)
		itemView.marker.setBackgroundColor(color)
		itemView.wish_cost.text = "${wish.cost}р"
		itemView.wish_cost_in_noodles.text = "~${wish.costInNoodles} пачек лапши"
	}
}