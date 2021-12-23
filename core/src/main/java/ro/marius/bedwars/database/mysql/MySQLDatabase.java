package ro.marius.bedwars.database.mysql;

import org.bukkit.plugin.java.JavaPlugin;
import ro.marius.bedwars.BedwarsLobbyPlugin;
import ro.marius.bedwars.database.Database;
import ro.marius.bedwars.playerdata.ArenaData;
import ro.marius.bedwars.ConsoleLogger;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MySQLDatabase implements Database {

    private Connection connection;
    private final String username;
    private final String database;
    private final String password;
    private final String host;
    private final int port;

    private static final int DATABASE_HEALTH_CHECK_RATE = 60 * 20;

    public MySQLDatabase(String username, String database, String password, String host, int port) {
        this.username = username;
        this.database = database;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    @Override
    public void createDatabase() {

        if ((database == null) || database.isEmpty()) {
            ConsoleLogger.sendError("&c&lERROR: Check your MySQL.Database from config.yml, it must have a name.");
            return;
        }

        String url = "jdbc:mysql://" + this.host + ":" + this.port + "?useSSQL=false";

        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, this.username, this.password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE DATABASE " + database);
            statement.close();
            connection.close();
            ConsoleLogger.sendSuccess("&a&lThe database " + database + " has been created.");
            setupDatabaseHealthCheck();
        } catch (ClassNotFoundException e) {
            ConsoleLogger.sendError("Unable to create the connection with MySQL database. Verify your MySQL information from config.yml.");
            e.printStackTrace();
        } catch (SQLException e) {

            if (e.getErrorCode() != 1007) {
                e.printStackTrace();
            }

        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setupDatabaseHealthCheck() {
        JavaPlugin javaPlugin = JavaPlugin.getPlugin(BedwarsLobbyPlugin.class);
        javaPlugin.getServer().getScheduler().runTaskTimerAsynchronously(javaPlugin, () -> {
            try {
                if ((connection != null) && !connection.isClosed()) {
                    connection.createStatement().execute("SELECT 1");
                }
            } catch (SQLException e) {
                createNewConnection();
            }
        }, DATABASE_HEALTH_CHECK_RATE, DATABASE_HEALTH_CHECK_RATE);
    }

    public void createNewConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/"
                    + this.database + "?autoReconnect=true&useSSL=false", this.username, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void openConnection() {
        try {
            if ((this.connection != null) && !this.connection.isClosed()) {
                return;
            }

            synchronized (this) {
                if ((this.connection != null) && !this.connection.isClosed()) {
                    return;
                }

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/"
                        + this.database + "?autoReconnect=true&useSSL=false", this.username, this.password);
            }
        } catch (SQLException e) {
            ConsoleLogger.sendError("Unable to create the connection with MySQL database. Verify your MySQL information from config.yml.");
            e.printStackTrace();
        }
    }

    public void closeConnection() {

        try {
            if ((this.connection == null) || this.connection.isClosed()) {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            this.connection.close();
        } catch (SQLException e) {
            ConsoleLogger.sendError("Couldn't close the connection.");
            e.printStackTrace();
        }

    }

    @Override
    public CompletableFuture<ArenaData> loadPlayerArenaData(UUID uuid, String arenaType) {
        CompletableFuture<ArenaData> completableFuture = new CompletableFuture<>();

        openConnection();

        String sql = "SELECT * FROM " + arenaType + " WHERE uuid = '" + uuid + "'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            boolean hasRecord = resultSet.next();

            if (hasRecord) {
                int gamesPlayed = resultSet.getInt("GamesPlayed");
                int bedsBroken = resultSet.getInt("BedsBroken");
                int bedsLost = resultSet.getInt("BedsLost");
                int kills = resultSet.getInt("Kills");
                int deaths = resultSet.getInt("Deaths");
                int finalKills = resultSet.getInt("FinalKills");
                int finalDeaths = resultSet.getInt("FinalDeaths");
                int wins = resultSet.getInt("Wins");
                int defeats = resultSet.getInt("Defeats");
                completableFuture.complete(
                        new ArenaData(
                                gamesPlayed,
                                bedsBroken,
                                bedsLost,
                                kills,
                                deaths,
                                finalKills,
                                finalDeaths,
                                wins,
                                defeats)
                );
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            closeConnection();
        }


        return completableFuture;
    }

}
