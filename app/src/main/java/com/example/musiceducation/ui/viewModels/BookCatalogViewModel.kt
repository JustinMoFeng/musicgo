package com.example.musiceducation.ui.viewModels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel

class BookCatalogViewModel: ViewModel() {
    var expandedStates: MutableMap<String, MutableState<Boolean>> = mutableMapOf()
}