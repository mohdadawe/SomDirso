package com.gabiley.somdirso


sealed class Screen(val route: String) {
   object Home: Screen(route = "Home")
   object LastVouchers_Screen: Screen(route = "Last_Vouchers_Screen")
   object BankScreen: Screen(route = "Bank_Screen")
   object LoginScreen: Screen(route = "Login_Screen")
   object SettingScreen: Screen(route = "Setting_Screen")
   object VerificationScreen: Screen(route = "Verification_Screen")
   object Signup: Screen(route = "Signup")
   object MainScreen: Screen(route = "MainScreen")
   object Customers: Screen(route = "Customers")

   object AddCustomer: Screen(route = "AddCustomer")
   object SubscriptionScreen: Screen(route = "SubscriptionScreen")

   object Settings: Screen(route = "Settings")
}

