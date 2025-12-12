package org.stypox.dicio.skills.unit_conversion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.dicio.skill.context.SkillContext
import org.dicio.skill.skill.SkillOutput
import org.stypox.dicio.R
import org.stypox.dicio.io.graphical.Headline
import org.stypox.dicio.io.graphical.Subtitle
import org.stypox.dicio.util.getString
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

sealed interface UnitConversionOutput : SkillOutput {
    data class Success(
        val inputValue: Double,
        val sourceUnit: Unit,
        val targetUnit: Unit,
        val result: Double
    ) : UnitConversionOutput {
        
        private fun formatNumber(value: Double): String {
            // Use appropriate precision based on magnitude
            val symbols = DecimalFormatSymbols().apply {
                groupingSeparator = ','
                decimalSeparator = '.'
            }
            
            return when {
                value == 0.0 -> "0"
                Math.abs(value) >= 1000000 -> {
                    DecimalFormat("#,##0.##E0", symbols).format(value)
                }
                Math.abs(value) >= 1 -> {
                    DecimalFormat("#,##0.####", symbols).format(value)
                }
                Math.abs(value) >= 0.01 -> {
                    DecimalFormat("0.####", symbols).format(value)
                }
                else -> {
                    DecimalFormat("0.######E0", symbols).format(value)
                }
            }
        }

        private fun getUnitDisplayName(unit: Unit, value: Double): String {
            // Use plural or singular form based on value
            val usePlural = Math.abs(value) != 1.0
            
            // Prefer abbreviations for some units, full names for others
            return when (unit.type) {
                UnitType.DIGITAL_STORAGE, UnitType.ENERGY, UnitType.POWER, UnitType.PRESSURE -> {
                    // Use abbreviations
                    unit.abbreviations.firstOrNull() ?: unit.names.first()
                }
                else -> {
                    // Use full names
                    if (usePlural && unit.names.size > 1) {
                        unit.names[1] // plural form
                    } else {
                        unit.names[0] // singular form
                    }
                }
            }
        }

        override fun getSpeechOutput(ctx: SkillContext): String {
            val inputStr = formatNumber(inputValue)
            val resultStr = formatNumber(result)
            val sourceUnitName = getUnitDisplayName(sourceUnit, inputValue)
            val targetUnitName = getUnitDisplayName(targetUnit, result)
            
            return ctx.getString(
                R.string.skill_unit_conversion_result,
                inputStr,
                sourceUnitName,
                resultStr,
                targetUnitName
            )
        }

        @Composable
        override fun GraphicalOutput(ctx: SkillContext) {
            Column {
                Subtitle(
                    text = "${formatNumber(inputValue)} ${getUnitDisplayName(sourceUnit, inputValue)}"
                )
                Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp))
                Headline(
                    text = "${formatNumber(result)} ${getUnitDisplayName(targetUnit, result)}"
                )
            }
        }
    }

    data class Error(
        val message: String
    ) : UnitConversionOutput {
        override fun getSpeechOutput(ctx: SkillContext): String {
            return ctx.getString(R.string.skill_unit_conversion_error, message)
        }

        @Composable
        override fun GraphicalOutput(ctx: SkillContext) {
            Headline(text = getSpeechOutput(ctx))
        }
    }
}
