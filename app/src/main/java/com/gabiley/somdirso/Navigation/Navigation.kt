package com.gabiley.dirso.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gabiley.somdirso.DataStore.StoreAccountNum
import com.gabiley.somdirso.Screen
import com.gabiley.somdirso.ScreenA
import com.gabiley.somdirso.ViewModelClass.MyViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav(myViewModel: MyViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.MainScreen.route
    ) {

        composable(route = Screen.MainScreen.route) {
            ScreenA(navController, myViewModel)
        }

    }

}

