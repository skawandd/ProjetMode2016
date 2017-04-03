package vues;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class TreeItemWithNode extends TreeCell<String> {

	TreeItemWithNode(){
		
	}
	
	public void setEventCallback(Callback cb){
		this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
			cb.call(e);
		});
		
		this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
			cb.call(e);
		});
		
		this.setOnMouseClicked( (e) -> {
		        if(e.getButton().equals(e.getButton().PRIMARY)){
		            if(e.getClickCount() == 2){
		                System.out.println("#todo renommage");
		            }
		        }
		    }
		);
		
	}
	
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
        } else {
            setText(getItem() == null ? "" : getItem().toString());
        }
        setGraphic(null);
    }
	
}
