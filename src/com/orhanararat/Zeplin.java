package com.orhanararat;

public class Zeplin {
    private final int maxYolcu=50;
    private final int minYolcu=5;
    private final double kmMaliyeti=1;
    private final double birimFiyati=200;

    public double getBirimFiyati() {
        return birimFiyati;
    }

    private int maxEgim=80;
    private int yolcuSayisi=0;
//    private double gidilenYol=0;

    public Zeplin(int yolcuSayisi) {
        if(yolcuSayisi<=50 && yolcuSayisi>=5 ){
            this.yolcuSayisi = yolcuSayisi;
            this.maxEgim=this.maxEgim-yolcuSayisi;
        }
        else
            System.out.println("Lutfen arac kapasitesine gore yolcu aliniz");
    }

    public int yolcuEkle(int yolcuSayisi){
        this.yolcuSayisi=this.yolcuSayisi+yolcuSayisi;
        this.maxEgim=this.maxEgim-yolcuSayisi;
        return this.yolcuSayisi;
    }


    public int getMaxEgim() {
        return maxEgim;
    }

    public int getYolcuSayisi() {
        return yolcuSayisi;
    }

    public double getKmMaliyeti() {
        return kmMaliyeti;
    }
    //    public double getGidilenYol() {
//        return gidilenYol;
//    }

    public void setYolcuSayisi(int yolcuSayisi) {
        this.yolcuSayisi = yolcuSayisi;
    }

//    public void setGidilenYol(double gidilenYol) {
//        this.gidilenYol = gidilenYol;
//    }

}
