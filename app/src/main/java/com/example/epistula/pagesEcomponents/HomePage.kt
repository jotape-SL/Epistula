package com.example.epistula.pagesEcomponents

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.*
import com.example.epistula.R
import com.example.epistula.ui.theme.fontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    var pesquisa by rememberSaveable { mutableStateOf("") }
    val placeholderPesquisa = "Pesquisar"
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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

                // Email List
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    items(2) { index ->
                        EmailItem()
                        Divider(color = Color(0xFF272727), thickness = 1.dp)
                    }
                }
            }
        }
    )
}

@Composable
fun EmailItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, Color(0x4DFFFFFF), shape = RoundedCornerShape(10.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.eye),
                contentDescription = "Email Icon",
                modifier = Modifier.size(48.dp)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text("Remetente", fontWeight = FontWeight.Bold, color = Color.White)
            Text("Título email", fontWeight = FontWeight.Bold, color = Color.White)
            Text(
                "Lorem ipsum dolor sit amet consectetur...",
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text("11/06/2024", color = Color.Gray, fontSize = 12.sp)
            Text("às 13:42", color = Color.Gray, fontSize = 12.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomePage(navController = rememberNavController())
}