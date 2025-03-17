package com.youyi.common.util;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.Strictness;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nonnull;

/**
 * @author <a href="https://github.com/yoyocraft">yoyocraft</a>
 * @date 2024/12/28
 */
public class GsonUtil {

    private static final String EMPTY_JSON = "{}";

    private static final String EMPTY_ARRAY_JSON = "[]";

    private static final Gson GSON;
    private static final Gson PRETTY_GSON;

    static {
        GSON = Converters.registerAll(new GsonBuilder())
            .registerTypeAdapter(File.class, new FileAdapter())
            .registerTypeAdapter(Charset.class, new CharsetAdapter())
            .setStrictness(Strictness.LENIENT)
            .create();

        PRETTY_GSON = Converters.registerAll(new GsonBuilder())
            .registerTypeAdapter(File.class, new FileAdapter())
            .registerTypeAdapter(Charset.class, new CharsetAdapter())
            .setPrettyPrinting().create();

    }

    private GsonUtil() {
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        return GSON.toJson(obj);
    }

    public static String toPrettyJson(Object obj) {
        if (obj == null) {
            return null;
        }

        return PRETTY_GSON.toJson(obj);
    }

    public static <T> T fromJson(String json, @Nonnull Type type) {
        if (json == null) {
            return null;
        }
        return GSON.fromJson(json, type);
    }

    public static <T> T fromJson(String json, @Nonnull TypeToken<T> typeToken) {
        if (json == null) {
            return null;
        }
        return GSON.fromJson(json, typeToken);
    }

    public static <T> T fromJson(String json, @Nonnull Class<T> clazz) {
        if (json == null) {
            return null;
        }
        return GSON.fromJson(json, clazz);
    }

    @Nonnull
    public static <E, T extends Collection<E>> T fromJson(String json, @Nonnull Class<? extends Collection> collectionType,
        @Nonnull Class<E> valueType) {
        if (Strings.isNullOrEmpty(json)) {
            json = EMPTY_ARRAY_JSON;
        }

        return GSON.fromJson(json, TypeToken.getParameterized(collectionType, valueType).getType());
    }

    @Nonnull
    public static <K, V, T extends Map<K, V>> T fromJson(String json, @Nonnull Class<? extends Map> mapType, @Nonnull Class<K> keyType,
        @Nonnull Class<V> valueType) {
        if (Strings.isNullOrEmpty(json)) {
            json = EMPTY_JSON;
        }

        return GSON.fromJson(json, TypeToken.getParameterized(mapType, keyType, valueType).getType());
    }

    @Nonnull
    public static Map<String, String> fromJson(String json) {
        return fromJson(json, Map.class, String.class, String.class);
    }

    @Nonnull
    public static <E> E getValueFromJson(String json, @Nonnull String key, @Nonnull Class<E> valueType) {
        Object value = fromJson(json, Map.class, String.class, Object.class).get(key);
        if (value != null) {
            if (valueType.isInstance(value)) {
                return valueType.cast(value);
            } else if (value instanceof String) {
                return fromJson((String) value, valueType);
            } else {
                return fromJson(toJson(value), valueType);
            }
        }
        String errorMsg = "json value is not instance of " + valueType.getName();
        throw new UnsupportedOperationException(errorMsg);
    }

    @Nonnull
    public static <E> E getValueFromJson(String json, @Nonnull Enum<?> key, @Nonnull Class<E> valueType) {
        return getValueFromJson(json, key.name(), valueType);
    }

    public static JsonObject toJsonObject(Object obj) {
        JsonElement jsonElement = GSON.toJsonTree(obj);
        return jsonElement.getAsJsonObject();
    }

    public static class FileAdapter implements JsonSerializer<File>, JsonDeserializer<File> {

        @Override
        public JsonElement serialize(File src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getPath());
        }

        @Override
        public File deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new File(json.getAsString());
        }
    }

    private static class CharsetAdapter extends TypeAdapter<Charset> {
        @Override
        public void write(JsonWriter out, Charset value) throws IOException {
            out.value(value.name());
        }

        @Override
        public Charset read(JsonReader in) throws IOException {
            return Charset.forName(in.nextString());
        }
    }

}
