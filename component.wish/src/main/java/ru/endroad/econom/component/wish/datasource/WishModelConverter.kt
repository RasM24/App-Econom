package ru.endroad.econom.component.wish.datasource

import ru.endroad.econom.component.wish.model.WishModel
import ru.endroad.shared.wish.core.entity.Wish

internal class WishModelConverter(
	private val converter: ImportanceConverter
) {

	fun to(wishModel: WishModel): Wish =
		Wish(
			id = wishModel.id,
			name = wishModel.name,
			cost = wishModel.cost,
			importance = converter.to(wishModel.importance),
			info = wishModel.info,
			complete = wishModel.complete,
		)

	fun from(wish: Wish): WishModel =
		WishModel(
			id = wish.id,
			name = wish.name,
			cost = wish.cost,
			importance = converter.from(wish.importance),
			info = wish.info,
			complete = wish.complete,
		)

}