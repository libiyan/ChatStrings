import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4; 

import java.util.*;


@RunWith(JUnit4.class)
public class ChatStringsTest {

    private ChatStrings chatStrings;

    @Before
    public void setUp() throws Exception { 
        chatStrings = new ChatStrings();
    }

    @Test
    public void testNullString() {
        try {
            String json = chatStrings.getAnnotations(null);
            assertTrue("Failure - Passing null should have thrown an exception", false);
        } catch (IllegalArgumentException e) {
            assertTrue("This will always be true", true);
        }
    }

    @Test
    public void testEmptyString() {
        String json = chatStrings.getAnnotations("");
        assertEquals("Passing \"\" should return an empty object", "{}", json);
    }

    @Test
    public void testStringNoCharsString() {
        String json = chatStrings.getAnnotations("   ");
        assertEquals("Passing \"   \" should return an empty object", "{}", json);
    }

    @Test
    public void testOnlyNumbers() {
        String json = chatStrings.getAnnotations("837 8 -1 10,000");
        assertEquals("Passing numbers returns an empty object", "{}", json);
    }

    @Test
    public void testOnlySymbols() {
        String json = chatStrings.getAnnotations("&(@&( @) !| \\ ^");
        assertEquals("Passing symbols should return an empty object", "{}", json);
    }

    @Test
    public void testNumbersAndSymbols() {
        String json = chatStrings.getAnnotations("&(5 @53&( @) 2!| 4\\ ^6");
        assertEquals("Passing numbers and symbols should return an empty object", "{}", json);
    }

    @Test
    public void testNumbersAndChars() {
        String json = chatStrings.getAnnotations("b5 5g3 25 5g3 988hsh hshq99");
        assertEquals("Passing numbers and symbols should return an empty object", "{}", json);
    }

    @Test
    public void testStringWithOneMention() {
        String json = chatStrings.getAnnotations("blah blah @ralph blah");
        assertEquals("Should return one mention", "{\"mentions\":[\"ralph\"]}", json);
    }
    
    @Test
    public void testStringWithMultipleMention() {
        String json = chatStrings.getAnnotations("blah blah @ralph blah @mike @john");
        assertEquals("Should return multiple mentions", "{\"mentions\":[\"ralph\",\"mike\",\"john\"]}", json);
    }

    @Test
    public void testEmoticon() {
        String json = chatStrings.getAnnotations("blah blah (ralph) blah");
        assertEquals("Should return one mention", "{\"emoticons\":[\"ralph\"]}", json);
    }
    
    @Test
    public void testMultipleEmoticon() {
        String json = chatStrings.getAnnotations("blah blah (ralph) (john) blah");
        assertEquals("Should return one mention", "{\"emoticons\":[\"ralph\",\"john\"]}", json);
    }
    
    @Test
    public void testMultipleEmoticonNoSpace() {
        String json = chatStrings.getAnnotations("blah blah (ralph)(john) blah");
        assertEquals("Should return one mention", "{\"emoticons\":[\"ralph\",\"john\"]}", json);
    }
    
    @Test
    public void testEmoticonWithMoreThan15Chars() {
        String json = chatStrings.getAnnotations("blah blah (ralphisareallylongemoticonsoitshouldcomeup) blah");
        assertEquals("Should return one mention", "{}", json);
    }
    
    @Test
    public void testUrl() {
        String json = chatStrings.getAnnotations("Olympics are starting soon; http://www.nbcolympics.com");
        assertEquals("Should return one mention", "{\"links\":[{\"url\":\"http://www.nbcolympics.com\",\"title\":\"2014 Sochi Olympic Winter Games | NBC Olympics\"}]}", json);
    }
    
    @Test
    public void testMultipleUrls() {
        String json = chatStrings.getAnnotations("Olympics are starting soon; http://www.nbcolympics.com such a cool feature; https://twitter.com/jdorfman/status/430511497475670016");
        assertEquals("Should return one mention", "{\"links\":[{\"url\":\"http://www.nbcolympics.com\",\"title\":\"2014 Sochi Olympic Winter Games | NBC Olympics\"},{\"url\":\"https://twitter.com/jdorfman/status/430511497475670016\",\"title\":\"Twitter / jdorfman: nice @littlebigdetail from ...\"}]}", json);
    }
    
    @Test
    public void testMentionsEmoticonsUrls() {
        String json = chatStrings.getAnnotations("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016");
        assertEquals("Should return one mention", "{\"mentions\":[\"bob\",\"john\"],\"emoticons\":[\"success\"],\"links\":[{\"url\":\"https://twitter.com/jdorfman/status/430511497475670016\",\"title\":\"Twitter / jdorfman: nice @littlebigdetail from ...\"}]}", json);
    }

    @After
    public void tearDown() throws Exception { 
    }
}