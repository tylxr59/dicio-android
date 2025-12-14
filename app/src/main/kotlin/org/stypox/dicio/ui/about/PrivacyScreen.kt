package org.stypox.dicio.ui.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.stypox.dicio.R
import org.stypox.dicio.ui.theme.AppTheme

@Composable
fun PrivacyScreen(
    navigationIcon: @Composable () -> Unit,
) {
    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(stringResource(R.string.privacy_policy)) },
                navigationIcon = navigationIcon,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            item {
                Text(
                    text = stringResource(R.string.privacy_intro),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            item {
                PrivacySection(
                    title = "Data Storage",
                    content = """The data that Dicio itself stores about the user are preferences, history and logs. They are kept in local and private databases which are only used by Dicio. Therefore, no such personal data is ever shared with any online service, except for specific preferences as explained shortly. Preferences are used to allow the user to customize their experience. Preferences strictly related to a specific online website might be sent to them for the purpose of enforcing such preference (for example, the language preference is used to download the correct Speech-To-Text model). History is used to make relevant suggestions and better understand user input. Logs are used only for debugging and troubleshooting purposes, and do not contain any specific user information."""
                )
            }
            
            item {
                PrivacySection(
                    title = "Skills and Internet Connectivity",
                    content = """Dicio contains multiple skills, that is separate components that provide services, so that users can interact with those using the assistant interface (for example weather, lyrics, ...). Some skills work without ever connecting to the internet, while others may connect to various online websites as a requirement for providing their service (for example, the weather skill needs to access the internet to obtain weather data). Skills will not share any personal information with online services, unless it is strictly necessary for them to function (for example, when the weather skill makes a web request, the city is necessarily included). Users may enable or disable each skill individually, so they have full control over which online websites may be contacted."""
                )
            }
            
            item {
                PrivacySection(
                    title = "Voice Recognition",
                    content = """The voice recognition in Dicio works offline, without ever sending audio to any external service. Once the Vosk AI Speech-To-Text model has been downloaded, Dicio can use it without needing to connect to the internet ever again. Therefore, once the STT model is ready, Dicio can be used without an internet connection, except for skills that need to find information on the internet (for example, the timer will work, while the weather will not). If the user wishes to, he can disable Dicio's access to the internet from Android settings, and the app will continue working normally. The user may even disable voice recognition completely from application settings."""
                )
            }
            
            item {
                PrivacySection(
                    title = "Data Deletion",
                    content = """The user can easily delete all of his personal data by just clearing the app's storage from Android settings, or by uninstalling the app completely."""
                )
            }
        }
    }
}

@Composable
private fun PrivacySection(
    title: String,
    content: String,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
    )
    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium,
        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight.times(1.5f),
    )
}

@Preview
@Composable
private fun PrivacyScreenPreview() {
    AppTheme {
        PrivacyScreen(navigationIcon = {})
    }
}
