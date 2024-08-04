package jp.glory.practice.eventStoreDb.user.domain.model

class Address(
    val postalCode: PostalCode,
    val prefecture: Prefecture,
    val city: City,
    val street: Street
)

@JvmInline
value class PostalCode(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.matches(Regex("[0-9]{7}")))
        require(value.length <= 7)
    }
}

@JvmInline
value class City(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 100)
    }
}

@JvmInline
value class Street(val value: String) {
    init {
        require(value.isNotEmpty())
        require(value.length <= 100)
    }
}

enum class Prefecture(
    val code: String
){
    Hokkaido("01"),
    Aomori("02"),
    Iwate("03"),
    Miyagi("04"),
    Akita("05"),
    Yamagata("06"),
    Fukushima("07"),
    Ibaraki("08"),
    Tochigi("09"),
    Gumma("10"),
    Saitama("11"),
    Chiba("12"),
    Tokyo("13"),
    Kanagawa("14"),
    Niigata("15"),
    Toyama("16"),
    Ishikawa("17"),
    Fukui("18"),
    Yamanashi("19"),
    Nagano("20"),
    Gifu("21"),
    Shizuoka("22"),
    Aichi("23"),
    Mie("24"),
    Shiga("25"),
    Kyoto("26"),
    Osaka("27"),
    Hyogo("28"),
    Nara("29"),
    Wakayama("30"),
    Tottori("31"),
    Shimane("32"),
    Okayama("33"),
    Hiroshima("34"),
    Yamaguchi("35"),
    Tokushima("36"),
    Kagawa("37"),
    Ehime("38"),
    Kochi("39"),
    Fukuoka("40"),
    Saga("41"),
    Nagasaki("42"),
    Kumamoto("43"),
    Oita("44"),
    Miyazaki("45"),
    Kagoshima("46"),
    Okinawa("47");

    companion object {
        fun fromCode(code: String): Prefecture =
            Prefecture.entries
                .firstOrNull { it.code == code }
                ?: throw IllegalArgumentException("Not found Prefecture")
    }
}