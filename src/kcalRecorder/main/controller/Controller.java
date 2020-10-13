package kcalRecorder.main.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import common.JDBCTemplate;
import kcalRecorder.func.file.FileController;
import kcalRecorder.model.vo.Food;
import kcalRecorder.model.vo.Foods;
import kcalRecorder.model.vo.Meal;
import kcalRecorder.model.vo.User;
import kcalRecorder.view.MainFrame;
import kcalRecorder.view.MenuBar;
import kcalRecorder.view.ShowMealFrame;
import kcalRecorder.view.AddMealFrame;
import kcalRecorder.view.AddMealFrame.*;
import kcalRecorder.view.LoginFrame;
import kcalRecorder.view.ShowMealFrame.*;
import kcalRecorder.view.SignUpFrame;
import kcalRecorder.model.dao.Dao;

public class Controller {
	
	private MainFrame mainFrame;
	private kcalRecorder.view.AddMealFrame AddMealFrameInstance;
	private kcalRecorder.view.ShowMealFrame ShowMealFrameInstance;
	addMealFrameConfirmButton addMealFrameConfirmButton;
	JButton consonantSearchButton;
	JButton nameSearchButton;
	JButton dateSearchButton;
	JButton loginFrameLoginButton;
	JButton signUpFrameSignUpButton;
	MenuBar menuBar;
	FileController fileController;
	String filePath, fileName;
	User loggedInUser;
	
	LoginFrame loginFrame;
	SignUpFrame signUpFrame;
	Dao dao;
	ArrayList<Meal> mealList;
	ArrayList<Food> foodList;
	ArrayList<Foods> foodsList;
	private int FOOD_COUNT_FOR_NO = 1;
	private int MEAL_COUNT_FOR_NO = 1;
	public Controller() {
		foodList = new ArrayList<Food>();
		foodsList = new ArrayList<Foods>();
		mealList = new ArrayList<Meal>();
		fileName = "data.dat";
		loggedInUser = null;
		
		fileController = new FileController(filePath, fileName);
		
		dao = new Dao();
		setTestValues();
	}
	public boolean inputNewFoodAndCheckNested(Food food) {
		if(isFoodNoUnseted(food)) {
			if(!isNestedFood(food)) {
				insertFoodList(food);
				return true;
			}
		}
		return false;
	}
	
	private void insertFoodList(Food food) {
		food.setF_no(FOOD_COUNT_FOR_NO++);
		foodList.add(food);
	}
	private boolean isFoodNoUnseted(Food food) {
		return food.getF_no()==-1;
	}
	public void setTestValues() {
		Calendar calendar = Calendar.getInstance();

		inputNewFoodAndCheckNested(new Food(500, "제육볶음"));
		inputNewFoodAndCheckNested(new Food(200, "소시지 야채볶음"));
		inputNewFoodAndCheckNested(new Food(300, "공기밥"));
		inputNewFoodAndCheckNested(new Food(250, "소고기 무국"));
		inputNewFoodAndCheckNested(new Food(100, "미역줄기 볶음"));
		Meal meal1 = inputNewMealAndInsertArr();
		inputNewFoodsAndInsertArr(meal1.getmNo(),1,"제육볶음");
		inputNewFoodsAndInsertArr(meal1.getmNo(),1,"소시지 야채볶음");
		inputNewFoodsAndInsertArr(meal1.getmNo(),1,"공기밥");
		inputNewFoodsAndInsertArr(meal1.getmNo(),1,"소고기 무국");
		inputNewFoodsAndInsertArr(meal1.getmNo(),1,"미역줄기 볶음");
		
		inputNewFoodAndCheckNested(new Food(650, "돈까스"));
		inputNewFoodAndCheckNested(new Food(200,  "마카로니 샐러드"));
		inputNewFoodAndCheckNested(new Food(250, "카레"));
		inputNewFoodAndCheckNested(new Food(200, "콩나물 무침"));
		Meal meal2 = inputNewMealAndInsertArr();
		inputNewFoodsAndInsertArr(meal2.getmNo(),1, "돈까스");
		inputNewFoodsAndInsertArr(meal2.getmNo(),1, "마카로니 샐러드");
		inputNewFoodsAndInsertArr(meal2.getmNo(),1, "카레");
		inputNewFoodsAndInsertArr(meal2.getmNo(),1, "콩나물 무침");
		inputNewFoodsAndInsertArr(meal2.getmNo(),1, "공기밥");
		
		calendar.set(Calendar.YEAR, 2019);
		calendar.set(Calendar.MONTH, 3);
		calendar.set(Calendar.DAY_OF_MONTH, 20);

		Date date2 = new Date(calendar.getTimeInMillis());
		meal2.setDate(date2);
		
		
		inputNewFoodAndCheckNested(new Food(700, "카레 돈까스"));
		inputNewFoodAndCheckNested(new Food(200,  "두부 부침"));
		inputNewFoodAndCheckNested(new Food(250,  "된장찌개"));
		inputNewFoodAndCheckNested(new Food(300,  "조기구이"));
		inputNewFoodAndCheckNested(new Food(300, "공기밥"));
		Meal meal3 = inputNewMealAndInsertArr();
		inputNewFoodsAndInsertArr(meal3.getmNo(), 1, "카레 돈까스");
		inputNewFoodsAndInsertArr(meal3.getmNo(),1,"두부 부침");
		inputNewFoodsAndInsertArr(meal3.getmNo(),0.5,"된장찌개");
		inputNewFoodsAndInsertArr(meal3.getmNo(),1,"조기구이");
		inputNewFoodsAndInsertArr(meal3.getmNo(),1,"공기밥");
		calendar.set(Calendar.YEAR, 2017);
		calendar.set(Calendar.MONTH, 7);
		calendar.set(Calendar.DAY_OF_MONTH, 2);
		Date date3 = new Date(calendar.getTimeInMillis());
		meal3.setDate(date3);
	}
	private Foods inputNewFoodsAndInsertArr(int mNo, double amount, String foodName) {
		Foods f = new Foods();
		f.setmNo(mNo);
		f.setAmount(amount);
		Food findFood = findFoodByName(foodName);
		if(findFood != null) {
			int fNo = findFood.getF_no();
			f.setfNo(fNo);
		}
		foodsList.add(f);
		return f;
	}
	private Food findFoodByName(String foodName) {
		for(Food food : foodList) {
			if(food.getName().equals(foodName))
				return food;
		}
		return null;
	}
	
	private Meal inputNewMealAndInsertArr() {
		Meal m = new Meal();
		m.setmNo(MEAL_COUNT_FOR_NO++);
		if(isLoggedIn()) {
			m.setuNo(loggedInUser.getuNo());
		} else {
			m.setuNo(-1);
		}
		m.setDate(new Date());
		mealList.add(m);
		return m;
	}
	public void main() {
		mainFrame = new MainFrame("temp");
		setMenuBar();
		setMenuBarActionListener();
		setMainFrameActionListener();

	}

	private void setMenuBar() {
		menuBar = new MenuBar();
		mainFrame.setJMenuBar(menuBar.getJMenuBar());
	}

	private void setMenuBarActionListener() {
		JMenuItem save = menuBar.getMenuFileSave();
		JMenuItem load = menuBar.getMenuFileLoad();
		JMenuItem serverSave = menuBar.getMenuServerSave();
		JMenuItem serverLoad = menuBar.getMenuServerLoad();
		JMenuItem login = menuBar.getMenuLogin();
		JMenuItem signUp = menuBar.getMenuSignUp();

		signUp.addActionListener(actionListenerMenuSignUp());
		save.addActionListener(actionListenerMenuFileSave());
		load.addActionListener(actionListenerMenuFileLoad());
		serverSave.addActionListener(actionListenerMenuServerSave());
		serverLoad.addActionListener(actionListenerMenuServerLoad());
		login.addActionListener(actionListenerMenuLogin());
	}

	public void setMainFrameActionListener() {
		JButton addMealButton = mainFrame.getAddMeal();
		addMealButton.addActionListener(actionListenerAddMeal());

		JButton showMealButton = mainFrame.getShowMeal();
		showMealButton.addActionListener(actionListenerShowMeal());
	}

	public ActionListener actionListenerMenuSignUp() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUpFrame = new SignUpFrame();
				signUpFrameSignUpButton = signUpFrame.getLoginButton();
				signUpFrameSignUpButton.addActionListener(actionListenerSignUpButton());
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerSignUpButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (signUp() > 0) {
					JOptionPane.showMessageDialog(mainFrame, "회원가입 성공");
					signUpFrame.setInvisible();
				} else {
					JOptionPane.showMessageDialog(mainFrame, "회원가입 실패");
				}

			}
		};
		return actionListener;
	}

	private int signUp() {
		JTextField idField = signUpFrame.getIdTextField();
		JTextField pwField = signUpFrame.getPwTextField();
		JTextField nickField = signUpFrame.getNickTextField();
		String id = idField.getText();
		String pw = pwField.getText();
		String nick = nickField.getText();
		idField.setText("");
		pwField.setText("");
		nickField.setText("");
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.signUpUser(conn, id, pw, nick);
		JDBCTemplate.close(conn);
		return result;
	}

	public ActionListener actionListenerMenuLogin() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginFrame = new LoginFrame();
				loginFrameLoginButton = loginFrame.getLoginButton();
				loginFrameLoginButton.addActionListener(actionListenerLoginButton());
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerLoginButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginUser();
				if (isLoggedIn()) {
					JOptionPane.showMessageDialog(mainFrame, "로그인 성공");
					loginFrame.setInvisible();
				} else {
					JOptionPane.showMessageDialog(mainFrame, "로그인 실패");
				}
			}

		};
		return actionListener;
	}

	private boolean isLoggedIn() {
		if (loggedInUser == null)
			return false;
		return true;
	}

	private void loginUser() {
		JTextField idField = loginFrame.getIdTextField();
		JTextField pwField = loginFrame.getPwTextField();
		String id = idField.getText();
		String pw = pwField.getText();
		idField.setText("");
		pwField.setText("");
		Connection conn = JDBCTemplate.getConnection();
		loggedInUser = dao.loginUser(conn, id, pw);
		JDBCTemplate.close(conn);
	}

	public ActionListener actionListenerMenuFileSave() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileController.saveFile(mealList,foodList,foodsList)) {
					JOptionPane.showMessageDialog(mainFrame, "저장되었습니다.");
				} else {
					JOptionPane.showMessageDialog(mainFrame, "저장 실패!");
				}
			}
		};
		return actionListener;
	}
	private boolean setArraymealListFoodFoodsFromFile() {
		ArrayList<Food> foodList = null;
		ArrayList<Foods> foodsList = null;
		ArrayList<Meal> mealList = null;
		
		foodList = fileController.readFoodList();
		foodsList = fileController.readFoodsList();
		mealList = fileController.readMealList();
		if(foodList == null || foodsList == null || mealList == null) {
			return false;
		} else {
			this.foodList = foodList;
			this.foodsList = foodsList;
			this.mealList = mealList;
		}
		return true;
	}
	public ActionListener actionListenerMenuFileLoad() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setArraymealListFoodFoodsFromFile()) {
					JOptionPane.showMessageDialog(mainFrame, "불러오기 실패!");
				} else {
					JOptionPane.showMessageDialog(mainFrame, "불러오기 성공!");
				}
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerMenuServerSave() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isLoggedIn()) {
					if (serverSave() > 0) {
						JOptionPane.showMessageDialog(mainFrame, "저장 성공");
					} else {
						JOptionPane.showMessageDialog(mainFrame, "저장 실패");
					}
				} else {
					JOptionPane.showMessageDialog(mainFrame, "로그인이 되지 않았습니다.");
				}

			}
		};
		return actionListener;
	}

	private int serverSave() {
		Connection conn = JDBCTemplate.getConnection();
		
		int foodResult = dao.insertMultipleFood(conn, foodList);
		for(Food food : foodList) {
			System.out.println(food.getF_no());
		}
		int mealResult = dao.insertMultipleMeal(conn, mealList, loggedInUser);
		int foodsResult = dao.insertMultipleFoods(conn, mealList, loggedInUser);

		
		if (foodResult == 0 || mealResult == 0 || foodsResult == 0) {
			commitOrRollback(conn, 0);
		} else {
			commitOrRollback(conn, foodsResult);
		}
		JDBCTemplate.close(conn);
		return foodResult;
	}

	private void commitOrRollback(Connection conn, int result) {
		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
	}


	private boolean isNestedFood(Food ori) {
		for (Food des : foodList) {
			if (ori.getName().equals(des.getName())) {
				return true;
			}
		}
		return false;
	}

	public ActionListener actionListenerMenuServerLoad() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerMenuExit() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerAddMeal() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setInvisible();
				AddMealFrameInstance = new AddMealFrame();

				JButton confirmButton = AddMealFrameInstance.getAddMealFrameConfirmButton();
				confirmButton.addActionListener(actionListenerAddMealConfirmButton());

			}
		};
		return actionListener;
	}

	public ActionListener actionListenerShowMeal() {

		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setInvisible();
				//TODO RIGHT NOW
				ShowMealFrameInstance = new ShowMealFrame("temp", foodList, foodsList, mealList);
				ShowMealFrameInstance.getShowMealConfirmButton().addActionListener(actionListenerShowMealReturnMain());
				
				consonantSearchButton = ShowMealFrameInstance.getConsonantSearchButton();
				consonantSearchButton.addActionListener(actionListenerConsonantSearchButton());

				nameSearchButton = ShowMealFrameInstance.getNameSearchButton();
				nameSearchButton.addActionListener(actionListenerNameSearchButton());

				dateSearchButton = ShowMealFrameInstance.getDateSearchButton();
				dateSearchButton.addActionListener(actionListenerDateSearchButton());

			}
		};
		return actionListener;
	}

	public ActionListener actionListenerAddMealConfirmButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.setVisible();
				AddMealFrameInstance.setInvisible();
				insertFoodAndFoodsFromAddMealFrame();
			}
		};
		return actionListener;
	}
	private void insertFoodAndFoodsFromAddMealFrame() {
		Meal meal = inputNewMealAndInsertArr();
		int mNo = meal.getmNo();
		
		ArrayList<String> foodNames = AddMealFrameInstance.getListFoodName();
		ArrayList<Double> amounts = AddMealFrameInstance.getListAmount();
		ArrayList<Integer> kcals = AddMealFrameInstance.getListKcal();
		
		for(int i = 0 ;i < foodNames.size(); ++i) {
			String name = foodNames.get(i);
			Double amount = amounts.get(i);
			int kcal = kcals.get(i);
			
			inputNewFoodAndCheckNested(new Food(kcal, name));
			inputNewFoodsAndInsertArr(mNo, amount, name);
		}
	}
	public ActionListener actionListenerShowMealReturnMain() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShowMealFrameInstance.setInvisible();
				mainFrame.setVisible();
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerConsonantSearchButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		};
		return actionListener;
	}

	public ActionListener actionListenerNameSearchButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFoodListWithSearchedName();
			}
		};
		return actionListener;
	}

	public void updateFoodListWithSearchedName() {
		JTextField textField = ShowMealFrameInstance.getNameSearchTextField();
		String valueBeIncluded = textField.getText();
		textField.setText("");
		ArrayList<Meal> mealListForReturn = new ArrayList<Meal>();
		ArrayList<Foods> foodsListForReturn = new ArrayList<Foods>();
		ArrayList<Food> foodListForReturn = new ArrayList<Food>();
		
//		1. 현재까지 저장된 음식 이름중에 입력된 값이랑 같은게 있는지 먼저 조회
		for(Food f : foodList) {
			if(f.getName().contentEquals(valueBeIncluded)) {
//				있다면 앞서 할당한 어레이 리스트에 삽입
				foodListForReturn.add(f);
			}
		}
//		2. 찾아낸 음식들을 기준으로 해당 음식이 Foods에 있는지 검색후 있다면 삽입
		for(Food food : foodListForReturn) {
			for(Foods f : foodsList) {
				String foodName = getFoodNameFromFoodsByFNo(f.getfNo());
				if(foodName.equals(food.getName())) {
					foodsListForReturn.add(f);
				}
			}
		}
//		3. foods들중 포함된 mNo를 찾아 맞으면 삽입
		for(Foods foods : foodsListForReturn) {
			for(Meal meal : mealList) {
				if(foods.getmNo() == meal.getmNo()) {
					mealListForReturn.add(meal);
				}
			}
		}
		
		ShowMealFrameInstance.updateFoodListTextAreaByController(mealListForReturn,foodsListForReturn,foodListForReturn);
	}
	public String getFoodNameFromFoodsByFNo(int fNo) {
		Food f = foodList.get(fNo-1);
		return f.getName();
	}
	public String getFoodNameFromFoods(Foods foods) {
		String name = "";
		int fNo = foods.getfNo();
		Food f = foodList.get(fNo-1);
		name = f.getName();
		return name;
	}
	public ActionListener actionListenerDateSearchButton() {
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFoodListWithSearchedDate();
			}
		};
		return actionListener;
	}

	public void updateFoodListWithSearchedDate() {
		showMealDateSearchPanel tempPanel = ShowMealFrameInstance.getShowMealDateSearchPanel();
		Date date = tempPanel.getDateFromTextField();
		tempPanel.clearTimeFields();
		ArrayList<Meal> mealListForReturn = new ArrayList<Meal>();
		ArrayList<Foods> foodsListForReturn = new ArrayList<Foods>();
		ArrayList<Food> foodListForReturn = new ArrayList<Food>();
		System.out.println(date);
		
//		시간과 맞는 일자 검색후 있으면 meal 삽입
		for(Meal meal : mealList) {
			Date ori = meal.getDate();
			if(isEqualDateInYearMonthDay(ori, date)) {
				mealListForReturn.add(meal);
			}
		}
//		삽입된 meal코드를 바탕으로 foods와 그와 연계된 food삽입
		for(Meal meal : mealListForReturn) {
			int mNo = meal.getmNo();
			
			for(Foods foods : foodsList) {
				if(mNo == foods.getmNo()) {
					foodsListForReturn.add(foods);
				}
			}
		}
		for(Foods foods : foodsListForReturn) {
			int fNo = foods.getfNo();
			System.out.println(foodList.get(fNo-1).getName());
			foodListForReturn.add(foodList.get(fNo-1));
		}
		ShowMealFrameInstance.updateFoodListTextAreaByController(mealListForReturn,foodsListForReturn,foodListForReturn);

	}

	public boolean isEqualDateInYearMonthDay(Date d1, Date d2) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d1);
		int firstYear = calendar.get(Calendar.YEAR);
		int firstMonth = calendar.get(Calendar.MONTH);
		int firstDay = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.setTime(d2);
		int secondYear = calendar.get(Calendar.YEAR);
		int secondMonth = calendar.get(Calendar.MONTH);
		int secondDay = calendar.get(Calendar.DAY_OF_MONTH);

		if (firstYear == secondYear && firstMonth == secondMonth && firstDay == secondDay) {
			return true;
		}
		return false;
	}
}