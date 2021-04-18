package ru.endroad.econom.feature.wishes.view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.endroad.shared.wish.core.entity.Wish

internal class WishAdapter(private val onItemClick: (Wish) -> Unit) : RecyclerView.Adapter<WishViewHolder>() {

	var items: MutableList<Wish> = mutableListOf()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishViewHolder =
		WishViewHolder(parent, onItemClick)

	override fun onBindViewHolder(holder: WishViewHolder, position: Int) {
		holder.bind(items[position])
	}

	override fun getItemCount(): Int =
		items.size

	fun remove(position: Int) {
		items.removeAt(position)
		notifyItemRemoved(position)
	}

	fun add(position: Int, wish: Wish){
		items.add(position, wish)
		notifyItemInserted(position)
	}
}