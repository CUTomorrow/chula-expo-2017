package cuexpo.cuexpo2017.dao;

/**
 * Created by dragonnight on 14/3/2560.
 */

public class EditAdultUser {
    private String name;
    private int age;
    private String gender;
    private String type;
    private String workerJob;

    public EditAdultUser(String name, int age, String gender, String type, String workerJob) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.workerJob = workerJob;
    }
}
