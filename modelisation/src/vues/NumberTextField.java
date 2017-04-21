package vues;

import javafx.scene.control.TextField;

public class NumberTextField extends TextField{

	boolean dot;
	boolean negative;
	
	public void setDot(boolean dot){
		this.dot = dot;
	}
	
	public void setNegative(boolean negative){
		this.negative = negative;
	}
	
	
    @Override
    public void replaceText(int start, int end, String text)
    {
    	String temp = this.getText().substring(0, start)+text+this.getText().substring(end);
        if (validate(temp))
        {
            super.replaceText(start, end, text);
        }
    }

    @Override
    public void replaceSelection(String text)
    {
        if (validate(text))
        {
            super.replaceSelection(text);
        }
    }

    private boolean validate(String text)
    {
    	String regex = "^"+(negative ? "-?" : "") + "(?:[0-9]" + (dot ? "|^[^\\.]+\\." : "") + ")*$";
    	return text.matches(regex);
    }
}