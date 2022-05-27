package com.zapcorp.transformator.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;


@UtilityClass
@SuppressWarnings("unchecked")
public class SerializationUtils {

    @SneakyThrows
    public String toString(Object o) {
        final var baos = new ByteArrayOutputStream();
        final var oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    @SneakyThrows
    public Function<Double, Double> functionFromString(String s) {
        return (Function<Double, Double>) fromString(s);
    }

    private Object fromString(String s) throws IOException, ClassNotFoundException {
        final var data = Base64.getDecoder().decode(s);
        final var ois = new ObjectInputStream(new ByteArrayInputStream(data));
        final var o = ois.readObject();
        ois.close();
        return o;
    }

    @SneakyThrows
    public List<Double> listFromString(String s) {
        return (List<Double>) fromString(s);
    }

    @SneakyThrows
    public Comparator<Double> comparatorFromString(String s) {
        return (Comparator<Double>) fromString(s);
    }
}
