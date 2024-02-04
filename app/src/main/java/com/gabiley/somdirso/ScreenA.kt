package com.gabiley.somdirso

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.gabiley.somdirso.DataStore.StoreAccountNum
import com.gabiley.somdirso.ViewModelClass.MyViewModel
import kotlinx.coroutines.launch


object Gender {
    const val SLSH = "SLSH"
    const val USD  = "USD"
}


@SuppressLint("ServiceCast", "HardwareIds")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScreenA(
    navController: NavController,
    myViewModel: MyViewModel
) {
    val maxLength          = 10
    val context            = LocalContext.current
    //val REQUEST_PHONE_CALL = 1
    val scope              = rememberCoroutineScope()
    val AccNum             = StoreAccountNum(context)
    val SavedUsername      = AccNum.getUsername.collectAsState(initial = "")


    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted
        }
        else {
            // Permission Denied
        }
    }

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.MainScreen.route){
                                popUpTo(Screen.MainScreen.route) {
                                    inclusive = true
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Menu"
                        )
                    }
                },
            )

        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(20.dp, 0.dp)
            //.background(Color.Green)
        ) {

            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Dirso App", fontSize = 33.sp)

            Spacer(modifier = Modifier.height(30.dp))


            ///////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    //.background(Color.LightGray)
                    .fillMaxWidth()
            ) {

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 30.sp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = myViewModel.mobile,
                    onValueChange = {
                        if(it.length <= maxLength) {
                            myViewModel.mobile = it
                        }
                    },
                    label = { Text("Enter Number") },
                    maxLines = 1,
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 30.sp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    value = myViewModel.money,
                    onValueChange = { myViewModel.money = it },
                    label = { Text("Enter Money") },
                    maxLines = 1,
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            ///////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////

            val selectedMoney = remember { mutableStateOf(Gender.SLSH) }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    //.background(Color.Cyan)
                    .fillMaxWidth()
            ) {

                RadioButton(
                    selected = selectedMoney.value == Gender.SLSH,
                    onClick = {
                        selectedMoney.value = Gender.SLSH
                    },
                    /*colors = RadioButtonDefaults.colors(Color.Green)*/
                )
                //Spacer(modifier = Modifier.size(16.dp))
                Text(
                    Gender.SLSH,
                    fontSize = 25.sp
                )
                //Spacer(modifier = Modifier.size(16.dp))

                RadioButton(
                    selected = selectedMoney.value == Gender.USD,
                    onClick = {
                        selectedMoney.value = Gender.USD
                    },
                    /*colors = RadioButtonDefaults.colors(Color.Green)*/
                )
                //Spacer(modifier = Modifier.size(16.dp))
                Text(
                    Gender.USD,
                    fontSize = 25.sp
                )

            }

            Spacer(modifier = Modifier.height(20.dp))

            ///////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////
            ///////////////////////////////////////////////////////////

            val context = LocalContext.current

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    /////////////////////////////////////////////////
                    /////////////////////////////////////////////////

                    val person = StoreAccountNum(context)
                    val queue = Volley.newRequestQueue(context)
                    val sr: StringRequest = object : StringRequest(
                        Method.POST,
                        "https://adeega.xisaabso.online/Api/Emoney/Username.php",
                        Response.Listener { response ->
                            //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()

                            if(response.toString() == "Expired") {
                                Toast.makeText(context, "Wuu dhacay Account-ku", Toast.LENGTH_LONG).show()
                                //navController.navigate(Screen.VerificationScreen.route)

                                scope.launch {
                                    person.saveVerficationCode("false")
                                }

                            }
                            else if(response.toString() == "Not Expired") {
                                //Toast.makeText(context, "Account-ku wuu shaqeenaya", Toast.LENGTH_LONG).show()
                            }
                        },
                        Response.ErrorListener {
                            //your error
                        }) {
                        override fun getParams(): Map<String, String> {
                            val params: MutableMap<String, String> = HashMap()
                            params["Username"] = SavedUsername.value.toString()
                            return params
                        }

                        //@Throws(AuthFailureError::class)
                        //override fun getHeaders(): Map<String, String> {
                        //val params: MutableMap<String, String> = HashMap()
                        //params["Content-Type"] = "application/x-www-form-urlencoded"
                        //return params
                        //}

                    }
                    queue.add(sr)

                    //Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()

                    when (PackageManager.PERMISSION_GRANTED) {
                        //Check permission
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) ->
                        {
                            // You can use the API that requires the permission.


                            if(myViewModel.mobile.isNotEmpty()) {

                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////    5 DIGITS   /////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////

                                if(myViewModel.mobile.length == 5) {
                                    val first            = myViewModel.mobile.substring(0,1).toInt()
                                    val first_two_digits = myViewModel.mobile.substring(0,2).toInt()

                                    if(first == 0) {
                                        if(selectedMoney.value == "SLSH") {
                                            val total = myViewModel.mobile.toInt()
                                            val dialedNum = "*775*00$total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val total = myViewModel.mobile.toInt()

                                            val dialedNum = "*115*00$total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    else if(first_two_digits > 20 && first_two_digits <= 99) {

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*773*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*113*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }


                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////    6 DIGITS   /////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////

                                else if(myViewModel.mobile.length == 6) {
                                    val first = myViewModel.mobile.substring(0,1).toInt()

                                    if(first == 2) {
                                        if(selectedMoney.value == "SLSH") {

                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*224*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }
                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*884*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }
                                    }

                                    else if(first == 3) {
                                        if(selectedMoney.value == "SLSH") {

                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*377*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }
                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*277*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }
                                    }

                                    else if(first == 4) {
                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*223*$Total*${myViewModel.money}%23"
                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*883*$Total*${myViewModel.money}%23"
                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    else if(first == 5) {
                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*223*$Total*${myViewModel.money}%23"
                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*883*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    }
                                }


                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////    7 DIGITS   /////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////

                                else if(myViewModel.mobile.length == 7) {
                                    val first     = myViewModel.mobile.substring(0,1).toInt()
                                    val first_two = myViewModel.mobile.substring(0,2).toInt()

                                    // NUMBER-ADA DOWLADA LOO FURO
                                    if(first_two == 18) {
                                        if(selectedMoney.value == "SLSH") {

                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*688*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*688*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    }

                                    else if(first == 3) {
                                        if(selectedMoney.value == "SLSH") {

                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    else if(first == 4) {
                                        if(selectedMoney.value == "SLSH") {

                                            val total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*$total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*$total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    else if(first == 6) {
                                        if(selectedMoney.value == "SLSH") {

                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    else if(first == 7) {
                                        if(selectedMoney.value == "SLSH") {

                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }

                                    else if(first == 9) {
                                        if(selectedMoney.value == "SLSH") {

                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }


                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////   9 DIGITS    /////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////

                                else if(myViewModel.mobile.length == 9) {
                                    //var first_two_digits = myViewModel.mobile.substring(0,2).toInt()
                                    val first_two_digits_except_zero = myViewModel.mobile.substring(0,2).toInt()

                                    // SOLTELCO
                                    if(first_two_digits_except_zero == 67) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            //val dialedNum = "*770*0$Total*${myViewModel.money}%23"
                                            val dialedNum = "*220*$Total*${myViewModel.money}%23"
                                            val callIntent = Intent(Intent.ACTION_CALL)

                                            //val sim1 = callIntent.putExtra("com.android.phone.extra.slot", 1)
                                            //callIntent.putExtra("com.android.phone.extra.slot", 0)

                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*661*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    } // END SOLTELCO

                                    // TELESOM
                                    if(first_two_digits_except_zero == 63) {
                                        Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                            val dialedNum = "*220*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    } // END TELESOM

                                    // SAHAL
                                    else if(first_two_digits_except_zero == 90) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }
                                    } // END SAHAL

                                    // SARRIF (SOMTEL)
                                    else if(first_two_digits_except_zero == 65) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            //val dialedNum = "*770*0$Total*${myViewModel.money}%23"
                                            val dialedNum = "*109*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*119*$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    } // END SOMTEL

                                    // HORMUUD
                                    else if(first_two_digits_except_zero == 61) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }

                                    } // END HORMUUD
                                }



                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////   10 DIGITS   /////////////////////////////
                                ////////////////////////               /////////////////////////////
                                ////////////////////////////////////////////////////////////////////
                                ////////////////////////////////////////////////////////////////////

                                if(myViewModel.mobile.length == 10) {
                                    //var zaadFieldINT     = moneyInput.text.toString().toInt()
                                    //var cents            = moneyInput2.text.toString().toInt()
                                    //var phoneNumber      = numberInput.text.toString().toInt()
                                    //var first_digit      = numberInput.text.substring(0,1).toInt()

                                    //var first_two_digits = myViewModel.mobile.substring(0,2).toInt()
                                    val first_two_digits_except_zero = myViewModel.mobile.substring(1,3).toInt()

                                    // SOLTELCO
                                    if(first_two_digits_except_zero == 67) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            //val dialedNum = "*770*0$Total*${myViewModel.money}%23"
                                            val dialedNum = "*220*0$Total*${myViewModel.money}%23"
                                            val callIntent = Intent(Intent.ACTION_CALL)

                                            //val sim1 = callIntent.putExtra("com.android.phone.extra.slot", 1)
                                            //callIntent.putExtra("com.android.phone.extra.slot", 0)

                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*661*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    } // END TELESOM

                                    // TELESOM
                                    if(first_two_digits_except_zero == 63) {
                                        Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                            val dialedNum = "*220*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:$dialedNum")
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    } // END TELESOM

                                    // SAHAL
                                    else if(first_two_digits_except_zero == 90) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)
                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }

                                    } // END SAHAL

                                    // SOMTEL
                                    else if(first_two_digits_except_zero == 65) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*770*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*110*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()
                                        }

                                    } // END SOMTEL

                                    // HORMUUD
                                    else if(first_two_digits_except_zero == 61) {
                                        //Toast.makeText(context, "Telesom", Toast.LENGTH_SHORT).show()

                                        if(selectedMoney.value == "SLSH") {
                                            val Total = myViewModel.mobile.toInt()
                                            val dialedNum = "*220*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }

                                        else if(selectedMoney.value == "USD") {
                                            val Total = myViewModel.mobile.toInt()

                                            val dialedNum = "*880*0$Total*${myViewModel.money}%23"

                                            val callIntent = Intent(Intent.ACTION_CALL)
                                            callIntent.data = Uri.parse("tel:" + dialedNum)
                                            ContextCompat.startActivity(context, callIntent, null)

                                            //Toast.makeText(context, Total.toString(), Toast.LENGTH_SHORT).show()

                                        }

                                    } // END HORMUUD
                                }

                            }

                            else {
                                Toast.makeText(context, "Empty Field...", Toast.LENGTH_SHORT).show()
                            }
                        }

                        else -> {
                            // Asking for permission
                            launcher.launch(Manifest.permission.CALL_PHONE)
                        }
                    }

                }) {
                Text("Send", fontSize = 23.sp)
            }
        }
    }



}

