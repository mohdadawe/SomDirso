package com.gabiley.somdirso.ViewModelClass

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {

    var mobile by mutableStateOf("")
    var money by mutableStateOf("")

/*
    fun onMobileChanged(MobileFun: String){
        mobile = MobileFun
    }

    fun onMoneyChanged(MoneyFun: String){
        money= MoneyFun
    }
*/

}