import java.io.Serializable;

public class Ack implements Serializable {
    private int ackNumber;

    public Ack(int ackNumber) {
        this.ackNumber = ackNumber;
    }

    public int getAckNumber() {
        return ackNumber;
    }
}