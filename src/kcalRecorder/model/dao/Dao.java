package kcalRecorder.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import kcalRecorder.model.vo.Food;
import kcalRecorder.model.vo.Meal;
import kcalRecorder.model.vo.User;

/*
create SEQUENCE food_SEQ;
create sequence meal_seq;
create sequence meals_seq;
create SEQUENCE user__SEQ;
create SEQUENCE foods_SEQ;

 */
public class Dao {

	private User getUser(ResultSet rset) {
		User u = new User();
		try {
			u.setId(rset.getString("id"));
			u.setPw(rset.getString("pw"));
			u.setNickname(rset.getString("nickName"));
			u.setuNo(rset.getInt("u_code"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}

	public User loginUser(Connection conn, String id, String pw) {
		PreparedStatement pstmt = null;
		String sql = "select * from user_ where id = ? and pw = ?";
		ResultSet rset = null;
		User u = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				u = getUser(rset);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rset.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return u;
	}

	public int signUpUser(Connection conn, String id, String pw, String nick) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = "insert into User_ values(user__seq.nextval,?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, nick);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int insertFood(Connection conn,Food food) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "insert into food values(food_seq.nextval,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, food.getName());
			pstmt.setInt(2, food.getKcalPerOneHundred());
			result = pstmt.executeUpdate();
			if(result > 0) {
				int f_no = getFNumber(conn,pstmt);
				food.setF_no(f_no);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	private int getFNumber(Connection conn, PreparedStatement pstmt) {
		
		String sql = "select food_seq.currval from dual";
		int result = 0;
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt("currval");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
		}
		
		
		return result;
	}

	public int insertMultipleFood(Connection conn, ArrayList<Food> foodList) {
		int result =0;
		for(Food food : foodList) {
			result += insertFood(conn, food);
		}
		return result;
	}

	public int insertMultipleFoods(Connection conn, ArrayList<Meal> mealList,User u) {
		int result = 0 ;
		ArrayList<Food> foodList = null;
		for(Meal meal : mealList) {
			foodList = meal.getFoodArr();
			for(Food food : foodList) {
				insertFoods(conn,food,meal);
			}
			
		}
		return result;
	}
	public int insertFoods(Connection conn,Food food,Meal meal) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "insert into foods values(?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, meal.getM_no());
			pstmt.setInt(2, food.getF_no());
			pstmt.setDouble(3, food.getSize());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.printf("%d m_code\n",meal.getM_no());
			System.out.printf("%d f_code\n",food.getF_no());
			e.printStackTrace();
		} finally { 
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int insertMultipleMeal(Connection conn, ArrayList<Meal> mealArr,User u ) {
		int result = 0;
		for(Meal meal : mealArr) {
			result += insertMeal(conn, meal, u);
		}
		return result;
	}
	public int insertMeal(Connection conn, Meal meal,User u) {
		int result =0;
		PreparedStatement pstmt = null;
		String sql = "insert into meal values(meal_seq.nextval,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, meal.getSqlDate());
			pstmt.setInt(2,u.getNo());
			result = pstmt.executeUpdate();
			if(result > 0) {
				int m_no = getMNumber(conn, pstmt);
				meal.setM_no(m_no);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			 JDBCTemplate.close(pstmt);
		}
		return result;
	}
	private int getMNumber(Connection conn, PreparedStatement pstmt) {
		int result = 0;
		String sql = "select meal_seq.currval from dual";
		ResultSet rset = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt("currval");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
