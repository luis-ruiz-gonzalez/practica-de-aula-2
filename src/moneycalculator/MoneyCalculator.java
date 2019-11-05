package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Scanner;

public class MoneyCalculator {
    private HashMap<String, Currency> currencies = new HashMap<String,Currency>();
    private double exchangeRate;
    private Currency currencyTo;
    private Money money;

    public MoneyCalculator() { 
        currencies.put("USD", new Currency("USD","Dolar","$"));
        currencies.put("EUR", new Currency("EUR","Euro","€"));        
    }
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.control();
    }
    
    private void control() throws IOException{
        input();
        process();
        output();
    }
        
    private void input() {
        System.out.println("Introduzca una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        double amount = Double.parseDouble(scanner.next());
    
        System.out.println("Introduzca una divisa origen");
        Currency currency = currencies.get(scanner.next().toUpperCase());
        
        money = new Money(amount,currency);
        
        System.out.println("Introduzca una divisa destino");
        currencyTo = currencies.get(scanner.next().toUpperCase());
    }
    
    private void process() throws IOException {
        exchangeRate = getExchangeRate(money.getCurrency().getIsoCode(),currencyTo.getIsoCode());
    }
    
    private void output() {
        System.out.println(money.getAmount() + " " + money.getCurrency().getSymbol() +
                " equivale a " + money.getAmount()*exchangeRate + currencyTo.getSymbol());
    }
    
    private static double getExchangeRate(String from, String to) throws IOException {
        URL url = 
            new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" +
                    from + "_" + to + "&compact=y&apiKey=b12a3c56600441a524a5");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to)+12, line.indexOf("}"));
            return Double.parseDouble(line1);
        }
    }            
}
