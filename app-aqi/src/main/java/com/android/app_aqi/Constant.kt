package com.android.app_aqi

object Constant {

    const val top: Int = 1000
    var BASE_URL: String = "https://opendata.epa.gov.tw/api/v1/"
    var WORKER_NAME: String =  "update_aqi_worker"

    enum class PollutionType(var pollutionName: String, var columnName: String) {
        aQI("AQI","AQI"),
        sO2("SO2","SO2"),
        cO("CO","CO"),
        cO8hr("CO 8hr","CO_8hr"),
        o3("O3","O3"),
        o38hr("O3 8hr","O3_8hr"),
        pM10("PM10", "PM10"),
        pM25("PM2.5", "PM2.5"),
        nO2("NO2", "NO2"),
        nOx("NOx", "NOx"),
        nO("NO", "NO"),
        pM25AVG("PM2.5 AVG","PM2.5_AVG"),
        pM10AVG("PM10 AVG","PM10_AVG"),
        sO2AVG("SO2 AVG","SO2_AVG"),
        windSpeed("WindSpeed","WindSpeed"),
        windDirec("WindDirec","WindDirec")
//        pollutant("Pollutant"),
//        status("Status"),
//        publishTime("PublishTime"),

    }

}
