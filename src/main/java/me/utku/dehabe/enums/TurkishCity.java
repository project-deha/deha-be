package me.utku.dehabe.enums;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("squid:S115")
public enum TurkishCity {
    ADANA("Adana"),
    ADIYAMAN("Adıyaman"),
    AFYONKARAHİSAR("Afyonkarahisar"),
    AĞRI("Ağrı"),
    AMASYA("Amasya"),
    ANKARA("Ankara"),
    ANTALYA("Antalya"),
    ARTVİN("Artvin"),
    AYDIN("Aydın"),
    BALIKESİR("Balıkesir"),
    BİLECİK("Bilecik"),
    BİNGÖL("Bingöl"),
    BİTLİS("Bitlis"),
    BOLU("Bolu"),
    BURDUR("Burdur"),
    BURSA("Bursa"),
    ÇANAKKALE("Çanakkale"),
    ÇANKIRI("Çankırı"),
    ÇORUM("Çorum"),
    DENİZLİ("Denizli"),
    DİYARBAKIR("Diyarbakır"),
    EDİRNE("Edirne"),
    ELAZIĞ("Elazığ"),
    ERZİNCAN("Erzincan"),
    ERZURUM("Erzurum"),
    ESKİŞEHİR("Eskişehir"),
    GAZİANTEP("Gaziantep"),
    GİRESUN("Giresun"),
    GÜMÜŞHANE("Gümüşhane"),
    HAKKARİ("Hakkari"),
    HATAY("Hatay"),
    ISPARTA("Isparta"),
    MERSİN("Mersin"),
    İSTANBUL("İstanbul"),
    İZMİR("İzmir"),
    KARS("Kars"),
    KASTAMONU("Kastamonu"),
    KAYSERİ("Kayseri"),
    KIRKLARELİ("Kırklareli"),
    KIRŞEHİR("Kırşehir"),
    KOCAELİ("Kocaeli"),
    KONYA("Konya"),
    KÜTAHYA("Kütahya"),
    MALATYA("Malatya"),
    MANİSA("Manisa"),
    KAHRAMANMARAŞ("Kahramanmaraş"),
    MARDİN("Mardin"),
    MUĞLA("Muğla"),
    MUŞ("Muş"),
    NEVŞEHİR("Nevşehir"),
    NİĞDE("Niğde"),
    ORDU("Ordu"),
    RİZE("Rize"),
    SAKARYA("Sakarya"),
    SAMSUN("Samsun"),
    SİİRT("Siirt"),
    SİNOP("Sinop"),
    SİVAS("Sivas"),
    TEKİRDAĞ("Tekirdağ"),
    TOKAT("Tokat"),
    TRABZON("Trabzon"),
    TUNCELİ("Tunceli"),
    ŞANLIURFA("Şanlıurfa"),
    UŞAK("Uşak"),
    VAN("Van"),
    YOZGAT("Yozgat"),
    ZONGULDAK("Zonguldak"),
    AKSARAY("Aksaray"),
    BAYBURT("Bayburt"),
    KARAMAN("Karaman"),
    KIRIKKALE("Kırıkkale"),
    BATMAN("Batman"),
    ŞIRNAK("Şırnak"),
    BARTIN("Bartın"),
    ARDAHAN("Ardahan"),
    IĞDIR("Iğdır"),
    YALOVA("Yalova"),
    KARABÜK("Karabük"),
    KİLİS("Kilis"),
    OSMANİYE("Osmaniye"),
    DÜZCE("Düzce");

    private final String value;

    TurkishCity(String name) {
        this.value = name;
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

