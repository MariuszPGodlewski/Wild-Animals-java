
import javax.swing.SwingUtilities;
public class Main {
    public static void main(String[] args) {
        World game = new World();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow window = new MainWindow(game);
                window.show();
            }
        });

    }
}
