# price

Using http://themoneyconverter.com/rss-feed/{xxx}/rss.xml feed and https://github.com/clojurewerkz/money  for display.

You can query forex conversions using a `GET` call with the following parameters:

* `from` 
* `into`
* `amount`

Such as 
`http://localhost:4444/c?from=EUR&into=GBP&amount=45`

Valid currency codes are: `SYP`, `IDR`, `MUR`, `NOK`, `BGN`, `XOF`, `BOB`, `ARS`, `QAR`, `MDL`, `KHR`, `ILS`, `AUD`, `LTL`, `PYG`, `BSD`, `SAR`, `PEN`, `RUB`, `NZD`, `GHS`, `JMD`, `VND`, `ZAR`, `ISK`, `TND`, `PKR`, `CZK`, `OMR`, `NGN`, `UYU`, `MKD`, `RON`, `USD`, `UAH`, `MGA`, `MAD`, `LKR`, `HUF`, `BMD`, `COP`, `BHD`, `PHP`, `KWD`, `CHF`, `HRK`, `FJD`, `BAM`, `EGP`, `MXN`, `LVL`, `GBP`, `SCR`, `AWG`, `UGX`, `NPR`, `KRW`, `CLP`, `GMD`, `BBD`, `KES`, `GTQ`, `SEK`, `JPY`, `XCD`, `PLN`, `JOD`, `BDT`, `HKD`, `TWD`, `LAK`, `INR`, `AED`, `VEF`, `CNY`, `RSD`, `MVR`, `THB`, `XPF`, `EUR`, `MYR`, `PAB`, `CAD`, `TRY`, `BRL`, `NAD`, `XAF`, `LBP`, `SGD`, `DKK`


## Running

To start a web server for the application, run:

    lein run

## License

JUNKEY © 2013
