package com.example.upstoxapplication.ui.LoginScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.upstoxapplication.composetheme.LightBlue
import com.example.upstoxapplication.composetheme.Purple40
import com.example.upstoxapplication.interfaces.NavigationFunction

@Composable
fun LoginScreen(navigationInterface: NavigationFunction) {
    var inputValueUserName by remember { mutableStateOf(TextFieldValue()) }
    var inputValuePassword by remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        val gradientColors = listOf(LightBlue, Purple40,Cyan)

        Text(
            text = "Login",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = gradientColors
                )
            )
        )
        NameTextField(
            inputValueUserName, onValueChange = {inputValueUserName = it}
            )
        PasswordTextField(
            inputValuePassword , onValueChange = {inputValuePassword = it}
            )
        SubmitButton(inputValueUserName,inputValuePassword,navigationInterface)
    }
}
@Composable
fun NameTextField(inputValueUserName: TextFieldValue,onValueChange: (TextFieldValue) -> Unit) {
    val maxLength = 25

    OutlinedTextField (
        value = inputValueUserName,
        onValueChange = {
            if(it.text.length <= maxLength){
                onValueChange(it)
            }},
        label = { Text(text = "Enter Your Username") },
        singleLine = true
    )
}

@Composable
fun PasswordTextField(inputValuePassword: TextFieldValue,onValueChange: (TextFieldValue) -> Unit) {
    var passwordVisible by remember { mutableStateOf(false) }
    var isTextEmpty by remember { mutableStateOf(true) }
    val maxLength = 12
    OutlinedTextField (
        value = inputValuePassword,
        onValueChange = { if(it.text.length <= maxLength){
            onValueChange(it)
            isTextEmpty = it.text.isEmpty()
        }},
        label = { Text(text = "Enter Your Password") },
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        trailingIcon = {
            if(!isTextEmpty){
                val image = if(passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Hide password" else "Show password"
                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    Icon(imageVector  = image, description)
                }
            }
        }
    )
}

@Composable
fun SubmitButton(
    inputValueUserName: TextFieldValue,
    inputValuePassword: TextFieldValue,
    navigationInterface: NavigationFunction,
) {
    val context = LocalContext.current
    ElevatedButton(
        modifier = Modifier.padding(all = 5.dp),
        onClick = {
            if(checkForUserNameAndPassword(inputValueUserName.text, inputValuePassword.text)){
                showToastMessage(context,"Login SuccessFully :) ")
//                navController.navigate("cryptocoinsFragment")
                navigationInterface.navigateToCryptoFragment()
            }else{
                showToastMessage(context,"Invalid Username or Password :( ")
            }
        }) {
        Text(text = "Submit")
    }
}

fun checkForUserNameAndPassword(userName: String , password: String) : Boolean{
    return (userName == "crypto" && password == "naman@12345")
}

fun showToastMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

