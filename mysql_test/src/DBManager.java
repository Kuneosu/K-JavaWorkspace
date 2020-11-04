import java.sql.*;
import java.util.Scanner;
public class DBManager{
	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement st =null;
		Scanner sc = new Scanner(System.in);
		
		try {
			// 데이터베이스 연결 ( Connection )
			Class.forName("com.mysql.cj.jdbc.Driver");	 
			// .cj. 을 통해 클래스로드 오류 해결									 database 이름 변경                    uses이름, 패스워드
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mrp?serverTimezone=UTC","root","fkdls324"); // ?serverTimezone=UTC 를 통해 serverTimezone 오류 해결
			// 쿼리 선택
			// 테이블 불러오기
			String sql = "INSERT INTO `mrp`.`main_tb` (`영화제목`, `장르ID`, `국가ID`, `등급ID`, `개봉년도`, `평점`, `관객수`, `감독`, `주연배우`) VALUES (?,?,?,?,?,?,?,?,?);";
			// sql 수정을 통해 database와 table column 들을 수정할 수 있다. ex ) INSERT INTO 'DATABASE_NAME'.'TABLE_NAME'('column1','column2') VALUES (?,?);";
			String qu = "select *from main_tb";
			// qu 수정을 통해 불러올 table을 수정할 수 있다.
			// java statement 생성
			Statement qt = con.createStatement();
			
			st = con.prepareStatement(sql);
			Boolean loop = true;
			String[] thing = {"영화제목","장르ID","국가ID","등급ID","개봉년도","평점","관객수","감독","주연배우"};
			while(loop) {
				System.out.print("사용할 기능을 선택하세요. 1: 데이터 입력 / 2: 데이터 확인 / 3: 데이터 삭제 / 99: 새 테이블 생성 / 0: 시스템 종료 : ");
				int check = sc.nextInt();
				switch(check) {
				case 1 :
					
					String[] s = new String[10] ;
					for(int i =0;i<10;i++) {
						if(i==0)
						{System.out.println("9가지 정보를 순서대로 입력하세요.");
						sc.nextLine();
						continue;
						}
						System.out.print(thing[i-1]+" : ");
						s[i-1]=sc.nextLine();
						st.setString(i, s[i-1]);
					}
						st.executeUpdate();	
						System.out.println("데이터 입력 완료 !");
						break;
				case 2 :
					ResultSet rs = qt.executeQuery(qu);
					while(rs.next()) {
						int ID=rs.getInt("ID");	// _number 테이블의 값을 number 에 저장
						String 영화제목 = rs.getString("영화제목");	// name 테이블의 값을 name 에 저장
						String 장르ID = rs.getString("장르ID");
						String 국가ID = rs.getString("국가ID");
						String 등급ID = rs.getString("등급ID");
						String 개봉년도 = rs.getString("개봉년도");
						String 평점 = rs.getString("평점");
						String 관객수 = rs.getString("관객수");
						String 감독 = rs.getString("감독");
						String 주연배우 = rs.getString("주연배우");
						
						System.out.format("[%s]\n",ID);
						System.out.println(thing[0]+" : "+영화제목);
						System.out.println(thing[1]+" : "+장르ID);
						System.out.println(thing[2]+" : "+국가ID);
						System.out.println(thing[3]+" : "+등급ID);
						System.out.println(thing[4]+" : "+개봉년도);
						System.out.println(thing[5]+" : "+평점);
						System.out.println(thing[6]+" : "+관객수);
						System.out.println(thing[7]+" : "+감독);
						System.out.println(thing[8]+" : "+주연배우+"\n");
					}
					break;
				case 3 :
					System.out.print("삭제할 열의 ID 번호를 입력하시오 : ");
					int delnum = sc.nextInt();
					String del = "DELETE FROM `mrp`.`main_tb` WHERE (`ID` = '열번호');";
					String ch = Integer.toString(delnum);
					String newdel = del.replace("열번호",ch);
					qt.executeUpdate(newdel);
					String reset = "SET @cnt = 0;";
					String reset1 = "UPDATE main_tb SET ID = @cnt:= @cnt+1; ";
					String reset2 = "ALTER TABLE main_tb AUTO_INCREMENT = 1;";
					qt.executeUpdate(reset);
					qt.executeUpdate(reset1);
					qt.executeUpdate(reset2);
					System.out.println("삭제 완료!");
					break;
				case 99 :
					System.out.print("생성할 테이블의 이름을 입력하시오 : ");
					String tb_name = sc.next();
					String create = "CREATE TABLE `mrp`.`테이블명` (`ID` INT NOT NULL AUTO_INCREMENT,`영화제목` VARCHAR(45) NOT NULL,"
							+ "`장르ID` INT NOT NULL,`국가ID` INT NOT NULL,`등급ID` INT NOT NULL,`개봉년도` VARCHAR(45) NOT NULL,`평점` VARCHAR(45) NOT NULL,"
							+ "`관객수` VARCHAR(45) NOT NULL,`감독` VARCHAR(45) NOT NULL,`주연배우` VARCHAR(45) NOT NULL, PRIMARY KEY (`ID`));";
					String newtb = create.replace("테이블명", tb_name);
					qt.execute(newtb);
					System.out.println("테이블 생성 완료!");
					break;					
				case 0 :
					System.out.println("시스템을 종료합니다...");
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