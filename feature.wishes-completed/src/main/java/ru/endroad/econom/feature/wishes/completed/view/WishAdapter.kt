package ru.endroad.econom.feature.wishes.completed.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.endroad.econom.component.wish.model.Wish

internal class WishAdapter : RecyclerView.Adapter<CompletedWishViewHolder>() {

	var items: List<Wish> = listOf()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedWishViewHolder =
		CompletedWishViewHolder(parent)

	override fun onBindViewHolder(holder: CompletedWishViewHolder, position: Int) {
		holder.bind(items[position])
	}

	override fun getItemCount(): Int =
		items.size
}