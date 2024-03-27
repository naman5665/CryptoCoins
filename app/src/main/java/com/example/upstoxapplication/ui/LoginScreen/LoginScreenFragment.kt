package com.example.upstoxapplication.ui.LoginScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upstoxapplication.R
import com.example.upstoxapplication.interfaces.NavigationFunction

class LoginScreenFragment() : Fragment() , NavigationFunction{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LoginScreen(this@LoginScreenFragment)
            }
        }
    }

    override fun navigateToCryptoFragment() {
        findNavController().navigate(R.id.action_loginScreenFragment_to_cryptocoinsFragment)
    }
}