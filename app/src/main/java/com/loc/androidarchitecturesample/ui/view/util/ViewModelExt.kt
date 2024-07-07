package com.loc.androidarchitecturesample.ui.view.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.androidarchitecturesample.utils.EventBus
import kotlinx.coroutines.launch

fun ViewModel.sendEvent(event: Any) {
    viewModelScope.launch {
        EventBus.sendEvent(event)
    }
}