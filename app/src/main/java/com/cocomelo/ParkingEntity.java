package com.cocomelo;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class ParkingEntity  implements Parcelable
{
    private int parkingSpots;
    private int maxSpots;
    private String name;
    private int radius;
    private Location geoLocation;

    public ParkingEntity() {}
    private ParkingEntity(Parcel in)
    {
        this.parkingSpots = in.readInt();
        this.maxSpots = in.readInt();
        this.name = in.readString();
        this.radius = in.readInt();
    }

    public void setParkingSpots(int value) {
        parkingSpots = value;
    }
    public int getParkingSpots() {
        return parkingSpots;
    }

    public void setMaxSpots(int value) {
        maxSpots = value;
    }
    public int getMaxSpots() {
        return maxSpots;
    }

    public int getRadius()
    {
        return radius;
    }
    public void setRadius(int value)
    {
        radius = value;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String value)
    {
        name = value;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(parkingSpots);
        dest.writeInt(maxSpots);
        dest.writeString(name);
        dest.writeInt(radius);
    }

    public static final Creator<ParkingEntity> CREATOR =
            new Creator<ParkingEntity>()
            {
                @Override
                public ParkingEntity createFromParcel(Parcel source)
                {
                    return new ParkingEntity(source);
                }

                @Override
                public ParkingEntity[] newArray(int size)
                {
                    return new ParkingEntity[size];
                }
            };
}