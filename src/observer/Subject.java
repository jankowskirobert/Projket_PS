package observer;
import java.util.ArrayList;
import java.util.List;

public class Subject {
	
	private final List<Listener> listeners = new ArrayList<Listener>();
	private boolean state = false;

	public void setState(){
		this.state = true;
	}
	
	public void notifyListeners(Object providedContent){
		if(state){
			for(Listener listener : listeners){
				listener.update(providedContent);
			}
			state = false;
		}
	}
	
	public void addListener(Listener listener){
		this.listeners.add(listener);
	}
	
	public boolean removeListener(Listener listener){
		return this.listeners.remove(listener);
	}
}
