package main.constantdata;

import main.core.Room;
import main.core.Service;
import main.database.DatabaseManager;

import java.util.HashMap;

public class ServicesList {
    public static final HashMap<String, Service> services = DatabaseManager.getServices();
}
