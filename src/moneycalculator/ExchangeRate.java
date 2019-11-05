package moneycalculator;

import java.time.LocalDate;

public class ExchangeRate {
    private Currency from;
    private Currency to;
    private LocalDate date;
    private double rate;

    public ExchangeRate(Currency from, Currency to, LocalDate date, double rate) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.rate = rate;
    }

    public Currency getFrom() {
        return from;
    }

    public Currency getTo() {
        return to;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getRate() {
        return rate;
    }
}
