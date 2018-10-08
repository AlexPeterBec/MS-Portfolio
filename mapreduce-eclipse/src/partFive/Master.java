package partFive;

import java.io.IOException;

public class Master {

    public void main(String[] args) throws IOException{
        ProcessBuilder pb = new ProcessBuilder("ls", "-al", "/tmp");
        Process p = pb.start();
    }
    
}