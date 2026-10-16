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
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.focus.focusable

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
                DesktopShell(selectedFolder = selectedFolder, onChooseFolder = { openFolder.launch(null) })
            }
        }
    }
}

@Composable
private fun DesktopShell(selectedFolder: String?, onChooseFolder: () -> Unit) {
    var window by remember { mutableStateOf("Desktop") }
    var browserUrl by remember { mutableStateOf("https://www.google.com") }
    val context = LocalContext.current
    Surface(Modifier.fillMaxSize().focusable().onPreviewKeyEvent { event ->\n        if (event.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false\n        when {\n            event.isAltPressed && event.key == Key.One -> { window = "Arquivos"; true }\n            event.isAltPressed && event.key == Key.Two -> { window = "Navegador"; true }\n            event.isAltPressed && event.key == Key.Three -> { window = "Aplicativos"; true }\n            event.isAltPressed && event.key == Key.Four -> { window = "Linux"; true }\n            event.isAltPressed && event.key == Key.Five -> { window = "Configurações"; true }\n            event.isAltPressed && event.key == Key.Six -> { window = "Sobre"; true }\n            event.key == Key.Escape -> { window = "Desktop"; true }\n            else -> false\n        }\n    }, color = Navy) {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.fillMaxWidth().background(Color(0xFF0C1728)).padding(horizontal = 16.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(34.dp).background(Brush.linearGradient(listOf(Cyan, Color(0xFF4B72FF))), RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Laptop, null, tint = Navy, modifier = Modifier.size(23.dp))
                }
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    Text("EROM DEX", color = Color.White, fontWeight = FontWeight.Black, fontSize = 18.sp)
                    Text("Desktop móvel", color = Muted, fontSize = 10.sp)
                }
                Text("● ONLINE", color = Cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }
            Box(Modifier.weight(1f).fillMaxWidth().background(Brush.verticalGradient(listOf(Color(0xFF142C45), Color(0xFF091322), Color(0xFF101B2D)))).padding(16.dp)) {
                Column(Modifier.fillMaxSize()) {
                    Text("Área de trabalho", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("Seu espaço de trabalho no celular", color = Muted, fontSize = 12.sp)
                    Spacer(Modifier.height(22.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        DesktopIcon("Arquivos", Icons.Default.Folder, Modifier.weight(1f)) { window = "Arquivos" }
                        DesktopIcon("Navegador", Icons.Default.Language, Modifier.weight(1f)) { window = "Navegador" }
                        DesktopIcon("Aplicativos", Icons.Default.Apps, Modifier.weight(1f)) { window = "Aplicativos" }
                    }
                    Spacer(Modifier.height(12.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        DesktopIcon("Linux", Icons.Default.Terminal, Modifier.weight(1f)) { window = "Linux" }
                        DesktopIcon("Configurações", Icons.Default.Settings, Modifier.weight(1f)) { window = "Configurações" }
                        DesktopIcon("Sobre", Icons.Default.Info, Modifier.weight(1f)) { window = "Sobre" }
                    }
                    Spacer(Modifier.height(24.dp))
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xE6111E30)), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text(if (window == "Desktop") "Bem-vindo ao Erom Dex" else window, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Spacer(Modifier.height(10.dp))
                            when (window) {
                                "Arquivos" -> {
                                    Text("Abra uma pasta do telefone usando o seletor seguro do Android.", color = Muted, fontSize = 13.sp)
                                    Spacer(Modifier.height(10.dp))
                                    Button(onClick = onChooseFolder, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Folder, null); Spacer(Modifier.width(8.dp)); Text("Abrir arquivos") }
                                    selectedFolder?.let { Text("Pasta selecionada", color = Cyan, fontSize = 11.sp) }
                                }
                                "Navegador" -> {
                                    Text("Abra a web no navegador instalado no telefone.", color = Muted, fontSize = 13.sp)
                                    Spacer(Modifier.height(10.dp))
                                    OutlinedTextField(value = browserUrl, onValueChange = { browserUrl = it }, label = { Text("Endereço da web") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                                    Spacer(Modifier.height(8.dp))
                                    Button(onClick = {
                                        val address = if (browserUrl.startsWith("http://") || browserUrl.startsWith("https://")) browserUrl else "https://$browserUrl"
                                        runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(address))) }
                                    }, modifier = Modifier.fillMaxWidth()) { Icon(Icons.Default.Language, null); Spacer(Modifier.width(8.dp)); Text("Abrir site") }
                                }
                                "Aplicativos" -> Text("Atalhos disponíveis: Arquivos, Navegador, Configurações e informações do sistema. Aplicativos Linux exigem o runtime Linux, ainda não integrado.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                                "Linux" -> Text("O desktop do app já está ativo, mas o runtime Debian ARM64 ainda não está integrado. Por isso, programas Linux nativos ainda não podem ser executados nesta versão.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                                "Configurações" -> Text("Interface adaptável a retrato e paisagem. Teclado físico: Alt+1 Arquivos, Alt+2 Navegador, Alt+3 Aplicativos, Alt+4 Linux, Alt+5 Sistema, Alt+6 Sobre; Esc volta à área de trabalho. O Android controla a taxa de atualização disponível, o desempenho e os limites térmicos. A meta de 120 Hz depende da tela e do modo ativo; ainda não é uma garantia de 120 FPS.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                                "Sobre" -> Text("Erom Dex 0.3.0 • interface desktop móvel. Este build é um protótipo funcional de navegação, não uma distribuição Linux completa.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                                else -> Text("Escolha um atalho acima para abrir uma ferramenta.", color = Muted, fontSize = 13.sp)
                            }
                            if (window != "Desktop") {
                                Spacer(Modifier.height(8.dp))
                                TextButton(onClick = { window = "Desktop" }) { Text("Voltar à área de trabalho") }
                            }
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Text("Erom Dex • área de trabalho", color = Color(0xFF8AA0B9), fontSize = 11.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
            Row(Modifier.fillMaxWidth().background(Color(0xFF0C1728)).padding(horizontal = 10.dp, vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { window = "Desktop" }) { Icon(Icons.Default.Laptop, "Início", tint = Cyan) }
                Spacer(Modifier.weight(1f))
                TextButton(onClick = { window = "Arquivos" }) { Icon(Icons.Default.Folder, null, tint = Muted); Spacer(Modifier.width(4.dp)); Text("Arquivos", color = Muted, fontSize = 11.sp) }
                TextButton(onClick = { window = "Configurações" }) { Icon(Icons.Default.Settings, null, tint = Muted); Spacer(Modifier.width(4.dp)); Text("Sistema", color = Muted, fontSize = 11.sp) }
            }
        }
    }
}

@Composable
private fun DesktopIcon(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(modifier = modifier.clickable(onClick = onClick), colors = CardDefaults.cardColors(containerColor = Color(0xB8111E30)), shape = RoundedCornerShape(14.dp)) {
        Column(Modifier.fillMaxWidth().padding(vertical = 15.dp, horizontal = 5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = Cyan, modifier = Modifier.size(27.dp))
            Spacer(Modifier.height(8.dp))
            Text(label, color = Color.White, fontWeight = FontWeight.Medium, fontSize = 10.sp, maxLines = 1)
        }
    }
}
