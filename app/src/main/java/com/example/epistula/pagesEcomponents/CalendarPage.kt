package com.example.epistula.pagesEcomponents

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.epistula.model.Lembrete
import com.example.epistula.dao.LembreteDao
import com.example.epistula.db.AppDataBase
import com.example.epistula.ui.theme.DarkGray
import com.example.epistula.ui.theme.LightGray
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPage(navController: NavController, lembreteDao: LembreteDao) {

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val majorColors = if (currentTheme == "dark") DarkGray else LightGray
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    val menu = if (currentTheme == "dark") R.drawable.icon_menu_d else R.drawable.icon_menu_l
    val avatar = if (currentTheme == "dark") R.drawable.icon_person_d else R.drawable.icon_person_l

    var lembretes by remember { mutableStateOf(listOf<Lembrete>())}
    var showDialog by remember { mutableStateOf(false)}
    var pesquisa by rememberSaveable { mutableStateOf("") }
    val placeholderPesquisa = "Pesquisar"
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val coroutineScope = rememberCoroutineScope()

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
                LaunchedEffect(Unit){
                    lembretes = lembreteDao.getAll()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(majorColors)
                        .padding(top = 16.dp)
                ){



                    Button(
                        shape = RoundedCornerShape(15),
                        colors = ButtonDefaults.buttonColors(textColors),
                        onClick = {showDialog = true},
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 48.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Text(text = "Adicionar Lembrete",
                            color = majorColors,
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontSize = 20.sp)
                    }
                }
                if(showDialog){
                    AlertaAdd(
                        onDismissRequest = {showDialog = false},
                        onConfirm = {
                            showDialog = false
                            coroutineScope.launch {
                                lembretes = lembreteDao.getAll()
                            }
                        },
                        lembreteDao = lembreteDao
                    )
                }
            }

        }
    )


}

@Composable
fun ListaLembretes(lembretes: List<Lembrete>){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
    ){
        items(lembretes){
                lembrete -> LembreteItem(lembrete)
            Divider(color = textColors, thickness = 1.dp)
        }
    }
}

@Composable
fun LembreteItem(lembrete: Lembrete) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(text = lembrete.titulo, style = MaterialTheme.typography.titleMedium, color = textColors)
        Text(text = lembrete.data, style = MaterialTheme.typography.bodyMedium, color = textColors)
    }
}

@Composable
fun AlertaAdd(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    lembreteDao: LembreteDao
){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val textColors = if (currentTheme == "dark") DarkGray else LightGray
    val majorColors = if (currentTheme == "dark") LightGray else DarkGray
    var titulo by remember { mutableStateOf("")}
    var data by remember { mutableStateOf("")}

    val coroutineScope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text("Adicionar Lembrete")
        },
        text = {
            Column {
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    placeholder = { Text("Título") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
                TextField(
                    value = data,
                    onValueChange = { data = it },
                    placeholder = { Text("Data - yyyy-mm-dd")},
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(majorColors),
                onClick = {
                    val novoLembrete = Lembrete(
                        data = data,
                        titulo = titulo
                    )
                    coroutineScope.launch{
                        lembreteDao.insert(novoLembrete)
                        onConfirm()
                    }
                }
            ) {
                Text(text ="Adicionar",
                    color = textColors,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 20.sp)
            }
        },
        dismissButton = {
            Button(
                shape = RoundedCornerShape(15),
                colors = ButtonDefaults.buttonColors(Color.Gray),
                onClick = { onDismissRequest() }
            ) {
                Text("Cancelar",
                    color = textColors,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 20.sp)
            }
        }
    )
}
