public class ChatStringsExample {
 
    public static void main(String[] args) {

        ChatStrings chatStrings = new ChatStrings();
        String json = chatStrings.getAnnotations("@chris you around?");
        System.out.println("json = " + json);
        
        json = chatStrings.getAnnotations("@bob @john (success) such a cool feature");
        System.out.println("json = " + json + "\n");
        
        json = chatStrings.getAnnotations("Good morning! (megusta) (coffee)");
        System.out.println("json = " + json + "\n");
        
        json = chatStrings.getAnnotations("Olympics are starting soon; http://www.nbcolympics.com");
        System.out.println("json = " + json + "\n");
        
        json = chatStrings.getAnnotations("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016");
        System.out.println("json = " + json + "\n");
    }
}