package com.gabiley.somdirso.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreAccountNum(private val context: Context) {
    // to make sure there's only one instance
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userEmail")
        val ACCOUNT_NUM_KEY  = stringPreferencesKey("accountNum")
        val USERNAME_KEY     = stringPreferencesKey("username")
        val ACCOUNT_TYPE_KEY = stringPreferencesKey("accountType")
        val PHONE_NUM_KEY    = stringPreferencesKey("Session_PhoneNum")
        val EXPIRY_ACCOUNT   = stringPreferencesKey("expiry_account")
        val VERIFICATION_KEY = stringPreferencesKey(("verification_code"))
        val MAX_SENDING_MONEY_USD = stringPreferencesKey("max_sending_money_usd")


        //Added at 16 Dec 2023
        val CUSTOMER_ID    = stringPreferencesKey("customer_ID")
        val Ka_Jar_Button  = stringPreferencesKey("Ka_Jar_Button")
        val Ku_Shub_Button = stringPreferencesKey("Ku_Shub_Button")
        val Sarrif_Button  = stringPreferencesKey("Sarrif_Button")
        val MoneyValue     = stringPreferencesKey("MoneyValue")

        val SUBSCRIPTION_FEE = stringPreferencesKey("SubscriptionFee")

        val MERCHANT_SECOND_PASSWORD = stringPreferencesKey("MERCHANT_SECOND_PASSWORD")


    }

    //get the saved Merchant_Second_Password
    val getMerchant_Second_Password: Flow<Any?> = context.dataStore.data
        .map { preferences ->
            preferences[MERCHANT_SECOND_PASSWORD] ?: ""
        }


    //get the saved CustomerID
    val getCustomerID: Flow<Any?> = context.dataStore.data
        .map { preferences ->
            preferences[CUSTOMER_ID] ?: ""
        }


    //get the saved Ka_Jar_Button
    val getKaJarButton: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[Ka_Jar_Button] ?: ""
        }

    //get the saved Ku_Shub_Button
    val getKuShubButton: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[Ku_Shub_Button] ?: ""
        }


    //get the saved Sarrif_Button
    val getSarrifButton: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[Sarrif_Button] ?: ""
        }

    //get the saved MoneyValue
    val getMoneyValue: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[MoneyValue] ?: ""
        }

    //get the saved MaxSendingMoneyUSD
    val getMaxSendingMoneyUSD: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[MAX_SENDING_MONEY_USD] ?: ""
        }

    //save MaxSendingMoneyUSD into datastore
    suspend fun saveMaxSendingMoneyUSD(sendingMoneyUSD: String) {
        context.dataStore.edit { preferences ->
            preferences[MAX_SENDING_MONEY_USD] = sendingMoneyUSD
        }
    }



    //get the saved AccountNum
    val getAccountNum: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCOUNT_NUM_KEY] ?: ""
        }

    //save AccountNum into datastore
    suspend fun saveAccountNum(accountNum: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCOUNT_NUM_KEY] = accountNum
        }
    }

    //get the saved AccountNum
    val getUsername: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[USERNAME_KEY] ?: ""
        }

    //save AccountNum into datastore
    suspend fun saveUsername(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    //get the saved AccountNum
    val getAccountType: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[ACCOUNT_TYPE_KEY] ?: ""
        }

    //save AccountNum into datastore
    suspend fun saveAccountType(accountType: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCOUNT_TYPE_KEY] = accountType
        }
    }

    //get the saved PhoneNum
    val getPhoneNum: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PHONE_NUM_KEY] ?: ""
        }

    //save PhoneNum into datastore
    suspend fun savePhoneNum(phoneNum: String) {
        context.dataStore.edit { preferences ->
            preferences[PHONE_NUM_KEY] = phoneNum
        }
    }

    //get the saved ExpiryAccount
    val getExpiryAccount: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[EXPIRY_ACCOUNT] ?: ""
        }


    //get the saved SubscriptionFee
    val getSubscriptionFee: Flow<Any?> = context.dataStore.data
        .map { preferences ->
            preferences[SUBSCRIPTION_FEE] ?: ""
        }

    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////


    suspend fun saveMerchant_Second_Password(Value: String) {
        context.dataStore.edit { preferences ->
            preferences[MERCHANT_SECOND_PASSWORD] = Value
        }
    }

    suspend fun saveCustomerID(Value: String) {
        context.dataStore.edit { preferences ->
            preferences[CUSTOMER_ID] = Value
        }
    }

    suspend fun save_KaJar(Value: String) {
        context.dataStore.edit { preferences ->
            preferences[Ka_Jar_Button] = Value
        }
    }

    suspend fun save_KuShub(Value: String) {
        context.dataStore.edit { preferences ->
            preferences[Ku_Shub_Button] = Value
        }
    }

    suspend fun save_Sarrif(Value: String) {
        context.dataStore.edit { preferences ->
            preferences[Sarrif_Button] = Value
        }
    }

    suspend fun saveMoneyValue(Value: String) {
        context.dataStore.edit { preferences ->
            preferences[MoneyValue] = Value
        }
    }

    suspend fun saveExpiryAccount(expiryAccount: String) {
        context.dataStore.edit { preferences ->
            preferences[EXPIRY_ACCOUNT] = expiryAccount
        }
    }

    //get the saved VerficationCode
    val getVerficationCode: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[VERIFICATION_KEY] ?: ""
        }

    //save VerficationCode into datastore
    suspend fun saveVerficationCode(verficationCode: String) {
        context.dataStore.edit { preferences ->
            preferences[VERIFICATION_KEY] = verficationCode
        }
    }

    //save SubscriptionFee into datastore
    suspend fun saveSubscriptionFee(Value: String) {
        context.dataStore.edit { preferences ->
            preferences[SUBSCRIPTION_FEE] = Value
        }
    }


}

