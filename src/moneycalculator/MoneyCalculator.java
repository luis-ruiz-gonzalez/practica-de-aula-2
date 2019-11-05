package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Scanner;
import java.time.LocalDate;

public class MoneyCalculator {
    private CurrencyList currencyList;
    private ExchangeRate exchangeRate;
    private Currency currencyTo;
    private Money money;

    public MoneyCalculator() { 
        currencyList = new CurrencyList();
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
    
        while (true) {
            System.out.println("Introduzca código divisa origen");
            Currency currency = currencyList.get(scanner.next());
            money = new Money(amount, currency);
            if (currency != null) break;
            System.out.println("Divisa no conocida");
        }

        while (true) {
            System.out.println("Introduzca codigo divisa destino");
            currencyTo = currencyList.get(scanner.next());
            if (currencyTo != null) break;
            System.out.println("Divisa no conocida");
        }
    }
    
    private void process() throws IOException {
        exchangeRate = getExchangeRate(money.getCurrency(), currencyTo);
    }
    
    private void output() {
        System.out.println(money.getAmount() + " " + money.getCurrency().getSymbol() +
                " equivale a " + money.getAmount() * exchangeRate.getRate() + currencyTo.getSymbol());
    }
    
    private static ExchangeRate getExchangeRate(Currency from, Currency to) throws IOException {
        URL url = 
            new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" +
                    from.getIsoCode() + "_" + to.getIsoCode() + "&compact=y&apiKey=b12a3c56600441a524a5");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to.getIsoCode()) + 12, line.indexOf("}"));
            return new ExchangeRate(from, to, LocalDate.of(2019, Month.NOVEMBER, 05), Double.parseDouble(line1));
        }
    }            
}
