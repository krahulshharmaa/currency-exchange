package com.currency.flip.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.flip.domain.model.Conversion
import com.currency.flip.domain.model.Conversions
import com.currency.flip.domain.repository.ConversionRepository
import com.currency.flip.ui.view.util.sendEvent
import com.currency.flip.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.HashMap
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@HiltViewModel
class ConversionViewModel @Inject constructor(
    private val conversionRepository: ConversionRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ConversionViewModelState())
    val state = _state.asStateFlow()

    private var _currenciesMapObservable = MutableLiveData<HashMap<String, String>>()
    var currenciesMapObservable: LiveData<HashMap<String, String>> = _currenciesMapObservable

    private var currenciesMap: HashMap<String, String> = hashMapOf<String, String>()

    private var _convertedData = MutableLiveData<String>()
    var convertedData: LiveData<String> = _convertedData


    init {
        initCurrenciesMap()
    }


    fun getCurrencies(base: String, baseData: String, toCurrency: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            conversionRepository.getConversion(base)
                .onRight { conversion ->
                    var conversList = arrayListOf<Conversions>()
                    var convertedRate = ""
                   try{
                       conversion.conversionRates?.let {
                           var currencyCode =
                               toCurrency.substring(toCurrency.lastIndexOf('-') + 1).trim()
                           for (prop in Conversion.ConversionRates::class.memberProperties) {
                               var conversionsItem = Conversions()

                               if (prop.name.equals(currencyCode, ignoreCase = true)) {
                                   convertedRate =
                                       (prop.getter.call(conversion.conversionRates).toString()
                                           .toDouble() * baseData.toDouble()).toString()
                               }

                               conversionsItem.name = prop.name
                               conversionsItem.rate =
                                   (prop.getter.call(conversion.conversionRates).toString()
                                       .toDouble() * baseData.toDouble())

                               conversList.add(conversionsItem)
                           }
                       }
                   }catch (e:Exception){}
                    _state.update {
                        it.copy(convertedRate = convertedRate, conversions = conversList)
                    }
                }.onLeft { error ->
                    _state.update {
                        it.copy(
                            error = error.error.message
                        )
                    }
                    sendEvent(Event.Toast(error.error.message))
                }

            _state.update { it.copy(isLoading = false) }
        }
    }


    fun getNestedVariableValue(
        outerInstance: Any,
        outerClassName: String,
        innerClassName: String,
        variableName: String
    ): Any? {
        val outerClass = outerInstance::class
        val innerClassProperty = outerClass.memberProperties.find { it.name == innerClassName }
        val innerInstance = innerClassProperty?.getter?.call(outerInstance)
        val innerClass = innerInstance?.let { it::class }
        val variableProperty = innerClass?.memberProperties?.find { it.name == variableName }
        return variableProperty?.getter?.call(innerInstance)
    }

    private fun initCurrenciesMap() {
        currenciesMap.put("Zimbabwean dollar", "ZWL")
        currenciesMap.put("United Arab Emirates dirham", "AED")
        currenciesMap.put("Afghan afghani", "AFN")
        currenciesMap.put("Albanian lek", "ALL")
        currenciesMap.put("Armenian dram", "AMD")
        currenciesMap.put("Netherlands Antillean guilder", "ANG")
        currenciesMap.put("Angolan kwanza", "AOA")
        currenciesMap.put("Argentine peso", "ARS")
        currenciesMap.put("Australian dollar", "AUD")
        currenciesMap.put("Aruban florin", "AWG")
        currenciesMap.put("Azerbaijani manat", "AZN")
        currenciesMap.put("Bosnia and Herzegovina convertible mark", "BAM")
        currenciesMap.put("Barbadian dollar", "BBD")
        currenciesMap.put("Bangladeshi taka", "BDT")
        currenciesMap.put("Bulgarian lev", "BGN")
        currenciesMap.put("Bahraini dinar", "BHD")
        currenciesMap.put("Burundian franc", "BIF")
        currenciesMap.put("Bermudian dollar", "BMD")
        currenciesMap.put("Brunei dollar", "BND")
        currenciesMap.put("Bolivian boliviano", "BOB")
        currenciesMap.put("Brazilian real", "BRL")
        currenciesMap.put("Bahamian dollar", "BSD")
        currenciesMap.put("Bhutanese ngultrum", "BTN")
        currenciesMap.put("Botswana pula", "BWP")
        currenciesMap.put("Belarusian ruble", "BYN")
        currenciesMap.put("Belize dollar", "BZD")
        currenciesMap.put("Canadian dollar", "CAD")
        currenciesMap.put("Congolese franc", "CDF")
        currenciesMap.put("Swiss franc", "CHF")
        currenciesMap.put("Chilean peso", "CLP")
        currenciesMap.put("Renminbi", "CNY")
        currenciesMap.put("Colombian peso", "COP")
        currenciesMap.put("Costa Rican colón", "CRC")
        currenciesMap.put("Cuban peso", "CUP")
        currenciesMap.put("Cape Verdean escudo", "CVE")
        currenciesMap.put("Czech koruna", "CZK")
        currenciesMap.put("Djiboutian franc", "DJF")
        currenciesMap.put("Danish krone", "DKK")
        currenciesMap.put("Dominican peso", "DOP")
        currenciesMap.put("Algerian dinar", "DZD")
        currenciesMap.put("Egyptian pound", "EGP")
        currenciesMap.put("Eritrean nakfa", "ERN")
        currenciesMap.put("Ethiopian birr", "ETB")
        currenciesMap.put("Euro", "EUR")
        currenciesMap.put("Fijian dollar", "FJD")
        currenciesMap.put("Falkland Islands pound", "FKP")
        currenciesMap.put("Sterling", "GBP")
        currenciesMap.put("Georgian lari", "GEL")
        currenciesMap.put("Ghanaian cedi", "GHS")
        currenciesMap.put("Gibraltar pound", "GIP")
        currenciesMap.put("Gambian dalasi", "GMD")
        currenciesMap.put("Guinean franc", "GNF")
        currenciesMap.put("Guatemalan quetzal", "GTQ")
        currenciesMap.put("Guyanese dollar", "GYD")
        currenciesMap.put("Hong Kong dollar", "HKD")
        currenciesMap.put("Honduran lempira", "HNL")
        currenciesMap.put("Haitian gourde", "HTG")
        currenciesMap.put("Hungarian forint", "HUF")
        currenciesMap.put("Indonesian rupiah", "IDR")
        currenciesMap.put("Israeli new shekel", "ILS")
        currenciesMap.put("Indian rupee", "INR")
        currenciesMap.put("Iraqi dinar", "IQD")
        currenciesMap.put("Iranian rial", "IRR")
        currenciesMap.put("Icelandic króna", "ISK")
        currenciesMap.put("Jamaican dollar", "JMD")
        currenciesMap.put("Jordanian dinar", "JOD")
        currenciesMap.put("Japanese yen", "JPY")
        currenciesMap.put("Kenyan shilling", "KES")
        currenciesMap.put("Kyrgyz som", "KGS")
        currenciesMap.put("Cambodian riel", "KHR")
        currenciesMap.put("Comorian franc", "KMF")
        currenciesMap.put("North Korean won", "KPW")
        currenciesMap.put("South Korean won", "KRW")
        currenciesMap.put("Kuwaiti dinar", "KWD")
        currenciesMap.put("Cayman Islands dollar", "KYD")
        currenciesMap.put("Kazakhstani tenge", "KZT")
        currenciesMap.put("Lao kip", "LAK")
        currenciesMap.put("Lebanese pound", "LBP")
        currenciesMap.put("Sri Lankan rupee", "LKR")
        currenciesMap.put("Liberian dollar", "LRD")
        currenciesMap.put("Lesotho loti", "LSL")
        currenciesMap.put("Libyan dinar", "LYD")
        currenciesMap.put("Moroccan dirham", "MAD")
        currenciesMap.put("Moldovan leu", "MDL")
        currenciesMap.put("Malagasy ariary", "MGA")
        currenciesMap.put("Macedonian denar", "MKD")
        currenciesMap.put("Burmese kyat", "MMK")
        currenciesMap.put("Mongolian tögrög", "MNT")
        currenciesMap.put("Macanese pataca", "MOP")
        currenciesMap.put("Mauritanian ouguiya", "MRU")
        currenciesMap.put("Mauritian rupee", "MUR")
        currenciesMap.put("Maldivian rufiyaa", "MVR")
        currenciesMap.put("Malawian kwacha", "MWK")
        currenciesMap.put("Mexican peso", "MXN")
        currenciesMap.put("Malaysian ringgit", "MYR")
        currenciesMap.put("Mozambican metical", "MZN")
        currenciesMap.put("Namibian dollar", "NAD")
        currenciesMap.put("Nigerian naira", "NGN")
        currenciesMap.put("Nicaraguan córdoba", "NIO")
        currenciesMap.put("Norwegian krone", "NOK")
        currenciesMap.put("Nepalese rupee", "NPR")
        currenciesMap.put("New Zealand dollar", "NZD")
        currenciesMap.put("Omani rial", "OMR")
        currenciesMap.put("Panamanian balboa", "PAB")
        currenciesMap.put("Peruvian sol", "PEN")
        currenciesMap.put("Papua New Guinean kina", "PGK")
        currenciesMap.put("Philippine peso", "PHP")
        currenciesMap.put("Pakistani rupee", "PKR")
        currenciesMap.put("Polish złoty", "PLN")
        currenciesMap.put("Paraguayan guaraní", "PYG")
        currenciesMap.put("Qatari riyal", "QAR")
        currenciesMap.put("Romanian leu", "RON")
        currenciesMap.put("Serbian dinar", "RSD")
        currenciesMap.put("Russian ruble", "RUB")
        currenciesMap.put("Rwandan franc", "RWF")
        currenciesMap.put("Saudi riyal", "SAR")
        currenciesMap.put("Solomon Islands dollar", "SBD")
        currenciesMap.put("Seychellois rupee", "SCR")
        currenciesMap.put("Swedish krona", "SEK")
        currenciesMap.put("Singapore dollar", "SGD")
        currenciesMap.put("Saint Helena pound", "SHP")
        currenciesMap.put("Saint Helena pound", "SHP")
        currenciesMap.put("Sierra Leonean leone", "SLE")
        currenciesMap.put("Somali shilling", "SOS")
        currenciesMap.put("Surinamese dollar", "SRD")
        currenciesMap.put("South Sudanese pound", "SSP")
        currenciesMap.put("São Tomé and Príncipe dobra", "STN")
        currenciesMap.put("Syrian pound", "SYP")
        currenciesMap.put("Swazi lilangeni", "SZL")
        currenciesMap.put("Thai baht", "THB")
        currenciesMap.put("Tajikistani somoni", "TJS")
        currenciesMap.put("Turkmenistani manat", "TMT")
        currenciesMap.put("Tunisian dinar", "TND")
        currenciesMap.put("Tongan paʻanga[K]", "TOP")
        currenciesMap.put("Turkish lira", "TRY")
        currenciesMap.put("Trinidad and Tobago dollar", "TTD")
        currenciesMap.put("New Taiwan dollar", "TWD")
        currenciesMap.put("Tanzanian shilling", "TZS")
        currenciesMap.put("Ukrainian hryvnia", "UAH")
        currenciesMap.put("Ugandan shilling", "UGX")
        currenciesMap.put("United States dollar", "USD")
        currenciesMap.put("Uruguayan peso", "UYU")
        currenciesMap.put("Uzbekistani sum", "UZS")
        currenciesMap.put("Venezuelan digital bolívar", "VED")
        currenciesMap.put("Venezuelan sovereign bolívar", "VES")
        currenciesMap.put("Vietnamese đồng", "VND")
        currenciesMap.put("Vanuatu vatu", "VUV")
        currenciesMap.put("Samoan tālā", "WST")
        currenciesMap.put("Central African CFA franc", "XAF")
        currenciesMap.put("Eastern Caribbean dollar", "XCD")
        currenciesMap.put("West African CFA franc", "XOF")
        currenciesMap.put("CFP franc", "XPF")
        currenciesMap.put("Yemeni rial", "YER")
        currenciesMap.put("South African rand", "ZAR")
        currenciesMap.put("Zambian kwacha", "ZMW")
        currenciesMap.put("Zimbabwe gold", "ZWG")

        _currenciesMapObservable.value = currenciesMap

    }
}