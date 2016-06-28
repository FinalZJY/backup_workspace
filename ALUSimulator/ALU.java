/**
 * 模拟ALU进行整数和浮点数的四则运算
 * 151250212_朱俊毅
 *
 */

public class ALU {
	public int[] toArray(String s) {
		int result[]=new int[s.length()];
		for(int i=0;i<s.length();i++){
			result[i]=s.charAt(i)-'0';
		}
		return result;
	}
	
	public String toString(int[] i) {
		String result="";
		for(int j:i){
			result=result+j;
		}
		return result;
	}
	
	public int and(int a,int b) {//a，b做与运算
		if(a==0 || b==0){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	public int or(int a,int b) {//a，b做或运算
		if(a==1 || b==1){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	public int or(int a,int b,int c) {//a，b，c做或运算
		return or(or(a, b), c);
	}
	
	public int xor(int a,int b) {//a，b做异或运算
		if(a==b){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	public int not(int a) {//a，b做非运算
		int result=a==0?1:0;
		return result;
	}
	
	public int[] negation(int[] original) {//取反加1
		int[] result=new int[original.length];
		for(int i=0;i<original.length;i++){
			result[i]=original[i]==0?1:0;
		}
		result=add1(result);
		return result;
	}
	
	public int[] add1(int[] regional) {//加1
		int[] result=regional.clone();
		result[result.length-1]++;
		for(int i=result.length-1;i>=0;i--){
			while(result[i]>1){
				result[i]=result[i]-2;
				if(i>0){
					result[i-1]=result[i-1]+1;
				}
			}
		}
		return result;
	}
	
	public String leftDecimalPoint(String operand) {//左移小数点
		return operand.substring(0,operand.indexOf(".")-1)+"."+
				operand.substring(operand.indexOf(".")-1,operand.indexOf("."))+
				operand.substring(operand.indexOf(".")+1);
	}
	
	public String rightDecimalPoint(String operand) {//右移小数点
		return operand.substring(0,operand.indexOf("."))+
				operand.substring(operand.indexOf(".")+1,operand.indexOf(".")+2)+"."+
				operand.substring(operand.indexOf(".")+2);
	}

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		boolean nagative=number.startsWith("-")?true:false;
		if(nagative){
			number=number.substring(1);
		}
		while(number.startsWith("0")){
			number=number.substring(1);
		}
		String result="";
		if(!number.equals("")){
			while(!number.equals("1")){
				int carry=0;
				for(int i=0;i<number.length();i++){
					if(i==number.length()-1){
						result=((number.charAt(i)-'0')%2)+result;
						number=number.substring(0, i)+((number.charAt(i)-'0'+carry*10)/2);
					}
					else {
						String s=number.substring(0, i)+((number.charAt(i)-'0'+carry*10)/2)+number.substring(i+1);
						carry=((number.charAt(i)-'0'+carry*10)%2);
						number=s;
					}
				}
				while(number.startsWith("0")){
					number=number.substring(1);
				}
			}
			result="1"+result;
		}
		
		while(result.length()<length){
			result="0"+result;
		}
		if(nagative){
			result=oneAdder(negation(result)).substring(1);
		}
		return result;
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		String sign="";
		if(number.startsWith("-")){
			sign="1";
			number=number.substring(1);
		}
		else {
			sign="0";
		}
		String[] strings=number.split("\\.");
		String integer=strings[0];
		String decimal;
		if(strings.length>1){
			decimal="0."+strings[1];
		}
		else{
			decimal="0.0";
		}
		
		String mantissa=integerRepresentation(integer, (int)Math.pow(2, eLength-1)+1);
		double decimal_10=Double.parseDouble(decimal);
		String decimal_2="";
		int count=0;
		while(decimal_10!=0.0 ){
			count++;
			decimal_10=decimal_10*2;
			if(decimal_10>=1){
				decimal_2=decimal_2+"1";
				decimal_10=decimal_10-1;
			}
			else{
				decimal_2=decimal_2+"0";
			}
		}
		mantissa=mantissa+"."+decimal_2;
		
		while (mantissa.startsWith("0")) {
			mantissa=mantissa.substring(1);			
		}
		int exponent=0;
		
		if(Double.parseDouble(number)==0.0){
			exponent=0;
			mantissa="";
			for(int i=0;i<sLength;i++){
				mantissa=mantissa+"0";
			}
		}
		else {
			while (!mantissa.startsWith("1.")) {
				if(mantissa.startsWith("1")){
					mantissa=leftDecimalPoint(mantissa);
					exponent++;
				}
				else if (mantissa.startsWith(".")) {
					mantissa=rightDecimalPoint(mantissa);
					exponent--;
				}
				while (mantissa.startsWith("0")) {
					mantissa=mantissa.substring(1);			
				}
			}
			mantissa=mantissa.substring(2);
			while(mantissa.length()<sLength){
				mantissa=mantissa+"0";
			}
			if(mantissa.length()>sLength){
				mantissa=mantissa.substring(0, sLength);
			}
			
			exponent=(int) (exponent+Math.pow(2, eLength-1)-1);
			if(exponent>=(int) Math.pow(2, eLength)-1){
				exponent=(int) Math.pow(2, eLength)-1;
				mantissa="";
				for(int i=0;i<sLength;i++){
					mantissa=mantissa+"0";
				}
			}
			else if (exponent<=0) {
				mantissa="1"+mantissa.substring(0,mantissa.length());
				exponent++;
				while(exponent<1){
					mantissa=logRightShift(mantissa, 1);
					exponent++;
				}
				exponent=0;
			}
		}
		String result=sign+integerRepresentation(Integer.toString(exponent), eLength+1).substring(1)+mantissa;
		
		return result;
	}
	
	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		if(length==32){
			return floatRepresentation(number, 8, 23);
		}
		else if (length==64) {
			return floatRepresentation(number, 11, 52);
		}
		return null;
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		String result="";
		int[] original=toArray(operand);		
		if(original[0]==1){
			original=negation(original);
			result=result+"-";
		}
		for(int i=0;i<original.length-1;i++){
			original[i+1]=original[i+1]+original[i]*2;
		}
		result=result+original[original.length-1];
		return result;
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		String result="";
		if(operand.startsWith("1")){
			result="-";
		}
		int exponent=Integer.parseInt(integerTrueValue("0"+operand.substring(1,eLength+1)));
		String mantissa=operand.substring(eLength+1);
		double sNum=0;
		for(int i=mantissa.length()-1;i>=0;i--){
			sNum=sNum*0.5;
			if(mantissa.charAt(i)=='1'){
				sNum++;
			}
		}
		sNum=1+sNum*0.5;
		
		if(exponent==0){
			if(sNum==1.0){
				result="0";
			}
			else {
				sNum--;
				result=result+Math.pow(2, -Math.pow(2, eLength-1)-2);
			}
		}
		else if (exponent==(int)Math.pow(2, eLength)-1) {
			if(sNum==1.0){
				if(result.startsWith("-")){
					result="-Inf";
				}
				else {
					result="+Inf";
				}
			}
			else {
				result="NaN";
			}
		}
		else {
			exponent=exponent-(int)Math.pow(2, eLength-1)+1;
			result=result+Math.pow(2, exponent)*sNum;
		}
		return result;
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		int[] original=toArray(operand);
		int[] result=new int[original.length];
		for(int i=0;i<original.length;i++){
			result[i]=original[i]==0?1:0;
		}
		return toString(result);
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		if(n>operand.length()){
			String result="";
			for(int i=0;i<operand.length();i++){
				result=result+"0";
			}
			return result;
		}
		String result=operand.substring(n, operand.length());
		for(int i=0;i<n;i++){
			result=result+"0";
		}
		return result;
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		if(n>operand.length()){
			String result="";
			for(int i=0;i<operand.length();i++){
				result=result+"0";
			}
			return result;
		}
		String result=operand.substring(0, operand.length()-n);
		for(int i=0;i<n;i++){
			result="0"+result;
		}
		return result;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		if(n>operand.length()){
			String result="";
			for(int i=0;i<operand.length();i++){
				result=result+operand.charAt(0);
			}
			return result;
		}
		String result=operand.substring(0, operand.length()-n);
		for(int i=0;i<n;i++){
			result=result.charAt(0)+result;
		}
		return result;
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		int thisBit=xor(xor(x-'0', y-'0'), c-'0');
		int nextBit=or(and(x-'0', y-'0'), and(x-'0', c-'0'),and(c-'0', y-'0'));
		String result=""+nextBit+thisBit;
		return result;
	}
	
	/**
	 * 4位先行进位加法器。要求采用{@link #fullAdder(char, char, char) fullAdder}来实现<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		int[] x=toArray(operand1);
		int[] y=toArray(operand2);
		int[] result=new int[operand1.length()+1];
		for(int i=1;i<result.length;i++){
			int C0=c-'0';
			result[result.length-1]=fullAdder((char)(x[x.length-1]+'0'), (char)(y[y.length-1]+'0'), (char)(C0+'0')).charAt(1)-'0';
			int Pi=or(x[x.length-i], y[y.length-i]);
			int Gi=x[x.length-i]*y[y.length-i];
			int Ci=Gi;
			for(int j=i-1;j>=0;j--){
				int Gj;
				if(j==0){
					Gj=C0;
				}
				else {
					Gj=x[x.length-j]*y[y.length-j];
				}
				int coefficient=1;
				for(int k=j+1;k<=i;k++){
					int Pk=or(x[x.length-k], y[y.length-k]);
					coefficient=coefficient*Pk;
				}
				Ci=or(Ci, coefficient*Gj);
			}
			if(i==result.length-1){
				result[0]=Ci;
			}
			else{
				result[result.length-i-1]=fullAdder((char)(x[x.length-i-1]+'0'), (char)(y[y.length-i-1]+'0'), (char)(Ci+'0')).charAt(1)-'0';
			}		
		}
		return toString(result);
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要采用与门、或门、异或门等模拟，
	 * 不可以直接调用{@link #fullAdder(char, char, char) fullAdder}、
	 * {@link #claAdder(String, String, char) claAdder}、
	 * {@link #adder(String, String, char, int) adder}、
	 * {@link #integerAddition(String, String, int) integerAddition}方法。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		int[] integer_2=new int[operand.length()+1];
		for(int i=1;i<operand.length()+1;i++){
			integer_2[i]=operand.charAt(i-1)-'0';
		}
		integer_2[0]=integer_2[1];
		int s=1;
		for(int i=operand.length();i>0;i--){
			int lastS=s;
			s=and(integer_2[i], lastS);
			integer_2[i]=xor(integer_2[i], lastS);
		}
		integer_2[0]=and(not(integer_2[0]), integer_2[1]);
		String result="";
		for(int i:integer_2){
			result=result+i;
		}
		return result;
	}
	
	/**
	 * 加法器，要求调用{@link #claAdder(String, String, char)}方法实现。<br/>
	 * 例：adder("0100", "0011", ‘0’, 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param c 最低位进位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String adder (String operand1, String operand2, char c, int length) {
		while (operand1.length()<length) {
			operand1=operand1.charAt(0)+operand1;			
		}
		while (operand2.length()<length) {
			operand2=operand2.charAt(0)+operand2;			
		}
		String result=claAdder(operand1, operand2, c).substring(1);
		if(operand1.startsWith("0") && operand2.startsWith("0") && result.startsWith("1")){
			result="1"+result;
		}
		else if (operand1.startsWith("1") && operand2.startsWith("1") && result.startsWith("0")) {
			result="1"+result;
		}
		else {
			result="0"+result;
		}
		return result;
	}
	
	/**
	 * 整数加法，要求调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数减法，可调用{@link #adder(String, String, char, int) adder}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		operand2=toString(negation(toArray(operand2)));
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #adder(String, String, char, int) adder}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		while (operand1.length()<length) {
			operand1=operand1.charAt(0)+operand1;			
		}
		while (operand2.length()<length) {
			operand2=operand2.charAt(0)+operand2;			
		}
		for(int i=0;i<=length;i++){
			operand2=operand2+"0";
		}
		String product=operand1+"0";
		while (product.length()<length*2+1) {
			product="0"+product;			
		}
		
		for(int count=0;count<length;count++){
			if(product.charAt(product.length()-1)-product.charAt(product.length()-2)==0){
				
			}
			else if(product.charAt(product.length()-1)-product.charAt(product.length()-2)==1){
				product=integerAddition(product, operand2, length*2+1).substring(1);
			}
			else {
				product=integerSubtraction(product, operand2, length*2+1).substring(1);
			}
			product=ariRightShift(product,1);
		}
		product=product.substring(1,product.length()-1);
		String result=product.substring(length-1);
		for(int i=0;i<length-1;i++){
			if(product.charAt(i)!=result.charAt(0)){
				result="1"+result;
				break;
			}
			if(i==length-2){
				result="0"+result;
			}
		}
	
		return result;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #adder(String, String, char, int) adder}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		while (operand1.length()<length) {
			operand1=operand1.charAt(0)+operand1;			
		}
		while (operand2.length()<length) {
			operand2=operand2.charAt(0)+operand2;			
		}
		String remainder=operand1;
		while (remainder.length()<length*2) {
			remainder=remainder.charAt(0)+remainder;			
		}
		remainder=remainder+"0";
		String divisor=operand2;
		while (divisor.length()<length*2+1) {
			divisor=divisor+"0";
			
		}
		
		for(int count=0;count<=length;count++){
			if(remainder.charAt(0)!=divisor.charAt(0)){
				remainder=integerAddition(remainder, divisor, length*2+1).substring(1);
			}
			else {
				remainder=integerSubtraction(remainder, divisor, length*2+1).substring(1);
			}
			if(remainder.charAt(0)==divisor.charAt(0)){
				remainder=remainder.substring(0,remainder.length()-1)+"1";
			}
			else {
				remainder=remainder.substring(0,remainder.length()-1)+"0";
			}
			if(count!=length){
				remainder=leftShift(remainder, 1);
			}
		}
		
		String quotient=remainder.substring(length+1);
		if(operand1.charAt(0)!=operand2.charAt(0)){
			quotient=oneAdder(quotient).substring(1);
		}
		if(remainder.charAt(0)!=operand1.charAt(0)){
			if(remainder.charAt(remainder.length()-1)=='0'){
				if(divisor.charAt(0)=='0'){
					remainder=integerAddition(remainder, divisor, length*2+1).substring(1);
				}
				else {
					remainder=integerSubtraction(remainder, divisor, length*2+1).substring(1);
				}
			}
			else{
				remainder=integerSubtraction(remainder, divisor, length*2+1).substring(1);
			}
		}
		remainder=remainder.substring(0,length);
		
		if(Integer.parseInt(integerTrueValue(remainder))==Integer.parseInt(integerTrueValue(operand2))){
			return "1"+quotient+remainder;
		}
		
		return "0"+quotient+remainder;
	}
	
	/**
	 * 带符号整数加法，可以调用{@link #adder(String, String, char, int) adder}等方法，
	 * 但不能直接将操作数转换为补码后使用{@link #integerAddition(String, String, int) integerAddition}、
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}来实现。<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		while (operand1.length()<=length) {
			operand1=operand1.charAt(0)+"0"+operand1.substring(1);			
		}
		while (operand2.length()<=length) {
			operand2=operand2.charAt(0)+"0"+operand2.substring(1);		
		}
		
		String result="";
		boolean overflow=false;
		if(operand1.charAt(0)==operand2.charAt(0)){
			result=adder("0"+operand1.substring(1), "0"+operand2.substring(1), '0', operand1.length()).substring(1);
			overflow=result.charAt(0)=='1'?true:false;
			result=operand1.charAt(0)+result.substring(1);
		}
		else {
			if(operand1.charAt(0)=='0'){
				result=adder("0"+operand1.substring(1), "0"+toString(negation(toArray(operand2.substring(1)))), '0', operand1.length()).substring(1);
			}
			else {
				result=adder("0"+toString(negation(toArray(operand1.substring(1)))), "0"+operand2.substring(1), '0', operand1.length()).substring(1);
			}
			if(result.charAt(0)=='1'){
				result="0"+result.substring(1);
			}
			else {
				result="1"+toString(negation(toArray(result.substring(1))));
			}
		}
		if(overflow){
			result="1"+result;
		}
		else {
			result="0"+result;
		}
		return result;
	}
	
	/**
	 * 浮点数加法，可调用{@link #signedAddition(String, String, int) signedAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		String sign1=operand1.substring(0,1);
		String sign2=operand2.substring(0,1);
		String exponent1=operand1.substring(1,eLength+1);
		String exponent2=operand2.substring(1,eLength+1);
		String mantissa1="1"+operand1.substring(eLength+1);
		String mantissa2="1"+operand2.substring(eLength+1);
		if(exponent1.charAt(1)==-1){
			mantissa1="0"+operand1.substring(eLength+1);
		}
		if(exponent2.charAt(1)==-1){
			mantissa2="0"+operand2.substring(eLength+1);
		}
		
		if(exponent1.indexOf("0")==-1 || exponent2.indexOf("0")==-1){
			String NaN="0";
			while (NaN.length()<2+eLength+sLength) {
				NaN=NaN+"1";
			}
			return NaN;
		}
		
		if(floatTrueValue(operand1, eLength, sLength).equals("0")){
			return "0"+operand2;
		}
		else if(floatTrueValue(operand2, eLength, sLength).equals("0")){
			return "0"+operand1;
		}
		
		String zero="";
		while (zero.length()<eLength+sLength) {
			zero=zero+"0";
		}
		
		for(int i=0;i<gLength;i++){//加上保护位
			mantissa1=mantissa1+"0";
			mantissa2=mantissa2+"0";
		}
		
		while(!exponent1.equals(exponent2)){
			if(Integer.parseInt(integerTrueValue("0"+exponent1))<Integer.parseInt(integerTrueValue("0"+exponent2))){
				if(exponent1.charAt(1)==-1){
					exponent1=oneAdder(exponent1).substring(1);
				}
				else {
					exponent1=oneAdder(exponent1).substring(1);
					mantissa1=logRightShift(mantissa1, 1);
				}
				if(integerTrueValue(mantissa1).equals("0")){
					return "0"+operand2;
				}
			}
			else if (Integer.parseInt(integerTrueValue("0"+exponent1))>Integer.parseInt(integerTrueValue("0"+exponent2))) {
				if(exponent1.charAt(1)==-1){
					exponent2=oneAdder(exponent2).substring(1);
				}
				else {
					exponent2=oneAdder(exponent2).substring(1);
					mantissa2=logRightShift(mantissa2, 1);
				}
				if(integerTrueValue(mantissa2).equals("0")){
					return "0"+operand1;
				}
			}
			
		}
		
		String addResult=signedAddition(sign1+mantissa1, sign2+mantissa2, mantissa1.length());
		String mantissa=addResult.substring(2);
		String exponent=exponent1;
		String sign=""+addResult.charAt(1);
		if(integerTrueValue(mantissa).equals("0")){
			return "0"+sign+zero;
		}
		
		if (addResult.charAt(0)=='1') {
			mantissa="1"+mantissa.substring(0,mantissa.length()-1);
			char overflow=oneAdder("0"+exponent).charAt(0);
			exponent=oneAdder("0"+exponent).substring(2);
			if(overflow=='1'  || Integer.parseInt(integerTrueValue(exponent.substring(1)))==
					(int)Math.pow(2, eLength)-1){
				exponent="";
				while (exponent.length()<eLength) {
					exponent=exponent+"1";
				}
				mantissa="";
				while (mantissa.length()<sLength) {
					mantissa=mantissa+"0";
				}
				return "1"+sign+exponent+mantissa;
			}
		}
		
		while (!mantissa.startsWith("1") ) {
			if(integerTrueValue(exponent).equals("0")){
				break;
			}
			mantissa=leftShift(mantissa, 1);
			exponent=integerSubtraction(exponent, "1", exponent.length()).substring(1);
			
		}
		mantissa=mantissa.substring(1,mantissa.length()-gLength);//去掉保护位并隐藏开头的1
		
		return "0"+sign+exponent+mantissa;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		operand2=(operand2.charAt(0)=='0'?1:0)+operand2.substring(1);
		return floatAddition(operand1, operand2, eLength, sLength, gLength);
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerMultiplication(String, String, int) integerMultiplication}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		String zero="";
		while (zero.length()<eLength+sLength) {
			zero=zero+"0";
		}
		String NaN="0";
		while (NaN.length()<2+eLength+sLength) {
			NaN=NaN+"1";
		}
		String infinity="";
		while (infinity.length()<eLength+sLength) {
			while (infinity.length()<eLength) {
				infinity=infinity+"1";
			}
			infinity=infinity+"0";
		}
		
		
		String sign1=operand1.substring(0,1);
		String sign2=operand2.substring(0,1);
		String exponent1=operand1.substring(1,eLength+1);
		String exponent2=operand2.substring(1,eLength+1);
		String mantissa1="1"+operand1.substring(eLength+1);
		String mantissa2="1"+operand2.substring(eLength+1);
		if(exponent1.charAt(1)==-1){
			mantissa1="0"+operand1.substring(eLength+1);
		}
		if(exponent2.charAt(1)==-1){
			mantissa2="0"+operand2.substring(eLength+1);
		}
		
		if((exponent1.indexOf("0")==-1 && mantissa1.indexOf("1")!=-1)  ||  
				(exponent2.indexOf("0")==-1 && mantissa2.indexOf("1")!=-1) || 
				(exponent1.indexOf("1")==-1 && mantissa1.indexOf("1")==-1 && exponent2.indexOf("0")==-1 && mantissa2.indexOf("1")==-1) || 
				(exponent1.indexOf("0")==-1 && mantissa1.indexOf("1")==-1 && exponent2.indexOf("1")==-1 && mantissa2.indexOf("1")==-1)){
			return NaN;
		}
		
		if(floatTrueValue(operand1, eLength, sLength).equals("0")){
			return "0"+zero;
		}
		else if(floatTrueValue(operand2, eLength, sLength).equals("0")){
			return "0"+zero;
		}
		
		String addResult=signedAddition("0"+exponent1, "0"+exponent2, exponent1.length()+1);		
		int exponent=Integer.parseInt(integerTrueValue(addResult.substring(2)));
		if(exponent1.charAt(1)==-1){
			exponent++;
		}
		if(exponent2.charAt(1)==-1){
			exponent++;
		}
		exponent=exponent-(int)Math.pow(2, eLength-1)+1;
		if(exponent>=(int)Math.pow(2, eLength)-1){
			return "1"+xor(Integer.parseInt(sign1), Integer.parseInt(sign2))+infinity;
		}
		else if (exponent<=0) {
			return "0"+xor(Integer.parseInt(sign1), Integer.parseInt(sign2))+zero;
		}
		
		String mantissa=integerMultiplication("0"+mantissa1, "0"+mantissa2, mantissa1.length()*2).substring(1);
		if(mantissa.startsWith("1")){
			mantissa=logRightShift(mantissa, 1).substring(2, 2+sLength);
			exponent++;
		}
		else if(mantissa.startsWith("00")){
			while (!mantissa.startsWith("01")) {
				exponent--;
				if(exponent==0){
					break;
				}
				mantissa=leftShift(mantissa, 1);
			}
			mantissa=mantissa.substring(2, 2+sLength);
		}
		else {
			mantissa=mantissa.substring(2, 2+sLength);
		}
		String sign=""+xor(Integer.parseInt(sign1), Integer.parseInt(sign2));
		return "0"+sign+integerRepresentation(Integer.toString(exponent), eLength+1).substring(1)+mantissa;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerDivision(String, String, int) integerDivision}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		String zero="";
		while (zero.length()<eLength+sLength) {
			zero=zero+"0";
		}
		String NaN="0";
		while (NaN.length()<2+eLength+sLength) {
			NaN=NaN+"1";
		}
		String infinity="";
		while (infinity.length()<eLength+sLength) {
			while (infinity.length()<eLength) {
				infinity=infinity+"1";
			}
			infinity=infinity+"0";
		}
		
		String sign1=operand1.substring(0,1);
		String sign2=operand2.substring(0,1);
		String exponent1=operand1.substring(1,eLength+1);
		String exponent2=operand2.substring(1,eLength+1);
		String mantissa1="1"+operand1.substring(eLength+1);
		String mantissa2="1"+operand2.substring(eLength+1);
		if(exponent1.charAt(1)==-1){
			mantissa1="0"+operand1.substring(eLength+1);
		}
		if(exponent2.charAt(1)==-1){
			mantissa2="0"+operand2.substring(eLength+1);
		}
		
		if((exponent1.indexOf("0")==-1 && mantissa1.indexOf("1")!=-1)  ||  
				(exponent2.indexOf("0")==-1 && mantissa2.indexOf("1")!=-1) || 
				(exponent1.indexOf("1")==-1 && mantissa1.indexOf("1")==-1 && exponent2.indexOf("1")==-1 && mantissa2.indexOf("1")==-1) || 
				(exponent1.indexOf("0")==-1 && mantissa1.indexOf("1")==-1 && exponent2.indexOf("0")==-1 && mantissa2.indexOf("1")==-1)){
			return NaN;
		}
		
		if(exponent1.indexOf("1")==-1 && mantissa1.indexOf("1")==-1){
			return "0"+sign2+zero;
		}
		else if (exponent2.indexOf("1")==-1 && mantissa2.indexOf("1")==-1) {
			return "0"+sign1+infinity;
		}
		
		String subResult=signedAddition("0"+exponent1, "1"+exponent2, exponent1.length()+1);
		int exponent=Integer.parseInt(integerTrueValue("0"+subResult.substring(2)));
		if(subResult.charAt(1)=='1'){
			exponent=-exponent;
		}
		if(exponent1.charAt(1)==-1){
			exponent++;
		}
		if(exponent2.charAt(1)==-1){
			exponent--;
		}
		exponent=exponent+(int)Math.pow(2, eLength-1)-1;
		
		if(exponent>=(int)Math.pow(2, eLength)-1){
			return "1"+xor(Integer.parseInt(sign1), Integer.parseInt(sign2))+infinity;
		}
		else if (exponent<=0) {
			return "0"+xor(Integer.parseInt(sign1), Integer.parseInt(sign2))+zero;
		}
		

		String remainder=mantissa1;
		String divisor=mantissa2;
		String quotient="";
		while (quotient.length()<remainder.length()) {
			quotient=quotient+"0";
		}
		for(int j=0;j<remainder.length();j++){		
			boolean enough=true;
			for(int i=0;i<remainder.length();i++){
				if(remainder.charAt(i)>divisor.charAt(i)){
					enough=true;
					break;
				}
				else if (remainder.charAt(i)<divisor.charAt(i)) {
					enough=false;
					break;
				}
			}
			if(enough){
				remainder=integerSubtraction("0"+remainder, "0"+divisor, remainder.length()+1).substring(2);
				remainder=leftShift(remainder, 1);
				quotient=quotient.substring(1)+"1";
			}
			else {
				remainder=leftShift(remainder, 1);
				quotient=quotient.substring(1)+"0";
			}
		}
		String mantissa=quotient.substring(1);
		String sign=""+xor(Integer.parseInt(sign1), Integer.parseInt(sign2));
		
		return "0"+sign+integerRepresentation(Integer.toString(exponent), eLength+1).substring(1)+mantissa;
	}
}
