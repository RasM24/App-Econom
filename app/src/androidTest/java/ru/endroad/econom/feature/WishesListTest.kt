package ru.endroad.econom.feature

import android.view.View.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.endroad.econom.SingleActivity
import ru.endroad.econom.mock.wishes
import ru.endroad.econom.screens.BottomSheetScreen
import ru.endroad.econom.screens.FulfilledListScreen
import ru.endroad.econom.screens.WishEditScreen
import ru.endroad.econom.screens.WishListScreen
import ru.endroad.econom.utils.getDatabasePresetRule
import ru.endroad.econom.utils.wishTable
import ru.endroad.tavern.assertions.*
import ru.endroad.tavern.interaction.enter
import ru.endroad.tavern.interaction.tapOn

@RunWith(AndroidJUnit4::class)
class WishesListTest {

	@get:Rule
	val activityActivityTestRule = ActivityTestRule(SingleActivity::class.java, false, false)

	@get:Rule
	val databaseRule = getDatabasePresetRule {
		wishTable fill wishes
	}

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
			tapOn(fulfilledList)
		}

		FulfilledListScreen {
			check(itemLexusWish) { visibility = GONE }

			TODO("реализовать tapOnBack")
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
			tapOn(floatingButton)
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
	}

	/**
	 * TestCase("Инвалидаторы")
	 * *написать тесты на валидаторы
	 */
	@Test
	fun invalidationTest() {

		WishListScreen {
			tapOn(floatingButton)
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

	}
}