/**
 * Author: Orchlon Chinbat
 * Student ID: 50291063
 * Description: This program demonstrates the Factory Method Pattern by creating a logistics system that can deliver cargo by road, sea, or air.
 */

public class FactoryMethodDemo {
    public static void main(String[] args) {
        // Demonstrate with three different creators (factories).
        Logistics road = new RoadLogistics();
        Logistics sea  = new SeaLogistics();
        Logistics air  = new AirLogistics();

        System.out.println("Road logistics ");
        road.planDelivery("Standard cargo", "San Francisco");
        System.out.println();

        System.out.println("Sea logistics ");
        sea.planDelivery("Machinery", "Singapore");
        System.out.println();

        System.out.println("Air logistics");
        air.planDelivery("Medical supplies", "Berlin");
        System.out.println();

        if (args.length > 0) {
            Logistics chosen = pick(args[0]);
            System.out.println("Chosen logistics (" + args[0] + ") ");
            chosen.planDelivery("Expedited cargo", "Tokyo");
        }
    }

    private static Logistics pick(String mode) {
        String m = mode.toLowerCase();
        if (m.equals("road")) return new RoadLogistics();
        if (m.equals("sea"))  return new SeaLogistics();
        return new AirLogistics();
    }
}

// Product interface
interface Transport {
    void load(String cargo);
    void deliver(String destination);
}

// Concrete products
class Truck implements Transport {
    public void load(String cargo) {
        System.out.println("Truck: loading " + cargo);
    }
    public void deliver(String destination) {
        System.out.println("Truck: delivering by road to " + destination);
    }
}

class Ship implements Transport {
    public void load(String cargo) {
        System.out.println("Ship: loading " + cargo);
    }
    public void deliver(String destination) {
        System.out.println("Ship: delivering by sea to " + destination);
    }
}

class Plane implements Transport {
    public void load(String cargo) {
        System.out.println("Plane: loading " + cargo);
    }
    public void deliver(String destination) {
        System.out.println("Plane: delivering by air to " + destination);
    }
}

// Creator (declares factory method)
abstract class Logistics {
    // Factory Method: subclasses override to select the product to create.
    protected abstract Transport createTransport();

    // Business logic that relies on the product created by the factory method.
    public void planDelivery(String cargo, String destination) {
        Transport t = createTransport();
        System.out.println("Planning delivery...");
        t.load(cargo);
        t.deliver(destination);
    }
}

// Concrete creators (override the factory method)
class RoadLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Truck();
    }
}

class SeaLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Ship();
    }
}

class AirLogistics extends Logistics {
    @Override
    protected Transport createTransport() {
        return new Plane();
    }
}
