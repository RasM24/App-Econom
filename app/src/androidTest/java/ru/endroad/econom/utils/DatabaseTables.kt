package ru.endroad.econom.utils

import androidx.test.platform.app.InstrumentationRegistry
import ru.endroad.econom.component.wish.datasource.WISH_TABLE
import ru.endroad.econom.component.wish.model.Wish
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
	get() = object : Table<Wish>(database, WISH_TABLE) {
		override fun insert(entity: Wish) = database.execSQL(SQL_INSERT_WISH, entity.arrayValues)
		override fun delete(entity: Wish) = database.execSQL("DELETE FROM $table WHERE id = ")
		override fun update(entity: Wish) = database.execSQL(SQL_UPDATE_WISH, entity.arrayValues)
	}