package main.constantdata;

import main.core.Room;
import main.database.DatabaseManager;

import java.util.HashMap;

public class RoomsList {
     public static final HashMap<String, Room> rooms = DatabaseManager.getRooms();;

}
