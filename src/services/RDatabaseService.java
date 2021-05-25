package services;

import models.*;

public class RDatabaseService {
    private static RDatabaseService rDatabaseService;

    public static RDatabaseService GetRDatabaseService() {
        if (rDatabaseService == null) {
            rDatabaseService = new RDatabaseService();
        }
        return rDatabaseService;
    }

    public void readAllData(School school) {

    }

}
