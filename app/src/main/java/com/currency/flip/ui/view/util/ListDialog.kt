package com.currency.flip.ui.view.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.currency.flip.ui.theme.Blue1
import com.currency.flip.viewmodels.ConversionViewModel
import com.currency.flip.viewmodels.ConversionViewModelState

@Composable
fun ListDialog(
    onDismissRequest: () -> Unit,
    state: ConversionViewModelState,
    fromSpinnerData: MutableState<String>,
    toSpinnerData: MutableState<String>,
    isBaseCurrency:  MutableState<Boolean>
) {
    val conversionViewModel:ConversionViewModel= hiltViewModel()
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Blue1)){
                    Text(
                        text = "Select Currency",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = Color.White

                    )
                }

                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)){
                    var list=conversionViewModel.currenciesMapObservable.observeAsState().value
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                    ) {
                        list?.let {
                            itemsIndexed(it.toList()){index, item->
                                Text(text = "${item.first } -> ${item.second}", modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(10.dp)
                                    .align(alignment = Alignment.Center)
                                    .clickable {
                                        if (isBaseCurrency.value){
                                            fromSpinnerData.value="${item.first} - ${item.second}"
                                        }else{
                                            toSpinnerData.value="${item.first} - ${item.second}"
                                        }
                                        onDismissRequest()
                                    })
                                HorizontalDivider()
                            }
                        }

                    }


                }

            }

        }
    }
}

