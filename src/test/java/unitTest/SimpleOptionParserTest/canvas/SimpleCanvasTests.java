package unitTest.SimpleOptionParserTest.canvas;

import canvas.SimpleCanvas;
import org.junit.Assert;
import org.junit.Test;

public class SimpleCanvasTests {

    @Test
    public void constructorShouldSetFieldToSpace() {
        SimpleCanvas test = new SimpleCanvas(20, 4);
        for (int j = 1; j <= 4; j++) {
            for (int i = 1; i <= 20; i++) {
                Assert.assertEquals(' ', test.get(i, j));
            }
        }
    }
}
