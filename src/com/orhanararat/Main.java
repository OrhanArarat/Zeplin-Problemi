package com.orhanararat;

import java.io.*;
import java.util.*;

public class Main {
    public static Scanner scanner=new Scanner(System.in);
    static ArrayList<Sehir> sehirlerList;
    static Map<Sehir,List> enKisaYol;
    static List<Sehir> eklenenSehirler= new LinkedList<>();
    static Zeplin zeplin;
    static String dosyaYolu;

    static Sehir source;
    static Sehir hedef;

    public static void main(String[] args) {
        long startTime=System.currentTimeMillis();
        dosyaYolu="/Users/orhanararat/IntelliJIdeaProject/Prolab2-1/src/com/orhanararat/latlong.txt";
//        enKisaYol=new HashMap<>();
//        sehirlerList= sehirListesiOlustur(dosyaYolu);


        System.out.println("Lutfen baslangic dugumunun plaka degerini giriniz");
        int plaka=scanner.nextInt();
//        source= sehirlerList.get(plaka-1);

        System.out.println("Lutfen gidilecek hedef sehrin plaka degerini giriniz");
        int hedefPlaka= scanner.nextInt();
//        hedef=sehirlerList.get(hedefPlaka-1);

        System.out.println("Lutfen yolcu sayisini giriniz");
        int yolcuSayisi=scanner.nextInt();
        if (yolcuSayisi>50|| yolcuSayisi<5){
            while(yolcuSayisi>50|| yolcuSayisi<5){
                System.out.println("Lutfen arac kapasitesine gore yolcu aliniz");
                System.out.println("Lutfen yolcu sayisini giriniz");
                yolcuSayisi=scanner.nextInt();
            }
        }
//        zeplin=new Zeplin(yolcuSayisi);

//        kisaYollariBelirle(source);
        kisaYollariBelirle(plaka,hedefPlaka,yolcuSayisi);
        hedef.yaz();
        System.out.println(hedef.getToplamYol());


        enKarliYolcuSayisi(plaka,hedefPlaka);

        yuzdeElliKar(plaka,hedefPlaka);

        long endTime= System.currentTimeMillis();
        double timee=(endTime-startTime);
        System.out.println();
        System.out.println("Programin calisma suresi "+timee+" milisaniyedir");
    }

    public static ArrayList<Sehir> sehirListesiOlustur(String dosyaYolu){
        try{
            File file = new File(dosyaYolu);
            FileReader fileReader = new FileReader(file);
            BufferedReader br=new BufferedReader(fileReader);
            ArrayList<Sehir> sehirler=new ArrayList<Sehir>();
            br.readLine();
            String line=br.readLine();
            while(line!=null){
                String[] values= line.split(",");
                double lat= Double.parseDouble(values[0]);
                double lang = Double.parseDouble(values[1]);
                int plaka= Integer.parseInt(values[2]);
                double alt= Double.parseDouble(values[3]);
            Sehir sehir = new Sehir(lat,lang,alt,plaka);
            sehirler.add(sehir);
            line = br.readLine();
            }
            return sehirler;
        }
        catch (Exception e){
            System.out.println("Sehirleri olustururken sorun olustu.");
            System.out.println(e);
            return null;

        }

    }

    public static void enKisaYolBul(Sehir sehir){
        if (enKisaYol.isEmpty()){
            sehir.setGidilenYol(sehir);
            enKisaYol.put(sehir,sehir.getGidilenYol());

            for (Sehir komsuSehir: sehir.getGidilebilecekSehirler()
                 ) {
                sehirEkle(komsuSehir);
//                System.out.println("--------------"+komsuSehir.getPlaka());
                if(komsuSehir.getGidilenYol().isEmpty()){
                    komsuSehir.setGidilenYol(sehir);
                    komsuSehir.setGidilenYol(komsuSehir);
                    komsuSehir.setToplamYol(sehir.getKomsuUzakliklari().get(komsuSehir));
//                    enKisaYol.put(komsuSehir,komsuSehir.getGidilenYol());
                    enKisaYol.put(komsuSehir,komsuSehir.getGidilenYol());
//                    System.out.println("Bos gecici Sehir: "+geciciSehir.getPlaka());
//                    for (Sehir sehira: geciciSehir.getGidilenYol()
//                            ) {
//                        System.out.println("Bos gecici sehir gidilen Yol: "+sehira.getPlaka());
//                    }
                }
            }
        }
        else{
//            System.out.println("SuanKi sehir: "+sehir.getPlaka());
            for (Sehir geciciSehir: sehir.getGidilebilecekSehirler()
                    ) {
//                System.out.println("aaaaaaa");
                if(!enKisaYol.containsKey(geciciSehir)){
                    sehirEkle(geciciSehir);
//                    System.out.println(geciciSehir.getPlaka());
                }
                if (!geciciSehir.getGidilenYol().isEmpty()){
                    double yol=sehir.getToplamYol()+sehir.getKomsuUzakliklari().get(geciciSehir);
                    if(yol<geciciSehir.getToplamYol()){
                        geciciSehir.setGidilenYol(sehir.getGidilenYol());
                        geciciSehir.setGidilenYol(geciciSehir);
                        geciciSehir.setToplamYol(yol);
                        enKisaYol.put(geciciSehir,geciciSehir.getGidilenYol());
//                        System.out.println("Dolu Gecici sehir: "+geciciSehir.getPlaka());
                    }
                }
                else if(geciciSehir.getGidilenYol().isEmpty()){
                    geciciSehir.setGidilenYol(sehir.getGidilenYol());
                    geciciSehir.setGidilenYol(geciciSehir);
                    geciciSehir.setToplamYol(sehir.getToplamYol()+sehir.getKomsuUzakliklari().get(geciciSehir));
                    enKisaYol.put(geciciSehir,geciciSehir.getGidilenYol());
//                    enKisaYol.put(geciciSehir,geciciSehir.getGidilenYol());
//                    System.out.println("Bos gecici Sehir: "+geciciSehir.getPlaka());
//                    for (Sehir sehira: geciciSehir.getGidilenYol()
//                            ) {
//                        System.out.println("Bos gecici sehir gidilen Yol: "+sehira.getPlaka());
//                    }
                }

            }
//        System.out.println(sehir.getKomsuSehirlerPlaka());
        }
    }

    public static void sehirEkle(Sehir sehir){
        eklenenSehirler.add(sehir);
    }

    public static void kisaYollariBelirle(Sehir source){
        sehirEkle(source);

        Iterator<Sehir> sehirIterator;

        while(eklenenSehirler.size()>0){
            sehirIterator=eklenenSehirler.listIterator();

            Sehir ileriSehir= sehirIterator.next();
            ileriSehir.gidilebilecekKomsulariBelirle(zeplin);
            enKisaYolBul(ileriSehir);

            eklenenSehirler.remove(0);
        }
    }

    public static void kisaYollariBelirle(int sourcePlaka,int hedefPlaka,int yolcuSayisi){
        enKisaYol=new HashMap<>();
        sehirlerList= sehirListesiOlustur(dosyaYolu);
        source= sehirlerList.get(sourcePlaka-1);
        hedef=sehirlerList.get(hedefPlaka-1);
        zeplin=new Zeplin(yolcuSayisi);

        sehirEkle(source);

        Iterator<Sehir> sehirIterator;

        while(eklenenSehirler.size()>0){
            sehirIterator=eklenenSehirler.listIterator();

            Sehir ileriSehir= sehirIterator.next();
            ileriSehir.gidilebilecekKomsulariBelirle(zeplin);
            enKisaYolBul(ileriSehir);

            eklenenSehirler.remove(0);
        }
    }

    public static void enKarliYolcuSayisi(int sourceSehirPlaka,int hedefSehirPlaka) {
        System.out.println();
        double toplamKar=-100000;
        double enKarliYolcuSayisi=0;
        Sehir sonSehir=new Sehir(0,0,0,1);
        for (int i = 5; i <= 50; i++) {
            kisaYollariBelirle(sourceSehirPlaka,hedefSehirPlaka,i);
            if (hedef.getToplamYol()!=0){
                double maliyet=hedef.getToplamYol()*zeplin.getKmMaliyeti();
                double yapilanKar=i*zeplin.getBirimFiyati()-maliyet;
//                System.out.println("Yapilan Kar: "+yapilanKar);
                if(yapilanKar>toplamKar){
                    toplamKar=yapilanKar;
                    sonSehir=hedef;
                    enKarliYolcuSayisi=i;
                    System.out.println("Yolcu sayisi: "+i);
                    System.out.println("Toplam Kar: "+toplamKar);
                    System.out.println();
                }
            }
        }

        if(sonSehir.getToplamYol()!=0){
            System.out.println("En karli yolcu sayisi: "+enKarliYolcuSayisi);
            System.out.println("Toplam Kar: "+toplamKar);
            sonSehir.yaz();
            try {
                File file = new File("problem1.txt");
                if(!file.exists()){
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file, false);
                BufferedWriter bWriter = new BufferedWriter(fileWriter);

                Iterator<Sehir> iSehir= sonSehir.getGidilenYol().listIterator();
//                Iterator<Sehir> iSehir2= sonSehir.getGidilenYol().listIterator();
                Sehir sehir1= iSehir.next();
                while (iSehir.hasNext()){
                    Sehir sehir2=iSehir.next();
//                    iSehir2.next();
//                    Sehir sehir2 = iSehir2.next();
                    bWriter.write(sehir1.getPlaka()+"-->"+ sehir1.getLatitude()+"  "+ sehir1.getLongtitude()+" ");
//                    bWriter.write(sehir2.getPlaka()+"-->"+ sehir2.getLatitude()+"  "+ sehir2.getLongtitude()+" ");
                    bWriter.write(sehir1.getPlaka()+"--->"+ sehir2.getPlaka()+"= "+sehir1.gidilenYoluHesapla(sehir1,sehir2));
                    sehir1=sehir2;
                    bWriter.write("\n");
                }
                bWriter.write("Toplam gidilen yol: "+sonSehir.getToplamYol()+"\n");
                bWriter.write("En karli yolcu sayisi: "+enKarliYolcuSayisi+"\n");
                bWriter.write("Toplam Kar: "+toplamKar+"\n");
                bWriter.close();
            }
            catch (IOException e){
                System.out.println("Problem 1 cozumunu dosyaya yazarken sorun olustu.");
            }

        }
        if(sonSehir.getToplamYol()==0){
            System.out.println("Belirlenen sehire yol bulunamamistir");
        }
    }

    public static void yuzdeElliKar(int sourceSehirPlaka,int hedefSehirPlaka){
        try {
            File file = new File("problem2.txt");
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bWriter = new BufferedWriter(fileWriter);
            for (int i = 10; i <= 50; i=i+10) {
                kisaYollariBelirle(sourceSehirPlaka,hedefSehirPlaka,i);
                if (hedef.getToplamYol()!=0){
                    double maliyet=hedef.getToplamYol()*zeplin.getKmMaliyeti();
//                double yapilanKar=i*zeplin.getBirimFiyati()-maliyet;
                    double alinanUcret=(maliyet/i)*2;
                    System.out.println(i+" yolcu sayisi icin alinacak ucret: "+alinanUcret);
                    Iterator<Sehir> iSehir= hedef.getGidilenYol().listIterator();
//                Iterator<Sehir> iSehir2= sonSehir.getGidilenYol().listIterator();
                    Sehir sehir1= iSehir.next();
                    while (iSehir.hasNext()){
                        Sehir sehir2=iSehir.next();
                        bWriter.write(sehir1.getPlaka()+"-->"+ sehir1.getLatitude()+"  "+ sehir1.getLongtitude()+" ");
//                    bWriter.write(sehir2.getPlaka()+"-->"+ sehir2.getLatitude()+"  "+ sehir2.getLongtitude()+" ");
                        bWriter.write(sehir1.getPlaka()+"--->"+ sehir2.getPlaka()+"= "+sehir1.gidilenYoluHesapla(sehir1,sehir2));
                        sehir1=sehir2;
                        bWriter.write("\n");
                    }
                    bWriter.write("Yuzde elli kar icin her musteriden alinacak ucret: "+ alinanUcret);
                }
                bWriter.write("\n\n");
            }

            bWriter.close();
        }
        catch (IOException e){
            System.out.println("Yuzde elli kar hesaplama kisminda dosya islemlerinde sorun olustu");
        }
    }
}
