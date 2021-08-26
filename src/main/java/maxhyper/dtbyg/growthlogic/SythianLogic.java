package maxhyper.dtbyg.growthlogic;

import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.CoordUtils;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SythianLogic extends GrowthLogicKit {

    public SythianLogic(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    public int[] directionManipulation(World world, BlockPos pos, Species species, int radius, GrowSignal signal, int[] probMap) {
        Direction originDir = signal.dir.getOpposite();

        //Alter probability map for direction change
        probMap[0] = 0;//Down is always disallowed
        probMap[1] = signal.isInTrunk() ? species.getUpProbability() : 0;
        probMap[2] = probMap[3] = probMap[4] = probMap[5] = //Only allow turns when we aren't in the trunk(or the branch is not a twig and step is odd)
                !signal.isInTrunk() || (signal.isInTrunk() && signal.numSteps % 2 == 0) ? 1 : 0;
        probMap[originDir.ordinal()] = 0;//Disable the direction we came from

        return probMap;
    }

    private static final int threshold = 5;
    @Override
    public Direction newDirectionSelected(World world, BlockPos pos, Species species, Direction newDir, GrowSignal signal) {
        if (signal.isInTrunk() && newDir != Direction.UP) {//Turned out of trunk
            int y = signal.delta.getY();
            boolean extra = y > threshold && y < getEnergy(world, signal.rootPos, species, species.getSignalEnergy()) - threshold;
            signal.energy = 1.5f + (extra?1:0);
        }
        return newDir;
    }

    private float getHashedVariation (World world, BlockPos pos){
        long day = world.getGameTime() / 24000L;
        int month = (int)day / 30;//Change the hashs every in-game month
        return (CoordUtils.coordHashCode(pos.above(month), 2) % 7);//Vary the height energy by a psuedorandom hash function
    }

    @Override
    public float getEnergy(World world, BlockPos pos, Species species, float signalEnergy) {
        float energy = signalEnergy + getHashedVariation(world, pos);
        if (((int)energy) % 2 == 1) return energy + 1;
        return energy;
    }

    @Override
    public int getLowestBranchHeight(World world, BlockPos pos, Species species, int lowestBranchHeight) {
        return lowestBranchHeight;
    }
}
