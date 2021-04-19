package ru.endroad.feature.wish.detail.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.single
import ru.endroad.feature.wish.detail.domain.CostValidator
import ru.endroad.feature.wish.detail.domain.ImportanceValidator
import ru.endroad.feature.wish.detail.domain.NameValidator
import ru.endroad.feature.wish.detail.presentation.EditWishViewModel

val featureWishDetailModule = module {

	single { NameValidator(40) } //TODO пока пусть будет как magicNumber
	single<CostValidator>()
	single<ImportanceValidator>()

	viewModel { (id: Int?) ->
		EditWishViewModel(
			getWish = get(),
			addWish = get(),
			editWish = get(),
			wishId = id,
			nameValidator = get(),
			costValidator = get(),
			importanceValidator = get()
		)
	}
}