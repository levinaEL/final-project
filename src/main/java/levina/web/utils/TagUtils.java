package levina.web.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * TagUtils used in jsp for custom tag
 */
public class TagUtils {

    public static boolean isContains(Set<?> coll, Object o) {
        if(coll == null){
            coll = new HashSet<>();
        }
        return coll.contains(o);
    }

    public static void add(Set<Long> setCollection, Long o){
        setCollection.add(o);
    }
}
