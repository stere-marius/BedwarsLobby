package ro.marius.bedwars.sockets;

import java.util.Objects;

public class ServerInfo {

    private String serverName;
    private final String serverIP;
    private final int serverPort;

    public ServerInfo(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public ServerInfo(String serverIP, int serverPort, String serverName) {
        this(serverIP, serverPort);
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInfo that = (ServerInfo) o;
        return serverIP.equals(that.serverIP) && serverPort == that.serverPort;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverIP, serverPort);
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "serverName='" + serverName + '\'' +
                ", serverIP='" + serverIP + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }
}
