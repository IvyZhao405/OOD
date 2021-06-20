package management;

import java.util.ArrayList;
import java.util.List;

enum VehicleSize{
    Compact(1), Large(2);
    private final int size;
    VehicleSize(int size) {
        this.size = size;
    }

    public int getSize(){
        return size;
    }
}

abstract class Vehicle{
    public abstract VehicleSize getSize();
}

class Car extends Vehicle{
    public VehicleSize getSize(){
        return VehicleSize.Compact;
    }
}

class Truck extends Vehicle{
    public VehicleSize getSize(){
        return VehicleSize.Large;
    }
}

class ParkingSpot{
    private final VehicleSize size;
    private Vehicle currentVehicle;

    ParkingSpot(VehicleSize size) {
        this.size = size;
    }

    boolean fit(Vehicle v) {
        return currentVehicle == null && v.getSize().getSize() <= size.getSize();
    }

    void park(Vehicle v) {
        currentVehicle = v;
    }

    void leave() {
        currentVehicle = null;
    }

    Vehicle getVehicle() {
        return currentVehicle;
    }
}

class Level{
    private final List<ParkingSpot> spots;

    Level(int numOfSpots) {
        spots = new ArrayList<>();
        int i = 0;
        for (; i < numOfSpots / 2; i++) {
            spots.add(new ParkingSpot(VehicleSize.Compact));
        }
        for (; i < numOfSpots; i++) {
            spots.add(new ParkingSpot(VehicleSize.Large));
        }
    }

    boolean hasSpot(Vehicle v) {
        for (ParkingSpot s: spots) {
            if (s.fit(v)) {
                return true;
            }
        }
        return false;
    }

    boolean park(Vehicle v) {
        for (ParkingSpot s : spots) {
            if (s.fit(v)) {
                s.park(v);
                return true;
            }
        }
        return false;
    }

    boolean leave(Vehicle v) {
        for (ParkingSpot s: spots) {
            if (s.getVehicle() == v) {
                s.leave();
                return true;
            }
        }
        return false;
    }
}

public class ParkingLot {
    private final Level[] levels;

    public ParkingLot(int numLevels, int numSpotsPerLevel) {
        levels = new Level[numLevels];

        for (int i = 0; i < numLevels; i++) {
            levels[i] = new Level(numSpotsPerLevel);
        }
    }

    public boolean hasSpot(Vehicle v) {
        for (Level l : levels) {
            if (l.hasSpot(v)){
                return true;
            }
        }
        return false;
    }

    public boolean park(Vehicle v) {
        for (Level l : levels) {
            if (l.park(v)){
                return true;
            }
        }
        return false;
    }

    public boolean leave(Vehicle v) {
        for (Level l : levels) {
            if (l.leave(v)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ParkingLot lot = new ParkingLot(4, 10);
        List<Vehicle> list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            final Vehicle v = i % 2 == 0 ? new Car() : new Truck();
            list.add(v);
            boolean hasSpot = lot.hasSpot(v);
            if (i < 40) {
                assert hasSpot;
                assert lot.park(v);
            } else {
                assert !hasSpot;
                assert !lot.park(v);
            }
        }
        assert list.size() == 50;
        int i = 0;
        for (Vehicle v : list){
            assert i >= 40 || lot.leave(v);
            i++;
        }
    }
}
