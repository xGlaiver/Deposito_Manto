import java.util.ArrayList;
import java.util.List;

public class NewsLetter implements Subject{
    private List<Observer> observers = new ArrayList<>();
    private String topic = "";
    
    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for (Observer obs : observers) {
            obs.update(topic);
        }
    }

    public void setTopic(String newTopic){
        this.topic = newTopic;
        notifyObservers();
    }
}
