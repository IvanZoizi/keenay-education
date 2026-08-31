package keenay.education.utils;

import org.springframework.stereotype.Component;

import java.util.List;

public class UtilsService {

    public static <T> boolean in(List<T> array, T elem) {
        for (T elements : array) {
            if (elements.equals(elem)) {
                return true;
            }
        }
        return false;
    }

}
