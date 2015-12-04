//package FanManager.view;
//
//
//import com.sun.deploy.uitoolkit.ui.ConsoleController;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.PipedInputStream;
//import java.io.PipedOutputStream;
//import java.io.PrintStream;
//import java.util.ResourceBundle;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Console{
//private Console(ResourceBundle resourceBundle) throws IOException {
//FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Console.fxml"), resourceBundle);
//Parent root = (Parent) loader.load();
//controller = loader.getController();
//Scene scene = new Scene(root);
//stage = new Stage();
//stage.setScene(scene);
//show();
//}
//if (errorOutputThread != null) {
//errorOutputThread.interrupt();
//try {
//errorOutputThread.join();
//} catch (InterruptedException e) {
//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//}
//errorOutputThread = null;
//}
//
//if (outOutputThread != null) {
//outOutputThread.interrupt();
//try {
//outOutputThread.join();
//} catch (InterruptedException e) {
//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//}
//outOutputThread = null;
//}
//
//System.err.flush();
//System.out.flush();
//
//outPipedInputStream = new PipedInputStream();
//outPipedOutputStream = new PipedOutputStream(outPipedInputStream);
//System.setOut(new PrintStream(outPipedOutputStream));
//
//errorPipedInputStream = new PipedInputStream();
//errorPipedOutputStream = new PipedOutputStream(errorPipedInputStream);
//System.setErr(new PrintStream(errorPipedOutputStream));
//
//outOutputThread = new Thread(new ConsoleStream(outPipedInputStream, "OUT"));
//outOutputThread.setDaemon(true);
//outOutputThread.start();
//
//errorOutputThread = new Thread(new ConsoleStream(errorPipedInputStream, "ERROR"));
//errorOutputThread.setDaemon(true);
//errorOutputThread.start();
//
////    controller.appendText("Start Console");
//
//}
//
//public static Console getInstance(ResourceBundle resourceBundle) {
//if (console == null) {
//try {
//console = new Console(resourceBundle);
//} catch (IOException e) {
//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//}
//}
//return console;
//}
//
//public void show() {
//stage.show();
//}
//
//private class ConsoleStream implements Runnable {
//private ConsoleStream(InputStream in, String type) {
//inputStream = in;
//this.type = type;
//}
//
//public void run() {
//try {
//InputStreamReader is = new InputStreamReader(inputStream);
//BufferedReader br = new BufferedReader(is);
//String read = null;
//read = br.readLine();
//while(read != null) {
////                controller.appendText(read + "\n");
//read = br.readLine();
//}
//} catch (IOException e) {
//e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//}
////        controller.appendText("Thread" + type + "started");
//
//}
//
//private final InputStream inputStream;
//private String type;
//}
//
//private static Console console = null;
//private ConsoleController controller;
//private Stage stage;
//private PrintStream printStream;
//private PipedOutputStream customPipedOutputStream;
//private PipedOutputStream errorPipedOutputStream;
//private PipedOutputStream outPipedOutputStream;
//private PipedInputStream customPipedInputStream;
//private PipedInputStream errorPipedInputStream;
//private PipedInputStream outPipedInputStream;
//private Thread customOutputThread;
//private Thread outOutputThread;
//private Thread errorOutputThread;
//}