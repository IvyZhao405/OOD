package real_life_object;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

enum Direction{
    Up, Down
}

enum Status{
    UP, DOWN, IDLE
}

class Request{
    protected int level;
    public Request(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }
}

class InternalRequest extends Request{

    public InternalRequest(int level) {
        super(level);
    }
}

class ExternalRequest extends Request{
    private Direction direction;
    public ExternalRequest(int level, Direction d) {
        super(level);
        this.direction = d;
    }
    public Direction getDirection(){
        return direction;
    }
}

class ElevatorButton{
    private int level;
    private Elevator elevator;
    public ElevatorButton(int level, Elevator elevator) {
        this.level = level;
        this.elevator = elevator;
    }
    public void pressButton() {
        InternalRequest request = new InternalRequest(level);
        elevator.handleInternalRequest(request);
    }
}

class InvalidExternalRequestException extends Exception {
    public InvalidExternalRequestException(String errorMessage){
        super(errorMessage);
    }
}

class OverWeightException extends Exception {
    public OverWeightException(String errorMessage){
        super(errorMessage);
    }
}

class Elevator{
    private List<ElevatorButton> buttons;
    private PriorityQueue<Integer> upStops;
    private PriorityQueue<Integer> downStops;
    private int currentLevel = 0;
    private Status status = Status.IDLE;
    private float weightLimit = 3000.0f;    //3000 ponds

    public Elevator(int n){
        buttons = new ArrayList<>();
        //min heap
        upStops = new PriorityQueue<>();
        downStops = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == o2) {
                    return 0;
                }
                return o1 > o2 ? -1 : 1;
            }
        });
    }

    public void handleExternalRequest(ExternalRequest r) {
        //If the request is for going up, and add level to upStops
        //And if there is no current down stop, change status to UP.
        //If the request is for going down, add level to downStop.
        //And if there is no current down stop,change status to down.
        if (r.getDirection() == Direction.Up) {
            upStops.add(r.level);
            if (downStops.size() == 0) {
                status = Status.UP;
            }
        } else {
            downStops.add(r.level);
            if(upStops.size() == 0) {
                status = Status.DOWN;
            }
        }
    }

    public void handleInternalRequest(InternalRequest r) {
        if (isRequestValid(r)) {
            if (status == status.DOWN){
                downStops.add(r.level);
            } else {
                upStops.add(r.level);
            }
        }
        //ignore invalid internal request
    }

    private boolean isRequestValid(InternalRequest r) {
        if (status == Status.DOWN) {
            if (r.getLevel() > currentLevel) {
                return false;
            }
        } else {
            if (r.getLevel() < currentLevel) {
                return false;
            }
        }
        return true;
    }

    private float getCurrentWeight() {
        //there should be a sensor measuring the weight
        return 1000.0f;
    }

    public void openGate(){
        if (status == Status.UP) {
            if (!upStops.isEmpty() && currentLevel == upStops.peek()) {
                upStops.poll();
                //open gate
            }
        } else {
            if (!downStops.isEmpty() && currentLevel == downStops.peek()) {
                downStops.poll();
                //open gate
            }
        }
    }

    public void closeGate() throws OverWeightException {
        if (getCurrentWeight() > weightLimit) {
            throw new OverWeightException("Elevator is over weight limit, cannot close door!");
        }

        if (status == Status.UP) {
            if (upStops.isEmpty()) {
                if (!downStops.isEmpty()) {
                    status = Status.DOWN;
                } else {
                    status = Status.IDLE;
                }
            }
        } else if (status == Status.DOWN) {
            if (downStops.isEmpty()) {
                if (!upStops.isEmpty()) {
                    status = Status.UP;
                } else {
                    status = Status.IDLE;
                }
            }
        }
    }
}
public class ElevatorSystem {
    //TODO: finish the implementation
    private List<Elevator> elevators;

    public void handleRequest(ExternalRequest r) throws InvalidExternalRequestException {
        boolean flag = true;
        if (true) {

        } else {
            throw new InvalidExternalRequestException("Invalid External Request");
        }
    }
}
