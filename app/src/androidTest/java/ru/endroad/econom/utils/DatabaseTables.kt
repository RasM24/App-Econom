package ru.endroad.econom.utils

import androidx.test.platform.app.InstrumentationRegistry
import ru.endroad.econom.component.wish.model.WishModel
import ru.endroad.econom.mock.SQL_INSERT_WISH
import ru.endroad.econom.mock.SQL_UPDATE_WISH
import ru.endroad.econom.mock.arrayValues
import ru.endroad.econom.room.DATABASE_NAME
import ru.endroad.tavern.rule.database.DatabasePresetRule
import ru.endroad.tavern.rule.database.DatabaseRuleContext
import ru.endroad.tavern.rule.database.DatabaseRuleDslMarker
import ru.endroad.tavern.rule.database.Table

fun getDatabasePresetRule(exec: DatabaseRuleContext.() -> Unit) =
	DatabasePresetRule(InstrumentationRegistry.getInstrumentation().targetContext, DATABASE_NAME, exec)

@DatabaseRuleDslMarker
val DatabaseRuleContext.wishTable
	get() = object : Table<WishModel>(database, WISH_TABLE) {
		override fun insert(entity: WishModel) = database.execSQL(SQL_INSERT_WISH, entity.arrayValues)
		override fun delete(entity: WishModel) = database.execSQL("DELETE FROM $table WHERE id = ")
		override fun update(entity: WishModel) = database.execSQL(SQL_UPDATE_WISH, entity.arrayValues)
	}