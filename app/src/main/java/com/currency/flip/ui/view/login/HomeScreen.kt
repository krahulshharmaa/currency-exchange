package com.currency.flip.ui.view.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import arrow.core.Either
import com.currency.flip.R
import com.currency.flip.domain.model.Conversion
import com.currency.flip.domain.model.NetworkError
import com.currency.flip.domain.repository.ConversionRepository
import com.currency.flip.ui.theme.BG_Gray
import com.currency.flip.ui.view.util.ListDialog
import com.currency.flip.ui.view.util.components.GradientTopAppBar
import com.currency.flip.ui.view.util.components.LoadingDialog
import com.currency.flip.viewmodels.ConversionViewModel
import com.currency.flip.viewmodels.ConversionViewModelState

@Composable
fun HomeScreen() {
    val conversionViewModel: ConversionViewModel = hiltViewModel()
    val state by conversionViewModel.state.collectAsStateWithLifecycle()
    val fromSpinnerVisibility = remember { mutableStateOf(false) }
    val toSpinnerVisibility = remember { mutableStateOf(false) }
    val isBaseCurrency = remember { mutableStateOf(false) }

    val fromSpinnerData = remember { mutableStateOf("America - USD") }
    val toSpinnerData = remember { mutableStateOf("India - INR") }

    val fromDataObservable = remember {
        mutableStateOf("")
    }

    val toDataObservable = remember {
        mutableStateOf("")
    }

    HomeContent(
        conversionViewModel,
        state = state,
        fromSpinnerVisibility,
        toSpinnerVisibility,
        fromSpinnerData,
        toSpinnerData,
        isBaseCurrency,
        fromDataObservable,
        toDataObservable
    )

}

@Composable
fun CurrencySelectionDialog(
    visibility: MutableState<Boolean>,
    state: ConversionViewModelState,
    fromSpinnerData: MutableState<String>,
    toSpinnerData: MutableState<String>,
    isBaseCurrency: MutableState<Boolean>,
) {
    if (visibility.value) {
        ListDialog(
            onDismissRequest = { visibility.value = !visibility.value },
            state,
            fromSpinnerData,
            toSpinnerData,
            isBaseCurrency
        )
    }
}

@Composable
fun HomeContent(
    conversionViewModel: ConversionViewModel,
    state: ConversionViewModelState,
    fromSpinnerVisibility: MutableState<Boolean>,
    toSpinnerVisibility: MutableState<Boolean>,
    fromSpinnerData: MutableState<String>,
    toSpinnerData: MutableState<String>,
    isBaseCurrency: MutableState<Boolean>,
    fromDataObservable: MutableState<String>,
    toDataObservable: MutableState<String>
) {

    LoadingDialog(isLoading = state.isLoading)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .heightIn(2000.dp)
            .background(color = BG_Gray)
    ) {
        GradientTopAppBar()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_currency_exchange),
                contentDescription = "Currency Exchange Logo",
                tint = Color.White,
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Currency Converter",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif
            )
            Text(
                text = "Converter",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Thin,
                fontFamily = FontFamily.SansSerif
            )

            Spacer(modifier = Modifier.height(25.dp))
            CurrenciesSelector(
                fromSpinnerVisibility,
                toSpinnerVisibility,
                fromSpinnerData,
                toSpinnerData,
                isBaseCurrency
            )

            ConversionCard(
                state,
                conversionViewModel,
                fromSpinnerData,
                toSpinnerData,
                fromDataObservable,
                toDataObservable
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.other_currencies),
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Thin
                    )
                }

                conversionViewModel.state.value.conversions?.let {
                    items(it.size) { index ->
                        Card(
                            onClick = { /*TODO*/ }, modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(5.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxSize().fillMaxHeight()) {
                                Row(modifier = Modifier.fillMaxWidth().fillMaxHeight().heightIn(60.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painterResource(id = R.drawable.rt_alert),
                                        contentDescription = "",
                                        tint = Color.LightGray
                                    )
                                    Text(text = it.get(index).name, color = Color.DarkGray, fontWeight = FontWeight.Thin)
                                    Spacer(modifier = Modifier.width(20.dp))
                                    if (!fromDataObservable.value.isNullOrEmpty()) {
                                        Text(
                                            text = (it.get(index).rate * fromDataObservable.value.toDouble()).toString(),
                                            color = Color.DarkGray,
                                            fontWeight = FontWeight.Normal
                                        )
                                    }
                                }
                            }

                        }

                    }
                }


            }
        }
    }

    CurrencySelectionDialog(
        fromSpinnerVisibility,
        state,
        fromSpinnerData,
        toSpinnerData,
        isBaseCurrency
    )
    CurrencySelectionDialog(
        toSpinnerVisibility,
        state,
        fromSpinnerData,
        toSpinnerData,
        isBaseCurrency
    )

//    Log.d("C_Data: ", Gson().toJson(currenciesMap))
}

@Composable
fun ConversionCard(
    state: ConversionViewModelState,
    conversionViewModel: ConversionViewModel,
    fromSpinnerData: MutableState<String>,
    toSpinnerData: MutableState<String>,
    fromDataObservable: MutableState<String>,
    toDataObservable: MutableState<String>
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        contentAlignment = Alignment.TopCenter, modifier = Modifier
            .width(screenWidth)
            .height(screenWidth)
            .padding(20.dp)
    ) {
        Card(
            modifier = Modifier
                .width(screenWidth)
                .height(screenWidth)
                .clip(RoundedCornerShape(26.dp)),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {


                Column {
                    Text(
                        text = stringResource(id = R.string.from),
                        color = Color.LightGray,
                        fontSize = 10.sp
                    )
                    HorizontalDivider(color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = fromSpinnerData.value, color = Color.DarkGray, fontSize = 10.sp)
                    OutlinedTextField(
                        modifier = Modifier.wrapContentHeight(),
                        textStyle = TextStyle.Default.copy(fontSize = 10.sp),
                        shape = RoundedCornerShape(20.dp),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.LightGray,
                            unfocusedBorderColor = Color.LightGray,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        placeholder = { Text(text = "0.00", fontSize = 10.sp) },
                        value = fromDataObservable.value ?: "",
                        onValueChange = { fromDataObservable.value = it })

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = stringResource(id = R.string.to),
                        color = Color.LightGray,
                        fontSize = 10.sp
                    )
                    HorizontalDivider(color = Color.LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = toSpinnerData.value, color = Color.DarkGray, fontSize = 10.sp)
                    OutlinedTextField(
                        modifier = Modifier.wrapContentHeight(),
                        maxLines = 1,
                        enabled = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = TextStyle.Default.copy(fontSize = 10.sp),
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color.LightGray,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        ),
                        placeholder = { Text(text = "0.00", fontSize = 10.sp) },
                        value = state.convertedRate ?: "",
                        onValueChange = { toDataObservable.value = it })


                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            conversionViewModel.getCurrencies(
                                base = fromSpinnerData.value.substring(
                                    fromSpinnerData.value.lastIndexOf('-') + 1
                                ).trim(),
                                baseData = fromDataObservable.value,
                                toCurrency = toSpinnerData.value
                            )
                        },
                        modifier = Modifier.align(alignment = Alignment.End),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text(
                            text = stringResource(id = R.string.convert),
                            color = Color.Black,
                            fontWeight = FontWeight.W300
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CurrenciesSelector(
    fromSpinner: MutableState<Boolean>,
    toSpinner: MutableState<Boolean>,
    fromSpinnerData: MutableState<String>,
    toSpinnerData: MutableState<String>,
    isBaseCurrency: MutableState<Boolean>
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp), horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp), modifier = Modifier
            .fillMaxWidth()
            .clickable {
                fromSpinner.value = !fromSpinner.value
                isBaseCurrency.value = true
            }
            .weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Row(
            ) {
                Text(
                    text = "${fromSpinnerData.value}", modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "Dropdown arrow",
                    modifier = Modifier.weight(0.5f)
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_sync_alt),
            contentDescription = "Exchange icon",
            modifier = Modifier.weight(0.5f), tint = Color.White
        )

        Card(elevation = CardDefaults.cardElevation(defaultElevation = 6.dp), modifier = Modifier
            .fillMaxWidth()
            .clickable {
                toSpinner.value = !toSpinner.value
                isBaseCurrency.value = false
            }
            .weight(1f), colors = CardDefaults.cardColors(containerColor = Color.White)) {

            Row(
            ) {
                Text(
                    text = "${toSpinnerData.value}", modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowDropDown,
                    contentDescription = "Dropdown arrow",
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    HomeContent(
        ConversionViewModel(object : ConversionRepository {
            override suspend fun getConversion(base: String): Either<NetworkError, Conversion> {
                return null!!
            }
        }),
        ConversionViewModelState(),
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf("INR") },
        remember { mutableStateOf("INR") },
        remember { mutableStateOf(true) },
        remember { mutableStateOf("0.00") },
        remember { mutableStateOf("0.00") }
    )
}