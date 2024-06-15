package com.example.epistula.pagesEcomponents

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.epistula.R
import com.example.epistula.model.Lembrete
import com.example.epistula.dao.LembreteDao
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPage(navController: NavController, lembreteDao: LembreteDao) {
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
                    .background(Color(0xFF333333))
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFA8A8A8))
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
                            } }) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_menu),
                                contentDescription = "Menu"
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre o ícone e a TextField

                        TextField(
                            value = pesquisa,
                            onValueChange = { pesquisa = it },
                            placeholder = {
                                if (pesquisa.isEmpty()) {
                                    Text(placeholderPesquisa, color = Color.Black)
                                }
                            },
                            modifier = Modifier
                                .weight(1f),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color(0xFFA7A7A7),
                                cursorColor = Color.Black,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(8.dp),
                        )

                        Spacer(modifier = Modifier.width(8.dp)) // Espaço entre a TextField e o ícone

                        IconButton(onClick = { /* TODO: Menu click */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_person),
                                contentDescription = "Menu"
                            )
                        }

                    }
                }

                // Filters Row
                CustomButtonsWithDropdown()
                LaunchedEffect(Unit){
                    lembretes = lembreteDao.getAll()
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF333333))
                        .padding(top = 16.dp)
                ){



                    Button(
                        shape = RoundedCornerShape(15),
                        colors = ButtonDefaults.buttonColors(Color.Gray),
                        onClick = {showDialog = true},
                        modifier = Modifier
                            .padding(end = 16.dp, bottom = 48.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        Text(text = "Adicionar Lembrete",
                            color = Color.White,
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
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
    ){
        items(lembretes){
                lembrete -> LembreteItem(lembrete)
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun LembreteItem(lembrete: Lembrete) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(text = lembrete.titulo, style = MaterialTheme.typography.titleMedium, color = Color.White)
        Text(text = lembrete.data, style = MaterialTheme.typography.bodyMedium, color = Color.White)
    }
}

@Composable
fun AlertaAdd(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    lembreteDao: LembreteDao
){
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
                colors = ButtonDefaults.buttonColors(Color.Gray),
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
                    color = Color.White,
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
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 20.sp)
            }
        }
    )
}