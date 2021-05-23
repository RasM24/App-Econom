package ru.endroad.econom.application

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.runtime.collectAsState
import org.koin.java.KoinJavaComponent.inject
import ru.endroad.compose.theme.ApplicationTheme
import ru.endroad.econom.feature.wish.active.view.WishListScreen
import ru.endroad.econom.feature.wish.completed.view.CompletedWishesScreen
import ru.endroad.econom.state.ApplicationState
import ru.endroad.econom.state.StateHolder
import ru.endroad.feature.wish.detail.view.EditWishScreen

class SingleActivity : AppCompatActivity() {

	private val stateHolder by inject(StateHolder::class.java)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val applicationState = stateHolder.applicationState.collectAsState()

			ApplicationTheme {
				Crossfade(targetState = applicationState) { state ->
					when (val screen = state.value) {
						ApplicationState.WishCompleted -> CompletedWishesScreen().SceneCompose()
						is ApplicationState.WishDetail -> EditWishScreen(screen.wishId).SceneCompose()
						ApplicationState.WishList      -> WishListScreen().SceneCompose()
					}
				}
			}
		}
	}

	override fun onBackPressed() {
		if (!stateHolder.back()) super.onBackPressed()
	}
}