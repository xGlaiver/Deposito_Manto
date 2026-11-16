class NewsApplication implements Observer{
    private String topic;

    @Override
    public void update(String topic){
        this.topic = topic;
        display();
    }

    private void display(){
        System.out.println("App News: Topic updated - " + topic);
    }
}