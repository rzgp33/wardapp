package world.thebestapps.boc.wardapp.patient;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by boc on 11/09/17.
 */

public class PatientContent extends AppCompatActivity {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<PatientItem> ITEMS = new ArrayList<PatientItem>();

    public static final Map<String, PatientItem> ITEM_MAP = new HashMap<String, PatientItem>();

    public static void PatientsFromLoop() {

        for (int i = 1; i <= 10; i++) {
                Random myrand = new Random();
                PatientItem p = new PatientItem(String.valueOf(myrand.nextInt(1000)), // PatientID
                        "firstName", // firstName
                        "lastName", // lastName
                        "imageURL", // imageURL
                        "diagnosis", // diagnosis
                        (MessageFormat.format("{0,time, HH:mm}", new Date())));

                ITEMS.add(p);
                ITEM_MAP.put(p.patientID, p);
            }
    }



    public static void PatientsFromFile(Context context) throws IOException {

        String line;
        String dataFile = "patients.dat";

        AssetManager am = context.getAssets();


        BufferedReader in  = new BufferedReader(new InputStreamReader(context.getAssets().open(dataFile)));

        while ((line = in.readLine()) != null) {
            PatientItem p = PatientItem.createPatient(line);
            if (p != null) {
                ITEMS.add(p);
                ITEM_MAP.put(p.patientID, p);
            }
        }

    }



    public static class PatientItem {

        public final String patientID;
        public final String firstName;
        public final String lastName;
        public final String fullName;
        public final String imageURL;
        public final String diagnosis;
        public final String timeEntered;


        /**
         * no argument constructor
         */
        public PatientItem() {
            this.patientID = "patientID";
            this.firstName = "firstName";
            this.lastName = "lastName";
            this.fullName = firstName + " " + lastName;
            this.imageURL = "imageURL";
            this.diagnosis = "diagnosis";
            this.timeEntered = MessageFormat.format("{0,time, HH:mm}", new Date());
        }


        public PatientItem(String patientID, String firstName, String lastName, String imageURL, String diagnosis, String timeEntered) {
            this.patientID = patientID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.fullName = firstName + " " + lastName;
            this.imageURL = imageURL;
            this.diagnosis = diagnosis;
            this.timeEntered = timeEntered;
        }

        public static PatientItem createPatient(String line) {
            StringTokenizer theTokens = new StringTokenizer(line, ",");

            if (theTokens.countTokens() == 6) {
                PatientItem newPatient = new PatientItem((theTokens.nextToken().trim()), // PatientID
                                                         (theTokens.nextToken().trim()), // firstName
                                                         (theTokens.nextToken().trim()), // lastName
                                                         (theTokens.nextToken().trim()), // imageURL
                                                         (theTokens.nextToken().trim()), // diagnosis
                                                         (theTokens.nextToken().trim())); // timeEntered
                return newPatient;
            } else {
                return null;
            }
        }

        /**
         *
         * @return text string representation of Patient object
         */
        @Override
        public String toString() {
            return "Patient{" + ", patientID=" + patientID + ", firstName=" + firstName + ", lastName=" + lastName + ", imageURL=" + imageURL + ", diagnosis=" + diagnosis + ", timeEntered=" + timeEntered + "}";
        }

        /**
         *
         * @return text string containing patient details separated by commas
         */
        public String toCSV() {
            return patientID + "," + firstName + "," + lastName + "," + imageURL + "," + diagnosis + "," + timeEntered;
        }

    }



}

