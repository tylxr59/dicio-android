package org.stypox.dicio.skills.unit_conversion

enum class UnitType {
    LENGTH,
    MASS,
    TEMPERATURE,
    VOLUME,
    AREA,
    SPEED,
    TIME,
    DIGITAL_STORAGE,
    ENERGY,
    POWER,
    PRESSURE,
    ANGLE
}

enum class Unit(
    val type: UnitType,
    val names: List<String>,
    val abbreviations: List<String>,
    val toBaseUnit: (Double) -> Double,
    val fromBaseUnit: (Double) -> Double
) {
    // LENGTH (base: meter)
    MILLIMETER(UnitType.LENGTH, listOf("millimeter", "millimeters", "millimetre", "millimetres"), listOf("mm"),
        { it / 1000.0 }, { it * 1000.0 }),
    CENTIMETER(UnitType.LENGTH, listOf("centimeter", "centimeters", "centimetre", "centimetres"), listOf("cm"),
        { it / 100.0 }, { it * 100.0 }),
    METER(UnitType.LENGTH, listOf("meter", "meters", "metre", "metres"), listOf("m"),
        { it }, { it }),
    KILOMETER(UnitType.LENGTH, listOf("kilometer", "kilometers", "kilometre", "kilometres"), listOf("km"),
        { it * 1000.0 }, { it / 1000.0 }),
    INCH(UnitType.LENGTH, listOf("inch", "inches"), listOf("in"),
        { it * 0.0254 }, { it / 0.0254 }),
    FOOT(UnitType.LENGTH, listOf("foot", "feet", "ft"), listOf("ft"),
        { it * 0.3048 }, { it / 0.3048 }),
    YARD(UnitType.LENGTH, listOf("yard", "yards"), listOf("yd"),
        { it * 0.9144 }, { it / 0.9144 }),
    MILE(UnitType.LENGTH, listOf("mile", "miles"), listOf("mi"),
        { it * 1609.344 }, { it / 1609.344 }),
    NAUTICAL_MILE(UnitType.LENGTH, listOf("nautical mile", "nautical miles"), listOf("nmi", "nm"),
        { it * 1852.0 }, { it / 1852.0 }),
    LIGHT_YEAR(UnitType.LENGTH, listOf("light year", "light years", "lightyear", "lightyears"), listOf("ly"),
        { it * 9.460730472580800e15 }, { it / 9.460730472580800e15 }),
    ASTRONOMICAL_UNIT(UnitType.LENGTH, listOf("astronomical unit", "astronomical units"), listOf("au", "ua"),
        { it * 1.495978707e11 }, { it / 1.495978707e11 }),

    // MASS (base: kilogram)
    MILLIGRAM(UnitType.MASS, listOf("milligram", "milligrams", "milligramme", "milligrammes"), listOf("mg"),
        { it / 1000000.0 }, { it * 1000000.0 }),
    GRAM(UnitType.MASS, listOf("gram", "grams", "gramme", "grammes"), listOf("g"),
        { it / 1000.0 }, { it * 1000.0 }),
    KILOGRAM(UnitType.MASS, listOf("kilogram", "kilograms", "kilogramme", "kilogrammes"), listOf("kg"),
        { it }, { it }),
    METRIC_TON(UnitType.MASS, listOf("metric ton", "metric tons", "tonne", "tonnes"), listOf("t"),
        { it * 1000.0 }, { it / 1000.0 }),
    OUNCE(UnitType.MASS, listOf("ounce", "ounces"), listOf("oz"),
        { it * 0.028349523125 }, { it / 0.028349523125 }),
    POUND(UnitType.MASS, listOf("pound", "pounds"), listOf("lb", "lbs"),
        { it * 0.45359237 }, { it / 0.45359237 }),
    STONE(UnitType.MASS, listOf("stone", "stones"), listOf("st"),
        { it * 6.35029318 }, { it / 6.35029318 }),
    TON_US(UnitType.MASS, listOf("ton", "tons", "us ton", "us tons", "short ton", "short tons"), listOf("ton"),
        { it * 907.18474 }, { it / 907.18474 }),
    TROY_OUNCE(UnitType.MASS, listOf("troy ounce", "troy ounces"), listOf("oz t", "ozt"),
        { it * 0.0311034768 }, { it / 0.0311034768 }),

    // TEMPERATURE (base: Celsius)
    CELSIUS(UnitType.TEMPERATURE, listOf("celsius", "degree celsius", "degrees celsius", "centigrade"), listOf("c", "°c"),
        { it }, { it }),
    FAHRENHEIT(UnitType.TEMPERATURE, listOf("fahrenheit", "degree fahrenheit", "degrees fahrenheit"), listOf("f", "°f"),
        { (it - 32.0) * 5.0 / 9.0 }, { it * 9.0 / 5.0 + 32.0 }),
    KELVIN(UnitType.TEMPERATURE, listOf("kelvin", "kelvins"), listOf("k"),
        { it - 273.15 }, { it + 273.15 }),

    // VOLUME (base: liter)
    MILLILITER(UnitType.VOLUME, listOf("milliliter", "milliliters", "millilitre", "millilitres"), listOf("ml"),
        { it / 1000.0 }, { it * 1000.0 }),
    LITER(UnitType.VOLUME, listOf("liter", "liters", "litre", "litres"), listOf("l"),
        { it }, { it }),
    CUBIC_METER(UnitType.VOLUME, listOf("cubic meter", "cubic meters", "cubic metre", "cubic metres"), listOf("m3", "m³"),
        { it * 1000.0 }, { it / 1000.0 }),
    TEASPOON(UnitType.VOLUME, listOf("teaspoon", "teaspoons"), listOf("tsp"),
        { it * 0.00492892 }, { it / 0.00492892 }),
    TABLESPOON(UnitType.VOLUME, listOf("tablespoon", "tablespoons"), listOf("tbsp"),
        { it * 0.01478676 }, { it / 0.01478676 }),
    FLUID_OUNCE(UnitType.VOLUME, listOf("fluid ounce", "fluid ounces"), listOf("fl oz"),
        { it * 0.02957353 }, { it / 0.02957353 }),
    CUP(UnitType.VOLUME, listOf("cup", "cups"), listOf("cup"),
        { it * 0.23658824 }, { it / 0.23658824 }),
    PINT(UnitType.VOLUME, listOf("pint", "pints"), listOf("pt"),
        { it * 0.47317647 }, { it / 0.47317647 }),
    QUART(UnitType.VOLUME, listOf("quart", "quarts"), listOf("qt"),
        { it * 0.94635295 }, { it / 0.94635295 }),
    GALLON(UnitType.VOLUME, listOf("gallon", "gallons"), listOf("gal"),
        { it * 3.78541178 }, { it / 3.78541178 }),

    // AREA (base: square meter)
    SQUARE_METER(UnitType.AREA, listOf("square meter", "square meters", "square metre", "square metres"), listOf("m2", "m²", "sq m"),
        { it }, { it }),
    SQUARE_KILOMETER(UnitType.AREA, listOf("square kilometer", "square kilometers", "square kilometre", "square kilometres"), listOf("km2", "km²", "sq km"),
        { it * 1000000.0 }, { it / 1000000.0 }),
    HECTARE(UnitType.AREA, listOf("hectare", "hectares"), listOf("ha"),
        { it * 10000.0 }, { it / 10000.0 }),
    SQUARE_FOOT(UnitType.AREA, listOf("square foot", "square feet"), listOf("ft2", "ft²", "sq ft"),
        { it * 0.09290304 }, { it / 0.09290304 }),
    SQUARE_YARD(UnitType.AREA, listOf("square yard", "square yards"), listOf("yd2", "yd²", "sq yd"),
        { it * 0.83612736 }, { it / 0.83612736 }),
    ACRE(UnitType.AREA, listOf("acre", "acres"), listOf("ac"),
        { it * 4046.8564224 }, { it / 4046.8564224 }),
    SQUARE_MILE(UnitType.AREA, listOf("square mile", "square miles"), listOf("mi2", "mi²", "sq mi"),
        { it * 2589988.110336 }, { it / 2589988.110336 }),

    // SPEED (base: meters per second)
    METERS_PER_SECOND(UnitType.SPEED, listOf("meter per second", "meters per second", "metre per second", "metres per second", "meter a second", "meters a second", "metre a second", "metres a second"), listOf("m/s", "mps"),
        { it }, { it }),
    KILOMETERS_PER_HOUR(UnitType.SPEED, listOf("kilometer per hour", "kilometers per hour", "kilometre per hour", "kilometres per hour", "kilometer an hour", "kilometers an hour", "kilometre an hour", "kilometres an hour"), listOf("km/h", "kmph", "kph"),
        { it / 3.6 }, { it * 3.6 }),
    MILES_PER_HOUR(UnitType.SPEED, listOf("mile per hour", "miles per hour", "mile an hour", "miles an hour"), listOf("mph", "mi/h"),
        { it * 0.44704 }, { it / 0.44704 }),
    FEET_PER_SECOND(UnitType.SPEED, listOf("foot per second", "feet per second", "foot a second", "feet a second"), listOf("ft/s", "fps"),
        { it * 0.3048 }, { it / 0.3048 }),
    KNOT(UnitType.SPEED, listOf("knot", "knots"), listOf("kn", "kt"),
        { it * 0.514444 }, { it / 0.514444 }),

    // TIME (base: second)
    MILLISECOND(UnitType.TIME, listOf("millisecond", "milliseconds"), listOf("ms"),
        { it / 1000.0 }, { it * 1000.0 }),
    SECOND(UnitType.TIME, listOf("second", "seconds"), listOf("s", "sec"),
        { it }, { it }),
    MINUTE(UnitType.TIME, listOf("minute", "minutes"), listOf("min"),
        { it * 60.0 }, { it / 60.0 }),
    HOUR(UnitType.TIME, listOf("hour", "hours"), listOf("h", "hr"),
        { it * 3600.0 }, { it / 3600.0 }),
    DAY(UnitType.TIME, listOf("day", "days"), listOf("d"),
        { it * 86400.0 }, { it / 86400.0 }),
    WEEK(UnitType.TIME, listOf("week", "weeks"), listOf("wk"),
        { it * 604800.0 }, { it / 604800.0 }),
    MONTH(UnitType.TIME, listOf("month", "months"), listOf("mo"),
        { it * 2629800.0 }, { it / 2629800.0 }), // average month (30.4375 days)
    YEAR(UnitType.TIME, listOf("year", "years"), listOf("y", "yr"),
        { it * 31557600.0 }, { it / 31557600.0 }), // average year (365.25 days)

    // DIGITAL STORAGE (base: byte)
    BIT(UnitType.DIGITAL_STORAGE, listOf("bit", "bits"), listOf("b"),
        { it / 8.0 }, { it * 8.0 }),
    BYTE(UnitType.DIGITAL_STORAGE, listOf("byte", "bytes"), listOf("B"),
        { it }, { it }),
    KILOBYTE(UnitType.DIGITAL_STORAGE, listOf("kilobyte", "kilobytes"), listOf("kB", "kb"),
        { it * 1000.0 }, { it / 1000.0 }),
    MEGABYTE(UnitType.DIGITAL_STORAGE, listOf("megabyte", "megabytes"), listOf("MB", "mb"),
        { it * 1000000.0 }, { it / 1000000.0 }),
    GIGABYTE(UnitType.DIGITAL_STORAGE, listOf("gigabyte", "gigabytes"), listOf("GB", "gb"),
        { it * 1000000000.0 }, { it / 1000000000.0 }),
    TERABYTE(UnitType.DIGITAL_STORAGE, listOf("terabyte", "terabytes"), listOf("TB", "tb"),
        { it * 1000000000000.0 }, { it / 1000000000000.0 }),
    PETABYTE(UnitType.DIGITAL_STORAGE, listOf("petabyte", "petabytes"), listOf("PB", "pb"),
        { it * 1000000000000000.0 }, { it / 1000000000000000.0 }),
    KIBIBYTE(UnitType.DIGITAL_STORAGE, listOf("kibibyte", "kibibytes"), listOf("KiB", "kib"),
        { it * 1024.0 }, { it / 1024.0 }),
    MEBIBYTE(UnitType.DIGITAL_STORAGE, listOf("mebibyte", "mebibytes"), listOf("MiB", "mib"),
        { it * 1048576.0 }, { it / 1048576.0 }),
    GIBIBYTE(UnitType.DIGITAL_STORAGE, listOf("gibibyte", "gibibytes"), listOf("GiB", "gib"),
        { it * 1073741824.0 }, { it / 1073741824.0 }),
    TEBIBYTE(UnitType.DIGITAL_STORAGE, listOf("tebibyte", "tebibytes"), listOf("TiB", "tib"),
        { it * 1099511627776.0 }, { it / 1099511627776.0 }),

    // ENERGY (base: joule)
    JOULE(UnitType.ENERGY, listOf("joule", "joules"), listOf("j", "J"),
        { it }, { it }),
    KILOJOULE(UnitType.ENERGY, listOf("kilojoule", "kilojoules"), listOf("kj", "kJ"),
        { it * 1000.0 }, { it / 1000.0 }),
    CALORIE(UnitType.ENERGY, listOf("calorie", "calories"), listOf("cal"),
        { it * 4.184 }, { it / 4.184 }),
    KILOCALORIE(UnitType.ENERGY, listOf("kilocalorie", "kilocalories", "food calorie", "food calories"), listOf("kcal", "Cal"),
        { it * 4184.0 }, { it / 4184.0 }),
    WATT_HOUR(UnitType.ENERGY, listOf("watt hour", "watt hours"), listOf("wh", "Wh"),
        { it * 3600.0 }, { it / 3600.0 }),
    KILOWATT_HOUR(UnitType.ENERGY, listOf("kilowatt hour", "kilowatt hours"), listOf("kwh", "kWh"),
        { it * 3600000.0 }, { it / 3600000.0 }),
    ELECTRONVOLT(UnitType.ENERGY, listOf("electronvolt", "electronvolts", "electron volt", "electron volts"), listOf("ev", "eV"),
        { it * 1.602176634e-19 }, { it / 1.602176634e-19 }),

    // POWER (base: watt)
    WATT(UnitType.POWER, listOf("watt", "watts"), listOf("W", "w"),
        { it }, { it }),
    KILOWATT(UnitType.POWER, listOf("kilowatt", "kilowatts"), listOf("kW", "kw"),
        { it * 1000.0 }, { it / 1000.0 }),
    MEGAWATT(UnitType.POWER, listOf("megawatt", "megawatts"), listOf("MW", "mw"),
        { it * 1000000.0 }, { it / 1000000.0 }),
    HORSEPOWER(UnitType.POWER, listOf("horsepower"), listOf("hp"),
        { it * 745.699872 }, { it / 745.699872 }), // This might be a little bit contentious. This is mechanical/imperial horsepower.

    // PRESSURE (base: pascal)
    PASCAL(UnitType.PRESSURE, listOf("pascal", "pascals"), listOf("pa", "Pa"),
        { it }, { it }),
    KILOPASCAL(UnitType.PRESSURE, listOf("kilopascal", "kilopascals"), listOf("kpa", "kPa"),
        { it * 1000.0 }, { it / 1000.0 }),
    BAR(UnitType.PRESSURE, listOf("bar", "bars"), listOf("bar"),
        { it * 100000.0 }, { it / 100000.0 }),
    ATMOSPHERE(UnitType.PRESSURE, listOf("atmosphere", "atmospheres"), listOf("atm"),
        { it * 101325.0 }, { it / 101325.0 }),
    PSI(UnitType.PRESSURE, listOf("pound per square inch", "pounds per square inch", "psi"), listOf("psi"),
        { it * 6894.75729 }, { it / 6894.75729 }),
    TORR(UnitType.PRESSURE, listOf("torr"), listOf("torr"),
        { it * 133.322368 }, { it / 133.322368 }),
    MMHG(UnitType.PRESSURE, listOf("millimeter of mercury", "millimeters of mercury", "millimetre of mercury", "millimetres of mercury"), listOf("mmhg", "mmHg"),
        { it * 133.322 }, { it / 133.322 }),

    // ANGLE (base: degree)
    DEGREE(UnitType.ANGLE, listOf("degree", "degrees"), listOf("deg", "°"),
        { it }, { it }),
    RADIAN(UnitType.ANGLE, listOf("radian", "radians"), listOf("rad"),
        { it * 180.0 / Math.PI }, { it * Math.PI / 180.0 }),
    GRADIAN(UnitType.ANGLE, listOf("gradian", "gradians", "gon", "gons"), listOf("grad"),
        { it * 0.9 }, { it / 0.9 });

    companion object {
        fun findUnit(text: String): Unit? {
            val normalizedText = text.lowercase().trim()
            
            // First try exact match with abbreviations (case-sensitive for some units like B vs b)
            for (unit in values()) {
                if (unit.abbreviations.contains(text.trim())) {
                    return unit
                }
            }
            
            // Then try case-insensitive abbreviations
            for (unit in values()) {
                for (abbr in unit.abbreviations) {
                    if (abbr.equals(normalizedText, ignoreCase = true)) {
                        return unit
                    }
                }
            }
            
            // Finally try full names
            for (unit in values()) {
                if (unit.names.contains(normalizedText)) {
                    return unit
                }
            }
            
            return null
        }

        fun convert(value: Double, from: Unit, to: Unit): Double? {
            if (from.type != to.type) {
                return null // Cannot convert between different types
            }
            val baseValue = from.toBaseUnit(value)
            return to.fromBaseUnit(baseValue)
        }
    }
}