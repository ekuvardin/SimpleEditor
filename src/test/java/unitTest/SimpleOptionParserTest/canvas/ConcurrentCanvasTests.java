package unitTest.SimpleOptionParserTest.canvas;

import canvas.ConcurrentCanvas;
import canvas.SimpleCanvas;
import org.junit.Assert;
import org.junit.Test;

public class ConcurrentCanvasTests {

    @Test
    public void constructorShouldSetFieldToSpace() {
        ConcurrentCanvas test = new ConcurrentCanvas(20, 4);
        for (int j = 1; j <= 4; j++) {
            for (int i = 1; i <= 20; i++) {
                Assert.assertEquals(' ', test.get(i, j));
            }
        }
    }
}
