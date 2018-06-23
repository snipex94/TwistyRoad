package com.example.blazk.twistyride;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

public class ServiceItem implements Parcelable{

    private int _id;
    private String _photoUri;
    private DataType _dataType;
    private String _date;
    private String _service = "";

    public enum DataType {
        IMAGE,
        TEXT;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(_photoUri);
        dest.writeString(get_dataTypeString());
        dest.writeString(_date);
        dest.writeString(_service);
    }

    private ServiceItem(Parcel in) {
        _id = in.readInt();
        _photoUri = in.readString();

        String dataType = in.readString();
        if(dataType == "TEXT") _dataType = DataType.TEXT;
        else _dataType = DataType.IMAGE;

        _date = in.readString();
        _service = in.readString();
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<ServiceItem> CREATOR
            = new Parcelable.Creator<ServiceItem>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public ServiceItem createFromParcel(Parcel in) {
            return new ServiceItem(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public ServiceItem[] newArray(int size) {
            return new ServiceItem[size];
        }
    };

    public ServiceItem(DataType dataType) {
        this._dataType = dataType;
    }

    public void set_dataType(DataType dataType) {
        this._dataType = dataType;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_photoUri(String _photoUri) {
        this._photoUri = _photoUri;
    }

    public int get_id() {
        return _id;
    }

    public String get_photoUri() {
        return _photoUri;
    }

    public DataType get_dataType() {
        return _dataType;
    }

    public String get_dataTypeString() {
        if(_dataType == DataType.IMAGE) {
            return "IMAGE";
        }else if (_dataType == DataType.TEXT) {
            return "TEXT";
        }
        else {
            return "ERROR";
        }
    }

    public void set_dateString(String date) {
        _date = date;
    }

    public void set_dateInt(int year, int month, int day) {
        _date = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
    }

    public String get_date() {
        return _date;
    }

    public void put_service(String service_item) {

        if (_service == "") {
            _service = service_item;
        } else {
            _service += service_item + ";";
        }
    }

    public void put_shopAndPrice(String shop, String price) {
        _service = shop + ";" + price + ";" +_service;
    }



    public String get_service() {
        return _service;
    }
}
