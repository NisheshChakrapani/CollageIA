package collageia;

import java.util.Comparator;

/**
 * Created by nishu on 2/4/2017.
 */
public class BlueComparator implements Comparator<Picture> {
    @Override
    public int compare(Picture o1, Picture o2) {
        return Integer.compare(o1.getBlue(), o2.getBlue());
    }
}
