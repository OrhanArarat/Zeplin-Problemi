package com.orhanararat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Sehir {
    private double latitude;
    private double longtitude;
    private double altitude;
    private int plaka;
    private double toplamYol=0;
    private List<Sehir> komsuSehirler;
    private List<Integer> komsuSehirlerPlaka = new ArrayList<>();
    private List<Sehir> gidilebilecekSehirler;
    private Map<Sehir,Double> komsuUzakliklari;
    private List<Sehir> gidilenYol=new LinkedList<>();

    public Sehir(double latitude, double longtitude, double altitude, int plaka) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.altitude = altitude;
        this.plaka = plaka;
        this.komsuSehirler=new ArrayList<Sehir>();
        this.komsuSehirleriBelirle();
//        this.gidilebilecekKomsulariBelirle(Main.zeplin);
    }


    public double getLatitude() {
        return latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public int getPlaka() {
        return plaka;
    }

    public List<Sehir> getKomsuSehirler() {
//        for (Sehir sehir: this.komsuSehirler
//             ) {
//            System.out.println(sehir.getPlaka());
//        }
        return komsuSehirler;
    }

    public List<Integer> getKomsuSehirlerPlaka() {
//        for (int plaka: komsuSehirlerPlaka
//             ) {
//            System.out.println(plaka);
//        }
        return komsuSehirlerPlaka;
    }

    public List<Sehir> getGidilebilecekSehirler() {
        return gidilebilecekSehirler;
    }

    public Map<Sehir, Double> getKomsuUzakliklari() {
        return komsuUzakliklari;
    }

    public void komsuSehirleriBelirle(){
        try {
            File file = new File("/Users/orhanararat/IntelliJIdeaProject/Prolab2-1/src/com/orhanararat/güncelkomsular.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader br=new BufferedReader(fileReader);

            for (int i = 0; i<this.plaka;i++){
                br.readLine();
            }
            String line = br.readLine();
            String[] values= line.split(",");
            for (int i = 1; i < values.length; i++) {
                komsuSehirlerPlaka.add(Integer.parseInt(values[i]));
            }
        }
        catch (IOException e){
            System.out.println("dosya islemlerinde hata olustu");
        }
    }

    public void gidilebilecekKomsulariBelirle(Zeplin zeplin){//gidilebilcek sehirlere bak burada yazdirma islemini yapmak lazim kontrol icin
        for (int plaka: komsuSehirlerPlaka
             ) {
            komsuSehirler.add(Main.sehirlerList.get(plaka-1));
        }
        this.gidilebilecekSehirler=new ArrayList<Sehir>();
        for (int i = 0; i < komsuSehirler.size(); i++) {
            if(gidilebilirlikKontrol(komsuSehirler.get(i),zeplin.getMaxEgim())){
                this.gidilebilecekSehirler.add(komsuSehirler.get(i));
            }
        }
        komsuUzakliklari=new HashMap<>();
        komsuUzakliklariniBelirle();
    }

    public boolean gidilebilirlikKontrol(Sehir sehir2,int egim){
        double uzaklik=Math.abs(yatayUzaklikHesapla(sehir2));
        double yukseklik= Math.abs(yukseklikHesapla(sehir2,Main.source,Main.hedef));

        double arcTan= Math.atan(yukseklik/uzaklik);
        arcTan=Math.toDegrees(arcTan);
//        System.out.println(this.getPlaka()+"`dan "+sehir2.getPlaka()+"`ya egim "+arcTan+" uzaklik: "+uzaklik+" yukseklik: "+yukseklik);
//        System.out.println(egim);
//        System.out.println("yukseklik: " + yukseklik);
//        System.out.println("uzaklik: "+uzaklik);
        if(Math.abs(arcTan)<=egim){
            return true;
        }
        else
            return false;
    }

    public double yatayUzaklikHesapla(Sehir sehir){
        double R= 6371;
        double latDistance= Math.toRadians(sehir.getLatitude()-this.getLatitude());
        double longDistance= Math.toRadians(sehir.getLongtitude()-this.getLongtitude());
        double a= Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(this.getLatitude())) * Math.cos(Math.toRadians(sehir.getLatitude())) *
                        Math.sin(longDistance / 2) * Math.sin(longDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;
        return distance;
    }

    public double yukseklikHesapla(Sehir sehir,Sehir source, Sehir hedef){
        if(this.equals(source)&&!sehir.equals(hedef)){
            return sehir.getAltitude()+50-this.getAltitude();
        }
        else if(!this.equals(source)&&!sehir.equals(hedef)){
            return sehir.getAltitude()-this.getAltitude();
        }
        else if(!this.equals(source)&&sehir.equals(hedef)){
            return sehir.getAltitude()-this.getAltitude()-50;
        }
        return sehir.getAltitude()+50-this.getAltitude();
    }

    public double gidilenYoluHesapla(Sehir sehir1, Sehir sehir2){
        double uzaklik= sehir1.yatayUzaklikHesapla(sehir2);
        double yukseklik= sehir1.yukseklikHesapla(sehir2,Main.source,Main.hedef);

        double toplamYol= Math.sqrt(Math.pow(uzaklik,2)+Math.pow((yukseklik/1000),2));

        return toplamYol;
    }

    public void komsuUzakliklariniBelirle(){
        for (Sehir sehir: this.gidilebilecekSehirler
             ) {
            double uzaklik=gidilenYoluHesapla(this,sehir);
            komsuUzakliklari.put(sehir,uzaklik);
        }
    }

    public static double toRad(double value){
        return value* Math.PI/180;
    }

    public double getToplamYol() {
        return toplamYol;
    }

    public void setToplamYol(double toplamYol) {
        this.toplamYol = toplamYol;
    }

    public void setGidilenYol(List<Sehir> gidilenYol) {
        this.gidilenYol.clear();
        this.gidilenYol.addAll(gidilenYol);
//        this.gidilenYol=gidilenYol;
    }
    public void setGidilenYol(Sehir sehir) {
        this.gidilenYol.add(sehir);
    }

    public List<Sehir> getGidilenYol() {
        return gidilenYol;
    }
    public void yaz(){
        for (Sehir sehir: this.gidilenYol
             ) {
            System.out.print("-->"+sehir.getPlaka());
        }
        System.out.println();
    }
}
