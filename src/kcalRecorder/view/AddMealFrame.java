package kcalRecorder.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import kcalRecorder.model.vo.Food;
import kcalRecorder.model.vo.Foods;
import kcalRecorder.model.vo.Meal;


public class AddMealFrame extends JFrame {
	nameInputPanel nameInputPanel;
	kcalPer100GramInputPanel kcalPer100GramInputPanel;
	sizeInputPanel sizeInputPanel;
	addedFoodWithScrollBar addedFoodWithScrollBar;
	addedFoodMainPanel addedFoodMainPanel;
	addMealFrameAddButton addMealFrameAddButton;
	addMealFrameConfirmButton addMealFrameConfirmButton;
	ArrayList<String> listFoodName;
	ArrayList<Double> listAmount;
	ArrayList<Integer> listKcal;
	
	public addMealFrameConfirmButton getAddMealFrameConfirmButton() {
		return addMealFrameConfirmButton;
	}


	public AddMealFrame() {
		listFoodName = new ArrayList<String>();
		listAmount = new ArrayList<Double>();
		listKcal = new ArrayList<Integer>();
		setDefaultOptions();
		setGridBagConstraintsLayout();
	}

	public ArrayList<String> getListFoodName() {
		return listFoodName;
	}
	public ArrayList<Double> getListAmount() {
		return listAmount;
	}
	public ArrayList<Integer> getListKcal() {
		return listKcal;
	}


	private void setGridBagConstraintsLayout() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		addPanel ap = new addPanel();
		add(ap, gbc);

		addedFoodMainPanel = new addedFoodMainPanel();
		// addedPanel.setPreferredSize(new Dimension(200,150));
		gbc.weightx = 0.6;
		gbc.weighty = 1;
		add(addedFoodMainPanel, gbc);
	}

	private void setDefaultOptions() {
		setSize(700, 400);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
	}

	public class addPanel extends JPanel {
		public addPanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(new JLabel("먹을것 추가하기"));
			add(nameInputPanel = new nameInputPanel());
			add(kcalPer100GramInputPanel = new kcalPer100GramInputPanel());
			add(sizeInputPanel = new sizeInputPanel());
			JPanel tempPanel = new JPanel();
			tempPanel.add(addMealFrameAddButton = new addMealFrameAddButton("추가"));
			
			tempPanel.add(addMealFrameConfirmButton = new addMealFrameConfirmButton("완료"));
			add(tempPanel);

		}
	}

	public class addMealFrameAddButton extends JButton {
		addMealFrameAddButton(String s) {
			super(s);
			addActionListener(addMealFrameAddButtonActionListener());
		}

		public ActionListener addMealFrameAddButtonActionListener() {
			ActionListener e = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addFoodFromTextField();
					addedFoodWithScrollBar.updateFood();
				}

			};
			return e;
		}
	}

	public void addFoodFromTextField() {
		String name = nameInputPanel.getText();
		String kcalPerGram = kcalPer100GramInputPanel.getText();
		String size = sizeInputPanel.getText();
		int kcal = Integer.parseInt(kcalPerGram);
		double size_ = Double.parseDouble(size);
		listFoodName.add(name);
		listAmount.add(size_);
		listKcal.add(kcal);
		
	}

	public class addedFoodPanel extends JPanel {
		public addedFoodPanel(String name, double amount, int kcal) {
			add(new addedFoodNamePanel(name));
			add(new addedFoodSizePanel(amount));
			add(new addedFoodKcalPanel(kcal));
		}
	}

	public class addedFoodNamePanel extends JPanel {
		public addedFoodNamePanel(String name) {
			JLabel indicator = new JLabel("음식 이름 :");
			JLabel name_ = new JLabel(name);
			add(indicator);
			add(name_);
		}
	}

	public class addedFoodSizePanel extends JPanel {
		public addedFoodSizePanel(double size) {
			JLabel indicator = new JLabel("몇 인분 :");
			JLabel size_ = new JLabel(Double.toString(size));
			add(indicator);
			add(size_);
		}
	}

	public class addedFoodKcalPanel extends JPanel {
		public addedFoodKcalPanel(int Kcal) {
			JLabel indicator = new JLabel("음식 칼로리 :");
			JLabel Kcal_ = new JLabel(Integer.toString(Kcal));
			add(indicator);
			add(Kcal_);
		}
	}
	static int PanelCount = 0;
	public class addedFoodWithScrollBar extends JPanel {
		public addedFoodWithScrollBar() {
			super();
			setAutoscrolls(true);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		}
		
		public void updateFood() {
		
				String name = listFoodName.get(PanelCount);
				Double amount = listAmount.get(PanelCount);
				int kcal = listKcal.get(PanelCount++);
				addedFoodPanel e = new addedFoodPanel(name, amount, kcal);
				add(e);
			
			super.updateUI();
		}
	}

	public class addMealFrameConfirmButton extends JButton {
		addMealFrameConfirmButton(String s) {
			super(s);
			addActionListener(confirmButtonActionListener());
		}

		public ActionListener confirmButtonActionListener() {
			ActionListener e = new ActionListener() {
				public void actionPerformed(ActionEvent e) {

				}

			};
			return null;
		}
	}

	public class nameInputPanel extends JPanel {
		JLabel nameInputLabel;
		JTextField nameInputField;

		nameInputPanel() {
			add(nameInputLabel = new JLabel("* Name\t"));
			add(nameInputField = new JTextField(10));
		}

		public JLabel getLabel() {
			return nameInputLabel;
		}

		public String getText() {
			String s = nameInputField.getText();
			nameInputField.setText("");
			return s;
		}
	}

	public class kcalPer100GramInputPanel extends JPanel {
		JLabel kcalPer100GramLabel;
		JTextField kcalPer100GramField;

		kcalPer100GramInputPanel() {
			add(kcalPer100GramLabel = new JLabel("* Kcal Per 100 Gram\t"));
			add(kcalPer100GramField = new JTextField(10));
		}

		public JLabel getLabel() {
			return kcalPer100GramLabel;
		}

		public String getText() {
			String s = kcalPer100GramField.getText();
			kcalPer100GramField.setText("");
			return s;
		}

	}

	public class sizeInputPanel extends JPanel {
		JLabel sizeLabel;
		JTextField sizeField;

		sizeInputPanel() {
			add(sizeLabel = new JLabel("* Size \t"));
			add(sizeField = new JTextField(10));
			sizeField.addActionListener(addButtonActionListener());
		}

		public JLabel getSizeLabel() {
			return sizeLabel;
		}

		public String getText() {
			String s = sizeField.getText();
			sizeField.setText("");
			return s;
		}

		public ActionListener addButtonActionListener() {
			ActionListener e = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addFoodFromTextField();
					addedFoodWithScrollBar.updateFood();

				}

			};
			return e;
		}
	}

	public class addedFoodMainPanel extends JPanel {
		public addedFoodMainPanel() {
			setBorder(BorderFactory.createLineBorder(Color.black));
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			add(new JLabel("* 먹은 음식들"));
			add(addedFoodWithScrollBar = new addedFoodWithScrollBar());
		}
	}
	public void setInvisible() {
		setVisible(false);
		setEnabled(false);
		PanelCount = 0;
	}
	public void setVisible() {
		setVisible(true);
		setEnabled(true);
	}
}
