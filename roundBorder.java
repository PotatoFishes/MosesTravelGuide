import javax.swing.border.Border;
import java.awt.*;

public class roundBorder
{
    public static class RoundedBorder implements Border {

        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 0, 0, 0);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(0, 0, width, height, radius, radius);
        }
    }

    public static class RectBorder implements Border {
//
//        private int width;
//        private int length;
//
//        public RectBorder(int width, int length) {
//            this.width = width;
//            this.length = length;
//        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRect(0, 0, width, height);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 0, 0);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
}
