package com.B00277126;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Information implements Parcelable,Serializable {

    public String arrivalDate;
    public String stayDays;
    public String adultsNum;
    public String under16;
    public String under5;
    public String park;
    public String accommodation;

    public boolean pet;
    public boolean over21;

    public int accommodationPrice;


    public Information() {
    }

    public int calculateTotalPrice() {
        int stay = parseInt(stayDays);
        int adults = parseInt(adultsNum);
        int num16 = parseInt(under16);
        int num5 = parseInt(under5);

        int totalPrice = stay * (adults + num16 + num5) * accommodationPrice;
        return totalPrice;
    }
    private int parseInt(String text){
        int num = 0;
        try{
            num = Integer.parseInt(text);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.arrivalDate);
        dest.writeString(this.stayDays);
        dest.writeString(this.adultsNum);
        dest.writeString(this.under16);
        dest.writeString(this.under5);
        dest.writeString(this.park);
        dest.writeString(this.accommodation);
        dest.writeByte(pet ? (byte) 1 : (byte) 0);
        dest.writeByte(over21 ? (byte) 1 : (byte) 0);
        dest.writeInt(this.accommodationPrice);
    }

    private Information(Parcel in) {
        this.arrivalDate = in.readString();
        this.stayDays = in.readString();
        this.adultsNum = in.readString();
        this.under16 = in.readString();
        this.under5 = in.readString();
        this.park = in.readString();
        this.accommodation = in.readString();
        this.pet = in.readByte() != 0;
        this.over21 = in.readByte() != 0;
        this.accommodationPrice = in.readInt();
    }

    public static final Creator<Information> CREATOR = new Creator<Information>() {
        public Information createFromParcel(Parcel source) {
            return new Information(source);
        }

        public Information[] newArray(int size) {
            return new Information[size];
        }
    };

    @Override
    public String toString() {
        return "Information{" +
                "arrivalDate='" + arrivalDate + '\'' +
                ", stayDays='" + stayDays + '\'' +
                ", adultsNum='" + adultsNum + '\'' +
                ", under16='" + under16 + '\'' +
                ", under5='" + under5 + '\'' +
                ", park='" + park + '\'' +
                ", accommodation='" + accommodation + '\'' +
                ", pet=" + pet +
                ", over21=" + over21 +
                ", accommodationPrice=" + accommodationPrice +
                '}';
    }
}
