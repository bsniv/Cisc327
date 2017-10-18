package DAL;

import com.google.gson.Gson;
import java.io.FileWriter;
import com.google.gson.reflect.TypeToken;

import SharedClasses.Transaction;
import SharedClasses.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * data class - responsble for data fetching and updating
 *
 */
public class Data {

    private static String filepath= "";
    private static String transactionFilePath="";
    public static void initUserFile(){
        Data.filepath = System.getProperty("user.dir")+"\\users.json";
        Data.transactionFilePath = System.getProperty("user.dir")+"\\transactions.txt";
        File newJsonFile = new File(Data.filepath);
        if (!newJsonFile.exists()) {
            try {
                Type type = new TypeToken<HashMap<String, User>>() {
                }.getType();
                Gson gson = new Gson();
                InputStream in = Data.class.getResourceAsStream("/Users/users.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                HashMap<String, User> userHashMap = gson.fromJson(reader, type);
                String jsonString = gson.toJson(userHashMap);
                newJsonFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(newJsonFile));
                writer.write(jsonString);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    

    /**
     * delete user by the given username
     * @param username
     */
    public static void deleteUser(String username){
        HashMap<String,User> userHashMap = loadUsers();
        HashMap<String,User> newUserHashMap = new HashMap<>();
        userHashMap.remove(username);
        for (String key : userHashMap.keySet()){
            User tmp = userHashMap.get(key);
            newUserHashMap.put(tmp.getUsername(), tmp);
            
        }
        writeUsers(newUserHashMap);
    }

    /**
     * loads all the users
     * @return
     */
    private static HashMap<String,User> loadUsers() {
        try {
            Type type = new TypeToken<HashMap<String, User>>() {
            }.getType();
            Gson gson = new Gson();
            BufferedReader reader = new BufferedReader(new FileReader(Data.filepath));
            HashMap<String, User> users = gson.fromJson(reader, type);
            return users;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * writes all the users hashmap to a json file
     * @param users
     */
    private static void writeUsers(HashMap<String,User> users) {
        try {
            Gson g = new Gson();
            String jsonstring = g.toJson(users);
            BufferedWriter writer = new BufferedWriter(new FileWriter(Data.filepath));
            writer.write(jsonstring);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * saves new user to the file
     * (not used for update exciting one)
     * @param user
     */
    public static void saveNewUser(User user) {
        HashMap<String, User> allUsers = loadUsers();
        if (allUsers==null)
        	allUsers = new HashMap<String,User>();
        allUsers.put(user.getUsername(), user);
        writeUsers(allUsers);
    }



    /**
     * loads specific User with given username
     * @param username - the username of the desired user
     * @return
     */
    public static User loadUser(String username){
        HashMap<String,User> users = loadUsers();
        if(users.keySet().contains((String)username)) {
            return users.get(username);
        }
        return null;
    }


    /**
     * writes all the Transactions LinkedList to the transaction file
     * @param transactions
     */
    public static void writeTransactions(LinkedList<Transaction> transactions) {
        try {
        	FileWriter writer = new FileWriter(transactionFilePath, false);
        	PrintWriter printWriter = new PrintWriter(writer);
        	for (Transaction t : transactions)
        		printWriter.printf("%s" + "%n", t.toString());
        	printWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



}
