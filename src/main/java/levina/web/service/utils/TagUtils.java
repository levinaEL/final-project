package levina.web.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by MY on 28.08.2016.
 */
public class TagUtils {

    public static boolean isContains(List<?> coll, Object o) {
        if(coll == null){
            coll = new ArrayList<>();
        }
        return coll.contains(o);
    }

    public static void add(Set<Long> setCollection, Long o){
        setCollection.add(o);
    }
}
