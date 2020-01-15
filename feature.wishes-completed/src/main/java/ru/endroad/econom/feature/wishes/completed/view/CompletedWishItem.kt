package ru.endroad.econom.feature.wishes.completed.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.items.ModelAbstractItem
import ru.endroad.econom.component.wish.model.Wish
import ru.endroad.econom.feature.wishes.completed.R

class CompletedWishItem(item: Wish) : ModelAbstractItem<Wish, CompletedWishItem.ViewHolder>(item) {

	override val layoutRes = R.layout.item_completed_wish

	override val type = R.id.item_completed_wish

	override fun getViewHolder(v: View) = ViewHolder(v)

	override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
		super.bindView(holder, payloads)

		holder.name.text = model.name
		holder.info.text = model.info
		val color = holder.marker.context.resources.getColor(model.importance.colorId)
		holder.marker.setBackgroundColor(color)
		holder.cost.text = "${model.cost}р"
		holder.costInNoodles.text = "~${model.costInNoodles} пачек лапши"
	}

	class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
		val name: TextView = root.findViewById(R.id.wish_name)
		val marker: View = root.findViewById(R.id.marker)
		val info: TextView = root.findViewById(R.id.wish_info)
		val cost: TextView = root.findViewById(R.id.wish_cost)
		val costInNoodles: TextView = root.findViewById(R.id.wish_cost_in_noodles)
	}
}