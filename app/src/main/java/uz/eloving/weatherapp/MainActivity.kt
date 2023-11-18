package uz.eloving.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import uz.eloving.core_ui.NavigationItem
import uz.eloving.weatherapp.ui.Navigation
import uz.eloving.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint
import uz.eloving.core_ui.PrefManager


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        var showDialog by remember { mutableStateOf(false) }
        val navController = rememberNavController()
        val topBarTitle = remember {
            mutableStateOf(NavigationItem.CurrentWeather.title)
        }
        val context = LocalContext.current

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(topBarTitle.value) },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(navController = navController)
                    Dialog(showDialog, onDismiss = { showDialog = false }, onSave = {
                        PrefManager.setCurrentLocation(
                            context,
                            it
                        )
                        showDialog = false
                    })
                }
            },
            bottomBar = {
                BottomNavigationBar(navController) {
                    topBarTitle.value = it
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        showDialog = true
                    },
                    content = {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                    }
                )
            }
        )
    }

    @Composable
    fun Dialog(showDialog: Boolean, onDismiss: () -> Unit, onSave: (String) -> Unit) {
        if (showDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray.copy(.4f))
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(.7f)
                        .height(200.dp)
                ) {
                    Box(
                        modifier = Modifier.matchParentSize()
                    ) {
                        var input by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = input,
                            onValueChange = {
                                input = it
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onSave(input)
                                    onDismiss()
                                }
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.Black // Change text color here
                            ),
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .align(Alignment.BottomCenter),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                onClick = {
                                    onDismiss()
                                },
                                modifier = Modifier.padding(end = 8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black // Change text color here
                                )
                            ) {
                                Text("Cancel")
                            }

                            Button(
                                onClick = {
                                    onSave(input)
                                    onDismiss()
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.Black // Change text color here
                                ),
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Save")
                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun TopBar(title: String) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = MaterialTheme.colors.onBackground
                )
            },
            contentColor = MaterialTheme.colors.onBackground
        )
    }

    @Composable
    fun BottomNavigationBar(navController: NavController, onValueChange: (String) -> Unit) {
        val items = listOf(
            NavigationItem.CurrentWeather,
            NavigationItem.Forecasting
        )

        BottomNavigation(
            contentColor = MaterialTheme.colors.onBackground
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                BottomNavigationItem(
                    selectedContentColor = colorResource(id = R.color.teal_200),
                    label = { Text(text = item.title) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            modifier = Modifier
                                .height(20.dp)
                                .width(20.dp),
                            contentDescription = item.title
                        )
                    },
                    alwaysShowLabel = true,
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }

                            launchSingleTop = true
                            restoreState = true
                            onValueChange(item.title)
                        }
                    }
                )
            }
        }
    }
}

