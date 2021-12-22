package ro.marius.bedwars.sockets;

import java.util.Objects;

public class ServerInfo {

    private final String serverIP;
    private final String serverPort;

    public ServerInfo(String serverIP, String serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }


    public String getServerIP() {
        return serverIP;
    }

    public String getServerPort() {
        return serverPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInfo that = (ServerInfo) o;
        return serverIP.equals(that.serverIP) && serverPort.equals(that.serverPort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverIP, serverPort);
    }
}
