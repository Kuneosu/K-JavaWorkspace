import java.sql.*;
import java.util.Scanner;
public class DBManager{
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement st =null;
		Scanner sc = new Scanner(System.in);
		
		try {
			// �����ͺ��̽� ���� ( Connection )
			Class.forName("com.mysql.cj.jdbc.Driver");	 
			// .cj. �� ���� Ŭ�����ε� ���� �ذ�									 database �̸� ����                    uses�̸�, �н�����
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mrp?serverTimezone=UTC","root","fkdls324"); // ?serverTimezone=UTC �� ���� serverTimezone ���� �ذ�
			// ���� ����
			// ���̺� �ҷ�����
			String sql = "INSERT INTO `mrp`.`main_tb` (`��ȭ����`, `�帣ID`, `����ID`, `���ID`, `�����⵵`, `����`, `������`, `����`, `�ֿ����`) VALUES (?,?,?,?,?,?,?,?,?);";
			// sql ������ ���� database�� table column ���� ������ �� �ִ�. ex ) INSERT INTO 'DATABASE_NAME'.'TABLE_NAME'('column1','column2') VALUES (?,?);";
			String qu = "select *from main_tb";
			// qu ������ ���� �ҷ��� table�� ������ �� �ִ�.
			// java statement ����
			Statement qt = con.createStatement();
			
			st = con.prepareStatement(sql);
			Boolean loop = true;
			String[] thing = {"��ȭ����","�帣ID","����ID","���ID","�����⵵","����","������","����","�ֿ����"};
			while(loop) {
				System.out.print("����� ����� �����ϼ���. 1: ������ �Է� / 2: ������ Ȯ�� / 3: ������ ���� / 99: �� ���̺� ���� / 0: �ý��� ���� : ");
				int check = sc.nextInt();
				switch(check) {
				case 1 :
					
					String[] s = new String[10] ;
					for(int i =0;i<10;i++) {
						if(i==0)
						{System.out.println("9���� ������ ������� �Է��ϼ���.");
						sc.nextLine();
						continue;
						}
						System.out.print(thing[i-1]+" : ");
						s[i-1]=sc.nextLine();
						st.setString(i, s[i-1]);
					}
						st.executeUpdate();	
						System.out.println("������ �Է� �Ϸ� !");
						break;
				case 2 :
					ResultSet rs = qt.executeQuery(qu);
					while(rs.next()) {
						int ID=rs.getInt("ID");	// _number ���̺��� ���� number �� ����
						String ��ȭ���� = rs.getString("��ȭ����");	// name ���̺��� ���� name �� ����
						String �帣ID = rs.getString("�帣ID");
						String ����ID = rs.getString("����ID");
						String ���ID = rs.getString("���ID");
						String �����⵵ = rs.getString("�����⵵");
						String ���� = rs.getString("����");
						String ������ = rs.getString("������");
						String ���� = rs.getString("����");
						String �ֿ���� = rs.getString("�ֿ����");
						
						System.out.format("[%s]\n",ID);
						System.out.println(thing[0]+" : "+��ȭ����);
						System.out.println(thing[1]+" : "+�帣ID);
						System.out.println(thing[2]+" : "+����ID);
						System.out.println(thing[3]+" : "+���ID);
						System.out.println(thing[4]+" : "+�����⵵);
						System.out.println(thing[5]+" : "+����);
						System.out.println(thing[6]+" : "+������);
						System.out.println(thing[7]+" : "+����);
						System.out.println(thing[8]+" : "+�ֿ����+"\n");
					}
					break;
				case 3 :
					System.out.print("������ ���� ID ��ȣ�� �Է��Ͻÿ� : ");
					int delnum = sc.nextInt();
					String del = "DELETE FROM `mrp`.`main_tb` WHERE (`ID` = '����ȣ');";
					String ch = Integer.toString(delnum);
					String newdel = del.replace("����ȣ",ch);
					qt.executeUpdate(newdel);
					String reset = "SET @cnt = 0;";
					String reset1 = "UPDATE main_tb SET ID = @cnt:= @cnt+1; ";
					String reset2 = "ALTER TABLE main_tb AUTO_INCREMENT = 1;";
					qt.executeUpdate(reset);
					qt.executeUpdate(reset1);
					qt.executeUpdate(reset2);
					System.out.println("���� �Ϸ�!");
					break;
				case 99 :
					System.out.print("������ ���̺��� �̸��� �Է��Ͻÿ� : ");
					String tb_name = sc.next();
					String create = "CREATE TABLE `mrp`.`���̺��` (`ID` INT NOT NULL AUTO_INCREMENT,`��ȭ����` VARCHAR(45) NOT NULL,"
							+ "`�帣ID` INT NOT NULL,`����ID` INT NOT NULL,`���ID` INT NOT NULL,`�����⵵` VARCHAR(45) NOT NULL,`����` VARCHAR(45) NOT NULL,"
							+ "`������` VARCHAR(45) NOT NULL,`����` VARCHAR(45) NOT NULL,`�ֿ����` VARCHAR(45) NOT NULL, PRIMARY KEY (`ID`));";
					String newtb = create.replace("���̺��", tb_name);
					qt.execute(newtb);
					System.out.println("���̺� ���� �Ϸ�!");
					break;					
				case 0 :
					System.out.println("�ý����� �����մϴ�...");
					loop = false;
					break;
				}
				
			}
			sc.close();
			st.close();
			qt.close();
		}catch(Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}finally {
			if(st!=null)try {st.close();}catch(SQLException sqle) {}
			if(con!=null)try {con.close();}catch(SQLException sqle) {}
		}
	}
}