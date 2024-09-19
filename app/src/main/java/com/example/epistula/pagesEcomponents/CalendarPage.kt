package com.example.epistula.pagesEcomponents

import android.content.Context
import android.content.Intent
import android.provider.CalendarContract
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.epistula.R
import com.example.epistula.ui.theme.DarkGray
import com.example.epistula.ui.theme.LightGray
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPage(navController: NavController) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val majorColors = if (currentTheme == "dark") DarkGray else LightGray
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    val blackNwhite = if (currentTheme == "dark") Color.White else Color.Black
    val menu = if (currentTheme == "dark") R.drawable.icon_menu_d else R.drawable.icon_menu_l
    val avatar = if (currentTheme == "dark") R.drawable.icon_person_d else R.drawable.icon_person_l

    val placeholderPesquisa = "Pesquisar"
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var pesquisa by rememberSaveable { mutableStateOf("") }
    var eventTitle by rememberSaveable { mutableStateOf("")}
    var eventDescription by rememberSaveable{ mutableStateOf("")}
    var eventLocation by rememberSaveable{ mutableStateOf("")}
    var eventDate by rememberSaveable{ mutableStateOf("")}
    var eventStartTime by rememberSaveable{ mutableStateOf("")}
    var eventEndTime by rememberSaveable{ mutableStateOf("")}


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController)
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(majorColors)
            ) {
                Box(
                    modifier = Modifier
                        .background(textColors)
                        .padding(horizontal = 10.dp, vertical = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = menu),
                                contentDescription = "Menu",
                                tint = majorColors
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre o ícone e a TextField

                        TextField(
                            value = pesquisa,
                            onValueChange = { pesquisa = it },
                            placeholder = {
                                if (pesquisa.isEmpty()) {
                                    Text(placeholderPesquisa, color = majorColors)
                                }
                            },
                            modifier = Modifier
                                .weight(1f),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = textColors,
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(8.dp),
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre a TextField e o ícone

                        IconButton(onClick = { /* TODO: Menu click */ }) {
                            Icon(
                                painter = painterResource(id = avatar),
                                contentDescription = "Menu",
                                tint = majorColors
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = eventTitle,
                    onValueChange = {eventTitle = it},
                    label = { Text("Título do Evento", color = majorColors)},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textColors,
                        focusedIndicatorColor = majorColors
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = eventDescription,
                    onValueChange = {eventDescription = it},
                    label = { Text("Descrição do Evento", color = majorColors)},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textColors,
                        focusedIndicatorColor = Color(0xFF464646)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = eventLocation,
                    onValueChange = {eventLocation = it},
                    label = { Text("Local do Evento", color = majorColors)},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textColors,
                        focusedIndicatorColor = Color(0xFF464646)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = eventDate,
                    onValueChange = {eventDate = it},
                    label = { Text("Data do Evento", color = majorColors)},
                    placeholder = { Text(" dd/MM/yyyy")},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textColors,
                        focusedIndicatorColor = Color(0xFF464646)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = eventStartTime,
                    onValueChange = {eventStartTime = it},
                    label = { Text("Horário de Início do Evento", color = majorColors)},
                    placeholder = { Text(" HH:mm")},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textColors,
                        focusedIndicatorColor = Color(0xFF464646)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = eventEndTime,
                    onValueChange = {eventEndTime = it},
                    label = { Text("Horário de Término do Evento", color = majorColors)},
                    placeholder = { Text(" HH:mm")},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = textColors,
                        focusedIndicatorColor = Color(0xFF464646)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    shape = RoundedCornerShape(15),
                    colors = ButtonDefaults.buttonColors(textColors),
                    onClick = {
                    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                    try {
                        val startDateTime = formatter.parse("$eventDate $eventStartTime")
                        val endDateTime = formatter.parse("$eventDate $eventEndTime")
                        if(startDateTime != null && endDateTime != null) {
                            val intent = Intent(Intent.ACTION_INSERT).apply {
                                data = CalendarContract.Events.CONTENT_URI
                                putExtra(CalendarContract.Events.TITLE, eventTitle)
                                putExtra(CalendarContract.Events.DESCRIPTION, eventDescription)
                                putExtra(CalendarContract.Events.EVENT_LOCATION, eventLocation)
                                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDateTime?.time)
                                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endDateTime?.time)
                            }
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "Formato de data ou hora inválido. Por favor insira uma data e hora válidas", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Adicionar Evento no Calendário",
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontSize = 20.sp)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarScreen() {
    CalendarPage(navController = rememberNavController())
}
