package maxhyper.dtbyg.cells.cell;

import com.ferreusveritas.dynamictrees.api.cells.ICell;
import net.minecraft.util.Direction;

/**
 * @author Harley O'Connor
 */
public final class BushyBranchCell implements ICell {

    @Override
    public int getValue() {
        return 5;
    }

    private static final int[] MAP = {6, 5, 6, 6, 6, 6};

    @Override
    public int getValueFromSide(Direction side) {
        return MAP[side.ordinal()];
    }

}
