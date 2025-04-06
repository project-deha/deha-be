package me.utku.dehabe.enums;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("squid:S115")
public enum TurkishCity {
    ADANA("Adana"),
    ADIYAMAN("Adıyaman"),
    AFYONKARAHİSAR("Afyonkarahisar"),
    AĞRI("Ağrı"),
    AKSARAY("Aksaray"),
    AMASYA("Amasya"),
    ANKARA("Ankara"),
    ANTALYA("Antalya"),
    ARDAHAN("Ardahan"),
    ARTVİN("Artvin"),
    AYDIN("Aydın"),
    BALIKESİR("Balıkesir"),
    BARTIN("Bartın"),
    BATMAN("Batman"),
    BAYBURT("Bayburt"),
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
    DÜZCE("Düzce"),
    EDİRNE("Edirne"),
    ELAZIĞ("Elazığ"),
    ERZİNCAN("Erzincan"),
    ERZURUM("Erzurum"),
    ESKİŞEHİR("Eskişehir"),
    GAZİANTEP("Gaziantep"),
    GİRESUN("Giresun"),
    GÜMÜŞHANE("Gümüşhane"),
    HAKKARİ("Hakkâri"),
    HATAY("Hatay"),
    IĞDIR("Iğdır"),
    ISPARTA("Isparta"),
    İSTANBUL("İstanbul"),
    İZMİR("İzmir"),
    KAHRAMANMARAŞ("Kahramanmaraş"),
    KARABÜK("Karabük"),
    KARAMAN("Karaman"),
    KARS("Kars"),
    KASTAMONU("Kastamonu"),
    KAYSERİ("Kayseri"),
    KİLİS("Kilis"),
    KIRIKKALE("Kırıkkale"),
    KIRKLARELİ("Kırklareli"),
    KIRŞEHİR("Kırşehir"),
    KOCAELİ("Kocaeli"),
    KONYA("Konya"),
    KÜTAHYA("Kütahya"),
    MALATYA("Malatya"),
    MANİSA("Manisa"),
    MARDİN("Mardin"),
    MERSİN("Mersin"),
    MUĞLA("Muğla"),
    MUŞ("Muş"),
    NEVŞEHİR("Nevşehir"),
    NİĞDE("Niğde"),
    ORDU("Ordu"),
    OSMANİYE("Osmaniye"),
    RİZE("Rize"),
    SAKARYA("Sakarya"),
    SAMSUN("Samsun"),
    ŞANLIURFA("Şanlıurfa"),
    SİİRT("Siirt"),
    SİNOP("Sinop"),
    ŞIRNAK("Şırnak"),
    SİVAS("Sivas"),
    TEKİRDAĞ("Tekirdağ"),
    TOKAT("Tokat"),
    TRABZON("Trabzon"),
    TUNCELİ("Tunceli"),
    UŞAK("Uşak"),
    VAN("Van"),
    YALOVA("Yalova"),
    YOZGAT("Yozgat"),
    ZONGULDAK("Zonguldak");

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

