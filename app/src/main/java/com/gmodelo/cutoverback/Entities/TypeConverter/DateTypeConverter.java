package com.gmodelo.cutoverback.Entities.TypeConverter;



import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConverter {

    @TypeConverter
    public Long ConvertDateToLong(Date date) {
        return date != null ? date.getTime() : null;
    }


    @TypeConverter
    public Date ConvertLongToDate(Long time) {
        return time != null ? new Date(time) : null;
    }

}
