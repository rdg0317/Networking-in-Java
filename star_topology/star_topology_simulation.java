class Host {
    private String name;

    Host(String name) {
        this.name = name;
    }

    void sendMessage(String message) {
        System.out.println(name + " sent: " + message);
    }
}

class Switch {
    private Host[] hosts;

    Switch(Host[] hosts) {
        this.hosts = hosts;
    }

    void broadcastMessage(String message) {
        for (Host host : hosts) {
            host.sendMessage(message);
        }
    }
}

class StarTopologyExample {
    public static void main(String[] args) {
        Host host1 = new Host("Host 1");
        Host host2 = new Host("Host 2");
        Host host3 = new Host("Host 3");
        Host host4 = new Host("Host 4");

        Host[] hosts = {host1, host2, host3, host4};

        Switch centralHub = new Switch(hosts);

        // Simulate sending a message from one host to all other hosts
        centralHub.broadcastMessage("Hello, everyone!");
    }
}
