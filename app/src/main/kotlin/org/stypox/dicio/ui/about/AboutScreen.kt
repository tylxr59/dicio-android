package org.stypox.dicio.ui.about

import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.stypox.dicio.BuildConfig
import org.stypox.dicio.R
import org.stypox.dicio.settings.ui.SettingsCategoryTitle
import org.stypox.dicio.settings.ui.SettingsItem
import org.stypox.dicio.ui.theme.AppTheme
import org.stypox.dicio.util.ShareUtils

@Composable
fun AboutScreen(
    navigationIcon: @Composable () -> Unit,
    navigateToPrivacy: () -> Unit,
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text(stringResource(R.string.about)) },
                navigationIcon = navigationIcon,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // App Information Section
            item {
                SettingsCategoryTitle(title = stringResource(R.string.about_app_info))
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.app_name),
                    description = stringResource(R.string.about_description),
                )
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_version),
                    description = BuildConfig.VERSION_NAME,
                )
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_application_id),
                    description = BuildConfig.APPLICATION_ID,
                )
            }
            
            // Build Information Section
            item {
                SettingsCategoryTitle(title = stringResource(R.string.about_build_info))
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_version_code),
                    description = BuildConfig.VERSION_CODE.toString(),
                )
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_copy_version_info),
                    icon = Icons.Default.Description,
                    modifier = Modifier.clickable {
                        val versionInfo = buildVersionInfo()
                        ShareUtils.copyToClipboard(context, versionInfo)
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                context.getString(R.string.copied_to_clipboard)
                            )
                        }
                    },
                )
            }
            
            // Links Section
            item {
                SettingsCategoryTitle(title = stringResource(R.string.about_links))
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_source_code),
                    icon = Icons.Default.Code,
                    description = "github.com/Stypox/dicio-android",
                    modifier = Modifier.clickable {
                        ShareUtils.openUrlInBrowser(
                            context,
                            "https://github.com/Stypox/dicio-android"
                        )
                    },
                )
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_report_issue),
                    icon = Icons.Default.BugReport,
                    description = "Report bugs and request features",
                    modifier = Modifier.clickable {
                        ShareUtils.openUrlInBrowser(
                            context,
                            "https://github.com/Stypox/dicio-android/issues"
                        )
                    },
                )
            }
            
            // Legal Section
            item {
                SettingsCategoryTitle(title = stringResource(R.string.about_legal))
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_license),
                    icon = Icons.Default.Gavel,
                    description = stringResource(R.string.about_license_gpl),
                    modifier = Modifier.clickable {
                        ShareUtils.openUrlInBrowser(
                            context,
                            "https://www.gnu.org/licenses/gpl-3.0.html"
                        )
                    },
                )
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_privacy_policy),
                    icon = Icons.Default.Policy,
                    description = stringResource(R.string.privacy_intro),
                    modifier = Modifier.clickable(onClick = navigateToPrivacy),
                )
            }
            
            // Credits Section
            item {
                SettingsCategoryTitle(title = stringResource(R.string.about_credits))
            }
            
            item {
                SettingsItem(
                    title = stringResource(R.string.about_contributors),
                    icon = Icons.Default.Group,
                    description = stringResource(R.string.about_contributors_description),
                    modifier = Modifier.clickable {
                        ShareUtils.openUrlInBrowser(
                            context,
                            "https://github.com/Stypox/dicio-android/graphs/contributors"
                        )
                    },
                )
            }
            
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

private fun buildVersionInfo(): String {
    return """
        ${BuildConfig.APPLICATION_ID}
        Version ${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})
        
        Device: ${Build.MANUFACTURER} ${Build.MODEL}
        Android: ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})
    """.trimIndent()
}

@Preview
@Composable
private fun AboutScreenPreview() {
    AppTheme {
        AboutScreen(
            navigationIcon = {},
            navigateToPrivacy = {},
        )
    }
}
