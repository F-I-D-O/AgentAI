import bwapi.*;
import bwta.BWTA;
import bwta.BaseLocation;

public class TestBot1 extends DefaultBWListener {

    private Mirror mirror = new Mirror();

    private Game game;

    private Player self;

    public void run() {
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    @Override
    public void onUnitCreate(Unit unit) {
        System.out.println("New unit " + unit.getType());
    }

    @Override
    public void onStart() {
        System.out.println("Analyzing map...");
        BWTA.readMap();
        BWTA.analyze();
        System.out.println("Map data ready");
        int i = 0;
        for (BaseLocation baseLocation : BWTA.getBaseLocations()) {
            System.out.println("Base location #" + (++i) + ". Printing location's region polygon:");
            for (Position position : baseLocation.getRegion().getPolygon().getPoints()) {
                System.out.print(position + ", ");
            }
            System.out.println();
        }
    }

    @Override
    public void onFrame() {
        game.drawTextScreen(10, 10, "Playing as " + self.getName() + " - " + self.getRace());
        StringBuilder units = new StringBuilder("My units:\n");
        for (Unit myUnit : self.getUnits()) {
            units.append(myUnit.getType()).append(" ").append(myUnit.getTilePosition()).append("\n");
            if (myUnit.getType() == UnitType.Terran_Command_Center && self.minerals() >= 50) {
                myUnit.train(UnitType.Terran_SCV);
            }
            if (myUnit.getType().isWorker() && myUnit.isIdle()) {
                Unit closestMineral = null;
                for (Unit neutralUnit : game.neutral().getUnits()) {
                    if (neutralUnit.getType().isMineralField()) {
                        if (closestMineral == null || myUnit.getDistance(neutralUnit) < myUnit.getDistance(closestMineral)) {
                            closestMineral = neutralUnit;
                        }
                    }
                }
                if (closestMineral != null) {
                    myUnit.gather(closestMineral, false);
                }
            }
        }
        game.drawTextScreen(10, 25, units.toString());
    }
}
