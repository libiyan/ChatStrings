/**
 * This class would presumably have other function besides
 * this method, but since this is just an exercise...
 * R.Pina 04262014
 * 
 * Assumptions: 
 * 1) If a null chat string is passed to the function, we will throw an error.
 * 2) If an empty chat string is passed, it will return an empty JSON object "{}".
 * 3) We are only looking for the annotaions @mentions, (emoticonname), and "http://...",
 *    anything else that is passed will be ignored.
 * 4) @mentions - Always starts with an '@' and ends when hitting a non-word character.
 * 5) emoticons - Are letters, no longer than 15 characters, contained in parenthesis.
 *    I am ssuming they don't contain numbers or other ASCII symbols.
 */

import java.util.*;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

public class ChatStrings {
    
    private enum TypeOfAnnotation {
        MENTION,
        EMOTICON,
        LINK
    }
  
    /*
    * Data we'll be parsing
    */
    String data;

    public ChatStrings() {
    }

    public ChatStrings(String data) {
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
    
    /**
     * Method is passed a string describing a chat message. It will parse it
     * looking for @mentions, (emoticons), and http:// links.
     * Return: A JSON object
     */
    public String getAnnotations(String chatString) {
        // gson to convert objects into JSON
        Gson gson = new Gson();
        
        // GsonWrapper we will use to populate the JSON object
        GsonWrapper wrapper = new GsonWrapper();
    	
        // check out input
        // is it a null pointer?
        if (chatString == null) {
            throw new IllegalArgumentException("Argument 'chatString' is null, please pass a valid object.");
        }
        // is the String empty
        if (chatString.length() == 0) {
            return "{}";
        }
        
        parseString(chatString, wrapper);
        
        return gson.toJson(wrapper);
    }


    /**
    * Parse the String and return the GsonWrapper
    */
    private void parseString(String s, GsonWrapper wrapper) {
        StringBuilder builder;

        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);

            if (isAt(c)) {

                builder = new StringBuilder();

                // increment the index
                index++;

                // make sure c was not the last char
                // in the document
                if (inBounds(s, index)) {

                    c = s.charAt(index);

                    // while next char is a letter
                    // keep adding it to the
                    // StringBuilder to get our word
                    while (isAChar(c)) {
                        
                        builder.append(c);           
                        index++;
                        if (inBounds(s, index)) {
                            c = s.charAt(index);
                        } else {
                            break;
                        }
                    }
      
                    if (builder.length() > 0) {
                        String newMention = builder.toString();
                        wrapper.setMention(newMention);
                    }
                }
            }
            
            if (isStartOfEmoticon(c)) {
                
                // use to mark if we encounter
                // text that breaks the emoticon rules
                boolean validEmoticon = true;
                
                builder = new StringBuilder();
                
                // increment the index
                index++;
                
                // make sure c was not the last char
                // in the document
                if (inBounds(s, index)) {
                    
                    c = s.charAt(index);
                    
                    // while next char is a letter
                    // keep adding it to the
                    // StringBuilder to get our emoticon
                    while (isAChar(c)) {
                        
                        builder.append(c);
                        index++;
                        if (inBounds(s, index)) {
                            c = s.charAt(index);
                        } else {
                            break;
                        }
                    }
                    
                    // if c is not a char, it must be the close param
                    if (!isEndOfEmoticon(c)) {
                        validEmoticon = false;
                    }
                    
                    if (builder.length() > 0 && validEmoticon) {
                        String newEmoticon = builder.toString();
                        wrapper.setEmoticon(newEmoticon);
                    }
                }
            }
            
            if (s.startsWith("http", index)) {
                
                builder = new StringBuilder();
                
                // add the first char
                builder.append(c);
                
                // increment the index
                index++;
                
                // make sure c was not the last char
                // in the document
                if (inBounds(s, index)) {
                    
                    c = s.charAt(index);
                    
                    // while next char is not a space
                    // keep adding it to the
                    // StringBuilder to get our url
                    while (isNotSpace(c)) {
                        
                        builder.append(c);
                        index++;
                        if (inBounds(s, index)) {
                            c = s.charAt(index);
                        } else {
                            break;
                        }
                    }
                    
                    if (builder.length() > 0) {
                        String newLink = builder.toString();
                        wrapper.setLink(newLink);
                    }
                }
            }
        }
    }
    
//    private void parseAnnotation(String s, int index, TypeOfAnnotation type)

    private boolean isAt(char c) {
        return c == '@';
    }
    
    private boolean isStartOfEmoticon(char c) {
        return c == '(';
    }
    
    private boolean isEndOfEmoticon(char c) {
        return c == ')';
    }
    
    private boolean isNotSpace(char c) {
        return c != ' ';
    }
    
    private boolean inBounds(String s, int index) {
        return index < s.length();
    }

    private boolean isAChar(char c) {
        return ( (65 <= c && c <= 90) || (97 <= c && c <= 122) );
    }

    private boolean isANumber(char c) {
        return 48 <= c && c <= 57;
    }

    private boolean isACharOrNum(char c) {
        return isAChar(c) || isANumber(c);
    }

    private boolean isASymbol(char c) {
        return ( (33 <= c && c <= 47) || (58 <= c && c <= 64) 
            || (91 <= c && c <= 96) || (123 <= c && c <= 126));
    }

    private boolean isACharOrNumOrSym(char c) {
        return isAChar(c) || isANumber(c) || isASymbol(c);
    }

    /**
     * Private inner utility class to use GSON converter
     */
    private class GsonWrapper {
        @SerializedName("mentions")
        private ArrayList<String> mentions;
        
        @SerializedName("emoticons")
        private ArrayList<String> emoticons;
        
        @SerializedName("links")
        private ArrayList<Link> links;
        
        public void setMention(String mention) {
            if (mentions == null) {
                mentions = new ArrayList<String>();
            }
            mentions.add(mention);
        }
        
        public ArrayList<String> getMentions() {
            return mentions;
        }
        
        public void setEmoticon(String emoticon) {
            if (emoticons == null) {
                emoticons = new ArrayList<String>();
            }
            emoticons.add(emoticon);
        }
        
        public ArrayList<String> getEmoticons() {
            return emoticons;
        }
        
        public void setLink(String url) {
            if (links == null) {
                links = new ArrayList<Link>();
            }
            
            // TODO add link
            Link link = new Link();
            link.setUrl(url);
            
            // sometimes the title will be a property,
            // otherwise I need to get the HTML tag <title>
            boolean setTitle = false;
            
            // get the title
            try {
                Document doc = Jsoup.connect(url).get();
                for (Element meta : doc.select("meta")) {
                    if (meta.attr("property").equals("og:title") || meta.attr("property").equals("title")) {
                        link.setTitle(meta.attr("content"));
                        setTitle = true;
                        break;
                    }
                }
                if (!setTitle) {
                    Element title = doc.getElementsByTag("title").first();
                    link.setTitle(title.text());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            links.add(link);
        }
        
        public ArrayList<Link> getLinks() {
            return links;
        }
    }
    
    /**
     * Private class to represent a link
     */
    private class Link {
        @SerializedName("url")
        private String url;
        
        @SerializedName("title")
        private String title;
        
        public void setUrl(String url) {
            this.url = url;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getTitle() {
            return title;
        }
    }
}