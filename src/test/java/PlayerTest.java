import io.github.andersonstv.character.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    public Player testPlayer;
    public Player testPlayer2;

    @Before
    public void setUp(){
        testPlayer = new Player("Usuariana");
        testPlayer2 = new Player("Usuariano");
    }

    @Test
    public void testGetCurrent(){
        Assert.assertNull(testPlayer.getCurrent());
    }
    @Test
    public void testSetCurrent(){
        testPlayer.createCharacter("JohnDoe");
        Assert.assertNull(testPlayer.getCurrent());
        testPlayer.setCurrent("JohnDoe");
        Assert.assertEquals("JohnDoe", testPlayer.getCurrent().getId());
    }
    @Test
    public void testCreateWoDCharacter(){
        testPlayer.createCharacter("JohnDoe");
        Assert.assertTrue(testPlayer.getCharacterMap().containsKey("JohnDoe"));
    }

    @Test
    public void testCheckNoCurrent(){
        Assert.assertEquals(testPlayer.check("Intelligence"), "No active character");
    }

}
