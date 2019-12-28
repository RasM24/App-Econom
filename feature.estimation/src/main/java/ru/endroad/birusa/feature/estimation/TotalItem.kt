package ru.endroad.birusa.feature.estimation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.items.ModelAbstractItem

class TotalItem(item: TotalResult) : ModelAbstractItem<TotalResult, TotalItem.ViewHolder>(item) {

	override val type = R.id.item_total

	override val layoutRes = R.layout.item_total

	override fun getViewHolder(v: View) = ViewHolder(v)

	override fun bindView(holder: ViewHolder, payloads: MutableList<Any>) {
		super.bindView(holder, payloads)

		holder.name.text = String.format(model.text, model.amount)
	}

	class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
		val name: TextView = root.findViewById(R.id.name)
	}
}

inline fun TotalResult.map(transform: (TotalResult) -> TotalItem): TotalItem =
	transform(this)