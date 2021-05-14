package ua.tqs.pollution_tqs.Model;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Particles {

    private double co;
    private double no;
    private double no2;
    private double o3;
    private double so2;
    private double pm2_5;
    private double pm10;
    private double nh3;
    private int aqi;
    private long dateTime;
    @Override
    public String toString() {
        return "Particles{" +
                "co=" + co +
                ", no=" + no +
                ", no2=" + no2 +
                ", o3=" + o3 +
                ", so2=" + so2 +
                ", pm2_5=" + pm2_5 +
                ", pm10=" + pm10 +
                ", nh3=" + nh3 +
                '}';
    }

    public Particles(double co, double no, double no2, double o3, double so2, double pm2_5, double pm10, double nh3, int aqi, long dateTime) {
        this.co = co;
        this.no = no;
        this.no2 = no2;
        this.o3 = o3;
        this.so2 = so2;
        this.pm2_5 = pm2_5;
        this.pm10 = pm10;
        this.nh3 = nh3;
        this.aqi = aqi;
        this.dateTime = dateTime;
    }

}
