package vues;
<<<<<<< HEAD
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
=======
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
>>>>>>> 8fa03f0420f9aad87b07d0a2098d59d96b5e133e
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
		
<<<<<<< HEAD
		this.setOnMouseClicked( (e) -> {
		        if(e.getButton().equals(e.getButton().PRIMARY)){
		            if(e.getClickCount() == 2){
		                System.out.println("#todo renommage");
		            }
		        }
		    }
		);
		
=======
>>>>>>> 8fa03f0420f9aad87b07d0a2098d59d96b5e133e
	}
	
    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
<<<<<<< HEAD
        } else {
            setText(getItem() == null ? "" : getItem().toString());
        }
        setGraphic(null);
=======
            setGraphic(null);
        } else {
        	this.setGraphic(getTreeItem().getGraphic());
            setText(getItem() == null ? "" : getItem().toString());
        }
>>>>>>> 8fa03f0420f9aad87b07d0a2098d59d96b5e133e
    }
	
}
