package helper;

import java.util.Comparator;
import staticobjects.SolidEntity;

/**
 * Used for rendering order of world objects.
 *
 * @author DominicWild
 */
public class WorldSorter implements Comparator<SolidEntity> {

    @Override
    public int compare(SolidEntity o1, SolidEntity o2) {
        return o1.getLayer() - o2.getLayer();
    }

}
