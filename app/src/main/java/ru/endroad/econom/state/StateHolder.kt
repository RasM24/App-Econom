package ru.endroad.econom.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StateHolder {

	private val initialState = ApplicationState.WishList

	private var stack: MutableList<ApplicationState> = mutableListOf(initialState)

	private val _applicationState: MutableStateFlow<ApplicationState> = MutableStateFlow(initialState)
	val applicationState: StateFlow<ApplicationState> = _applicationState

	fun openScreen(state: ApplicationState) {
		stack.add(state)
		_applicationState.tryEmit(state)
	}

	fun back(): Boolean {
		return if (stack.size > 1) {
			stack.removeLast()
			_applicationState.tryEmit(stack.last())
			true
		} else {
			false
		}
	}
}