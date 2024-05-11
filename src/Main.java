import api.Data;
import gui.Welcome;

public class Main {
    private Data data;
    private Welcome welcome;
    public Main() {
        data  = new Data();
        data.load();
        welcome = new Welcome(data);
    }
    public static void main(String[] args) {
        Main main = new Main();
    }
}
