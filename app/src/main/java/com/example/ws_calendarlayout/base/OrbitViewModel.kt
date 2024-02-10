package com.example.ws_calendarlayout.base

import androidx.lifecycle.viewModelScope
import com.example.ws_calendarlayout.calendar.common.SideEffect
import com.example.ws_calendarlayout.calendar.common.State
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.viewmodel.container

open class OrbitViewModel<STATE : State,SIDE_EFFECT : SideEffect>(
    val state: STATE
) : BaseViewModel(), ContainerHost<STATE,SIDE_EFFECT> {

    override val container: Container<STATE, SIDE_EFFECT> = container(state)
    fun postAction(sideEffect: SIDE_EFFECT) = intent {
        viewModelScope.launch {
            postSideEffect(sideEffect = sideEffect)
        }
    }

}
