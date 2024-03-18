package fr.thomasbernard03.tosnavigationjetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import fr.thomasbernard03.composents.buttons.PrimaryButton
import fr.thomasbernard03.tosnavigationjetpackcompose.ui.theme.TosNavigationJetpackComposeTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("RestrictedApi")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TosNavigationJetpackComposeTheme {
                Scaffold {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        val navController = rememberNavController()
                        var backStack by remember { mutableStateOf("") }

                        LaunchedEffect(Unit) {
                            // Création d'un écouteur pour les changements de destination
                            val listener = NavController.OnDestinationChangedListener { controller, _, _ ->
                                backStack = controller.currentBackStack.value
                                    .mapNotNull { it.destination.route } // Assurez-vous de filtrer les routes nulles
                                    .joinToString("/")
                            }

                            navController.addOnDestinationChangedListener(listener)
                        }


                        val routes = listOf("blue", "green", "red")
                        var selectedRoute by remember { mutableStateOf(routes.first()) }
                        var launchSingleTop by remember { mutableStateOf(false) }
                        var popupTo by remember { mutableStateOf(false) }
                        var popupToRoute by remember { mutableStateOf(routes.first()) }
                        var popupToInclusive by remember { mutableStateOf(false) }


                        Column {
                            Column(
                                modifier = Modifier.padding(12.dp)
                            ) {
                                SingleChoiceSegmentedButtonRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    routes.forEachIndexed { index, route ->
                                        SegmentedButton(
                                            colors = SegmentedButtonDefaults.colors(
                                                activeContainerColor = MaterialTheme.colorScheme.primary,
                                                activeContentColor = Color.White
                                            ),
                                            selected = route == selectedRoute,
                                            onClick = { selectedRoute = route },
                                            shape = SegmentedButtonDefaults.itemShape(index = index, count = routes.size),
                                        ) {
                                            Text(text = route)
                                        }
                                    }
                                }

                                Button(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color.Black
                                    ),
                                    onClick = { launchSingleTop = !launchSingleTop}
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "Launch Single Top")
                                        Switch(
                                            checked = launchSingleTop,
                                            onCheckedChange = {
                                                launchSingleTop = it
                                            })
                                    }
                                }

                                Button(
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent,
                                        contentColor = Color.Black
                                    ),
                                    onClick = { popupTo = !popupTo}
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "Popup to")
                                        Switch(
                                            checked = popupTo,
                                            onCheckedChange = {
                                                popupTo = it
                                            })
                                    }
                                }

                                AnimatedVisibility(visible = popupTo) {
                                    Column {
                                        SingleChoiceSegmentedButtonRow(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            routes.forEachIndexed { index, route ->
                                                SegmentedButton(
                                                    colors = SegmentedButtonDefaults.colors(
                                                        activeContainerColor = MaterialTheme.colorScheme.primary,
                                                        activeContentColor = Color.White
                                                    ),
                                                    selected = route == popupToRoute,
                                                    onClick = { popupToRoute = route },
                                                    shape = SegmentedButtonDefaults.itemShape(index = index, count = routes.size),
                                                ) {
                                                    Text(text = route)
                                                }
                                            }
                                        }

                                        Button(
                                            shape = RoundedCornerShape(8.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.Transparent,
                                                contentColor = Color.Black
                                            ),
                                            onClick = { popupToInclusive = !popupToInclusive}
                                        ) {
                                            Row(
                                                modifier = Modifier.weight(1f),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(text = "Inclusive")
                                                Switch(
                                                    checked = popupToInclusive,
                                                    onCheckedChange = {
                                                        popupToInclusive = it
                                                    })
                                            }
                                        }
                                    }
                                }

                                PrimaryButton(
                                    text = "Navigate",
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    navController.navigate(
                                        route = selectedRoute,
                                        builder = {
                                            this.launchSingleTop = launchSingleTop
                                            if (popupTo) {
                                                popUpTo(popupToRoute) {
                                                    this.inclusive = popupToInclusive
                                                }
                                            }
                                        }
                                    )
                                }
                            }



                            Text(
                                text = backStack,
                                color = Color.Black
                            )


                            NavHost(navController = navController, startDestination = "blue"){
                                composable("blue"){
                                    BlueScreen()
                                }
                                composable("green"){
                                    GreenScreen()
                                }
                                composable("red"){
                                    RedScreen()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BlueScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    )
}

@Composable
fun GreenScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
    )
}

@Composable
fun RedScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    )
}

