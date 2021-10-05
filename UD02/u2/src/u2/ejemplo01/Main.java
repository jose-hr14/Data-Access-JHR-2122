package u2.ejemplo01;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
