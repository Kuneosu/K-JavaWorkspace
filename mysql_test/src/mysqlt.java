import java.sql.*;	

public class mysqlt{
	public static void main(String[] args) {
		try {
			// �����ͺ��̽� ���� ( Connection )
			Class.forName("com.mysql.cj.jdbc.Driver");	 
			// .cj. �� ���� Ŭ�����ε� ���� �ذ�
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mrp?serverTimezone=UTC","root","fkdls324"); // ?serverTimezone=UTC �� ���� serverTimezone ���� �ذ�
			// ���� ����
			// ���̺� �ҷ�����
			String qu = "select *from main_table";
			// java statement ����
			Statement st = con.createStatement();
			// ���� execute, ��ü ����
			ResultSet rs = st.executeQuery(qu);
			// �� ���� �ݺ������� ��Ÿ��
			System.out.println("ID\t��ȭ����\t\t\t�帣\t\t���۱���\t�������\t�����⵵\t\t����\t������\t\t����\t�ֿ� ���");
			while(rs.next()) {
				int id=rs.getInt("ID");	// _number ���̺��� ���� number �� ����
				String movie_name = rs.getString("��ȭ����");	// name ���̺��� ���� name �� ����
				String genre = rs.getString("�帣");
				String country = rs.getString("���۱���");
				String grade = rs.getString("�������");
				String openyear = rs.getString("�����⵵");
				String score = rs.getString("����");
				String watch_person = rs.getString("������");
				String producer = rs.getString("����");
				String main_actor = rs.getString("�ֿ� ���");
				
				System.out.format("%s\t%s\t\t\t%s\t\t%s\t%s\t%s\t\t%s\t%s\t\t%s\t%s \n",id,movie_name,genre,country,grade,openyear,score,watch_person,producer,main_actor);
			}
			st.close();
		}catch(Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
}
