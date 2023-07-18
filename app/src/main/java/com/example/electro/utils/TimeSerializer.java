package com.example.electro.utils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;



import java.lang.reflect.Type;
import java.sql.Time;

public class TimeSerializer implements JsonDeserializer<Time>, JsonSerializer<Time> {
    @Override
    public Time deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        return Time.valueOf(json.getAsString());
    }

    @Override
    public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
        Date date = new Date(src.getTime()); // Convertir Time a Date
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss"); // Formateador de fecha para la hora
        String formattedTime = sdf.format(date); // Formatear la hora como "HH:mm:ss"
        return new JsonPrimitive(formattedTime);
    }
}
