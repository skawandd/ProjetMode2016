package vues;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
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
		
	}
	
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
        	this.setGraphic(getTreeItem().getGraphic());
            setText(getItem() == null ? "" : getItem().toString());
        }
    }
	
}
