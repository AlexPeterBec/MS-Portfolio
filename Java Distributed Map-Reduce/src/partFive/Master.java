package partFive;

public class Master {

    public void main(String[] args){
        ProcessBuilder pb = new ProcessBuilder("ls", "-al", "/tmp");
        Process p = pb.start();
    }
}
