package moneycalculator;

import java.util.HashMap;

public class CurrencyList {
    private HashMap<String, Currency> currencies = new HashMap<String,Currency>();

    public CurrencyList() {
        add(new Currency("USD","Dolar","$"));
        add(new Currency("EUR","Euro","€"));
    }
    
    private void add(Currency currency){
        currencies.put(currency.getIsoCode(), currency);
    }
    
    public Currency get(String isoCode){
        return currencies.get(isoCode.toUpperCase());
    }
}

