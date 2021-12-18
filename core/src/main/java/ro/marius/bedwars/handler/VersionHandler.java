package ro.marius.bedwars.handler;

import org.bukkit.Bukkit;
import ro.marius.bedwars.VersionWrapper;
import ro.marius.bedwars.utils.ServerVersion;

public class VersionHandler {

    private final String versionName;
    private ServerVersion serverVersion;
    private VersionWrapper versionWrapper;

    public VersionHandler(){
        this.versionName = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        this.setupVersionNMS();
    }

    private void setupVersionNMS() {

        if (this.versionName == null) {
            return;
        }

        switch (this.versionName) {

            case "v1_16_R3":
                this.serverVersion = ServerVersion.v1_16_R3;
                this.versionWrapper = new v1_16_R3();
                break;
            case "v1_16_R2":
                this.serverVersion = ServerVersion.v1_16_R2;
                this.versionWrapper = new v1_16_R2();
                break;
            case "v1_16_R1":
                this.serverVersion = ServerVersion.v1_16_R1;
                this.versionWrapper = new v1_16_R1();
                break;
            case "v1_15_R1":
                this.serverVersion = ServerVersion.v1_15_R1;
                this.versionWrapper = new v1_15_R1();
                break;
            case "v1_14_R1":
                this.serverVersion = ServerVersion.v1_14_R1;
                this.versionWrapper = new v1_14_R1();
                break;
            case "v1_13_R2":
                this.serverVersion = ServerVersion.v1_13_R2;
                this.versionWrapper = new v1_13_R2();
                break;
            case "v1_12_R1":
                this.serverVersion = ServerVersion.v1_12_R1;
                this.versionWrapper = new v1_12_R1();
                break;
            case "v1_11_R1":
                this.serverVersion = ServerVersion.v1_11_R1;
                this.versionWrapper = new v1_11_R1();
                break;
            case "v1_10_R1":
                this.serverVersion = ServerVersion.v1_10_R1;
                this.versionWrapper = new v1_10_R1();
                break;
            case "v1_9_R1":
                this.serverVersion = ServerVersion.v1_9_R1;
                this.versionWrapper = new v1_9_R1();
                break;
            case "v1_9_R2":
                this.serverVersion = ServerVersion.v1_9_R2;
                this.versionWrapper = new v1_9_R2();
                break;
            case "v1_8_R3":
                this.serverVersion = ServerVersion.v1_8_R3;
                this.versionWrapper = new v1_8_R3();
                break;
        }

    }

}
