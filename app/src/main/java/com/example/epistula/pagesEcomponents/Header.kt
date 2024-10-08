package com.example.epistula.pagesEcomponents

import android.app.Activity
import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.epistula.R
import com.example.epistula.ui.theme.*
import com.example.epistula.ui.theme.fontFamily

@Composable
fun DrawerContent(navController: NavController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val majorColors = if (currentTheme == "dark") DarkGray else LightGray
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    val textoModo = if (currentTheme == "dark") "Modo escuro" else "Modo claro"
    val home = if (currentTheme == "dark") R.drawable.icon_home_d else R.drawable.icon_home_l
    val mode = if (currentTheme == "dark") R.drawable.icon_night_mode else R.drawable.icon_light_mode
    val bell = if (currentTheme == "dark") R.drawable.icon_notifications_d else R.drawable.icon_notifications_l
    val calendar = if (currentTheme == "dark") R.drawable.icon_calendar_d else R.drawable.icon_calendar_l
    val lock = if (currentTheme == "dark") R.drawable.icon_lock_d else R.drawable.icon_lock_l
    val help = if (currentTheme == "dark") R.drawable.icon_help_d else R.drawable.icon_help_l
    val more = if (currentTheme == "dark") R.drawable.icon_more_d else R.drawable.icon_more_l

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .fillMaxHeight()
            .background(majorColors)
            .padding(vertical = 70.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = home,
            textoBotao = "Home"
        )
        Divider(
            color = textColors,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = mode,
            textoBotao = textoModo,
            onClick = {
                val newTheme = if (currentTheme == "light") "dark" else "light"
                sharedPreferences.edit().putString("app_theme", newTheme).apply()

                (context as? Activity)?.recreate()
            }
        )
        Divider(
            color = textColors,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = bell,
            textoBotao = "Notificações"
        )
        Divider(
            color = textColors,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            rota = "calendario",
            imagemDrawable = calendar,
            textoBotao = "Calendario"
        )
        Divider(
            color = textColors,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = lock,
            textoBotao = "Segurança"
        )
        Divider(
            color = textColors,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = help,
            textoBotao = "Ajuda"
        )
        Divider(
            color = textColors,
            modifier = Modifier
                .height(2.dp)
        )
        ItemMenuDrawer(
            navController = navController,
            imagemDrawable = more,
            textoBotao = "Mais Configurações",
        )
        Spacer(modifier = Modifier.height(70.dp))
        Button(
            onClick = { navController.navigate("login")},
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(textColors),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
        ) {
            Text(
                text = "Sair",
                color = majorColors,
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 20.sp,
                fontFamily = fontFamily
            )
        }
    }
}

@Composable
fun ItemMenuDrawer(navController: NavController, rota  : String = "home", imagemDrawable : Int, textoBotao : String,  onClick: (() -> Unit)? = null){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }
    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    Button(
        onClick = {
            onClick?.invoke()
            navController.navigate(rota)
        },
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
                color = textColors,
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

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
    var currentTheme by remember { mutableStateOf(sharedPreferences.getString("app_theme", "light")) }

    val textColors = if (currentTheme == "dark") LightGray else DarkGray
    val filtro = if (currentTheme == "dark") R.drawable.icon_filter_d else R.drawable.icon_filter_l
    val arrow = if (currentTheme == "dark") R.drawable.icon_arrow_d else R.drawable.icon_arrow_l

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = textColors,
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
                        color = textColors,
                        start = Offset(x, 0f),
                        end = Offset(x, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        ) {
            androidx.compose.material3.Icon(
                painter = painterResource(id = filtro),
                contentDescription = "Filtros",
                tint = textColors
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text("Filtros", color = textColors)
        }
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Button(
                onClick = { expanded = !expanded },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(filter, color = textColors)
                androidx.compose.material3.Icon(
                    painter = painterResource(id = arrow),
                    contentDescription = "Arrow Down",
                    tint = textColors
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(textColors)
                    .fillMaxWidth(.45f)
                    .border(.5.dp, textColors, shape = RoundedCornerShape(5.dp))
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option, color = textColors) },
                        onClick = {
                            onFilterSelected(option)
                            expanded = false
                        })
                }
            }
        }
    }
}