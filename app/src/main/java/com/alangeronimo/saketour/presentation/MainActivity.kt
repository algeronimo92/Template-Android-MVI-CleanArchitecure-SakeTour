package com.alangeronimo.saketour.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.alangeronimo.designsystem.ui.theme.SakeTourTheme
import com.alangeronimo.saketour.presentation.screens.NavHostGenerator
import com.alangeronimo.saketour.presentation.state.SakeUiEvent
import com.alangeronimo.saketour.presentation.viewmodel.SakeShopViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<SakeShopViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(Unit) {
                viewModel.onEvent(SakeUiEvent.LoadShops)
            }

            SakeTourTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHostGenerator(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
