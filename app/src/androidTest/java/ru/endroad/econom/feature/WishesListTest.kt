package ru.endroad.econom.feature

import android.view.View.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.endroad.econom.R
import ru.endroad.econom.application.SingleActivity
import ru.endroad.econom.mock.wishes
import ru.endroad.econom.mock.wishesLastPosition
import ru.endroad.econom.screens.BottomSheetScreen
import ru.endroad.econom.screens.FulfilledListScreen
import ru.endroad.econom.screens.WishEditScreen
import ru.endroad.econom.screens.WishListScreen
import ru.endroad.econom.utils.getDatabasePresetRule
import ru.endroad.econom.utils.wishTable
import ru.endroad.tavern.assertions.*
import ru.endroad.tavern.interaction.enter
import ru.endroad.tavern.interaction.tapOn
import ru.endroad.tavern.interaction.tapOnBack
import ru.endroad.tavern.interaction.tapOnItem

@RunWith(AndroidJUnit4::class)
class WishesListTest {

	@get:Rule
	val activityActivityTestRule = ActivityTestRule(SingleActivity::class.java, false, false)

	@get:Rule
	val databaseRule = getDatabasePresetRule {
		wishTable fill wishes
	}

	private val estimationCount = 1
	private val expectedItemsCount = wishes.size + estimationCount //TODO придумать, как выключать дразнилку

	@Before
	fun setUp() {
		activityActivityTestRule.launchActivity(null)
	}

	/**
	 * TestCase("Выполнить")
	 * *Нажать "Список выполненных"
	 * *Чек: элемент нет в списке
	 * *Назад
	 * Выбрать элемент
	 * Нажать "Выполнено"
	 * Чек: элемента больше нет в списке
	 * Нажать "Список выполненных"
	 * Чек: элемент появился в списке
	 */
	@Test
	fun performWish() {
		WishListScreen {
			check(wishList) { itemCount = expectedItemsCount }
			tapOn(fulfilledList)
		}

		FulfilledListScreen {
			check(itemLexusWish) { visibility = GONE }

			tapOnBack()
		}

		WishListScreen {
			tapOn(itemLexusWish)
		}

		BottomSheetScreen {
			tapOn(performButton)
		}

		WishListScreen {
			check(itemLexusWish) { visibility = GONE }
		}

		WishListScreen {
			check(wishList) { itemCount = expectedItemsCount - 1 }
			tapOn(fulfilledList)
		}

		FulfilledListScreen {
			check(itemLexusWish) { visibility = VISIBLE }
		}
	}

	/**
	 * TestCase("Удалить")
	 * Выбрать элемент
	 * Нажать "Удалить"
	 * Чек: элемента больше нет в списке
	 * Нажать "Список выполненных"
	 * Чек: элемент нет в списке
	 */
	@Test
	fun deleteWish() {
		WishListScreen {
			check(wishList) { itemCount = expectedItemsCount }
			tapOn(itemLexusWish)
		}

		BottomSheetScreen {
			tapOn(deleteButton)
		}

		WishListScreen {
			check(itemLexusWish) { visibility = GONE }

			tapOn(fulfilledList)
		}

		FulfilledListScreen {
			check(WishListScreen.wishList) { itemCount = expectedItemsCount - 1 }
			check(itemLexusWish) { visibility = GONE }
		}
	}

	/**
	 * TestCase("Изменить")
	 * *Чек: в toolbar изменить
	 * Заполнить все поля
	 * Чек: кнопка называется "изменить"
	 * Нажать изменить
	 * Элемент был изменен в списке
	 */
	@Test
	fun editWish() {
		WishListScreen {
			check(wishList) { itemCount = expectedItemsCount }
			tapOnItem(0)
		}

		BottomSheetScreen {
			tapOn(editButton)
		}

		WishEditScreen {
			//			check(title) {
			//				text = "Изменение"
			//			}
			check(primaryTitle) {
				text = "Основная информация"
				visibility = VISIBLE
			}
			check(detailTitle) {
				text = "Детали"
				visibility = VISIBLE
			}

			enter(nameInput) { "Автомобиль Honda" }
			enter(infoInput) { "Гнилушка-старушка :)" }
			enter(costInput) { "200000" }
			enter(importanceInput) { "BACKLOG" }
			tapOn(applyButton)
		}

		WishListScreen {
			check(wishList) { itemCount = expectedItemsCount }
			item(R.id.list, atPosition = 0) {
				check(R.id.wish_name) { text = "Автомобиль Honda" }
				check(R.id.wish_info) { text = "Гнилушка-старушка :)" }
				check(R.id.wish_cost) { text = "200000р" }
			}
		}
	}

	/**
	 * TestCase("Создать")
	 * нажать на fab
	 * *Чек: в toolbar создать
	 * Заполнить все поля
	 * Чек: кнопка называется "создать"
	 * Нажать создать
	 * Элемент был создан в списке
	 */
	@Test
	fun newWish() {
		WishListScreen {
			check(wishList) { itemCount = expectedItemsCount }
			tapOn(newWishFab)
		}

		WishEditScreen {
			check(primaryTitle) {
				text = "Основная информация"
				visibility = VISIBLE
			}
			check(detailTitle) {
				text = "Детали"
				visibility = VISIBLE
			}
			enter(nameInput) { "Квартира на рублевке" }
			enter(infoInput) { "Мечтать не вредно" }
			enter(costInput) { "1000000000" }
			enter(importanceInput) { "BACKLOG" }
			tapOn(applyButton)
		}

		WishListScreen {
			check(wishList) { itemCount = expectedItemsCount + 1 }
			item(R.id.list, atPosition = wishesLastPosition + 1) {
				check(R.id.wish_name) { text = "Квартира на рублевке" }
				check(R.id.wish_info) { text = "Мечтать не вредно" }
				check(R.id.wish_cost) { text = "1000000000р" }
			}
		}
	}

	/**
	 * TestCase("Инвалидаторы")
	 * *написать тесты на валидаторы
	 */
	@Test
	fun invalidationTest() {
		WishListScreen {
			tapOn(newWishFab)
		}

		WishEditScreen {
			tapOn(applyButton)

			check(nameInputLayout) { error = "Введите название" }
			check(costInputLayout) { error = "Введите ценник" }
			check(importanceInputLayout) { error = "Выберите важность" }

			enter(nameInput) { "Невообразимое желание, невозможно уместить в одну строчку" }
			enter(costInput) { "цена введена буквами штааа" }
			enter(importanceInput) { "выбрали как-то не то.. штааа" }
			tapOn(applyButton)

			check(nameInputLayout) { error = "Текст не должен быть длиннее 40 символов" }
			check(costInputLayout) { error = "Значение неккоректно" }
			check(importanceInputLayout) { error = "Значение неккоректно" }

			enter(nameInput) { "Квартира на рублевке" }
			enter(costInput) { "1000000000" }
			enter(importanceInput) { "BACKLOG" }

			check(nameInputLayout) { error = empty() }
			check(costInputLayout) { error = empty() }
			check(importanceInputLayout) { error = empty() }
		}
	}

	/**
	 * TestCase("Дразнилка")
	 * Проверить показ дразнилки
	 */
	@Test
	fun estimations() {
		TODO("Придумать как проверить дразнилку")
	}

	/**
	 * Тест на заглушки
	 */
	@Test
	fun testCase() {
	}
}