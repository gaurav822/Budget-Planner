package com.gaurav.budgetplanner.Utils

import com.gaurav.budgetplanner.R

class Constants {
     companion object {
          const val baseUrl = "https to//cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json"

          val currencies = mapOf(
               "1inch" to "1inch Network",
               "aave" to "Aave",
               "ada" to "Cardano",
               "aed" to "United Arab Emirates Dirham",
               "afn" to "Afghan afghani",
               "algo" to "Algorand",
               "all" to "Albanian lek",
               "amd" to "Armenian dram",
               "amp" to "Synereo",
               "ang" to "Netherlands Antillean Guilder",
               "aoa" to "Angolan kwanza",
               "ar" to "Arweave",
               "ars" to "Argentine peso",
               "a tom" to "A tomic Coin",
               "aud" to "Australian dollar",
               "avax" to "Avalanche",
               "awg" to "Aruban florin",
               "axs" to "AXS",
               "azn" to "Azerbaijani manat",
               "bake" to "Bakery token",
               "bam" to "Bosnia-Herzegovina Convertible Mark",
               "bat" to "Basic Attention  token",
               "bbd" to "Bajan dollar",
               "bch" to "Bitcoin Cash",
               "bdt" to "Bangladeshi taka",
               "bgn" to "Bulgarian lev",
               "bhd" to "Bahraini dinar",
               "bif" to "Burundian Franc",
               "bmd" to "Bermudan dollar",
               "bnb" to "Binance Coin",
               "bnd" to "Brunei dollar",
               "bob" to "Bolivian boliviano",
               "brl" to "Brazilian real",
               "bsd" to "Bahamian dollar",
               "bsv" to "Bitcoin SV",
               "bsw" to "Biswap",
               "btc" to "Bitcoin",
               "btcb" to "Bitcoin BEP2",
               "btg" to "Bitcoin Gold",
               "btn" to "Bhutan currency",
               "busd" to "Binance USD",
               "bwp" to "Botswanan Pula",
               "byn" to "New Belarusian Ruble",
               "byr" to "Belarusian Ruble",
               "bzd" to "Belize dollar",
               "cad" to "Canadian dollar",
               "cake" to "PancakeSwap",
               "cdf" to "Congolese franc",
               "celo" to "Celo",
               "chf" to "Swiss franc",
               "chz" to "Chiliz",
               "clp" to "Chilean peso",
               "cny" to "Chinese Yuan",
               "comp" to "Compound Coin",
               "cop" to "Colombian peso",
               "crc" to "Costa Rican Colón",
               "cro" to "Cryp to.com Chain  token",
               "crv" to "Cravy",
               "cuc" to "Cuban peso",
               "cup" to "Cuban Peso",
               "cve" to "Cape Verdean escudo",
               "cvx" to "Convex Finance",
               "czk" to "Czech koruna",
               "dai" to "Dai",
               "dash" to "Dash",
               "dcr" to "Decred",
               "dfi" to "DfiStarter",
               "djf" to "Djiboutian franc",
               "dkk" to "Danish krone",
               "doge" to "Dogecoin",
               "dop" to "Dominican peso",
               "dot" to "Dotcoin",
               "dzd" to "Algerian dinar",
               "egld" to "Elrond",
               "egp" to "Egyptian pound",
               "enj" to "Enjin Coin",
               "eos" to "EOS",
               "ern" to "Eritrean nakfa",
               "etb" to "Ethiopian birr",
               "etc" to "Ethereum Classic",
               "eth" to "Ether",
               "eur" to "Euro",
               "fei" to "Fei USD",
               "fil" to "FileCoin",
               "fjd" to "Fijian dollar",
               "fkp" to "Falkland Islands pound",
               "flow" to "Flow",
               "frax" to "Frax",
               "ftm" to "Fan tom",
               "ftt" to "FarmaTrust",
               "gala" to "Gala",
               "gbp" to "Pound sterling",
               "gel" to "Georgian lari",
               "ggp" to "GGPro",
               "ghs" to "Ghanaian cedi",
               "gip" to "Gibraltar pound",
               "gmd" to "Gambian dalasi",
               "gnf" to "Guinean franc",
               "gno" to "Gnosis",
               "grt" to "Golden Ratio  token",
               "gt" to "Gate token",
               "gtq" to "Guatemalan quetzal",
               "gyd" to "Guyanaese Dollar",
               "hbar" to "Hedera",
               "hkd" to "Hong Kong dollar",
               "hnl" to "Honduran lempira",
               "hnt" to "Helium",
               "hot" to "Hydro Pro tocol",
               "hrk" to "Croatian kuna",
               "ht" to "Huobi  token",
               "htg" to "Haitian gourde",
               "huf" to "Hungarian forint",
               "icp" to "Internet Computer",
               "idr" to "Indonesian rupiah",
               "ils" to "Israeli New Shekel",
               "imp" to "CoinIMP",
               "inj" to "Injective",
               "inr" to "Indian rupee",
               "iqd" to "Iraqi dinar",
               "irr" to "Iranian rial",
               "isk" to "Icelandic króna",
               "jep" to "Jersey Pound",
               "jmd" to "Jamaican dollar",
               "jod" to "Jordanian dinar",
               "jpy" to "Japanese yen",
               "kas" to "",
               "kava" to "Kava",
               "kcs" to "Kucoin",
               "kda" to "Kadena",
               "kes" to "Kenyan shilling",
               "kgs" to "Kyrgystani Som",
               "khr" to "Cambodian riel",
               "klay" to "Klaytn",
               "kmf" to "Comorian franc",
               "knc" to "Kyber Network",
               "kpw" to "North Korean won",
               "krw" to "South Korean won",
               "ksm" to "Kusama",
               "kwd" to "Kuwaiti dinar",
               "kyd" to "Cayman Islands dollar",
               "kzt" to "Kazakhstani tenge",
               "lak" to "Laotian Kip",
               "lbp" to "Lebanese pound",
               "leo" to "LEOcoin",
               "link" to "ChainLink",
               "lkr" to "Sri Lankan rupee",
               "lrc" to "Loopring",
               "lrd" to "Liberian dollar",
               "lsl" to "Lesotho loti",
               "ltc" to "Litecoin",
               "ltl" to "Lithuanian litas",
               "luna" to "Luna Coin",
               "lvl" to "Latvian lats",
               "lyd" to "Libyan dinar",
               "mad" to "Moroccan dirham",
               "mana" to "Decentraland",
               "matic" to "Polygon",
               "mdl" to "Moldovan leu",
               "mga" to "Malagasy ariary",
               "mina" to "Mina",
               "miota" to "IOTA",
               "mkd" to "Macedonian denar",
               "mkr" to "Maker",
               "mmk" to "Myanmar Kyat",
               "mnt" to "Mongolian tugrik",
               "mop" to "Macanese pataca",
               "mro" to "Mauritanian ouguiya",
               "mur" to "Mauritian rupee",
               "mvr" to "Maldivian rufiyaa",
               "mwk" to "Malawian kwacha",
               "mxn" to "Mexican peso",
               "myr" to "Malaysian ringgit",
               "mzn" to "Mozambican Metical",
               "nad" to "Namibian dollar",
               "near" to "NEAR Pro tocol",
               "neo" to "NEO",
               "nexo" to "NEXO",
               "ngn" to "Nigerian naira",
               "nio" to "Nicaraguan córdoba",
               "nok" to "Norwegian krone",
               "npr" to "Nepalese rupee",
               "nzd" to "New Zealand dollar",
               "okb" to "Okex",
               "omr" to "Omani rial",
               "one" to "Menlo One",
               "pab" to "Panamanian balboa",
               "paxg" to "PAX Gold",
               "pen" to "Sol",
               "pgk" to "Papua New Guinean kina",
               "php" to "Philippine peso",
               "pkr" to "Pakistani rupee",
               "pln" to "Poland złoty",
               "pyg" to "Paraguayan guarani",
               "qar" to "Qatari Rial",
               "qnt" to "Quant",
               "qtum" to "QTUM",
               "ron" to "Romanian leu",
               "rsd" to "Serbian dinar",
               "rub" to "Russian ruble",
               "rune" to "THORChain (ERC20)",
               "rwf" to "Rwandan Franc",
               "sand" to "BeachCoin",
               "sar" to "Saudi riyal",
               "sbd" to "Solomon Islands dollar",
               "scr" to "Seychellois rupee",
               "sdg" to "Sudanese pound",
               "sek" to "Swedish krona",
               "sgd" to "Singapore dollar",
               "shib" to "Shiba Inu",
               "shp" to "Saint Helena pound",
               "sle" to "",
               "sll" to "Sierra Leonean leone",
               "sol" to "Sola",
               "sos" to "Somali shilling",
               "srd" to "Surinamese dollar",
               "std" to "São  tomé and Príncipe Dobra (pre-2018)",
               "stx" to "S tox",
               "svc" to "Salvadoran Colón",
               "syp" to "Syrian pound",
               "szl" to "Swazi lilangeni",
               "thb" to "Thai baht",
               "theta" to "Theta",
               "tjs" to "Tajikistani somoni",
               "tmt" to "Turkmenistani manat",
               "tnd" to "Tunisian dinar",
               " top" to " tongan Paʻanga",
               "trx" to "TRON",
               "try" to "Turkish lira",
               "ttd" to "Trinidad &  tobago Dollar",
               "ttt" to "Tap Project",
               "tusd" to "True USD",
               "twd" to "New Taiwan dollar",
               "tzs" to "Tanzanian shilling",
               "uah" to "Ukrainian hryvnia",
               "ugx" to "Ugandan shilling",
               "uni" to "Universe",
               "usd" to "United States dollar",
               "usdc" to "USD Coin",
               "usdp" to "USDP Stablecoin",
               "usdt" to "Tether",
               "uyu" to "Uruguayan peso",
               "uzs" to "Uzbekistani som",
               "vef" to "Sovereign Bolivar",
               "ves" to "",
               "vet" to "Vechain",
               "vnd" to "Vietnamese dong",
               "vuv" to "Vanuatu vatu",
               "waves" to "Waves",
               "wbtc" to "Wrapped Bitcoin",
               "wemix" to "WEMIX",
               "wst" to "Samoan tala",
               "xaf" to "Central African CFA franc",
               "xag" to "Silver Ounce",
               "xau" to "XauCoin",
               "xcd" to "East Caribbean dollar",
               "xch" to "Chia",
               "xdc" to "XDC Network",
               "xdr" to "Special Drawing Rights",
               "xec" to "Eternal Coin",
               "xem" to "NEM",
               "xlm" to "Stellar",
               "xmr" to "Monero",
               "xof" to "West African CFA franc",
               "xpf" to "CFP franc",
               "xrp" to "XRP",
               "xtz" to "Tezos",
               "yer" to "Yemeni rial",
               "zar" to "South African rand",
               "zec" to "ZCash",
               "zil" to "Zilliqa",
               "zmk" to "Zambian kwacha",
               "zmw" to "Zambian Kwacha",
               "zwl" to "Zimbabwean Dollar")


           val categories:Map<String,Int> =
               mapOf("Transportation" to R.drawable.img_transport,
                    "Workout" to R.drawable.img_gym,
                    "Family" to R.drawable.img_family,
                    "Groceries" to R.drawable.img_groceries,
                    "Gifts" to R.drawable.img_gifts,
                    "More" to R.drawable.img_add,)

           val incomeCategories:Map<String,Int> =
               mapOf(
                    "Interest" to R.drawable.img_transport,
                    "Gift" to R.drawable.img_gym,
                    "Paycheck" to R.drawable.img_family,
                    "Other" to R.drawable.img_groceries,
                    "More" to R.drawable.img_add,
               )
     }



     enum class BaseUrl {
          CURRENCY_BASE_URL, DASHBOARD
     }


}

