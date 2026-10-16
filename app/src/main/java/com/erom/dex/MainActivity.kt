package com.erom.dex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF08111F)
private val Panel = Color(0xFF111E30)
private val Cyan = Color(0xFF4CE1D2)
private val Muted = Color(0xFF9CB0C8)

class MainActivity : ComponentActivity() {
    private val openFolder = registerForActivityResult(ActivityResultContracts.OpenDocumentTree()) { uri: Uri? ->
        uri?.let { contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION) }
        selectedFolder = uri?.toString()
    }
    private var selectedFolder by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.rgb(8, 17, 31)
        window.navigationBarColor = android.graphics.Color.rgb(8, 17, 31)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(primary = Cyan, background = Navy, surface = Panel)) {
                EromDexApp(selectedFolder = selectedFolder, onChooseFolder = { openFolder.launch(null) })
            }
        }
    }
}

@Composable
private fun EromDexApp(selectedFolder: String?, onChooseFolder: () -> Unit) {
    var page by remember { mutableStateOf("Início") }
    var performance by remember { mutableStateOf(true) }
    Surface(Modifier.fillMaxSize(), color = Navy) {
        Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(48.dp).background(Brush.linearGradient(listOf(Cyan, Color(0xFF4B72FF))), RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Laptop, null, tint = Navy, modifier = Modifier.size(29.dp))
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("EROM DEX", fontSize = 23.sp, fontWeight = FontWeight.Black, color = Color.White)
                    Text("Seu computador, no celular", fontSize = 12.sp, color = Muted)
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { page = "Configurações" }) { Icon(Icons.Default.Settings, "Configurações", tint = Muted) }
            }
            Spacer(Modifier.height(26.dp))
            Text("OLÁ, BEM-VINDO", color = Cyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
            Text("Um desktop Linux\nna palma da mão.", color = Color.White, fontSize = 29.sp, fontWeight = FontWeight.Bold, lineHeight = 34.sp)
            Spacer(Modifier.height(10.dp))
            Text("Transforme seu Android em um ambiente de trabalho portátil, sem precisar usar o terminal.", color = Muted, fontSize = 14.sp, lineHeight = 21.sp)
            Spacer(Modifier.height(22.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Terminal, null, tint = Cyan, modifier = Modifier.size(26.dp))
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) {
                            Text("Ambiente Linux", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                            Text("Preparação do sistema", color = Muted, fontSize = 12.sp)
                        }
                        Surface(color = Color(0xFF45391D), shape = RoundedCornerShape(30.dp)) {
                            Text("PENDENTE", color = Color(0xFFFFD479), fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                        }
                    }
                    Spacer(Modifier.height(14.dp))
                    Text("O aplicativo base está pronto para receber a integração do Debian ARM64. O runtime Linux ainda não está incluído nesta versão.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { page = "Linux" }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(13.dp)) { Text("Ver configuração do Linux") }
                }
            }
            Spacer(Modifier.height(18.dp))
            Text("FERRAMENTAS", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ToolCard("Arquivos", "Acessar pastas", Icons.Default.Folder, Modifier.weight(1f)) { page = "Arquivos" }
                ToolCard("Aplicativos", "Atalhos locais", Icons.Default.Apps, Modifier.weight(1f)) { page = "Aplicativos" }
            }
            Spacer(Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ToolCard("Linux", "Estado do ambiente", Icons.Default.Terminal, Modifier.weight(1f)) { page = "Linux" }
                ToolCard("Sistema", "Desempenho", Icons.Default.Settings, Modifier.weight(1f)) { page = "Configurações" }
            }
            if (page != "Início") {
                Spacer(Modifier.height(20.dp))
                Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(18.dp)) {
                        Text(page, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(Modifier.height(10.dp))
                        when (page) {
                            "Arquivos" -> {
                                Text("Escolha uma pasta do armazenamento usando o seletor seguro do Android.", color = Muted)
                                Spacer(Modifier.height(12.dp))
                                Button(onClick = onChooseFolder) { Icon(Icons.Default.Folder, null); Spacer(Modifier.width(8.dp)); Text("Abrir pasta") }
                                selectedFolder?.let { Text("Pasta selecionada: $it", color = Cyan, fontSize = 11.sp) }
                            }
                            "Aplicativos" -> Text("Os atalhos e o catálogo de aplicativos Linux serão adicionados junto com o runtime.", color = Muted)
                            "Linux" -> Text("Estado: runtime não instalado. O app ainda não executa uma distribuição Linux. A integração planejada é Debian ARM64 com ambiente gráfico, sem exigir comandos do usuário.", color = Muted, lineHeight = 21.sp)
                            "Configurações" -> {
                                Text("Solicitar modo de desempenho sustentado quando suportado pelo dispositivo.", color = Muted)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Modo de desempenho", color = Color.White, modifier = Modifier.weight(1f))
                                    Switch(checked = performance, onCheckedChange = { performance = it })
                                }
                                Text("Esta opção é apenas uma solicitação ao Android; não faz overclock nem garante uso máximo de todos os núcleos.", color = Muted, fontSize = 12.sp)
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        TextButton(onClick = { page = "Início" }) { Text("Voltar ao início") }
                    }
                }
            }
            Spacer(Modifier.height(22.dp))
            Text("EROM DEX • versão 0.2.0 • protótipo", color = Color(0xFF647891), fontSize = 11.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
private fun ToolCard(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(modifier = modifier.clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(15.dp)) {
            Icon(icon, null, tint = Cyan, modifier = Modifier.size(25.dp))
            Spacer(Modifier.height(12.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Text(subtitle, color = Muted, fontSize = 11.sp)
        }
    }
}
