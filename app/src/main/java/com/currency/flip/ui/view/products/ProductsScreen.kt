package com.currency.flip.ui.view.products

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.currency.flip.ui.view.util.components.LoadingDialog
import com.currency.flip.viewmodels.ProductsViewModel
import com.currency.flip.viewmodels.ProductViewModelState

@Composable
internal fun ProductsScreen() {
    val viewModel: ProductsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProductsContent(state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsContent(
    state: ProductViewModelState
) {
    LoadingDialog(isLoading = state.isLoading)
    /*Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { GradientTopAppBar(text = "Products") }
    ) {
        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(top = it.calculateTopPadding()),
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalItemSpacing = 10.dp
        ) {
            items(state.products) { product ->
                ProductCard(product = product)
            }
        }
    }*/
}




