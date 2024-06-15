package com.example.epistula.pagesEcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.epistula.R
import com.example.epistula.ui.theme.fontFamily

@Composable
fun DrawerContent(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight()
            .background(Color(0xFF393939))
            .padding(vertical = 70.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = R.drawable.icon_home,
            textoBotao = "Home"
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = R.drawable.icon_brush,
            textoBotao = "Personalização"
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = R.drawable.icon_notifications,
            textoBotao = "Notificações"
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            rota = "calendario",
            imagemDrawable = R.drawable.icon_calendar,
            textoBotao = "Calendario"
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = R.drawable.icon_lock,
            textoBotao = "Segurança"
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = R.drawable.icon_help,
            textoBotao = "Ajuda"
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = R.drawable.icon_more,
            textoBotao = "Mais Configurações"
        )
        Spacer(modifier = Modifier.height(70.dp))
        Button(
            onClick = { navController.navigate("login")},
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Text(
                text = "Sair",
                color = Color.White,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 20.sp,
                fontFamily = fontFamily
            )
        }
    }
}

@Composable
fun ItemMenuDrawer(navController: NavController, rota  : String = "home", imagemDrawable : Int, textoBotao : String){
    Button(
        onClick = { navController.navigate(rota) },
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){
            Icon(imagemDrawable)

            Text(
                text = textoBotao,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding( horizontal = 30.dp, vertical = 15.dp),
                fontFamily = fontFamily,
            )
        }

    }
}

@Composable
fun CustomButtonsWithDropdown(filter: String, onFilterSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    //var selectedText by remember { mutableStateOf("Todos os emails") }
    val options = listOf("Todos os emails", "Lidos", "Não lidos", "Spam")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color(0xFF272727),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
    ) {
        Button(
            onClick = { /* TODO: Filtros */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier
                .weight(1f)
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val x = size.width - strokeWidth / 2
                    drawLine(
                        color = Color(0xFF272727),
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            androidx.compose.material3.Icon(
                painter = painterResource(id = R.drawable.icon_filter),
                contentDescription = "Filtros",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Filtros", color = Color.White)
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Button(
                onClick = { expanded = !expanded },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(filter, color = Color.White)
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.icon_arrow),
                    contentDescription = "Arrow Down",
                    tint = Color.White
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxWidth(.45f)
                    .border(.5.dp, Color.White, shape = RoundedCornerShape(5.dp))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, color = Color.White) },
                        onClick = {
                            onFilterSelected(option)
                            expanded = false
                        })
                }
            }
        }
    }
}