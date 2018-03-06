package unitTest.SimpleOptionParserTest.canvas;

import canvas.SimpleModel;
import org.junit.Assert;
import org.junit.Test;

public class SimpleModelTests {

    @Test
    public void constructorShouldSetFieldToSpace() {
        SimpleModel test = new SimpleModel(20, 4);
        for (int j = 1; j <= 4; j++) {
            for (int i = 1; i <= 20; i++) {
                Assert.assertEquals(' ', test.get(i, j));
            }
        }
    }
}
