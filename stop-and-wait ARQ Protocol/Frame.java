import java.io.Serializable;

public class Frame implements Serializable {
    private int sequenceNumber;
    private byte data;

    public Frame(int sequenceNumber, byte data) {
        this.sequenceNumber = sequenceNumber;
        this.data = data;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public byte getData() {
        return data;
    }
}