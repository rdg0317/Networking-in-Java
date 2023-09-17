class protocol {

    public static void main(String[] args) {
        String message = " Hello! This is Rama. How are you? I am from lucknow Hello! This is Rama. How are you? I am from lucknow Hello! This is Rama. How are you? I am from lucknow";
        applicationLayer(message);
    }

    public static void applicationLayer(String message) {
        System.out.println("\nApplication Layer      :" + message);
        presentationLayer(message);
    }

    public static void presentationLayer(String message) {
        String header = "PRH ";
        String messageWithHeader = addHeader(header, message);
        System.out.println("\nPresentation Layer     :" + messageWithHeader);
        sessionLayer(messageWithHeader);
    }

    public static void sessionLayer(String message) {
        String header = "SSH ";
        String messageWithHeader = addHeader(header, message);
        System.out.println("\nSession Layer          :" + messageWithHeader);
        transportLayer(messageWithHeader);
    }

    public static void transportLayer(String message) {
        String header = "TRH ";
        String messageWithHeader = addHeader(header, message);
        System.out.println("\nTransport Layer        :" + messageWithHeader);
        networkLayer(messageWithHeader);
    }

    public static void networkLayer(String message) {
        String header = "NTH ";
        String messageWithHeader = addHeader(header, message);
        System.out.println("\nNetwork Layer          :" + messageWithHeader);
        dataLinkLayer(messageWithHeader);
    }

    public static void dataLinkLayer(String message) {
        String header = "DLLH ";
        String trailer = "DLLT ";
        String messageWithHeader = addHeader(header, message);
        String messageWithTail = addHeader(messageWithHeader, trailer);
        System.out.println("\nData Link Layer        :" + messageWithTail);
        physicalLayer(messageWithTail);
    }

    public static void physicalLayer(String message) {
        String header = "PHH ";
        String messageWithHeader = addHeader(header, message);
        System.out.println("\nPhysical Layer         :" + messageWithHeader);
    }

    public static String addHeader(String header, String message) {
        if (header.length() > 64) {
            throw new IllegalArgumentException("Header length exceeds 64 characters.");
        }

        if ((header + message).length() > 64) {
            throw new IllegalArgumentException("Total message length exceeds 64 characters.");
        }

        return header + message;
    }
}
