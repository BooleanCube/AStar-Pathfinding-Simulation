import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSlider;

public class ControlHandler {
	private final Frame frame;
	private final JLabel modeText, speedT, speedC, openT,
	closedT, pathT, openC, closedC, pathC, noPathT;
	private final JCheckBox showStepsCheck, diagonalCheck;
	private final JSlider speed;
	private final JButton run, clear;
	private final ArrayList<JLabel> labels;
	private final ArrayList<JCheckBox> checks;
	private final ArrayList<JSlider> sliders;
	private final ArrayList<JButton> buttons;
	Dimension npD;
	
	public ControlHandler(Frame frame) {
		this.frame = frame;
		labels = new ArrayList<>();
		checks = new ArrayList<>();
		sliders = new ArrayList<>();
		buttons = new ArrayList<>();
		
		modeText = new JLabel("Mode: ");
		modeText.setName("modeText");
		modeText.setFont(style.bigText);
		modeText.setForeground(style.darkText);
		modeText.setVisible(true);
		
		speedT = new JLabel("Speed: ");
		speedT.setName("speedT");
		speedT.setFont(style.numbers);
		speedT.setVisible(true);
		
		speedC = new JLabel("50");
		speedC.setName("speedC");
		speedC.setFont(style.numbers);
		speedC.setVisible(true);
		
		openT = new JLabel("Open");
		openT.setName("openT");
		openT.setFont(style.numbers);
		openT.setVisible(true);
		
		openC = new JLabel("0");
		openC.setName("openC");
		openC.setFont(style.numbers);
		openC.setVisible(true);
		
		closedT = new JLabel("Closed");
		closedT.setName("closedT");
		closedT.setFont(style.numbers);
		closedT.setVisible(true);
		
		closedC = new JLabel("0");
		closedC.setName("closedC");
		closedC.setFont(style.numbers);
		closedC.setVisible(true);
		
		pathT = new JLabel("Path");
		pathT.setName("pathT");
		pathT.setFont(style.numbers);
		pathT.setVisible(true);
		
		pathC = new JLabel("0");
		pathC.setName("pathC");
		pathC.setFont(style.numbers);
		pathC.setVisible(true);
		
		noPathT = new JLabel("NO PATH");
		noPathT.setName("noPathT");
		noPathT.setForeground(Color.white);
		noPathT.setFont(style.REALBIGText);
		npD = noPathT.getPreferredSize();
		
		labels.add(modeText);
		labels.add(speedT);
		labels.add(speedC);
		labels.add(openT);
		labels.add(openC);
		labels.add(closedT);
		labels.add(closedC);
		labels.add(pathT);
		labels.add(pathC);
		labels.add(noPathT);
		
		showStepsCheck = new JCheckBox();
		showStepsCheck.setText("showSteps");
		showStepsCheck.setName("showStepsCheck");
		showStepsCheck.setSelected(true);
		showStepsCheck.setOpaque(false);
		showStepsCheck.setFocusable(false);
		showStepsCheck.setVisible(true);
		
		diagonalCheck = new JCheckBox();
		diagonalCheck.setText("Diagonal");
		diagonalCheck.setName("diagonalCheck");
		diagonalCheck.setOpaque(false);
		diagonalCheck.setSelected(true);
		diagonalCheck.setFocusable(false);
		diagonalCheck.setVisible(true);
		
		checks.add(showStepsCheck);
		checks.add(diagonalCheck);
		
		speed = new JSlider();
		speed.setName("speed");
		speed.setOpaque(false);
		speed.setVisible(true);
		speed.setFocusable(false);
		speed.addChangeListener(e -> {
			JSlider source = (JSlider) e.getSource();
			speed.setValue(source.getValue());
			frame.setSpeed();
			frame.repaint();
		});
		
		sliders.add(speed);
		
		run = new JButton();
		run.setText("run");
		run.setName("run");
		run.setFocusable(false);
		run.addActionListener(frame);
		run.setMargin(new Insets(0,0,0,0));
		run.setVisible(true);

		clear = new JButton();
		clear.setText("clear");
		clear.setName("clear");
		clear.setFocusable(false);
		clear.addActionListener(frame);
		clear.setMargin(new Insets(0,0,0,0));
		clear.setVisible(true);

		buttons.add(run);
		buttons.add(clear);
	}
	
	public JLabel getL(String t) {
		for(JLabel label : labels) if(label.getName().equals(t)) return label;
		return null;
	}

	public JCheckBox getC(String t) {
		for(JCheckBox check : checks) if(check.getName().equals(t)) return check;
		return null;
	}

	public JSlider getS(String t) {
		for(JSlider slider : sliders) if(slider.getName().equals(t)) return slider;
		return null;
	}

	public JButton getB(String t) {
		for(JButton button : buttons) if(button.getName().equals(t)) return button;
		return null;
	}
	
	public void noPathTBounds() {
		noPathT.setBounds((int)((frame.getWidth()/2)-(npD.getWidth()/2)), (frame.getHeight()/2)-70, (int)npD.getWidth(), (int)npD.getHeight());
	}
	
	public void position() {
		speedT.setBounds(180, frame.getHeight()-88, 60, 20);
		speedC.setBounds(224, frame.getHeight()-88, 60, 20);
		openT.setBounds(254, frame.getHeight()-92, 60, 20);
		openC.setBounds(300, frame.getHeight()-92, 60, 20);
		closedT.setBounds(254, frame.getHeight()-76, 60, 20);
		closedC.setBounds(300, frame.getHeight()-76, 60, 20);
		pathT.setBounds(254, frame.getHeight()-60, 60, 20);
		pathC.setBounds(300, frame.getHeight()-60, 60, 20);
		Dimension size = modeText.getPreferredSize();
		modeText.setBounds(20, frame.getHeight() - 39, size.width, size.height);

		showStepsCheck.setBounds(20, frame.getHeight()-88, 90, 20);
		diagonalCheck.setBounds(20, frame.getHeight()-64, 90, 20);

		speed.setBounds(178, frame.getHeight()-63, 68, 20);

		run.setBounds(116, frame.getHeight()-88, 52, 22);
		clear.setBounds(116, frame.getHeight()-64, 52, 22);
	}

	public void hoverColor() {
		modeText.setForeground(style.lightText);
		showStepsCheck.setForeground(style.lightText);
		diagonalCheck.setForeground(style.lightText);
		speed.setForeground(style.lightText);
		speedT.setForeground(style.lightText);
		speedC.setForeground(style.lightText);
		openT.setForeground(style.lightText);
		openC.setForeground(style.lightText);
		closedT.setForeground(style.lightText);
		closedC.setForeground(style.lightText);
		pathT.setForeground(style.lightText);
		pathC.setForeground(style.lightText);
	}

	public void nonHoverColor() {
		modeText.setForeground(style.darkText);
		showStepsCheck.setForeground(style.darkText);
		diagonalCheck.setForeground(style.darkText);
		speed.setForeground(style.darkText);
		speedT.setForeground(style.darkText);
		speedC.setForeground(style.darkText);
		openT.setForeground(style.darkText);
		openC.setForeground(style.darkText);
		closedC.setForeground(style.darkText);
		closedT.setForeground(style.darkText);
		pathT.setForeground(style.darkText);
		pathC.setForeground(style.darkText);
	}

	public void addAll() {
		frame.add(showStepsCheck);
		frame.add(diagonalCheck);
		frame.add(run);
		frame.add(clear);
		frame.add(modeText);
		frame.add(openT);
		frame.add(openC);
		frame.add(closedT);
		frame.add(closedC);
		frame.add(pathT);
		frame.add(pathC);
		frame.add(speed);
		frame.add(speedT);
		frame.add(speedC);
	}
	
}
