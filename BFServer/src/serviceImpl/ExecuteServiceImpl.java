//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;

import Interpreter.Interpreter;
import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code, String param) throws RemoteException {
		Interpreter interpreter=new Interpreter();
		interpreter.setInput(param);
		interpreter.run(code);
		return interpreter.getOutput();
	}

	public static void main(String args[]) {
		ExecuteServiceImpl ex=new ExecuteServiceImpl();
		try {
			System.out.println(ex.execute(",>,,>++++++++[<------<------>>-]<<[>[>+>+<<-]>>[<<+>>-]<<<-]>>>++++++[<++++++++>-],<.>.", "2 4\n"));;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
