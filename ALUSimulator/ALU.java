/**
 * ģ��ALU���������͸���������������
 * 151250212_�쿡��
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
	
	public int and(int a,int b) {//a��b��������
		if(a==0 || b==0){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	public int or(int a,int b) {//a��b��������
		if(a==1 || b==1){
			return 1;
		}
		else{
			return 0;
		}
	}
	
	public int or(int a,int b,int c) {//a��b��c��������
		return or(or(a, b), c);
	}
	
	public int xor(int a,int b) {//a��b���������
		if(a==b){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	public int not(int a) {//a��b��������
		int result=a==0?1:0;
		return result;
	}
	
	public int[] negation(int[] original) {//ȡ����1
		int[] result=new int[original.length];
		for(int i=0;i<original.length;i++){
			result[i]=original[i]==0?1:0;
		}
		result=add1(result);
		return result;
	}
	
	public int[] add1(int[] regional) {//��1
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
	
	public String leftDecimalPoint(String operand) {//����С����
		return operand.substring(0,operand.indexOf(".")-1)+"."+
				operand.substring(operand.indexOf(".")-1,operand.indexOf("."))+
				operand.substring(operand.indexOf(".")+1);
	}
	
	public String rightDecimalPoint(String operand) {//����С����
		return operand.substring(0,operand.indexOf("."))+
				operand.substring(operand.indexOf(".")+1,operand.indexOf(".")+2)+"."+
				operand.substring(operand.indexOf(".")+2);
	}

	/**
	 * ����ʮ���������Ķ����Ʋ����ʾ��<br/>
	 * ����integerRepresentation("9", 8)
	 * @param number ʮ������������Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʋ����ʾ�ĳ���
	 * @return number�Ķ����Ʋ����ʾ������Ϊlength
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
	 * ����ʮ���Ƹ������Ķ����Ʊ�ʾ��
	 * ��Ҫ���� 0������񻯡����������+Inf���͡�-Inf������ NaN�����أ������� IEEE 754��
	 * �������Ϊ��0���롣<br/>
	 * ����floatRepresentation("11.375", 8, 11)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return number�Ķ����Ʊ�ʾ������Ϊ 1+eLength+sLength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
	 * ����ʮ���Ƹ�������IEEE 754��ʾ��Ҫ�����{@link #floatRepresentation(String, int, int) floatRepresentation}ʵ�֡�<br/>
	 * ����ieee754("11.375", 32)
	 * @param number ʮ���Ƹ�����������С���㡣��Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
	 * @param length �����Ʊ�ʾ�ĳ��ȣ�Ϊ32��64
	 * @return number��IEEE 754��ʾ������Ϊlength���������ң�����Ϊ���š�ָ���������ʾ����β������λ���أ�
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
	 * ��������Ʋ����ʾ����������ֵ��<br/>
	 * ����integerTrueValue("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ
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
	 * ���������ԭ���ʾ�ĸ���������ֵ��<br/>
	 * ����floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return operand����ֵ����Ϊ���������һλΪ��-������Ϊ������ 0�����޷���λ����������ֱ��ʾΪ��+Inf���͡�-Inf���� NaN��ʾΪ��NaN��
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
	 * ��λȡ��������<br/>
	 * ����negation("00001001")
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @return operand��λȡ���Ľ��
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
	 * ���Ʋ�����<br/>
	 * ����leftShift("00001001", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand����nλ�Ľ��
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
	 * �߼����Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand�߼�����nλ�Ľ��
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
	 * �������Ʋ�����<br/>
	 * ����logRightShift("11110110", 2)
	 * @param operand �����Ʊ�ʾ�Ĳ�����
	 * @param n ���Ƶ�λ��
	 * @return operand��������nλ�Ľ��
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
	 * ȫ����������λ�Լ���λ���мӷ����㡣<br/>
	 * ����fullAdder('1', '1', '0')
	 * @param x ��������ĳһλ��ȡ0��1
	 * @param y ������ĳһλ��ȡ0��1
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ��ӵĽ�����ó���Ϊ2���ַ�����ʾ����1λ��ʾ��λ����2λ��ʾ��
	 */
	public String fullAdder (char x, char y, char c) {
		int thisBit=xor(xor(x-'0', y-'0'), c-'0');
		int nextBit=or(and(x-'0', y-'0'), and(x-'0', c-'0'),and(c-'0', y-'0'));
		String result=""+nextBit+thisBit;
		return result;
	}
	
	/**
	 * 4λ���н�λ�ӷ�����Ҫ�����{@link #fullAdder(char, char, char) fullAdder}��ʵ��<br/>
	 * ����claAdder("1001", "0001", '1')
	 * @param operand1 4λ�����Ʊ�ʾ�ı�����
	 * @param operand2 4λ�����Ʊ�ʾ�ļ���
	 * @param c ��λ�Ե�ǰλ�Ľ�λ��ȡ0��1
	 * @return ����Ϊ5���ַ�����ʾ�ļ����������е�1λ�����λ��λ����4λ����ӽ�������н�λ��������ѭ�����
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
	 * ��һ����ʵ�ֲ�������1�����㡣
	 * ��Ҫ�������š����š�����ŵ�ģ�⣬
	 * ������ֱ�ӵ���{@link #fullAdder(char, char, char) fullAdder}��
	 * {@link #claAdder(String, String, char) claAdder}��
	 * {@link #adder(String, String, char, int) adder}��
	 * {@link #integerAddition(String, String, int) integerAddition}������<br/>
	 * ����oneAdder("00001001")
	 * @param operand �����Ʋ����ʾ�Ĳ�����
	 * @return operand��1�Ľ��������Ϊoperand�ĳ��ȼ�1�����е�1λָʾ�Ƿ���������Ϊ1������Ϊ0��������λΪ��ӽ��
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
	 * �ӷ�����Ҫ�����{@link #claAdder(String, String, char)}����ʵ�֡�<br/>
	 * ����adder("0100", "0011", ��0��, 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param c ���λ��λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
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
	 * �����ӷ���Ҫ�����{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerAddition("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����ӽ��
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * �����������ɵ���{@link #adder(String, String, char, int) adder}����ʵ�֡�<br/>
	 * ����integerSubtraction("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ļ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ��������
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		operand2=toString(negation(toArray(operand2)));
		return adder(operand1, operand2, '0', length);
	}
	
	/**
	 * �����˷���ʹ��Booth�㷨ʵ�֣��ɵ���{@link #adder(String, String, char, int) adder}�ȷ�����<br/>
	 * ����integerMultiplication("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊlength+1���ַ�����ʾ����˽�������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������lengthλ����˽��
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
	 * �����Ĳ��ָ������������ɵ���{@link #adder(String, String, char, int) adder}�ȷ���ʵ�֡�<br/>
	 * ����integerDivision("0100", "0011", 8)
	 * @param operand1 �����Ʋ����ʾ�ı�����
	 * @param operand2 �����Ʋ����ʾ�ĳ���
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ���ĳ���������ĳ���С��lengthʱ����Ҫ�ڸ�λ������λ
	 * @return ����Ϊ2*length+1���ַ�����ʾ�������������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0�������lengthλΪ�̣����lengthλΪ����
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
	 * �����������ӷ������Ե���{@link #adder(String, String, char, int) adder}�ȷ�����
	 * ������ֱ�ӽ�������ת��Ϊ�����ʹ��{@link #integerAddition(String, String, int) integerAddition}��
	 * {@link #integerSubtraction(String, String, int) integerSubtraction}��ʵ�֡�<br/>
	 * ����signedAddition("1100", "1011", 8)
	 * @param operand1 ������ԭ���ʾ�ı����������е�1λΪ����λ
	 * @param operand2 ������ԭ���ʾ�ļ��������е�1λΪ����λ
	 * @param length ��Ų������ļĴ����ĳ��ȣ�Ϊ4�ı�����length��С�ڲ������ĳ��ȣ����������ţ�����ĳ���������ĳ���С��lengthʱ����Ҫ���䳤����չ��length
	 * @return ����Ϊlength+2���ַ�����ʾ�ļ����������е�1λָʾ�Ƿ���������Ϊ1������Ϊ0������2λΪ����λ����lengthλ����ӽ��
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
	 * �������ӷ����ɵ���{@link #signedAddition(String, String, int) signedAddition}�ȷ���ʵ�֡�<br/>
	 * ����floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����ӽ�������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
		
		for(int i=0;i<gLength;i++){//���ϱ���λ
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
		mantissa=mantissa.substring(1,mantissa.length()-gLength);//ȥ������λ�����ؿ�ͷ��1
		
		return "0"+sign+exponent+mantissa;
	}
	
	/**
	 * �������������ɵ���{@link #floatAddition(String, String, int, int, int) floatAddition}����ʵ�֡�<br/>
	 * ����floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ļ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param gLength ����λ�ĳ���
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ�������������е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		operand2=(operand2.charAt(0)=='0'?1:0)+operand2.substring(1);
		return floatAddition(operand1, operand2, eLength, sLength, gLength);
	}
	
	/**
	 * �������˷����ɵ���{@link #integerMultiplication(String, String, int) integerMultiplication}�ȷ���ʵ�֡�<br/>
	 * ����floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
	 * �������������ɵ���{@link #integerDivision(String, String, int) integerDivision}�ȷ���ʵ�֡�<br/>
	 * ����floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 �����Ʊ�ʾ�ı�����
	 * @param operand2 �����Ʊ�ʾ�ĳ���
	 * @param eLength ָ���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @param sLength β���ĳ��ȣ�ȡֵ���ڵ��� 4
	 * @return ����Ϊ2+eLength+sLength���ַ�����ʾ����˽��,���е�1λָʾ�Ƿ�ָ�����磨���Ϊ1������Ϊ0��������λ����������Ϊ���š�ָ���������ʾ����β������λ���أ����������Ϊ��0����
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
