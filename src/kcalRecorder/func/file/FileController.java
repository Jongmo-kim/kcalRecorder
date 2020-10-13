package kcalRecorder.func.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import kcalRecorder.model.vo.Food;
import kcalRecorder.model.vo.Foods;
import kcalRecorder.model.vo.Meal;

public class FileController {
	private String filePath;
	private String fileName;
	
	public FileController(String filePath,String fileName) {
		this.fileName = fileName;
		this.filePath = filePath;
	}
	public boolean saveFile(ArrayList<Meal> list,ArrayList<Food> foodList,ArrayList<Foods> foodsList) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		boolean check = true;
		try {
			fos = new FileOutputStream(fileName);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(list);
			oos.writeObject(foodList);
			oos.writeObject(foodsList);
		} catch (IOException e) {
			e.printStackTrace();
			check = !check;
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Meal> readMealList(){
		ArrayList<Meal> list= null;
		ArrayList<Foods> foodsList = null;
		ArrayList<Food> foodList = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			list = (ArrayList<Meal>)ois.readObject();
			foodsList = (ArrayList<Foods>)ois.readObject();
			foodList = (ArrayList<Food>)ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList<Food> readFoodList(){
		ArrayList<Food> list= null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			list = (ArrayList<Food>)ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public ArrayList<Foods> readFoodsList(){
		ArrayList<Foods> foodsList = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		
		try {
			fis = new FileInputStream(fileName);
			ois = new ObjectInputStream(fis);
			foodsList = (ArrayList<Foods>)ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return foodsList;
	}


	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
