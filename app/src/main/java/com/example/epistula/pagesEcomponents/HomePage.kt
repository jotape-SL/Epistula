package com.example.epistula.pagesEcomponents

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.epistula.R
import com.example.epistula.model.Email
import com.example.epistula.model.SendEmail
import com.example.epistula.service.ApiService
import com.example.epistula.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, apiService: ApiService) {
    var pesquisa by rememberSaveable { mutableStateOf("") }
    val placeholderPesquisa = "Pesquisar"
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var emails by remember { mutableStateOf<List<Email>>(emptyList())}
    var filteredEmails by remember { mutableStateOf(emails) }
    var filter by remember { mutableStateOf("Todos os emails") }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false)}

    LaunchedEffect(Unit){
        scope.launch{
            try{
                emails = apiService.getEmails()
                filteredEmails = emails
            } catch (e: Exception) {
                snackbarMessage = "Erro ao carregar os e-mails: ${e.message}"
                showSnackbar = true
            }
        }
    }

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }

    val majorColors = if (currentTheme == "dark") DarkGray else LightGray
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    val menu = if (currentTheme == "dark") R.drawable.icon_menu_d else R.drawable.icon_menu_l
    val avatar = if (currentTheme == "dark") R.drawable.icon_person_d else R.drawable.icon_person_l

    fun updateFilteredEmails() {
        filteredEmails = emails.filter {
            (filter == "Todos os emails" || it.status == filter) &&
                    (pesquisa.isBlank() || it.body.contains(pesquisa, ignoreCase = true) ||
                            it.sender.contains(pesquisa, ignoreCase = true) ||
                            it.title.contains(pesquisa, ignoreCase = true))
        }
    }

    LaunchedEffect(pesquisa) {
        updateFilteredEmails()
    }
    SideEffect {
        updateFilteredEmails()
    }

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

                // Filters Row
                CustomButtonsWithDropdown(filter) { selectedFilter ->
                    filter = selectedFilter
                    filteredEmails = emails.filter {
                        when (filter) {
                            "Todos os emails" -> true
                            "Lidos" -> it.status == "Lido"
                            "Não lidos" -> it.status == "Não lido"
                            "Spam" -> it.status == "Spam"
                            else -> true
                        }
                    }
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    // Email List
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(filteredEmails.size) { index ->
                            EmailItem(
                                email = filteredEmails[index],
                                onFavorite = {
                                    snackbarMessage =
                                        "Email ${filteredEmails[index].title} de ${filteredEmails[index].sender} favoritado"
                                    showSnackbar = true
                                }
                            )
                            Divider(color = Color(0xFF272727), thickness = 1.dp)
                        }
                    }
                    Button(
                        shape = RoundedCornerShape(15),
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        onClick = {showDialog = true},
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 48.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Text(text = "Enviar E-Mail",
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontSize = 20.sp)
                    }
                }
            }
        }
    )
    if(showDialog){
        addEmail(
            onDissmiss = { showDialog = false },
            onSend = {recipient, title, body ->
                scope.launch {
                    try {
                        val response = apiService.sendEmail(
                            SendEmail(recipient, title, body)
                        )
                        if(response.isSuccessful){
                            println("Email enviado com sucesso")
                        } else {
                            println("Falha ao enviar o Email: ${response.errorBody()?.toString()}")
                        }
                    } catch (e: Exception){
                        println("Erro: ${e.message}")
                    }
                }
                showDialog = false
            }
        )
    }
    if (showSnackbar) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.Transparent),
            contentAlignment = Alignment.BottomCenter
        ){
            Snackbar(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                action = {
                    Button(onClick = {
                        showSnackbar = false },
                        border = BorderStroke(0.dp, Color.Transparent),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ) {
                        Text("OK")
                    }
                },
                content = {
                    Text(snackbarMessage)
                }
            )
        }

    }
}

data class Email(
    val remetente: String,
    val title: String,
    val desc: String,
    val date: String,
    val time: String,
    val status: String
)

@Composable
fun EmailItem(email: Email, onFavorite: () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }

    val majorColors = if (currentTheme == "dark") DarkGray else LightGray
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    val eye = if (currentTheme == "dark") R.drawable.eye_d else R.drawable.eye_l
    val arrw = if (currentTheme == "dark") R.drawable.icon_arrow_d else R.drawable.icon_arrow_l

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onFavorite() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, textColors, shape = RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = eye),
                contentDescription = "Email Icon",
                modifier = Modifier.size(48.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(email.sender, fontWeight = FontWeight.Bold, color = Color.White)
            Text(email.title, fontWeight = FontWeight.Bold, color = Color.White)
            Text(
                email.body,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

//        Column(
//            horizontalAlignment = Alignment.End
//        ) {
//            Text(email.date, color = textColors, fontSize = 12.sp)
//            Text(email.time, color = textColors, fontSize = 12.sp)
//        }
    }
}

@Composable
fun addEmail(
    onDissmiss: () -> Unit,
    onSend: (String, String, String) -> Unit
){
    var recipient by remember { mutableStateOf("")}
    var title by remember { mutableStateOf("")}
    var body by remember { mutableStateOf("")}

    AlertDialog(
        onDismissRequest = onDissmiss,
        title = { Text("Enviar Email")},
        text = {
            Column {
                OutlinedTextField(
                    value = recipient,
                    onValueChange = {recipient = it},
                    label = { Text("Destinatário")}
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = {title = it},
                    label = { Text("Título do E-mail")}
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = body,
                    onValueChange = {body = it},
                    label = { Text("Conteúdo do E-mail")},
                    modifier = Modifier.height(150.dp),
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSend(recipient, title, body)
            }) {
                Text("Enviar")
            }
        },
        dismissButton = {
            Button(onClick = onDissmiss){
                Text("Cancel")
            }
        }
    )
}