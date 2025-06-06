package org.deha.be.enums;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("squid:S115")
public enum TurkishCity {
    ADANA("ADANA"),
    ADIYAMAN("ADIYAMAN"),
    AFYONKARAHİSAR("AFYONKARAHİSAR"),
    AĞRI("AĞRI"),
    AKSARAY("AKSARAY"),
    AMASYA("AMASYA"),
    ANKARA("ANKARA"),
    ANTALYA("ANTALYA"),
    ARDAHAN("ARDAHAN"),
    ARTVİN("ARTVİN"),
    AYDIN("AYDIN"),
    BALIKESİR("BALIKESİR"),
    BARTIN("BARTIN"),
    BATMAN("BATMAN"),
    BAYBURT("BAYBURT"),
    BİLECİK("BİLECİK"),
    BİNGÖL("BİNGÖL"),
    BİTLİS("BİTLİS"),
    BOLU("BOLU"),
    BURDUR("BURDUR"),
    BURSA("BURSA"),
    ÇANAKKALE("ÇANAKKALE"),
    ÇANKIRI("ÇANKIRI"),
    ÇORUM("ÇORUM"),
    DENİZLİ("DENİZLİ"),
    DİYARBAKIR("DİYARBAKIR"),
    DÜZCE("DÜZCE"),
    EDİRNE("EDİRNE"),
    ELAZIĞ("ELAZIĞ"),
    ERZİNCAN("ERZİNCAN"),
    ERZURUM("ERZURUM"),
    ESKİŞEHİR("ESKİŞEHİR"),
    GAZİANTEP("GAZİANTEP"),
    GİRESUN("GİRESUN"),
    GÜMÜŞHANE("GÜMÜŞHANE"),
    HAKKARİ("HAKKARİ"),
    HATAY("HATAY"),
    IĞDIR("IĞDIR"),
    ISPARTA("ISPARTA"),
    İSTANBUL("İSTANBUL"),
    İZMİR("İZMİR"),
    KAHRAMANMARAŞ("KAHRAMANMARAŞ"),
    KARABÜK("KARABÜK"),
    KARAMAN("KARAMAN"),
    KARS("KARS"),
    KASTAMONU("KASTAMONU"),
    KAYSERİ("KAYSERİ"),
    KİLİS("KİLİS"),
    KIRIKKALE("KIRIKKALE"),
    KIRKLARELİ("KIRKLARELİ"),
    KIRŞEHİR("KIRŞEHİR"),
    KOCAELİ("KOCAELİ"),
    KONYA("KONYA"),
    KÜTAHYA("KÜTAHYA"),
    MALATYA("MALATYA"),
    MANİSA("MANİSA"),
    MARDİN("MARDİN"),
    MERSİN("MERSİN"),
    MUĞLA("MUĞLA"),
    MUŞ("MUŞ"),
    NEVŞEHİR("NEVŞEHİR"),
    NİĞDE("NİĞDE"),
    ORDU("ORDU"),
    OSMANİYE("OSMANİYE"),
    RİZE("RİZE"),
    SAKARYA("SAKARYA"),
    SAMSUN("SAMSUN"),
    ŞANLIURFA("ŞANLIURFA"),
    SİİRT("SİİRT"),
    SİNOP("SİNOP"),
    ŞIRNAK("ŞIRNAK"),
    SİVAS("SİVAS"),
    TEKİRDAĞ("TEKİRDAĞ"),
    TOKAT("TOKAT"),
    TRABZON("TRABZON"),
    TUNCELİ("TUNCELİ"),
    UŞAK("UŞAK"),
    VAN("VAN"),
    YALOVA("YALOVA"),
    YOZGAT("YOZGAT"),
    ZONGULDAK("ZONGULDAK");

    private final String value;

    TurkishCity(String value) {
        this.value = value;
    }

    public static List<String> getValues() {
        return Arrays.stream(TurkishCity.values())
                .map(TurkishCity::getValue)
                .toList();
    }

    public String getValue() {
        return value;
    }
}

