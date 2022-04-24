
import io.github.andersonstv.character.WoDCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WodCharacterTest {
    public WoDCharacter character;
    @Before
    public void setUp(){
        character = new WoDCharacter("Test");
    }
    @Test
    public void testDefault(){
        Assert.assertEquals(1, (int) character.getAttribute("intelligence"));
    }
    @Test
    public void testSkillCheck(){
        String check = character.check("medicine");
        Assert.assertTrue(check.contains("**Total Successes:** "));
    }
    @Test
    public void testSkillAttCheck(){
        String check = character.check("medicine", "intelligence");
        System.out.println();
        Assert.assertTrue(check.contains("**Total Successes:** "));
    }
    @Test
    public void testAttributeCheck(){
        String check = character.check("intelligence");
        Assert.assertTrue(check.contains("**Total Successes:** "));
    }
}
