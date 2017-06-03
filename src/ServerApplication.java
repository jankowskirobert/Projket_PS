import com.projectnine.gui.MainUI;
import com.projectnine.logic.ServerService;
import com.projectnine.logic.ServerServiceStatus;

public class ServerApplication {
	public static void main(String[] args) {
		ServerService serverService = new ServerService();
		MainUI gui = new MainUI(serverService);
		serverService.setActualizer(gui.getTransferActualizer());
	}
}
