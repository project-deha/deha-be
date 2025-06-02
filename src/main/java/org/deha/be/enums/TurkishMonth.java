package org.deha.be.enums;

public enum TurkishMonth {
    JAN("Ocak"),
    FEB("Şubat"),
    MAR("Mart"),
    APR("Nisan"),
    MAY("Mayıs"),
    JUNE("Haziran"),
    JUL("Temmuz"),
    AUG("Ağustos"),
    SEP("Eylül"),
    OCT("Ekim"),
    NOV("Kasım"),
    DEC("Aralık");

    private final String name;

    TurkishMonth(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
