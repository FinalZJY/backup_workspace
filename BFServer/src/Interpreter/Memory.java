package Interpreter;

import java.util.ArrayList;

public class Memory extends ArrayList{
	public Memory() {
		super();
		add(0);
	}
	public char getChar(int index) {
		Object character=get(index);
		return (char)character;
	}
	public void ADD(int index) {
		int i=(int) get(index);
		set(index, i+1);
	}
	public void SUB(int index) {
		int i=(int) get(index);
		set(index, i-1);
	}

}
