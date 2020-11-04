import java.sql.*;	

public class mysqlt{
	public static void main(String[] args) {
		try {
			// 데이터베이스 연결 ( Connection )
			Class.forName("com.mysql.cj.jdbc.Driver");	 
			// .cj. 을 통해 클래스로드 오류 해결
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mrp?serverTimezone=UTC","root","fkdls324"); // ?serverTimezone=UTC 를 통해 serverTimezone 오류 해결
			// 쿼리 선택
			// 테이블 불러오기
			String qu = "select *from main_table";
			// java statement 생성
			Statement st = con.createStatement();
			// 쿼리 execute, 객체 형성
			ResultSet rs = st.executeQuery(qu);
			// 각 열을 반복적으로 나타냄
			System.out.println("ID\t영화제목\t\t\t장르\t\t제작국가\t관람등급\t개봉년도\t\t평점\t관객수\t\t감독\t주연 배우");
			while(rs.next()) {
				int id=rs.getInt("ID");	// _number 테이블의 값을 number 에 저장
				String movie_name = rs.getString("영화제목");	// name 테이블의 값을 name 에 저장
				String genre = rs.getString("장르");
				String country = rs.getString("제작국가");
				String grade = rs.getString("관람등급");
				String openyear = rs.getString("개봉년도");
				String score = rs.getString("평점");
				String watch_person = rs.getString("관객수");
				String producer = rs.getString("감독");
				String main_actor = rs.getString("주연 배우");
				
				System.out.format("%s\t%s\t\t\t%s\t\t%s\t%s\t%s\t\t%s\t%s\t\t%s\t%s \n",id,movie_name,genre,country,grade,openyear,score,watch_person,producer,main_actor);
			}
			st.close();
		}catch(Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
	}
}
