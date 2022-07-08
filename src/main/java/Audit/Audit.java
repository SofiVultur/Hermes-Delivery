package Audit;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

public class Audit {

    private static Audit instance;
    private final String filePath;

    private Audit(String filePath){

        this.filePath = filePath;
        File file = new File(filePath);

        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {

            String[] header = {"Action name", "timestamp"};
            writer.writeNext(header);


        } catch (IOException e) {
            System.out.println("CSV file not found!");
        }

    }

    public static Audit getInstance(String filePath) {
        if (instance == null) {
            instance = new Audit(filePath);
        }
        return instance;
    }

    public void writeToFile(){

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {
            String nameofCurrMethod = new Throwable().getStackTrace()[1].getMethodName();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String[] data = {nameofCurrMethod, String.valueOf(timestamp)};

            writer.writeNext(data);



        } catch (IOException e) {
            System.out.println("CSV file not found!");
        }
    }
}
