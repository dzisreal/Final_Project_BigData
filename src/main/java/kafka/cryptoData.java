package kafka;

import com.opencsv.bean.CsvBindByName;

public class cryptoData {

    @CsvBindByName
    public String OpenTime;

    @CsvBindByName
    public String CloseTime;

    @CsvBindByName
    public double ETH_USD_Open;

    @CsvBindByName
    public double ETH_USD_High;

    @CsvBindByName
    public double ETH_USD_Low;

    @CsvBindByName
    public double ETH_USD_Close;

    @CsvBindByName
    public double ETH_USD_volume;

    @CsvBindByName
    public double BTC_USD_Open;

    @CsvBindByName
    public double BTC_USD_High;

    @CsvBindByName
    public double BTC_USD_Low;

    @CsvBindByName
    public double BTC_USD_Close;

    @CsvBindByName
    public double BTC_USD_volume;
}
