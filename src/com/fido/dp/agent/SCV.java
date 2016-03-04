package com.fido.dp.agent;

import bwapi.Position;
import bwapi.Unit;
import com.fido.dp.Scout;
import com.fido.dp.action.Action;
import com.fido.dp.action.ExploreBaseLocation;
import com.fido.dp.action.HarvestMineralsAction;
import com.fido.dp.Material;

public class SCV extends LeafAgent implements Scout {

    @Override
    public void commandExploreBaseLocation(Position targetBase) {
        setCommandedAction(new ExploreBaseLocation(this, targetBase));
    }

    public SCV(Unit unit) {
        super(unit);
    }

    public void commandHarvest(Material material) {
        if (material == Material.MINERALS) {
            setCommandedAction(new HarvestMineralsAction(this));
        }
    }

    @Override
    protected Action chooseAction() {
        return getCommandedAction();
    }

    public void commandHarvest() {
    }
}
