package levina.web.service.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MY on 28.08.2016.
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
