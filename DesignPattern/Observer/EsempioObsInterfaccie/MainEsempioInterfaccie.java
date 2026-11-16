public  class MainEsempioInterfaccie {
    public static void main(String[] args) {
            NewsLetter newsLetter = new NewsLetter();
            Observer newsApp = new NewsApplication();
    
            newsLetter.addObserver(newsApp);
    
            newsLetter.setTopic("Politica");
            newsLetter.setTopic("Cucina");

        }
}