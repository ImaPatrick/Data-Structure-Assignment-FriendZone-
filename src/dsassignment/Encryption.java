package dsassignment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import utils.ConnectionUtils;

public class Encryption {

    
    
    private String sender, receiver;
    
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    
    public static String UNICODE_FORMAT = "UTF-8";
    public String encryptedMessage,decryptedMessage;
    LinkedList<String> list_encryptMessages = new LinkedList<>();
    private String message = ""; 
    private static Random r = new Random();
    private static int random_key;
    int decryptKey;
    
    public void encryptThisMessage(String message){
        
        LinkedList<String> list_doubleencryption = new LinkedList <>();
        //System.out.println(message);
        encrypt(message);
        
        //linkedlist.addNode(encrypt(message));
        //linkedlist.showList();

        try{
            SecretKey key = generateKey("AES");
            Cipher chipher;
            chipher = Cipher.getInstance("AES");
            
            byte[] encryptedData = encryptString(encrypt(message),key,chipher);
            String encryptedString = new String(encryptedData);
            //System.out.println(encryptedString);
            encryptedMessage = encryptedString;
            String decrypted = decryptString(encryptedData,key,chipher);
            decryptedMessage = decrypt(decrypted);
            //System.out.println(decrypted);
            //System.out.println(decrypt(decrypted));
        }
        catch (Exception e){
            System.out.println("Error");
        }
        
        registerEncryptedMessage(encryptedMessage);
        System.out.println(sender+" "+encryptedMessage+" "+receiver);
        System.out.println("Random key = "+getRandomKey());
    }
    
     public static SecretKey generateKey(String encryptionType){
        try{
            KeyGenerator keyGenerator = KeyGenerator.getInstance(encryptionType);
            SecretKey myKey = keyGenerator.generateKey();
            return myKey;
        }
        catch (Exception e){
            return null;
        }
    }
    
    public static byte[] encryptString(String dataToEncrypt, SecretKey myKey, Cipher cipher){
        try{
            byte[] text = dataToEncrypt.getBytes(UNICODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, myKey);
            byte[] textEncrypted = cipher.doFinal(text);
            
            return textEncrypted;
        }
        catch (Exception e){
            return null;
        }
    }
    
    public static String decryptString(byte[] dataToDecrypt, SecretKey myKey, Cipher cipher){
        try{
            cipher.init(Cipher.DECRYPT_MODE, myKey);
            byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
            String result = new String(textDecrypted);
            
            return result;
        }
        catch (Exception e){
            return null;
        }
    }
    
    public String decrypt(String a){
        String str = "";
        int key = getDecryptKey(a);
        char[] chars = a.toCharArray(); 
        for (char c : chars){
            c-=key;
            str += c;
        }
        return str;
    }
    
    public static String encrypt(String a){
        String str = "";
        int randomKey = r.nextInt(101);
        setRandomKey(randomKey);
        char[] chars = a.toCharArray();
        for (char c : chars){
            c+=randomKey;
            str += c;
        }
        return str;
    }
    
    public Encryption() throws Exception {
        con = ConnectionUtils.getConnection();
    }
    
    public void getSender(String text){
        sender = text;
    }
    
    public void getReceiver(String text){
        receiver = text;
    }
    
    public String getEncrypted(){
        return encryptedMessage;
    }
    
    public String getDecrypted(){
        return decryptedMessage;
    }
    
    private void registerEncryptedMessage(String encryptedMessage) {
        try {
            int randomKey = getRandomKey();
            String sql = "INSERT INTO `chat` (`sender_name`, `encrypted_messages`, `receiver_name`, `random_key`) VALUES (?,?,?,?)";

            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, encryptedMessage);
            preparedStatement.setString(3, receiver);
            preparedStatement.setInt(4, randomKey);
            
            int status = preparedStatement.executeUpdate();
            if (status < 0) {
                System.out.println("Failed");
            } else {
                System.out.println("Successful");
            }
        } catch (SQLException ex) {
            System.out.println("");
        }
    }
    
    public void getListMessages() throws Exception{
        Client cc = new Client(sender, receiver);
        String str = cc.getListMessages();
        System.out.println(str);
        String [] arr_message = message.split(" %% ");
        for(int i=0 ; i<arr_message.length ; i++){
            //encryptThisMessage(arr_message[i]);
            System.out.println(arr_message[i]+"--->");
            list_encryptMessages.addNode(arr_message[i]);
        }
    }
    
    public void setListMessages(String text){
        
        message += text;
    }
    
    public void launch(){
        for(int i=0 ; i<list_encryptMessages.length() ; i++){
            encryptThisMessage(list_encryptMessages.get(i));
        }
    }
    
    private static int getRandomKey() {
        return random_key;
    }

    private static void setRandomKey(int randomKey) {
        random_key = randomKey;
    }
    
    private int getDecryptKey(String encryptedMessage){
        try{
        String sql = "SELECT * FROM chat Where encrypted_messages = ?";

        
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, encryptedMessage);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                decryptKey = resultSet.getInt("random_key");
            } 

        } catch (SQLException ex) {
            System.err.println("");
            
        }
        return decryptKey;
    }
    
    
}
